package emhs.db.components;

import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;

import java.awt.*;

public class UILine extends UIOpenShape {
    static {
        UIFace.addRenderProcedure(UILine.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return UIFace.getRenderProcedure(UIOpenShape.class).setup(g, component);
            }

            public void draw(Graphics2D g, UIComponent component) {
                UILine line = (UILine) component;

                Point pos = line.getPos();

                g.drawLine(pos.x + line.a.x, pos.y + line.a.y, pos.x + line.b.x, pos.y + line.b.y);
            }
        });
    }

    protected Point a, b;

    public UILine() {
        this(UILine.class);
    }

    protected UILine(Class<? extends UIComponent> cl) {
        super(cl);
        a = new Point();
        b = new Point();
    }

    public UILine setStart(int x, int y) {
        if (a.x == x && a.y == y) return this;
        a.x = x;
        a.y = y;

        setSize(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;


        return this;
    }

    public UILine setEnd(int x, int y) {
        if (b.x == x && b.y == y) return this;
        b.x = x;
        b.y = y;

        setSize(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;


        return this;
    }
}
