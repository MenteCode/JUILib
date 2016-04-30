package emhs.db.components;

import java.awt.geom.Point2D;

public enum ComponentRegion {
    TOP_LEFT(new Point2D.Float(0, 0)),
    TOP_MIDDLE(new Point2D.Float(0.5f, 0)),
    TOP_RIGHT(new Point2D.Float(1, 0)),
    MIDDLE_LEFT(new Point2D.Float(0, 0.5f)),
    MIDDLE_MIDDLE(new Point2D.Float(0.5f, 0.5f)),
    MIDDLE_RIGHT(new Point2D.Float(1, 0.5f)),
    BOTTOM_LEFT(new Point2D.Float(0, 1)),
    BOTTOM_MIDDLE(new Point2D.Float(0.5f, 1)),
    BOTTOM_RIGHT(new Point2D.Float(1, 1));

    private final Point2D multiplier;

    ComponentRegion(Point2D multiplier) {
        this.multiplier = multiplier;
    }

    public Point2D getMultiplier() {
        return multiplier;
    }
}