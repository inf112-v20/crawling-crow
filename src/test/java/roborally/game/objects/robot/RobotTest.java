package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import roborally.game.objects.cards.CardsInHand;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class RobotTest {
    private IRobot testRobot1;
    private IRobot testRobot2;
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
        testRobot1.getLogic().setCardsInHand(cardsInHand);
        card = cardsInHand.getCards().get(2);
        int[] order = {2, 0, 1, 3, 4};
        testRobot1.getLogic().arrangeCardsInHand(order);

        initialStartPosition = new GridPoint2(0,0);
    }

    @Test
    public void verifyThatRobotHasInitialPosition() {
        assertEquals(initialStartPosition, testRobot1.getPosition());
    }

    @Test
    public void verifyThatNextCardToPlayIsTheFirstInTheRegister() {
        assertEquals(card, testRobot1.getLogic().getNextCardInHand());
    }

    @Test
    public void verifyThatPeekingNextCardIsFirstInTheRegister() {
        assertEquals(card, testRobot1.getLogic().peekNextCardInHand());
    }

    @Test
    public void verifyThatRobotHas5CardsInHand() {
        assertEquals(5, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas9CardsInHandAfterTaking0Damage() {
        testRobot1.getLogic().takeDamage(0);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(9, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas8CardsInHandAfterTaking1Damage() {
        testRobot1.getLogic().takeDamage(1);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(8, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas7CardsInHandAfterTaking2Damage() {
        testRobot1.getLogic().takeDamage(2);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(7, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas6CardsInHandAfterTaking3Damage() {
        testRobot1.getLogic().takeDamage(3);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(6, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas5CardsInHandAfterTaking4Damage() {
        testRobot1.getLogic().takeDamage(4);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(5, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas4CardsInHandAfterTaking5Damage() {
        testRobot1.getLogic().takeDamage(5);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(4, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas3CardsInHandAfterTaking6Damage() {
        testRobot1.getLogic().takeDamage(6);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(3, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas2CardsInHandAfterTaking7Damage() {
        testRobot1.getLogic().takeDamage(7);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(2, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas1CardInHandAfterTaking8Damage() {
        testRobot1.getLogic().takeDamage(8);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(1, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotHas0CardsInHandAfterTaking9Damage() {
        testRobot1.getLogic().takeDamage(9);
        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(0, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobotIsDestroyedAfterTaking10Damage() {
        testRobot1.getLogic().takeDamage(10);
        assertEquals("Destroyed", testRobot1.getLogic().getStatus());
    }

    @Test
    public void verifyThatRobotHas8CardsAfterTaking2DamageAndGoingToRepairSite() {
        testRobot1.getLogic().takeDamage(2);
        testRobot1.getLogic().addHealth(1);

        // Draws new cards as if it were a new Round
        testRobot1.getLogic().drawCards(programCards);

        assertEquals(8, testRobot1.getLogic().getCardsInHand().size());
    }

    @Test
    public void verifyThatRobot1NameNotEqualToRobot2Name() {
        assertNotEquals(testRobot1.getName(), testRobot2.getName());
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
        assertEquals(Direction.West, testRobot1.getLogic().getDirection());
    }

    @Test
    public void verifyThatRobotRotatesRight() {
        testRobot1.getLogic().rotate(Direction.turnRightFrom(testRobot1.getLogic().getDirection()));
        assertEquals(Direction.East, testRobot1.getLogic().getDirection());
    }

    @Test
    public void verifyThatRobotHasFullHealth() {
        assertEquals(10, testRobot1.getLogic().getHealth());
    }

    @Test
    public void verifyThatEverythingIsOk() {
        assertEquals("Everything is OK!", testRobot1.getLogic().getStatus());
    }

    @Test
    public void verifyThatRobotIsBadlyDamaged() {
        testRobot1.getLogic().takeDamage(6);
        assertEquals("Badly damaged", testRobot1.getLogic().getStatus());
    }

    @Test
    public void verifyThatRobotIsDestroyed() {
        testRobot1.getLogic().takeDamage(SettingsUtil.MAX_DAMAGE);
        assertEquals("Destroyed", testRobot1.getLogic().getStatus());
    }

    @Test
    public void verifyThatRobotUsesARebootWhenReenteringTheBoard() {
        testRobot1.getLogic().setArchiveMarker(new GridPoint2(1, 1));
        testRobot1.getLogic().backToArchiveMarker();
        assertEquals(testRobot1.getLogic().getReboots(), 3);
    }

    @Test
    public void verifyThatRobotSetsNewArchiveMarker() {
        testRobot1.getLogic().setArchiveMarker(new GridPoint2(2, 5));

        assertEquals(new GridPoint2(2, 5), testRobot1.getLogic().getArchiveMarker());
    }

    @Test
    public void verifyThatRobotGoesBackToArchiveMarkerWhenReenteringTheBoard() {
        testRobot1.getLogic().setArchiveMarker(new GridPoint2(3, 6));
        testRobot1.getLogic().takeDamage(10);
        testRobot1.getLogic().backToArchiveMarker();

        assertEquals(new GridPoint2(3, 6), testRobot1.getLogic().getPosition());
    }
}