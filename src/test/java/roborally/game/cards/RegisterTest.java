package roborally.game.cards;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class RegisterTest {
    private Register register;
    private IProgramCards.Card card1;
    private IProgramCards.Card card2;
    private IProgramCards.Card[] cards;
    private IProgramCards.Card card3;
    private IProgramCards.Card card4;
    private IProgramCards.Card card5;

    @Before
    public void setUp(){
        register = new Register();
        card1 = new IProgramCards.Card(IProgramCards.CardType.MOVE_1);
        card2 = new IProgramCards.Card(IProgramCards.CardType.MOVE_1);
        card3 = new IProgramCards.Card(IProgramCards.CardType.MOVE_1);
        card4 = new IProgramCards.Card(IProgramCards.CardType.MOVE_1);
        card5 = new IProgramCards.Card(IProgramCards.CardType.MOVE_1);
        cards = new IProgramCards.Card[5];
        cards[0] = card1;
        cards[1] = card2;
        cards[2] = card3;
        cards[3] = card4;
        cards[4] = card5;
        register.add(cards);
    }

    @Test
    public void VerifyNewRegisterIsEmpty() {
        assertEquals(0, (new Register()).getNumberOfLockedCards());
    }

    @Test
    public void VerifyFirstCardInIsFirstCardOut() {
        assertEquals(card1, register.getNextCard());
    }

    @Test
    public void VerifyFirstCardOutIsNotTheSecondCardInRegister() {
        assertNotEquals(card2, register.getNextCard());
    }

    @Test
    public void VerifySecondCardInIsSecondCardOut() {
        assertEquals(card2, register.peekAtPosition(2));
    }

    @Test
    public void VerifyThatWhenAddingCardsAsArrayCorrectNumberOfCardsAreAddedToRegister() {
        assertEquals(cards.length, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWithNoLockedCardsYieldAnEmptyRegister() {
        int lockedCards = 0;
        register.cleanRegister(lockedCards);
        assertEquals(0, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWith1LockedCardsYieldARegisterWithSize1() {
        int lockedCards = 1;
        register.cleanRegister(lockedCards);
        assertEquals(1, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWith2LockedCardsYieldARegisterWithSize2() {
        int lockedCards = 2;
        register.cleanRegister(lockedCards);
        assertEquals(2, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWith3LockedCardsYieldARegisterWithSize3() {
        int lockedCards = 3;
        register.cleanRegister(lockedCards);
        assertEquals(3, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWith4LockedCardsYieldARegisterWithSize4() {
        int lockedCards = 4;
        register.cleanRegister(lockedCards);
        assertEquals(4, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWith5LockedCardsYieldARegisterWithSize5() {
        int lockedCards = 5;
        register.cleanRegister(lockedCards);
        assertEquals(5, register.getNumberOfCardsInRegister());
    }

    @Test
    public void VerifyCleaningRegisterWith1LockedCardsLocksTheLastCardInPlace() {
        int lockedCards = 1;
        register.cleanRegister(lockedCards);
        assertEquals(card5, register.peekAtPosition(5));
    }

    @Test
    public void VerifyCleaningRegisterWith2LockedCardsLocksTheLastCardInPlace() {
        int lockedCards = 2;
        register.cleanRegister(lockedCards);
        assertEquals(card5, register.peekAtPosition(5));
    }

    @Test
    public void VerifyCleaningRegisterWith2LockedCardsLocksTheFourthCardInPlace() {
        int lockedCards = 2;
        register.cleanRegister(lockedCards);
        assertEquals(card4, register.peekAtPosition(4));
    }

    @Test
    public void VerifyCleaningRegisterWith3LockedCardsLocksTheThirdCardInPlace() {
        int lockedCards = 3;
        register.cleanRegister(lockedCards);
        assertEquals(card3, register.peekAtPosition(3));
    }

    @Test
    public void VerifyCleaningRegisterWith4LockedCardsLocksTheSecondCardInPlace() {
        int lockedCards = 4;
        register.cleanRegister(lockedCards);
        assertEquals(card2, register.peekAtPosition(2));
    }

    @Test
    public void VerifyCleaningRegisterWith5LockedCardsLocksTheFirstCardInPlace() {
        int lockedCards = 5;
        register.cleanRegister(lockedCards);
        assertEquals(card1, register.peekAtPosition(1));
    }
}