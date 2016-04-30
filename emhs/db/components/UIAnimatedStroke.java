package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIAnimatedStroke extends UIComponent {
    static {
        UIFace.addRenderProcedure(UIAnimatedStroke.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIComponent.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
            }
        });
    }

    protected UIOpenShape shape;
    protected float iteration;
    protected float speed;

    public UIAnimatedStroke(UIOpenShape shape) {
        super(UIAnimatedStroke.class);
        this.shape = shape;
        shape.setParent(this);
    }

    public UIAnimatedStroke setAnimationSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public void intervalUpdate() {
        shape.setStroke(new BasicStroke(shape.stroke.getLineWidth(), shape.stroke.getEndCap(), shape.stroke.getLineJoin(), shape.stroke.getMiterLimit(), shape.stroke.getDashArray(), iteration += speed));
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;
    }
}