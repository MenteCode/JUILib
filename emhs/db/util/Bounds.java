package emhs.db.util;

import java.awt.*;

public class Bounds {
    public int minX, minY, maxX, maxY;

    public Bounds(Bounds b) {
        minX = b.minX;
        minY = b.minY;
        maxX = b.maxX;
        maxY = b.maxY;
    }

    public Bounds() {}

    public Bounds(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Rectangle toRectangle() {
        if(minX == Integer.MAX_VALUE && minY == minX && maxX == Integer.MIN_VALUE && maxY == maxX) return new Rectangle();
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public String toString() {
        return "(" + minX + ", " + minY + ") : (" + maxX + ", " + maxY + ")";
    }
}
