package roborally.game.objects.cards;

import java.util.ArrayList;
import java.util.Collections;

public class ProgramCards implements IProgramCards {
    private ArrayList<Card> deckOfCards;

    public ProgramCards() {
        deckOfCards = new ArrayList<>();
        for (CardTypes type : CardTypes.values()) {
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
        return this.deckOfCards;
    }
}