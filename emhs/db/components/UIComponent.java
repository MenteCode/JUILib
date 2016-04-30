package emhs.db.components;

import emhs.db.handlers.UIAttributeUpdateHandler;
import emhs.db.handlers.UIInteractionHandler;
import emhs.db.handlers.UITimedUpdateHandler;
import emhs.db.util.Bounds;
import emhs.db.util.RenderProcedure;
import emhs.db.util.UIFace;
import emhs.db.util.UIUtilities;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class UIComponent {
    static {
        UIFace.addRenderProcedure(UIComponent.class, new RenderProcedure() {
            public boolean setup(Graphics2D g, UIComponent component) {
                return true;
            }

            public void draw(Graphics2D g, UIComponent component) {

            }
        });
    }

    protected Dimension size;
    protected UIComponent parent;
    protected RenderProcedure rProcedure;
    protected ComponentRegion region = ComponentRegion.MIDDLE_MIDDLE;
    protected CopyOnWriteArrayList<UIAttributeUpdateHandler> attributeUpdateHandlers;
    protected CopyOnWriteArrayList<UITimedUpdateHandler> timedUpdateHandlers;
    protected CopyOnWriteArrayList<UIInteractionHandler> interactHandlers;
    protected Bounds bound, previousBound;
    protected boolean componentUpdated;
    protected boolean renderRequested;
    protected boolean boundUpdated;
    protected boolean includeInPack;
    private Point pos;

    protected UIComponent(Class<? extends UIComponent> cl) {
        pos = new Point();
        size = new Dimension();
        interactHandlers = new CopyOnWriteArrayList<>();
        timedUpdateHandlers = new CopyOnWriteArrayList<>();
        attributeUpdateHandlers = new CopyOnWriteArrayList<>();
        bound = new Bounds();
        includeInPack = true;

        if (cl == null) {
            rProcedure = null;
        } else {
            rProcedure = UIFace.getRenderProcedure(cl);
        }
    }

    public void intervalUpdate() {
        for (UITimedUpdateHandler i : timedUpdateHandlers) {
            i.intervalUpdate();
        }
    }

    public boolean update(AWTEvent e, String eventType) {
        boolean updated = false;

        for (UIInteractionHandler i : interactHandlers) {
            updated |= i.update(e, eventType);
        }

        if (componentUpdated()) {
            for (UIAttributeUpdateHandler i : attributeUpdateHandlers) {
                i.update();
            }

            componentUpdated = false;
        }

        return updated;
    }

    public void draw(Graphics2D g, Rectangle renderArea) {
        if (rProcedure != null) {
            if (rProcedure.setup(g, this)) {
                rProcedure.draw(g, this);
            }
        }

        if (!boundUpdated) renderRequested = false;
    }

    public final Dimension getSize() {
        return new Dimension(size);
    }

    /*
     Be DAMN WELL AWARE that this is the reason that rectangles can be manipulated by their centers! WOOHOO!
     */
    public final Point getPos() {
        Point abs = pos.getLocation();
        if (parent != null) {
            Point parentPos = parent.getPos();
            Point2D multiplier = region.getMultiplier();
            abs.translate((int) (parentPos.x + (parent.size.width - size.width) * multiplier.getX()), (int) (parentPos.y + (parent.size.height - size.height) * multiplier.getY()));
        }

        return abs;
    }

    public final Point getLocalPos() {
        return new Point(pos);
    }

    public final UIComponent getParent() {
        return parent;
    }

    public final UIComponent setParent(UIComponent c) {
        if (parent == c || c == null) return this;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        parent = c;
        return this;
    }

    public final boolean includedInPack() {
        return includeInPack;
    }

    public final UIComponent setSize(int width, int height) {
        if (size.width == width && size.height == height) return this;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        size.width = width;
        size.height = height;
        return this;
    }

    public final UIComponent setRegion(ComponentRegion region) {
        if (this.region == region) return this;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        this.region = region;
        return this;
    }

    public final UIComponent setPos(int x, int y) {
        if (pos.x == x && pos.y == y) return this;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        pos.x = x;
        pos.y = y;

        return this;
    }

    public final UIComponent translate(int x, int y) {
        if ((x | y) == 0) return this;
        renderRequested = true;
        boundUpdated = true;
        componentUpdated = true;

        pos.x += x;
        pos.y += y;
        return this;
    }

    public void setCursor(Cursor c) {
        parent.setCursor(c);
    }

    public final UIComponent addAttributeUpdateHandler(UIAttributeUpdateHandler... handlers) {
        attributeUpdateHandlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public final UIComponent addInteractionHandler(UIInteractionHandler... handlers) {
        interactHandlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public final UIComponent addTimedUpdateHandler(UITimedUpdateHandler... handlers) {
        timedUpdateHandlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public final UIComponent removeAttributeUpdateHandler(UIAttributeUpdateHandler... handlers) {
        attributeUpdateHandlers.removeAll(Arrays.asList(handlers));

        return this;
    }

    public final UIComponent removeInteractionHandler(UIInteractionHandler... handlers) {
        interactHandlers.removeAll(Arrays.asList(handlers));

        return this;
    }

    public final UIComponent removeTimedUpdateHandler(UITimedUpdateHandler... handlers) {
        timedUpdateHandlers.removeAll(Arrays.asList(handlers));

        return this;
    }

    public final UIComponent setIncludeInPack(boolean ignorePack) {
        includeInPack = ignorePack;
        return this;
    }

    public void prepareBounds() {
        Point p = getPos();
        bound.minX = p.x;
        bound.minY = p.y;
        bound.maxX = p.x + size.width;
        bound.maxY = p.y + size.height;
    }

    public Bounds getBound(Rectangle screenSize) {
        boundUpdated = false;
        if (!renderRequested()) return new Bounds(0, 0, 0, 0);

        if (!UIUtilities.contains(new Rectangle(getPos(), size), screenSize) && previousBound != null) {
            Bounds newOut = new Bounds(previousBound);
            previousBound = null;
            return newOut;
        }

        Bounds out = new Bounds(bound);

        if (previousBound != null && (previousBound.maxX - previousBound.minX > 0 && previousBound.maxY - previousBound.minY > 0)) {
            out.minX = Math.min(previousBound.minX, out.minX);
            out.minY = Math.min(previousBound.minY, out.minY);
            out.maxX = Math.max(previousBound.maxX, out.maxX);
            out.maxY = Math.max(previousBound.maxY, out.maxY);
        }

        previousBound = new Bounds(bound);

        return out;
    }

    public boolean renderRequested() {
        return renderRequested || parent.renderRequested();
    }

    public boolean componentUpdated() {
        return componentUpdated || parent.componentUpdated();
    }

    public void injectEvent(AWTEvent event) {
        parent.injectEvent(event);
    }
}
