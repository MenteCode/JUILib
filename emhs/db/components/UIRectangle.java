package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIRectangle extends UIClosedShape {
    static {
        UIFace.addRenderProcedure(UIRectangle.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIClosedShape.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIRectangle rect = (UIRectangle) component;

                Point pos = rect.getPos();

                if (!rect.isHollow) g.fillRect(pos.x, pos.y, rect.size.width, rect.size.height);
                else g.drawRect(pos.x, pos.y, rect.size.width, rect.size.height);
            }
        });
    }

    public UIRectangle() {
        super(UIRectangle.class);
    }

    protected UIRectangle(Class<? extends UIRectangle> cl) {
        super(cl);
    }
}
