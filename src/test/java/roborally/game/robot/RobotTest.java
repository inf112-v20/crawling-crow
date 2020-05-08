package roborally.game.robot;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import roborally.game.cards.CardsInHand;
import roborally.game.cards.IProgramCards;
import roborally.game.cards.ProgramCards;
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

    private final int nFlags = 3;

    @Before
    public void setUp() {
        this.testRobot1 = new Robot(new RobotLogic("T1"));
        this.testRobot2 = new Robot(new RobotLogic("T2"));
        this.testRobot1.getLogic().setNumberOfFlags(nFlags);
        this.programCards = new ProgramCards();

        this.cardsInHand = new CardsInHand(programCards.getDeck());
        this.testRobot1.getLogic().setCardsInHand(cardsInHand);
        this.card = cardsInHand.getCards().get(2);
        int[] order = {2, 0, 1, 3, 4};
        this.testRobot1.getLogic().arrangeCardsInHand(order);
        this.testRobot1.getLogic().putChosenCardsIntoRegister();

        this.initialStartPosition = new GridPoint2(0,0);
    }

    @Test
    public void verifyThatRobotHasInitialPosition() {
        assertEquals(initialStartPosition, testRobot1.getPosition());
    }

    @Test
    public void verifyThatNextCardToPlayIsTheFirstInTheRegister() {
        assertEquals(card, testRobot1.getLogic().getNextCardInRegister());
    }

    @Test
    public void verifyThatPeekingNextCardIsFirstInTheRegister() {
        assertEquals(card, testRobot1.getLogic().peekNextCardInRegister());
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
        assertEquals(Direction.WEST, testRobot1.getLogic().getDirection());
    }

    @Test
    public void verifyThatRobotRotatesRight() {
        testRobot1.getLogic().rotate(Direction.turnRightFrom(testRobot1.getLogic().getDirection()));
        assertEquals(Direction.EAST, testRobot1.getLogic().getDirection());
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
    public void verifyThatRobotIsNotDestroyed() {
        assertFalse(testRobot1.getLogic().isDestroyed());
    }

    @Test
    public void verifyThatRobotIsDestroyedBoolean() {
        testRobot1.getLogic().takeDamage(SettingsUtil.MAX_DAMAGE);
        assertTrue(testRobot1.getLogic().isDestroyed());
    }

    @Test
    public void verifyThatRobotUsesARebootWhenDestroyed() {
        testRobot1.getLogic().takeDamage(10);
        assertEquals(3, testRobot1.getLogic().getReboots());
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

    @Test
    public void verifyThatRobotIsNotUserRobot() {
        assertFalse(testRobot1.getLogic().isUserRobot());
    }

    @Test
    public void verifyThatRobotIsUserRobot() {
        testRobot1.getLogic().setUserRobot();
        assertTrue(testRobot1.getLogic().isUserRobot());
    }

    @Test
    public void verifyThatRobotVisitsFlag() {
        testRobot1.getLogic().visitNextFlag();

        assertTrue(testRobot1.getLogic().getVisitedFlags()[0]);
    }

    @Test
    public void verifyThatRobotVisitsAllFlags() {
        for (int i = 0; i < nFlags; i++) {
            testRobot1.getLogic().visitNextFlag();
        }

        assertTrue(testRobot1.getLogic().hasVisitedAllFlags());
    }

    @Test
    public void verifyThatRobotHasNotWon() {
        assertFalse(testRobot1.getLogic().hasWon());
    }

    @Test
    public void verifyThatRobotHasWon() {
        testRobot1.getLogic().setHasWon();
        assertTrue(testRobot1.getLogic().hasWon());
    }

    @Test
    public void verifyThatRobotWentIntoHole() {
        testRobot1.isRobotInHole(true);
        assertTrue(testRobot1.isRobotInHole());
    }

    @Test
    public void verifyThatRobotIsNotInHole() {
        assertFalse(testRobot1.isRobotInHole());
    }
}