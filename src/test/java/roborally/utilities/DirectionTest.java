package roborally.utilities;

import org.junit.Test;
import roborally.utilities.enums.Direction;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void turnRightFromNorth() {
        assertEquals(Direction.East, Direction.turnRightFrom(Direction.North));
    }

    @Test
    public void turnLeftFromNorth() {
        assertEquals(Direction.West, Direction.turnLeftFrom(Direction.North));
    }

    @Test
    public void turnRightFromWest() {
        assertEquals(Direction.North, Direction.turnRightFrom(Direction.West));
    }

    @Test
    public void turnLeftFromWest() {
        assertEquals(Direction.South, Direction.turnLeftFrom(Direction.West));
    }

    @Test
    public void turnRightFromSouth() {
        assertEquals(Direction.West, Direction.turnRightFrom(Direction.South));
    }

    @Test
    public void turnLeftFromSouth() {
        assertEquals(Direction.East, Direction.turnLeftFrom(Direction.South));
    }

    @Test
    public void turnRightFromEast() {
        assertEquals(Direction.South, Direction.turnRightFrom(Direction.East));
    }

    @Test
    public void turnLeftFromEast() {
        assertEquals(Direction.North, Direction.turnLeftFrom(Direction.East));
    }
}