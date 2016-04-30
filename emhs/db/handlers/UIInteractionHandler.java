package emhs.db.handlers;

import emhs.db.components.UIComponent;

import java.awt.*;

public abstract class UIInteractionHandler {
    protected UIComponent parent;

    public UIInteractionHandler(UIComponent parent) {
        this.parent = parent;
    }

    public UIInteractionHandler setParent(UIComponent parent) {
        this.parent = parent;

        return this;
    }

    public abstract boolean update(AWTEvent e, String eventType);
}
