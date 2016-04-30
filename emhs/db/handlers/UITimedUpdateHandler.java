package emhs.db.handlers;

import emhs.db.components.UIComponent;

public abstract class UITimedUpdateHandler {
    protected UIComponent parent;

    public UITimedUpdateHandler(UIComponent parent) {
        this.parent = parent;
    }

    public UITimedUpdateHandler setParent(UIComponent parent) {
        this.parent = parent;

        return this;
    }

    public abstract void intervalUpdate();
}
