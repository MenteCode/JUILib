package emhs.db.util;

import emhs.db.components.UIComponent;

import java.awt.*;

public interface RenderProcedure {
    boolean setup(Graphics2D g, UIComponent component);

    void draw(Graphics2D g, UIComponent component);
}
