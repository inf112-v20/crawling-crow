package roborally.game.cards;

import java.util.*;

public class ProgramCards implements IProgramCards {
    private ArrayList<Card> deckOfCards;
    private Queue<Card> nextCard;

    public ProgramCards() {
        this.deckOfCards = new ArrayList<>();
        putAllCardsIntoDeck();
        shuffleCards();
        this.nextCard = new LinkedList<>();
        this.nextCard.addAll(deckOfCards);
    }

    private void putAllCardsIntoDeck() {
        for (CardType type : CardType.values()) {
            for (int i = 0; i < type.getNumberOfCards(); i++) {
                this.deckOfCards.add(new Card(type));
            }
        }
    }

    @Override
    public void shuffleCards() {
        Collections.shuffle(deckOfCards);
    }

    @Override
    public ArrayList<Card> getDeck() {
        return deckOfCards;
    }

    @Override
    public Card getNextCard() {
        if(nextCard.isEmpty()) {
            shuffleCards();
            nextCard.addAll(this.deckOfCards);
        }
        return nextCard.remove();
    }
}