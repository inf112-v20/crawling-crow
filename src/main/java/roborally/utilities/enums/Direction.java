package roborally.utilities.enums;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Enums containing the different libGdxDirectionId and their vector change
 * when moving
 */
public enum Direction {
    North(0, new GridPoint2(0, 1)),
    East(3, new GridPoint2(1, 0)),
    South(2, new GridPoint2(0, -1)),
    West(1, new GridPoint2(-1, 0));

    private final int libGdxDirectionId;
    private final GridPoint2 step;

    Direction(int libGdxDirectionId, GridPoint2 step) {

        this.libGdxDirectionId = libGdxDirectionId;
        this.step = step;
    }

    public static Direction turnRightFrom(Direction direction) {
        return getDirection(direction, North, South);
    }

    public static Direction turnLeftFrom(Direction direction) {
        return getDirection(direction, South, North);
    }

    private static Direction getDirection(Direction direction, Direction south, Direction north) {
        if (direction.equals(West)) {
            return south;
        } else if (direction.equals(south)) {
            return East;
        } else if (direction.equals(East)) {
            return north;
        } else if (direction.equals(north)) {
            return West;
        } else {
            throw new IllegalStateException();
        }
    }

    public int getDirectionID() {
        return this.libGdxDirectionId;
    }

    public GridPoint2 getStep() {
        return this.step;
    }
}
