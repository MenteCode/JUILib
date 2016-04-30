package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIImage extends UIVisible {
    static {
        UIFace.addRenderProcedure(UIImage.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIVisible.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIImage img = (UIImage) component;

                Point pos = img.getPos();
                g.drawImage(img.img, pos.x, pos.y, img.size.width, img.size.height, null);
            }
        });
    }

    protected Image img;

    public UIImage() {
        super(UIImage.class);
    }

    public UIImage setImage(Image img) {
        if (this.img == img) return this;
        this.img = img;
        setSize(img.getWidth(null), img.getHeight(null));
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }
}
