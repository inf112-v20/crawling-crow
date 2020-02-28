package roborally.game.objects;

import java.util.ArrayList;

public interface IProgramCards {
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

    /**
     * Shuffles the initial deck of program cards
     */
    void shuffleCards();

    /**
     * @return Deck of program cards
     */
    ArrayList<Card> getDeck();

    class Card {
        private CardTypes cardType;
        private int priority;
        private int priorityRangeMin;
        private int priorityRangeMax;

        public Card(CardTypes cardType){
            this.cardType = cardType;
            this.priorityRangeMin = 1;
            this.priorityRangeMax = 500;
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

        public int getPriorityRangeMin() {
            return this.priorityRangeMin;
        }

        public int getPriorityRangeMax() {
            return this.priorityRangeMax;
        }
    }

}
