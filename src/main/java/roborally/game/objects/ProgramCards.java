package roborally.game.objects;

import java.util.ArrayList;

public class ProgramCards {
    private enum CardTypes {
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
        for (int i = 0; i < CardTypes.MOVE_1.getNumberOfCards(); i++) {
            // TODO: implement card generation
        }
    }

    public class Card {
        private String operation;
        private int value;
        private int priority;

        public Card(String o, int v){
            this.operation = o;
            this.value = v;
            this.priority = 1 + (int)(Math.random() * 500);
        }

        public String getCard() {
            return this.operation + " " + this.value + " " + this.priority;
        }

    }
    public ArrayList<Card> getDeck() {
        return this.deckOfCards;
    }
}