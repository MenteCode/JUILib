package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UICheckMark extends UIOpenShape {
    static {
        UIFace.addRenderProcedure(UICheckMark.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIOpenShape.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UICheckMark check = (UICheckMark) component;

                Point pos = check.getPos();
                Dimension size = check.getSize();

                double point = check.contact;
                double m1point = 1 - point;
                double diff = (size.height * Math.min(point, m1point)) * 0.5;

                g.drawPolyline(
                        new int[]{pos.x, (int) (pos.x + size.width * point), pos.x + size.width},
                        new int[]{(int) (pos.y + size.height * m1point - diff), (int) (pos.y + size.height - diff), (int) (pos.y + size.height * point - diff)},
                        3);
            }
        });
    }

    protected double contact;

    public UICheckMark() {
        super(UICheckMark.class);
        stroke = new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
        paint = Color.GREEN;
        contact = 1 / 3.d;
    }

    protected UICheckMark(Class<? extends UIRectangle> cl) {
        super(cl);
    }

    public UICheckMark setContact(double contact) {
        if (contact < 0 || contact >= 1 || this.contact == contact) return this;
        this.contact = contact;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public void intervalUpdate() {
    }
}
