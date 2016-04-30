package emhs.db.components;

import emhs.db.util.Bounds;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class UIElement extends UIComponent {
    protected CopyOnWriteArrayList<UIComponent> children;

    protected UIElement() {
        super(null);
        children = new CopyOnWriteArrayList<>();
    }

    /**
    Note: Return <code>true</code> only if you wish to consume the event.
     */

    public final boolean update(final AWTEvent e, final String eventType) {
        for (int i = children.size() - 1; i >= 0; i--) {
            if(children.get(i).update(e, eventType)) return true;
        }

        return super.update(e, eventType);
    }

    public final void intervalUpdate() {
        super.intervalUpdate();

        for (UIComponent c : children) {
            c.intervalUpdate();
        }
    }

    public UIElement add(UIComponent... comps) {
        for (UIComponent c : comps) {
            if (c == null || c == this) continue;
            children.add(c);
            c.setParent(this);
        }

        return this;
    }

    public final UIElement clear() {
        children.clear();
        pack();

        return this;
    }

    public final UIElement remove(UIComponent... comps) {
        for (UIComponent c : comps) {
            children.remove(c);
        }

        return this;
    }

    public final UIElement pack() {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        Point pos;
        Dimension size;

        for (UIComponent c : children) {
            if (!c.includedInPack()) continue;
            pos = c.getLocalPos();
            size = c.getSize();

            minX = Math.min(pos.x, minX);
            minY = Math.min(pos.y, minY);
            maxX = Math.max(pos.x + size.width, maxX);
            maxY = Math.max(pos.y + size.height, maxY);
        }

        this.size.width = Math.max(maxX - minX, 0);
        this.size.height = Math.max(maxY - minY, 0);

        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        return this;
    }

    public void prepareBounds() {
        super.prepareBounds();

        for (UIComponent c : children) {
            c.prepareBounds();
        }
    }

    public Bounds getBound(Rectangle screenSize) {
        Bounds out = new Bounds(), current;
        out.minX = out.minY = Integer.MAX_VALUE;
        out.maxX = out.maxY = Integer.MIN_VALUE;

        for (UIComponent c : children) {
            current = c.getBound(screenSize);
            if (current.maxX - current.minX <= 0 || current.maxY - current.minY <= 0) continue;
            out.minX = Math.min(current.minX, out.minX);
            out.minY = Math.min(current.minY, out.minY);
            out.maxX = Math.max(current.maxX, out.maxX);
            out.maxY = Math.max(current.maxY, out.maxY);
        }

        return out;
    }

    public final int getNumChildren() {
        return children.size();
    }

    public final void draw(Graphics2D g, Rectangle renderArea) {
        for (UIComponent c : children) {
            c.draw(g, renderArea);
        }

        renderRequested = false;
    }

    public final void updateSelf() {
        final UIElement t = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                t.intervalUpdate();
            }
        });
    }
}
