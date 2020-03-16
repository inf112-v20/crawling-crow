package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RobotModelTest {
    private GridPoint2 initialStartPosition;
    private int posX;
    private int posY;
    private RobotModel robotModel;
    private String robotName;

    @Before
    public void setUp() {
        robotName = "TestRobot";
        robotModel = new RobotModel(robotName);
        // For setting direct x and y coordinates in RobotPresenter Position
        posX = 12;
        posY = 10;
        initialStartPosition = new GridPoint2(0,0);
    }

    @Test
    public void robotHasInitialPosition() {
        assertEquals(initialStartPosition, robotModel.getPos());
    }

    @Test
    public void robotSetAndGetPosition() {
        robotModel.setPos(new GridPoint2(posX, posY));
        assertEquals(new GridPoint2(posX, posY), robotModel.getPos());
    }

    @Test
    public void checkNameIsCorrect() {
        assertEquals(robotName, robotModel.getName());
    }
}