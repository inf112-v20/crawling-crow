package roborally.game.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProgramCardsTest {
    private IProgramCards programCards;
    private ProgramCards.Card card;

    @Before
    public void setUp() throws Exception {
        programCards = new ProgramCards();
        card = new ProgramCards.Card(IProgramCards.CardTypes.MOVE_1);
    }

    @Test
    public void checkNumberOfCards() {
        assertEquals(84, programCards.getDeck().size());
    }

    @Test
    public void generateNewCard() {
        ProgramCards.Card newCard = new ProgramCards.Card(IProgramCards.CardTypes.MOVE_1);
        assertNotNull(newCard);
    }

    @Test
    public void cardTypeIsMove1() {
        assertEquals("MOVE_1", card.getCardType().name());
    }

    @Test
    public void cardPriorityIsWithinRange() {
        assertTrue(card.priorityRangeMin <= card.getPriority() && card.getPriority() <= card.priorityRangeMax);
    }

    @Test
    public void cardsGetShuffled() {
        IProgramCards notShuffled = new ProgramCards();
        programCards.shuffleCards();

        assertNotEquals(notShuffled, programCards);
    }
}