package roborally.game.cards;

import roborally.game.cards.IProgramCards.Card;
import roborally.utilities.SettingsUtil;


public class Register {
    private final Card[] cards;
    private int nextCardID;
    private int lockedCards;

    /**
     * The registers holds up to five cards. New cards can be added one by one or in bulk.
     */
    public Register(){
        this.cards = new Card[SettingsUtil.REGISTER_SIZE];
        this.nextCardID = 0;
        this.lockedCards = 0;

        if (SettingsUtil.DEBUG_MODE) {
            for (int i = 0; i < SettingsUtil.REGISTER_SIZE; i++) {
                cards[i] = new Card(IProgramCards.CardType.MOVE_1);
            }
        }
    }

    /**
     * Removes all cards that should not be locked in the register. Will remove last card in the register until only
     * the correct number of locked cards remain
     *
     * @param lockCards how many card to lock down.
     */
    public void cleanRegister(int lockCards) {
        this.lockedCards = lockCards;
        for (int i = 0; i < getNumberOfCardsToPutIntoRegister(); i++){
            cards[i] = null;
        }
        this.nextCardID = 0;
    }

    /**
     * Adds an ArrayList of Cards to the start of the register. If any cards are locked into position, the new cards
     * will end up the next vacant positions.
     *
     * @param newCardsToRegister the cards to be added to the register
     */
    public void add(Card[] newCardsToRegister) {
        if (newCardsToRegister.length != getNumberOfCardsToPutIntoRegister()){
            throw new IllegalStateException("Number of cards do not match number of available slots in registers");
        }

        for (int i = 0; i < newCardsToRegister.length; i++){
            add(newCardsToRegister[i], i);
        }
    }

    /**
     * Returns the next in register and changes which card to return for next method call.
     *
     * @return the next card in the register
     */
    public IProgramCards.Card getNextCard() {
        return cards[nextCardID++];
    }

    /**
     * Returns the next card in register without changing which card is next in play.
     *
     * @return next card in the register.
     */
    public IProgramCards.Card peekNextCard(){
        return cards[nextCardID];
    }

    /**
     * @return a string representation of the register
     */
    public String toString(){
        StringBuilder s = new StringBuilder("[");
        for(IProgramCards.Card card : cards){
            if(card != null) {
                s.append(card.getCardType()).append(", ");
            } else {
                s.append("null, ");
            }
        }
        s = new StringBuilder(s.substring(0, s.length() - 2) + "]");
        return s.toString();
    }


    /**
     * @return the number of locked cards in the register
     */
    public int getNumberOfLockedCards(){
        return lockedCards;
    }

    /**
     * @return the number of available slots in the register
     */
    public int getNumberOfCardsToPutIntoRegister() {
        return SettingsUtil.REGISTER_SIZE - lockedCards;
    }

    /**
     * @return the number of cards in the register, locked and not locked
     */
    public int getNumberOfCardsInRegister(){
        int counter = 0;
        for (Card card : cards){
            if (card != null){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Peeks at card
     *
     * @param position between 1 and 5, representing the position in the register that you want to look at
     * @return the card in requested position. 1 represents the first position.
     */
    public Card peekAtPosition(int position){
        return cards[position-1];
    }


    /**
     * Add a Cards to a specific location in the of the register. If any cards are locked into position, the new card
     * will end up the next vacant positions.
     *
     * @param card  the card to be added to the register
     */
    private void add(Card card, int position){
        if (position >= getNumberOfCardsToPutIntoRegister()){
            throw new IllegalStateException("Position " + position + "is locked. There is a total of " + lockedCards +
                    "locked cards.");
        }
        cards[position] = card;
    }

    public Card[] getCards() {
        return cards;
    }
}
