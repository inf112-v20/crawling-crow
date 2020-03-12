package roborally.game.objects.cards;

import java.util.ArrayList;

public class PlayCards {
    private ArrayList<IProgramCards.Card> cards;

    public PlayCards(ArrayList<IProgramCards.Card> cards) {
        this.cards = cards;
    }

    public void arrangeCards(int[] order) {
        ArrayList<IProgramCards.Card> temp = new ArrayList<>();
        for (int value : order) {
            temp.add(cards.get(value));
        }
        this.cards = temp;
    }
    public ArrayList<IProgramCards.Card> getCards() {
        return this.cards;
    }

}
