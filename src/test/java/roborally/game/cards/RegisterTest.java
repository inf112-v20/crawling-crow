package roborally.game.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RegisterTest {
    private Register register;
    private IProgramCards.Card card1;
    private IProgramCards.Card card2;
    private ArrayList<IProgramCards.Card> cards;
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
        cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
    }

    @Test
    public void VerifyNewRegisterIsEmpty() {
        assertEquals(0, (new Register()).size());
    }

    @Test
    public void VerifyCardIsPutIntoRegisterIsTheCardWeGetOut() {
        register.add(card1);
        assertEquals(card1, register.getNextCard());
    }

    @Test
    public void VerifyCardIsPutIntoRegisterIsNotAnotherCard() {
        register.add(card1);
        assertNotEquals(card2, register.getNextCard());
    }

    @Test
    public void VerifyFirstCardInIsFirstCardOut() {
        register.add(card1);
        register.add(card2);
        assertEquals(card1, register.getNextCard());
    }

    @Test
    public void VerifySecondCardInIsSecondCardOut() {
        register.add(card1);
        register.add(card2);
        register.getNextCard();
        assertEquals(card2, register.getNextCard());
    }

    @Test
    public void VerifyThatWhenAddingCardsAsArrayListCorrectNumberOfCardsAreAddedToRegister() {
        register.add(cards);
        assertEquals(cards.size(), register.size());
    }

    @Test
    public void VerifyThatWhenAddingCardsAsArraysListTheyAreAddedInCorrectOrderFIFO() {
        register.add(cards);
        assertEquals(cards.get(0), register.getNextCard());
    }

    @Test
    public void VerifyCleaningRegisterWithNoLockedCardsYieldAnEmptyRegister() {
        int lockedCards = 0;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(0, register.size());
    }

    @Test
    public void VerifyCleaningRegisterWith1LockedCardsYieldARegisterWithSize1() {
        int lockedCards = 1;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(1, register.size());
    }

    @Test
    public void VerifyCleaningRegisterWith2LockedCardsYieldARegisterWithSize2() {
        int lockedCards = 2;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(2, register.size());
    }

    @Test
    public void VerifyCleaningRegisterWith3LockedCardsYieldARegisterWithSize3() {
        int lockedCards = 3;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(3, register.size());
    }

    @Test
    public void VerifyCleaningRegisterWith4LockedCardsYieldARegisterWithSize4() {
        int lockedCards = 4;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(4, register.size());
    }

    @Test
    public void VerifyCleaningRegisterWith5LockedCardsYieldARegisterWithSize5() {
        int lockedCards = 5;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(5, register.size());
    }

    @Test
    public void VerifyCleaningRegisterWith1LockedCardsLocksTheFirstCardInPlace() {
        int lockedCards = 1;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(cards.get(0), register.getNextCard());
    }

    @Test
    public void VerifyCleaningRegisterWith2LockedCardsLocksTheFirstCardInPlace() {
        int lockedCards = 2;
        register.add(cards);
        register.cleanRegister(lockedCards);
        assertEquals(cards.get(0), register.getNextCard());
    }

    @Test
    public void VerifyCleaningRegisterWith2LockedCardsLocksTheSecondCardInPlace() {
        int lockedCards = 2;
        register.add(cards);
        register.cleanRegister(lockedCards);
        register.getNextCard();
        assertEquals(cards.get(1), register.getNextCard());
    }

    @Test
    public void VerifyCleaningRegisterWith3LockedCardsLocksTheThirdCardInPlace() {
        int lockedCards = 3;
        register.add(cards);
        register.cleanRegister(lockedCards);
        register.getNextCard();
        register.getNextCard();
        assertEquals(cards.get(2), register.getNextCard());
    }

    @Test
    public void VerifyCleaningRegisterWith4LockedCardsLocksTheFourthCardInPlace() {
        int lockedCards = 4;
        register.add(cards);
        register.cleanRegister(lockedCards);
        register.getNextCard();
        register.getNextCard();
        register.getNextCard();
        assertEquals(cards.get(3), register.getNextCard());
    }

    @Test
    public void VerifyCleaningRegisterWith5LockedCardsLocksTheFifthCardInPlace() {
        int lockedCards = 5;
        register.add(cards);
        register.cleanRegister(lockedCards);
        register.getNextCard();
        register.getNextCard();
        register.getNextCard();
        register.getNextCard();
        assertEquals(cards.get(4), register.getNextCard());
    }


}