package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public abstract class UIOpenShape extends UIVisible {
    static {
        UIFace.addRenderProcedure(UIOpenShape.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                if (!UIFace.getRenderProcedure(UIVisible.class).setup(g, component)) return false;
                g.setStroke(((UIOpenShape) component).stroke);

                return true;
            }

            public void draw(Graphics2D g, UIComponent component) {
            }
        });
    }

    protected BasicStroke stroke;

    protected UIOpenShape(Class<? extends UIComponent> cl) {
        super(cl);
        stroke = new BasicStroke(1);
    }

    public UIOpenShape setStroke(BasicStroke s) {
        if (stroke.equals(s)) return this;
        stroke = s;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public void prepareBounds() {
        super.prepareBounds();
        int sw = (int) Math.ceil(stroke.getLineWidth());
        bound.minX -= sw + 1;
        bound.minY -= sw + 1;
        bound.maxX += sw + 1;
        bound.maxY += sw + 1;
    }
}
