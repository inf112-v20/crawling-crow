package roborally.game.objects.cards;

import java.util.ArrayList;
import java.util.Arrays;

public class CardsInHand {
    private ArrayList<IProgramCards.Card> cards;

    public CardsInHand(ArrayList<IProgramCards.Card> cards) {
        this.cards = cards;
    }

    public void arrangeCards(int[] order) {
        ArrayList<IProgramCards.Card> temp = new ArrayList<>();
        System.out.println(cards.toString());
        for (int value : order) {
            temp.add(cards.get(value));
        }
        this.cards = temp;
    }
    public ArrayList<IProgramCards.Card> getCards() {
        return this.cards;
    }

}
