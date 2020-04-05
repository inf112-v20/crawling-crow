package roborally.utilities.enums;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void verifyThatDirectionIsTurningRightFromNorth() {
        assertEquals(Direction.EAST, Direction.turnRightFrom(Direction.NORTH));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromNorth() {
        assertEquals(Direction.WEST, Direction.turnLeftFrom(Direction.NORTH));
    }

    @Test
    public void verifyThatDirectionIsTurningRightFromWest() {
        assertEquals(Direction.NORTH, Direction.turnRightFrom(Direction.WEST));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromWest() {
        assertEquals(Direction.SOUTH, Direction.turnLeftFrom(Direction.WEST));
    }

    @Test
    public void verifyThatDirectionIsTurningRightFromSouth() {
        assertEquals(Direction.WEST, Direction.turnRightFrom(Direction.SOUTH));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromSouth() {
        assertEquals(Direction.EAST, Direction.turnLeftFrom(Direction.SOUTH));
    }

    @Test
    public void verifyThatDirectionIsTurningRightFromEast() {
        assertEquals(Direction.SOUTH, Direction.turnRightFrom(Direction.EAST));
    }

    @Test
    public void verifyThatDirectionIsTurningLeftFromEast() {
        assertEquals(Direction.NORTH, Direction.turnLeftFrom(Direction.EAST));
    }

    @Test
    public void verifyThatSteppingNorthReturnsExpectedValue() {
        assertEquals(new GridPoint2(0, 1), Direction.NORTH.getStep());
    }

    @Test
    public void verifyThatSteppingSouthReturnsExpectedValue() {
        assertEquals(new GridPoint2(0, -1), Direction.SOUTH.getStep());
    }

    @Test
    public void verifyThatSteppingEastReturnsExpectedValue() {
        assertEquals(new GridPoint2(1, 0), Direction.EAST.getStep());
    }

    @Test
    public void verifyThatSteppingWestReturnsExpectedValue() {
        assertEquals(new GridPoint2(-1, 0), Direction.WEST.getStep());
    }

    @Test
    public void verifyThatNorthHasDirectionID0() {
        assertEquals(0, Direction.NORTH.getID());
    }

    @Test
    public void verifyThatEastHasDirectionID3() {
        assertEquals(3, Direction.EAST.getID());
    }

    @Test
    public void verifyThatWestHasDirectionID1() {
        assertEquals(1, Direction.WEST.getID());
    }

    @Test
    public void verifyThatSouthHasDirectionID2() {
        assertEquals(2, Direction.SOUTH.getID());
    }
}