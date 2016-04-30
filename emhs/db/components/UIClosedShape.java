package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public abstract class UIClosedShape extends UIOpenShape {
    static {
        UIFace.addRenderProcedure(UIClosedShape.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIOpenShape.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
            }
        });
    }

    protected boolean isHollow;

    protected UIClosedShape(Class<? extends UIComponent> cl) {
        super(cl);
    }

    public UIClosedShape setHollow(boolean hollow) {
        if (isHollow == hollow) return this;
        isHollow = hollow;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }
}
