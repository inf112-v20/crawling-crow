package roborally.utilities.enums;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Enums containing the different libGdxDirectionId and their vector change
 * when moving
 */
public enum Direction {
    NORTH(0, new GridPoint2(0, 1)),
    EAST(3, new GridPoint2(1, 0)),
    SOUTH(2, new GridPoint2(0, -1)),
    WEST(1, new GridPoint2(-1, 0));

    private final int libGdxDirectionId;
    private final GridPoint2 step;

    Direction(int libGdxDirectionId, GridPoint2 step) {

        this.libGdxDirectionId = libGdxDirectionId;
        this.step = step;
    }

    public static Direction turnRightFrom(Direction direction) {
        return getDirection(direction, NORTH, SOUTH);
    }

    public static Direction turnLeftFrom(Direction direction) {
        return getDirection(direction, SOUTH, NORTH);
    }

    private static Direction getDirection(Direction direction, Direction south, Direction north) {
        if (direction.equals(WEST)) {
            return south;
        } else if (direction.equals(south)) {
            return EAST;
        } else if (direction.equals(EAST)) {
            return north;
        } else if (direction.equals(north)) {
            return WEST;
        } else {
            throw new IllegalStateException();
        }
    }

    public int getID() {
        return this.libGdxDirectionId;
    }

    public GridPoint2 getStep() {
        return this.step;
    }
}
