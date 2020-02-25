package roborally.game.objects;

import java.util.ArrayList;
import java.util.Collections;

public class ProgramCards {
    enum CardTypes {
        MOVE_1(18),
        MOVE_2(12),
        MOVE_3(6),
        BACKUP(6),
        ROTATE_RIGHT(18),
        ROTATE_LEFT(18),
        U_TURN(6);

        private final int nCards;
        CardTypes(int nCards) {
            this.nCards = nCards;
        }

        public int getNumberOfCards() {
            return nCards;
        }
    }

    private ArrayList<Card> deckOfCards;

    public ProgramCards() {
        deckOfCards = new ArrayList<>();

        for (CardTypes type : CardTypes.values()) {
            for (int i = 0; i < type.getNumberOfCards(); i++) {
                this.deckOfCards.add(new Card(type));
            }
        }
    }

    public void shuffleCards() {
        Collections.shuffle(deckOfCards);
    }

    public static class Card {
        private CardTypes cardType;
        private int priority;

        int priorityRangeMin = 1;
        int priorityRangeMax = 500;

        public Card(CardTypes cardType){
            this.cardType = cardType;
            this.priority = priorityRangeMin + (int)(Math.random() * priorityRangeMax);
        }

        public String getCard() {
            return this.cardType + " " + this.priority;
        }

        public CardTypes getCardType() {
            return this.cardType;
        }

        public int getPriority() {
            return this.priority;
        }
    }

    public ArrayList<Card> getDeck() {
        return this.deckOfCards;
    }
}