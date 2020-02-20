package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import roborally.game.objects.robot.Robot;
import roborally.game.objects.robot.RobotCore;

import static org.junit.Assert.*;

public class RobotTest {
    private Vector2 initialStartPosition;
    private int posX;
    private int posY;
    private RobotCore robotCore;

    @Before
    public void setUp() {
        robotCore = new RobotCore(new Robot("TestRobot"));
        // For setting direct x and y coordinates in Robot Position
        posX = 12;
        posY = 10;
        initialStartPosition = new Vector2(0,0);
    }

    @Test
    public void robotHasInitialPosition() {
        assertEquals(initialStartPosition, robotCore.getPosition());
    }

    @Test
    public void robotSetAndGetPosition() {
        robotCore.setPos(posX, posY);
        assertEquals(new Vector2(posX, posY), robotCore.getPosition());
    }
}