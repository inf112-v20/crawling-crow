package roborally.utilities;

import org.junit.Test;
import roborally.utilities.enums.Direction;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void verifyThatDirectionIsTurningRightFromNorth() {
        assertEquals(Direction.East, Direction.turnRightFrom(Direction.North));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromNorth() {
        assertEquals(Direction.West, Direction.turnLeftFrom(Direction.North));
    }

    @Test
    public void verifyThatDirectionIsTurningRightFromWest() {
        assertEquals(Direction.North, Direction.turnRightFrom(Direction.West));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromWest() {
        assertEquals(Direction.South, Direction.turnLeftFrom(Direction.West));
    }

    @Test
    public void verifyThatDirectionIsTurningRightFromSouth() {
        assertEquals(Direction.West, Direction.turnRightFrom(Direction.South));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromSouth() {
        assertEquals(Direction.East, Direction.turnLeftFrom(Direction.South));
    }

    @Test
    public void verifyThatDirectionIsTurningRightFromEast() {
        assertEquals(Direction.South, Direction.turnRightFrom(Direction.East));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromEast() {
        assertEquals(Direction.North, Direction.turnLeftFrom(Direction.East));
    }
}