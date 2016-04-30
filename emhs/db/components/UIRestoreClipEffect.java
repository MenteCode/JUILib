package emhs.db.components;

import emhs.db.util.Pointer;
import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIRestoreClipEffect extends UIComponent {
    protected Pointer<Boolean> active;
    protected Pointer<Shape> oldClip;

    static {
        UIFace.addRenderProcedure(UIRestoreClipEffect.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                UIFace.getRenderProcedure(UIComponent.class).setup(g, component);

                return true;
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIRestoreClipEffect effect = (UIRestoreClipEffect) component;

                if (!effect.active.value) return;
                g.setClip(effect.oldClip.value);
            }
        });
    }

    public UIRestoreClipEffect(Pointer<Shape> oldClip, Pointer<Boolean> effectActive) {
        this(UIRestoreClipEffect.class, oldClip, effectActive);
    }

    protected UIRestoreClipEffect(Class<? extends UIComponent> cl, Pointer<Shape> oldClip, Pointer<Boolean> effectActive) {
        super(cl);

        this.oldClip = oldClip;
        this.active = effectActive;
    }
}
