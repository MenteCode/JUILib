package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UICrossMark extends UIOpenShape {
    static {
        UIFace.addRenderProcedure(UICrossMark.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIOpenShape.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UICrossMark cross = (UICrossMark) component;

                Point pos = cross.getPos();

                g.drawPolyline(new int[]{(int) (pos.x + cross.size.width * cross.contact), (int) (pos.x + cross.size.width * (1 - cross.contact))}, new int[]{pos.y, pos.y + cross.size.height}, 2);
                g.drawPolyline(new int[]{pos.x + cross.size.width, pos.x}, new int[]{(int) (pos.y + cross.size.height * cross.contact), (int) (pos.y + cross.size.height * (1 - cross.contact))}, 2);
            }
        });
    }

    protected double contact;

    public UICrossMark() {
        super(UICrossMark.class);
        stroke = new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
        paint = Color.RED;
    }

    protected UICrossMark(Class<? extends UIRectangle> cl) {
        super(cl);
    }

    public UICrossMark setContact(float contact) {
        if (contact < 0 || contact >= 1 || this.contact == contact) return this;
        this.contact = contact;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;


        return this;
    }
}
