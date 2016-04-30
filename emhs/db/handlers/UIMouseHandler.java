package emhs.db.handlers;

import emhs.db.components.UIComponent;
import emhs.db.util.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public abstract class UIMouseHandler extends UIInteractionHandler {
    private boolean mouseOver;
    private boolean validPress;
    private int hoverDelay = 500;
    private int holdDelay = 500;
    private Point pmouse;
    private Timer holdTimer;
    private Timer hoverTimer;
    private MouseEvent lastMouseEvent;
    private boolean consumeEvent;

    protected UIMouseHandler(UIComponent parentComp) {
        super(parentComp);
        hoverTimer = new Timer(hoverDelay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mouseHovered();
                hoverTimer.stop();
            }
        });

        holdTimer = new Timer(holdDelay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mouseHeld();
                holdTimer.stop();
            }
        });


        pmouse = new Point();
    }

    public boolean update(AWTEvent e, String eventType) {
        MouseEvent me;

        if (e.getClass().equals(MouseEvent.class)) lastMouseEvent = (MouseEvent) e;

        consumeEvent = false;

        switch (eventType) {
            case "MouseMoved":
                me = (MouseEvent) e;
                mouseMoved(me);
                break;
            case "MouseDragged":
                me = (MouseEvent) e;
                mouseDragged(me);
                break;
            case "MousePressed":
                me = (MouseEvent) e;
                mousePressed(me);
                break;
            case "MouseReleased":
                me = (MouseEvent) e;
                mouseReleased(me);
                mouseClicked(me);
                validPress = false;
                break;
        }

        //TODO Add component attribute update handler code.

        return consumeEvent;
    }

    public boolean isPressed() {
        return validPress;
    }

    public boolean isSelected() {
        return mouseOver;
    }

    public UIMouseHandler setHoldDelay(int holdDelay) {
        this.holdDelay = holdDelay;
        return this;
    }

    public UIMouseHandler setHoverDelay(int hoverDelay) {
        this.hoverDelay = hoverDelay;
        return this;
    }

    private void mouseMoved(MouseEvent e) {
        hoverTimer.stop();

        Point origin = parent.getPos();
        Dimension size = parent.getSize();

        if (UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY())) {
            if (!mouseOver) {
                onMouseEnter();
                mouseOver = true;
            }

            hoverTimer.start();

            onMouseMove(e.getX(), e.getY());
            pmouse.x = e.getX();
            pmouse.y = e.getY();
        } else if (!UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY())) {
            if (mouseOver) {
                onMouseExit();
                mouseOver = false;
            }
        }
    }

    private void mouseDragged(MouseEvent e) {
        Point origin = parent.getPos();
        Dimension size = parent.getSize();

        if (isPressed()) onMouseDrag(e.getX(), e.getY(), e.getButton());

        if (UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY())) {
            if (!mouseOver) {
                mouseOver = true;
                onMouseEnter();
            }

            if (isPressed() && !holdTimer.isRunning()) holdTimer.start();

            pmouse.x = e.getX();
            pmouse.y = e.getY();
        } else if (!UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY())) {
            if (mouseOver) {
                holdTimer.stop();
                onMouseExit();
                mouseOver = false;
            }
        }
    }

    private void mousePressed(MouseEvent e) {
        Point origin = parent.getPos();
        Dimension size = parent.getSize();

        if (UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY())) {
            hoverTimer.stop();
            holdTimer.start();

            validPress = true;

            onMousePress(e.getX(), e.getY(), e.getButton());
        } else {
            onExternMousePress(e.getButton());
        }
    }

    private void mouseReleased(MouseEvent e) {
        Point origin = parent.getPos();
        Dimension size = parent.getSize();

        if (UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY()) || isPressed()) {
            holdTimer.stop();
            hoverTimer.start();

            onMouseRelease(e.getX(), e.getY(), e.getButton());
        } else {
            onExternMouseRelease(e.getButton());
        }
    }

    private void mouseClicked(MouseEvent e) {
        Point origin = parent.getPos();
        Dimension size = parent.getSize();

        if (validPress && UIUtilities.inBounds(origin.x, origin.y, size.width, size.height, e.getX(), e.getY())) {
            holdTimer.stop();
            hoverTimer.start();

            onMouseClick(e.getX(), e.getY(), e.getButton());
        }
    }

    private void mouseHovered() {
        onMouseHover(lastMouseEvent.getX(), lastMouseEvent.getY());
    }

    private void mouseHeld() {
        onMouseHold(lastMouseEvent.getX(), lastMouseEvent.getY(), lastMouseEvent.getButton());
    }

    public void consumeEvent() {
        consumeEvent = true;
    }

    public void onMouseExit() {
    }

    public void onMouseEnter() {
    }

    public void onMouseMove(int mX, int mY) {
    }

    public void onMouseDrag(int mX, int mY, int button) {
    }

    public void onMousePress(int mX, int mY, int button) {
    }

    public void onMouseClick(int mX, int mY, int button) {
    }

    public void onExternMousePress(int button) {
    }

    public void onExternMouseRelease(int button) {
    }

    public void onMouseRelease(int mX, int mY, int button) {
    }

    public void onMouseHover(int mX, int mY) {
    }

    public void onMouseHold(int mX, int mY, int button) {
    }

    public Point getLastMouse() {
        return pmouse;
    }
}
