package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.CardsInHand;
import roborally.game.objects.cards.ProgramCards;
import roborally.utilities.enums.Direction;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RobotTest {
    private Programmable testRobot1;
    private Programmable testRobot2;
    private IProgramCards.Card card;

    @Before
    public void setUp() {
        testRobot1 = new Robot(new RobotLogic("T1"));
        testRobot2 = new Robot(new RobotLogic("T2"));
        IProgramCards programCards = new ProgramCards();
        CardsInHand cardsInHand = new CardsInHand(programCards.getDeck());
        testRobot1.getModel().newCards(cardsInHand);
        card = cardsInHand.getCards().get(2);
    }

    @Test
    public void verifyThatCardsArePlayedInTheCorrectOrder() {
        int[] order = {2,0,1,3,4};
        testRobot1.getModel().arrangeCards(order);
        assertEquals(card, testRobot1.getModel().getNextCard());
    }

    @Test
    public void verifyThatRobot1NameNotEqualToRobot2Name() {
        assertThat(testRobot1.getName(), not(testRobot2.getName()));
    }

    @Test
    public void verifyThatRobot1PositionNotEqualToRobot2Position() {
        testRobot1.setPosition(new GridPoint2(5, 5));
        testRobot2.setPosition(new GridPoint2(4, 4));
        assertThat(testRobot1.getPosition(), not(testRobot2.getPosition()));
    }

    @Test
    public void testThatRobotRotatesLeft() {
        testRobot1.getModel().rotate("L", 1);
        assertEquals(testRobot1.getModel().getDirection(), Direction.West);
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