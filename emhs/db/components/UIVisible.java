package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public abstract class UIVisible extends UIComponent {
    static {
        UIFace.addRenderProcedure(UIVisible.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                UIFace.getRenderProcedure(UIComponent.class).setup(g, component);

                UIVisible obj = (UIVisible) component;
                if (!obj.isVisible) return false;
                if (obj.isAntiAliased)
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                else g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

                g.setPaint(obj.paint);
                g.setComposite(obj.composite);

                return true;
            }

            public void draw(Graphics2D g, UIComponent component) {
            }
        });
    }

    protected Paint paint;
    protected boolean isVisible;
    protected boolean isAntiAliased;
    protected AlphaComposite composite;

    protected UIVisible(Class<? extends UIComponent> cl) {
        super(cl);
        paint = Color.BLACK;
        isVisible = true;
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
    }

    public UIVisible setPaint(Paint p) {
        if (paint.equals(p)) return this;
        paint = p;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public final UIVisible setAntiAliased(boolean isAntiAliased) {
        if (this.isAntiAliased == isAntiAliased) return this;
        this.isAntiAliased = isAntiAliased;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public final UIVisible setOpacity(float alpha) {
        if (alpha < 0 || alpha > 1 || composite.getAlpha() == alpha) return this;
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public UIVisible setVisible(boolean isVisible) {
        if (this.isVisible == isVisible) return this;
        this.isVisible = isVisible;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;


        return this;
    }
}
