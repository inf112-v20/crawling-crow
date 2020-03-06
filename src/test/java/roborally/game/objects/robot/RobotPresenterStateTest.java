package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RobotPresenterStateTest {
    private GridPoint2 initialStartPosition;
    private int posX;
    private int posY;
    private IRobotModel robotState;
    private String robotName;

    @Before
    public void setUp() {
        robotName = "TestRobot";
        robotState = new RobotModel(robotName);
        // For setting direct x and y coordinates in RobotPresenter Position
        posX = 12;
        posY = 10;
        initialStartPosition = new GridPoint2(0,0);
    }

    @Test
    public void robotHasInitialPosition() {
        assertEquals(initialStartPosition, robotState.getPosition());
    }

    @Test
    public void robotSetAndGetPosition() {
        robotState.setPosition(new GridPoint2(posX, posY));
        assertEquals(new GridPoint2(posX, posY), robotState.getPosition());
    }

    @Test
    public void checkNameIsCorrect() {
        assertEquals(robotName, robotState.getName());
    }
}