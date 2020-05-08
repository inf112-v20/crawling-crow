package roborally.game.cards;

import java.util.ArrayList;

public interface IProgramCards {

    /**
     * Shuffles the initial deck of program cards
     */
    void shuffleCards();

    /**
     * @return a new card from the deck
     */
    Card getNextCard();

    /**
     * @return Deck of program cards
     */
    ArrayList<Card> getDeck();

    /**
     * Enums for the different cards
     * the {@link #nCards} represent the number cards in each category
     */
    enum CardType {
        MOVE_1(18),
        MOVE_2(12),
        MOVE_3(6),
        BACKUP(6),
        ROTATE_RIGHT(18),
        ROTATE_LEFT(18),
        U_TURN(6);


        private final int nCards;

        CardType(int nCards) {
            this.nCards = nCards;
        }

        public int getNumberOfCards() {
            return nCards;
        }
    }

    class Card {
        private final CardType cardType;
        private final int priority;
        private final int priorityRangeMin;
        private final int priorityRangeMax;

        public Card(CardType cardType) {
            this.cardType = cardType;
            this.priorityRangeMin = 1;
            this.priorityRangeMax = 500;
            this.priority = priorityRangeMin + (int) (Math.random() * priorityRangeMax);
        }

        public String toString() {
            return cardType + " " + priority;
        }

        /**
         * @return int value representing the card
         */
        public int getValue() {
            if (cardType.toString().contains("MOVE"))
                return Integer.parseInt(toString().substring(5, 6));
            else if (cardType.toString().contains("U_TURN"))
                return -180;
            else if (cardType.toString().contains("ROTATE"))
                return cardType.toString().contains("RIGHT") ? 90 : -90;
            return -1;
        }

        public CardType getCardType() {
            return cardType;
        }

        public int getPriority() {
            return priority;
        }

        public int getPriorityRangeMin() {
            return priorityRangeMin;
        }

        public int getPriorityRangeMax() {
            return priorityRangeMax;
        }
    }

}
