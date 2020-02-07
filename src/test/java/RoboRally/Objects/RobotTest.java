package RoboRally.Objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    IRobot robot;

    @Before
    public void setUp(){
        //TODO: Ask how to initialize a map from within a test
        robot = new Robot();
    }
    @Test
    public void robotHasTexture(){
        assertNotNull(robot.getTexture());
    }

    @Test
    public void robotHasACell(){
        assertNotNull(robot.getCell());
    }

    @Test
    public void robotHasAWinCell(){
        assertNotNull(robot.getWinCell());
    }

    @Test
    public void robotHasALoseCell(){
        assertNotNull(robot.getLostCell());
    }

    @Test
    public void robotHasAPosition(){
        assertNotNull(robot.getPosition());
    }

    @Test
    public void robotCellHasATile(){
        assertNotNull(robot.getCell().getTile());
    }

    @Test
    public void robotWonCellHasATile(){
        assertNotNull(robot.getWinCell().getTile());
    }

    @Test
    public void robotLoseCellHasATile(){
        assertNotNull(robot.getLostCell().getTile());
    }
}