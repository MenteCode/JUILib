package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIEllipse extends UIClosedShape {
    static {
        UIFace.addRenderProcedure(UIEllipse.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIClosedShape.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIEllipse ellipse = (UIEllipse) component;

                Point pos = ellipse.getPos();

                if (!ellipse.isHollow) g.fillOval(pos.x, pos.y, ellipse.size.width, ellipse.size.height);
                else g.drawOval(pos.x, pos.y, ellipse.size.width, ellipse.size.height);
            }
        });
    }

    public UIEllipse() {
        super(UIEllipse.class);
    }

    protected UIEllipse(Class<? extends UIRectangle> cl) {
        super(cl);
    }
}
