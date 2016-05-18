package emhs.db.util;

import emhs.db.components.UIComponent;
import javafx.scene.paint.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class AppWindow {
    protected JFrame frame;
    private JPanel panel;
    private UISurface appUI;
    private UIComponent frameComp;
    private Rectangle renderBounds;
    private boolean doRender;
    private boolean renderAll;
    private boolean fullscreen;
    private boolean keepMaximized;

    /*
TODO:
Foreach UIComponent:
- make all vars protected, all funcs public.
- all public ctors call protected ctors.
 */

    public AppWindow(final String title, final int width, final int height, final boolean exitOnClose) {
        frame = new JFrame(title);
        frame.setFocusable(true);
        frame.setLayout(new BorderLayout());
        frame.addNotify();

        renderBounds = new Rectangle();

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                if (doRender) {
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
                    appUI.prepareBounds();

                    Rectangle frameBorder = new Rectangle(frame.getSize());
                    if (renderAll) renderBounds = frameBorder;
                    else renderBounds = appUI.getBound(frameBorder).toRectangle();
                    renderBounds = renderBounds.intersection(frameBorder);

                    g.setClip(renderBounds.x, renderBounds.y, renderBounds.width, renderBounds.height); //TODO Test with compound clipping boundaries.
                    if ((renderBounds.width | renderBounds.height) > 0) appUI.draw((Graphics2D) g, renderBounds);

                    Toolkit.getDefaultToolkit().sync();
                    renderAll = false;
                }
            }

            public void update(Graphics g) {
            }
        };

        panel.setDoubleBuffered(true);
        panel.setIgnoreRepaint(true);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        frame.getContentPane().add(panel);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                if (exitOnClose) System.exit(0);
            }

            public void windowDeactivated(WindowEvent e) {
                if(keepMaximized) setFullscreen(fullscreen);
            }
        });

        frameComp = new UIComponent(null) {
            public boolean renderRequested() {
                return false;
            }

            public void setCursor(Cursor c) {
                frame.setCursor(c);
            }

            public void injectEvent(AWTEvent e) {
                AppWindow.this.injectEvent(e);
            }
        };

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                panel.setSize(e.getComponent().getSize());
                frameComp.setSize(panel.getWidth(), panel.getHeight());
                if (appUI != null) appUI.update(e, "WindowResized", null);
                renderAll = true;
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(width, height));

        frame.pack();
        frame.requestFocus();
    }

    public static FontData getFontDimensions(Font f, String s) {
        FontRenderContext frc = ((Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY).getGraphics()).getFontRenderContext();
        LineMetrics lm = f.getLineMetrics(s, frc);
        return new FontData((int) f.getStringBounds(s, frc).getWidth(), (int) lm.getHeight(), (int) lm.getAscent(), (int) lm.getDescent(), (int) lm.getLeading());
    }

    public void bindUI(UISurface surface) {
        appUI = surface;
        initCanvasListeners();
        appUI.setParent(frameComp);
        appUI.update(new ComponentEvent(panel, ComponentEvent.COMPONENT_RESIZED), "WindowResized", null);
    }

    public void beginRender() {
        if (appUI != null) {
            doRender = true;
            renderAll = true;
            panel.repaint();
            renderAll = false;

            java.util.Timer t = new java.util.Timer();

            t.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (doRender) {
                        appUI.updateSelf();
                        panel.repaint();
                    }
                }
            }, 8, 8);
        }
    }

    public void close() {
        frame.dispose();
    }

    public void injectEvent(AWTEvent event) {
        event.setSource(frame);
        frame.dispatchEvent(event);
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;

        frame.dispose();
        frame.setUndecorated(fullscreen);
        frame.setVisible(true);

        frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);

        renderAll = true;
    }

    public void setDecorated(boolean decorated) {
        frame.dispose();
        frame.setUndecorated(!decorated);
        frame.setVisible(true);
        frame.toFront();
    }

    public void keepMaximized(boolean maximized) {
        this.keepMaximized = maximized;
    }

    private void initCanvasListeners() {
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                appUI.update(e, "MousePressed", null);
            }

            public void mouseReleased(MouseEvent e) {
                appUI.update(e, "MouseReleased", null);
            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                appUI.update(e, "MouseDragged", null);
            }

            public void mouseMoved(MouseEvent e) {
                appUI.update(e, "MouseMoved", null);
            }
        });

        panel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                appUI.update(e, "MouseWheelMoved", null);
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                appUI.update(e, "KeyPressed", null);
            }

            public void keyReleased(KeyEvent e) {
                appUI.update(e, "KeyReleased", null);
            }
        });

        frame.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                renderAll = true;
                panel.repaint();
            }
        });
    }
}
