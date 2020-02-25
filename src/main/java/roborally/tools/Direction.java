package roborally.tools;


public enum Direction {
    North,
    South,
    West,
    East;

    public static Direction turnRight(Direction direction) {
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

    public static Direction turnLeft(Direction direction) {
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
