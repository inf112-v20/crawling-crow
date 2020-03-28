package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import roborally.game.objects.cards.CardsInHand;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.utilities.enums.Direction;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RobotTest {
    private Programmable testRobot1;
    private Programmable testRobot2;
    private IProgramCards.Card card;
    private IProgramCards programCards;
    private CardsInHand cardsInHand;

    private GridPoint2 initialStartPosition;


    @Before
    public void setUp() {
        testRobot1 = new Robot(new RobotLogic("T1"));
        testRobot2 = new Robot(new RobotLogic("T2"));
        programCards = new ProgramCards();

        cardsInHand = new CardsInHand(programCards.getDeck());
        testRobot1.getLogic().newCards(cardsInHand);
        card = cardsInHand.getCards().get(2);
        int[] order = {2, 0, 1, 3, 4};
        testRobot1.getLogic().arrangeCards(order);

        initialStartPosition = new GridPoint2(0,0);
    }

    @Test
    public void verifyThatRobotHasInitialPosition() {
        assertEquals(initialStartPosition, testRobot1.getPosition());
    }

    @Test
    public void verifyThatNextCardToPlayIsTheFirstInTheRegister() {
        assertEquals(card, testRobot1.getLogic().getNextCard());
    }

    @Test
    public void verifyThatPeekingNextCardIsFirstInTheRegister() {
        assertEquals(card, testRobot1.getLogic().peekNextCard());
    }

    @Test
    public void verifyThatRobotHas5CardsInHand() {
        assertEquals(5, testRobot1.getLogic().getCards().size());
    }

    // verifyThatRobotHas3CardsInHandAfterTaking7Damage

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
    public void verifyThatRobotRotatesLeft() {
        testRobot1.getLogic().rotate(Direction.turnLeftFrom(testRobot1.getLogic().getDirection()));
        assertEquals(testRobot1.getLogic().getDirection(), Direction.West);
    }

    @Test
    public void verifyThatRobotRotatesRight() {
        testRobot1.getLogic().rotate(Direction.turnRightFrom(testRobot1.getLogic().getDirection()));
        assertEquals(testRobot1.getLogic().getDirection(), Direction.East);
    }

    @Test
    public void verifyThatRobotHasFullHealth() {
        assertEquals(testRobot1.getLogic().getHealth(), 10);
    }

    @Test
    public void verifyThatEverythingIsOk() {
        assertEquals(testRobot1.getLogic().getStatus(), "Everything ok!");
    }

    @Test
    public void verifyThatRobotIsBadlyDamaged() {
        testRobot1.getLogic().takeDamage(6);
        assertEquals(testRobot1.getLogic().getStatus(), "Badly damaged");
    }

    @Test
    public void verifyThatRobotIsDestroyed() {
        testRobot1.getLogic().takeDamage(10);
        assertEquals(testRobot1.getLogic().getStatus(), "Destroyed");
    }

    @Test
    public void verifyThatRobotUsesARebootWhenReenteringTheBoard() {
        testRobot1.getLogic().setArchiveMarker(new GridPoint2(1, 1));
        testRobot1.getLogic().backToArchiveMarker();
        assertEquals(testRobot1.getLogic().getReboots(), 3);
    }

    @Test
    public void name() {
    }
}