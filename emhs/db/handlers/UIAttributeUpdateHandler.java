package emhs.db.handlers;

import emhs.db.components.UIComponent;

public abstract class UIAttributeUpdateHandler {
    protected UIComponent parent;

    public UIAttributeUpdateHandler(UIComponent parent) {
        this.parent = parent;
    }

    public UIAttributeUpdateHandler setParent(UIComponent parent) {
        this.parent = parent;

        return this;
    }

    public abstract void update();
}
