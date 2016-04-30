package emhs.db.util;

import emhs.db.components.UIComponent;

import java.awt.*;

public class UIUtilities {
    private UIUtilities() {}

    public static boolean inBounds(double x, double y, double width, double height, double pointX, double pointY) {
        return (pointX >= x && pointX <= x + width) && (pointY >= y && pointY <= y + height);
    }

    public static boolean contains(Rectangle c, Rectangle a) {
        return c.x <= a.x + a.width && c.x + c.width > a.x && c.y <= a.y + a.height && c.y + c.height > a.y;
    }

    public static Point toRelativeCoords(int x, int y, UIComponent c) {
        Point out = new Point(x, y);
        Point compOrigin = c.getPos();

        out.translate(-compOrigin.x, -compOrigin.y);

        return out;
    }
}
