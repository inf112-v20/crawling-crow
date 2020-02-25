package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import roborally.game.objects.robot.RobotState;
import roborally.game.objects.robot.Robot;

import static org.junit.Assert.*;

public class RobotStateTest {
    private Vector2 initialStartPosition;
    private int posX;
    private int posY;
    private Robot robot;

    @Before
    public void setUp() {
        robot = new Robot(new RobotState("TestRobot"));
        // For setting direct x and y coordinates in Robot Position
        posX = 12;
        posY = 10;
        initialStartPosition = new Vector2(0,0);
    }

    @Test
    public void robotHasInitialPosition() {
        assertEquals(initialStartPosition, robot.getPosition());
    }

    @Test
    public void robotSetAndGetPosition() {
        robot.setPos(posX, posY);
        assertEquals(new Vector2(posX, posY), robot.getPosition());
    }
}