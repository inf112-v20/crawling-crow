package roborally.game.objects.robot;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class RobotTest {
    private IRobot testRobot1;
    private IRobot testRobot2;
    @Before
    public void setUp() {
        testRobot1 = new Robot(new RobotState("T1"));
        testRobot2 = new Robot(new RobotState("T2"));
    }

    @Test
    public void verifyThatRobot1NameNotEqualToRobot2Name() {
        assertThat(testRobot1.getName(), not(testRobot2.getName()));
    }
    @Test
    public void verifyThatRobot1PositionNotEqualToRobot2Position() {
        testRobot1.setPos(5,5);
        testRobot2.setPos(4,4);
        assertThat(testRobot1.getPosition(), not(testRobot2.getPosition()));
    }
}