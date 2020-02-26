package roborally.tools;

public enum Direction {
    North(0),
    East(3),
    South(2),
    West(1);


    private final int directionId;

    Direction(int directionId) {
        this.directionId = directionId;
    }

    public int getDirectionId(){
        return this.directionId;
    }

    public static Direction turnRightFrom(Direction direction) {
        return getDirection(direction, North, South);
    }

    public static Direction turnLeftFrom(Direction direction) {
        return getDirection(direction, South, North);
    }

    private static Direction getDirection(Direction direction, Direction south, Direction north) {
        if (direction == West) {
            return south;
        } else if (direction == south) {
            return East;
        } else if (direction == East) {
            return north;
        } else if (direction == north) {
            return West;
        } else {
            throw new IllegalStateException();
        }
    }
}
