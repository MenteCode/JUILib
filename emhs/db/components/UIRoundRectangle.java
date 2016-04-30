package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIRoundRectangle extends UIRectangle {
    static {
        UIFace.addRenderProcedure(UIRoundRectangle.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIRectangle.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIRoundRectangle rect = (UIRoundRectangle) component;

                Point pos = rect.getPos();
                if (rect.paint == new Color(189, 169, 113)) rect.getPos();

                if (!rect.isHollow)
                    g.fillRoundRect(pos.x, pos.y, rect.size.width, rect.size.height, rect.arcSize.width << 1, rect.arcSize.height << 1);
                else
                    g.drawRoundRect(pos.x, pos.y, rect.size.width, rect.size.height, rect.arcSize.width << 1, rect.arcSize.height << 1);
            }
        });
    }

    protected Dimension arcSize;

    public UIRoundRectangle() {
        this(UIRoundRectangle.class);
    }

    protected UIRoundRectangle(Class<? extends UIRectangle> cl) {
        super(cl);
        arcSize = new Dimension();
    }

    public UIRoundRectangle setArcSize(int width, int height) {
        if (arcSize.width == width && arcSize.height == height) return this;
        arcSize.width = width;
        arcSize.height = height;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }
}
