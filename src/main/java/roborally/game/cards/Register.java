package roborally.game.cards;

import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class Register {
    private ArrayList<IProgramCards.Card> cards;
    private int nextCardID;

    /**
     * The registers holds up to five cards. New cards can be added one by one or in bulk.
     */
    public Register(){
        this.cards = new ArrayList<>();
        this.nextCardID = 0;
    }

    /**
     * Removes all cards that should not be locked in the register. Will remove last card in the register until only
     * the correct number of locked cards remain
     *
     * @param lockCards how many card to lock down.
     */
    public void cleanRegister(int lockCards){
        while(cards.size() > lockCards){
            removeFirstCard();
        }
        this.nextCardID = 0;
    }

    /**
     * Add a Cards to the end of the register. If any cards are locked into position, the new card
     * will end up the next vacant positions.
     * @param card the card to be added to the register
     */
    public void add(IProgramCards.Card card){
        cards.add(card);
        if (cards.size() > SettingsUtil.REGISTER_SIZE){
            throw new IllegalStateException("The register should max hold 5 cards. Now holds " + cards.size());
        }
    }

    /**
     * Adds an ArrayList of Cards to the end of the register. If any cards are locked into position, the new cards
     * will end up the next vacant positions.
     * @param newCardsToRegister the cards to be added to the register
     */
    public void add(ArrayList<IProgramCards.Card> newCardsToRegister) {
        cards.addAll(newCardsToRegister);
        if (cards.size() > SettingsUtil.REGISTER_SIZE){
            throw new IllegalStateException("The register should max hold 5 cards. Now holds " + cards.size());
        }
    }

    /**
     * Returns the next in register and changes which card to return for next method call.
     * @return the next card in the register
     */
    public IProgramCards.Card getNextCard() {
        if (cards.isEmpty()){
            throw new IllegalStateException("Cannot get next card, when register has no cards");
        }
        return cards.get(nextCardID++);
    }

    /**
     * Returns the next card in register without changing which card is next in play.
     * @return next card in the register.
     */
    public IProgramCards.Card peekNextCard(){
        if (cards.isEmpty()){
            return null;
        }
        return cards.get(nextCardID);
    }

    /**
     *
     * @return a string representation of the register
     */
    public String toString(){
        if(cards.size() == 0){
            return "[]";
        }
        StringBuilder s = new StringBuilder("[");
        for(IProgramCards.Card card : cards){
            s.append(card.getCardType()).append(", ");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2) + "]");
        return s.toString();
    }

    /**
     *
     * @return the number of cards currently in the register
     */
    public int size(){
        return cards.size();
    }

    private void removeFirstCard() {
        cards.remove(0);
    }
}
