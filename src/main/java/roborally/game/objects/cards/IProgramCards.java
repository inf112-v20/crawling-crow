package roborally.game.objects.cards;

import java.util.ArrayList;

public interface IProgramCards {
    /**
     * Shuffles the initial deck of program cards
     */
    void shuffleCards();

    /**
     * @return Deck of program cards
     */
    ArrayList<Card> getDeck();

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

    class Card {
        private CardTypes cardType;
        private int priority;
        private int priorityRangeMin;
        private int priorityRangeMax;

        public Card(CardTypes cardType) {
            this.cardType = cardType;
            this.priorityRangeMin = 1;
            this.priorityRangeMax = 500;
            this.priority = priorityRangeMin + (int) (Math.random() * priorityRangeMax);
        }

        // TODO: @adipid. Er det en plan å bruke dette eller er dette deprecate? @thomas
        public String getCard() {
            return this.cardType + " " + this.priority;
        }

        public int getValue() {
            if ("MOVE".equals(cardType.toString().substring(0, 4)))
                return Integer.parseInt(getCard().substring(5, 6));
            else if ("U_TURN".equals(cardType.toString().substring(0,6)))
                return -180;
            else if ("ROTATE".equals(cardType.toString().substring(0, 6)))
                return "RIGHT".equals(getCard().substring(7, 12)) ? 90 : -90;
            return 0;
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
