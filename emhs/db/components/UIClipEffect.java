package emhs.db.components;

import emhs.db.util.Pointer;
import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class UIClipEffect extends UIComponent {
    static {
        UIFace.addRenderProcedure(UIClipEffect.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                UIFace.getRenderProcedure(UIComponent.class).setup(g, component);

                return true;
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIClipEffect effect = (UIClipEffect) component;

                if (!effect.active.value) return;

                Point pos = effect.getPos();
                effect.oldClip.value = g.getClip();

                Area clip = new Area(effect.clip).createTransformedArea(new AffineTransform(1, 0, 0, 1, pos.x, pos.y));
                clip.intersect(new Area(effect.oldClip.value));

                g.setClip(clip);
            }
        });
    }

    protected Pointer<Boolean> active;
    protected Pointer<Shape> oldClip;
    protected Shape clip;

    public UIClipEffect() {
        this(UIClipEffect.class);
    }

    protected UIClipEffect(Class<? extends UIComponent> cl) {
        super(cl);

        active = new Pointer<>(false);
        oldClip = new Pointer<>(null);
    }

    public UIClipEffect setClip(Shape clip) {
        this.clip = clip;

        return this;
    }

    public UIClipEffect setActive(boolean active) {
        if(clip == null) return this;
        this.active.value = active;

        return this;
    }

    public UIRestoreClipEffect genRestoreClipEffect() {
        return new UIRestoreClipEffect(oldClip, active);
    }
}
