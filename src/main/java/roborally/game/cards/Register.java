package roborally.game.cards;

import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class Register {
    private ArrayList<IProgramCards.Card> cards;
    private int nextCardID;

    public Register(){
        this.cards = new ArrayList<>();
        this.nextCardID = 0;
    }

    public void cleanRegister(int lockCards){
        System.out.println("Number of cards before cleaning: " + cards.size());
        while(cards.size() > lockCards){
            removeLastCard();
        }
        System.out.println(toString());
        this.nextCardID = 0;
    }

    private void removeLastCard() {
        cards.remove(cards.size()-1);
    }

    public void add(IProgramCards.Card card){
        cards.add(card);
    }

    public void add(ArrayList<IProgramCards.Card> newCardsToRegister) {
        cards.addAll(newCardsToRegister);
        if (cards.size() != SettingsUtil.REGISTER_SIZE){
            throw new IllegalStateException("The register should only hold 5 cards. Now holds " + cards.size());
        }
    }

    public IProgramCards.Card getNextCard() {
        if (cards.isEmpty()){
            throw new IllegalStateException("Cannot get next card, when register has no cards");
        }
        return cards.get(nextCardID++);
    }

    public String toString(){
        if(cards.size() == 0){
            return "[]";
        }
        String s = "[";
        for(IProgramCards.Card card : cards){
            s = s + card.getCardType() + ", ";
        }
        s = s.substring(0, s.length()-2) + "]";
        return s;
    }

    public int size(){
        return cards.size();
    }
}
