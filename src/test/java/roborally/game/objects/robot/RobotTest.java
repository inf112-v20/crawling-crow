package roborally.game.objects.robot;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RobotTest {
    private IRobot testRobot1;
    private IRobot testRobot2;

    @Before
    public void setUp() {
        testRobot1 = new Robot(new RobotLogic("T1"));
        testRobot2 = new Robot(new RobotLogic("T2"));
    }

    @Test
    public void verifyThatRobot1NameNotEqualToRobot2Name() {
        assertThat(testRobot1.getName(), not(testRobot2.getName()));
    }

    @Test
    public void verifyThatRobot1PositionNotEqualToRobot2Position() {
        testRobot1.setPos(5, 5);
        testRobot2.setPos(4, 4);
        assertThat(testRobot1.getPosition(), not(testRobot2.getPosition()));
    }

    @Test
    public void assertThatRobotHasFullHealth() {
        assertEquals(testRobot1.getModel().getHealth(), 10);
    }

    @Test
    public void assertThatEverythingIsOk() {
        assertEquals(testRobot1.getModel().getStatus(), "Everything ok!");
    }

    @Test
    public void assertThatRobotIsBadlyDamaged() {
        testRobot1.getModel().takeDamage(6);
        assertEquals(testRobot1.getModel().getStatus(), "Badly damaged");
    }

    @Test
    public void assertThatRobotIsDestroyed() {
        testRobot1.getModel().takeDamage(10);
        assertEquals(testRobot1.getModel().getStatus(), "Destroyed");
    }


}