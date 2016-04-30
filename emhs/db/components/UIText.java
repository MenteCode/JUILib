package emhs.db.components;

import emhs.db.util.AppWindow;
import emhs.db.util.FontData;
import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UIText extends UIVisible {
    static {
        UIFace.addRenderProcedure(UIText.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                if (!UIFace.getRenderProcedure(UIVisible.class).setup(g, component)) return false;
                g.setFont(((UIText) component).font);

                return true;
            }

            public void draw(Graphics2D g, UIComponent component) {
                UIText text = (UIText) component;

                //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); TODO Test what works and what doesn't

                Point pos = text.getPos();

                g.drawString(text.text, pos.x, pos.y + text.fd.ascent);
            }
        });
    }

    protected String text;
    protected Font font;
    protected FontData fd;

    public UIText() {
        this(UIText.class);
    }

    protected UIText(Class<? extends UIComponent> cl) {
        super(cl);
        text = "";
        font = new Font("Arial", Font.PLAIN, 12);
        fd = AppWindow.getFontDimensions(font, text);
        size.width = fd.width;
        size.height = fd.height;
    }

    public UIText setText(String string) {
        text = string;
        fd = AppWindow.getFontDimensions(font, text);
        size.width = fd.width;
        size.height = fd.height;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public String getText() {
        return text;
    }

    public UIText setFont(Font f) {
        if (font == f) return this;
        font = f;
        fd = AppWindow.getFontDimensions(font, text);
        size.width = fd.width;
        size.height = fd.height;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }
}