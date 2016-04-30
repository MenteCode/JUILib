package emhs.db.util;

import emhs.db.components.UIElement;
import emhs.db.handlers.UIInteractionHandler;
import emhs.db.handlers.UIAttributeUpdateHandler;
import emhs.db.handlers.UITimedUpdateHandler;
import emhs.db.components.UIComponent;

import java.awt.*;

public class ViewLayout extends UIElement {
    public ViewLayout() {
    }

    public UISurface generateSurface() {
        UISurface surface = new UISurface();

        Point thisPos = getLocalPos();
        surface.setPos(thisPos.x, thisPos.y);
        surface.setSize(size.width, size.height);

        for(UIComponent c : children) {
            surface.add(c);
            c.setParent(surface);
        }

        surface.setRegion(region);

        while (!interactHandlers.isEmpty()) {
            UIInteractionHandler i = interactHandlers.remove(0);
            i.setParent(surface);
            surface.addInteractionHandler(i);
        }

        while (!timedUpdateHandlers.isEmpty()) {
            UITimedUpdateHandler i = timedUpdateHandlers.remove(0);
            i.setParent(surface);
            surface.addTimedUpdateHandler(i);
        }

        while (!attributeUpdateHandlers.isEmpty()) {
            UIAttributeUpdateHandler i = attributeUpdateHandlers.remove(0);
            i.setParent(surface);
            surface.addAttributeUpdateHandler(i);
        }

        surface.setIncludeInPack(includeInPack);

        return surface;
    }
}
