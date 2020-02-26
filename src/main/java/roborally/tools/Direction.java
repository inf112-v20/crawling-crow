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
        if(direction == West) {
            return North;
        } else if (direction == North) {
            return East;
        } else if (direction == East) {
            return South;
        } else if (direction == South){
            return West;
        } else {
            throw new IllegalStateException();
        }
    }

    public static Direction turnLeftFrom(Direction direction) {
        if (direction == West) {
            return South;
        } else if (direction == South) {
            return East;
        } else if (direction == East) {
            return North;
        } else if (direction == North) {
            return West;
        } else {
            throw new IllegalStateException();
        }
    }
}
