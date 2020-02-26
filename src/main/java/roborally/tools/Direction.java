package roborally.tools;

public enum Direction {
    North(0),
    South(180),
    West(-90),
    East(90);

    private final int degrees;

    Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees(){
        return this.degrees;
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
