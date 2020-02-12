package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {
    private IRobot robot;
    private Vector2 initialStartPosition;
    private int posX;
    private int posY;

    @Before
    public void setUp() {
        robot = new Robot();
        initialStartPosition = robot.getPosition();

        // For setting direct x and y coordinates in Robot Position
        posX = 12;
        posY = 10;
    }

    @Test
    public void robotHasInitialPosition() {
        assertEquals(initialStartPosition, robot.getPosition());
    }

    @Test
    public void robotSetAndGetPosition() {
        robot.setPosition(posX, posY);
        assertEquals(new Vector2(posX, posY), robot.getPosition());
    }

    @Test
    public void robotGetPositionX() {
        robot.setPosition(posX, posY);
        assertEquals(posX, robot.getPositionX());
    }

    @Test
    public void robotGetPositionY() {
        robot.setPosition(posX, posY);
        assertEquals(posY, robot.getPositionY());
    }
}