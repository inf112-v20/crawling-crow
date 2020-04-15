package roborally.game.gameboard.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.cards.CardsInHand;
import roborally.game.cards.IProgramCards;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;

public interface IRobotLogic {
    /**
     * @return Robot's name
     */
    String getName();

    /**
     * @return The Robot's remaining health
     */
    int getHealth();

    void addHealth(int amount);

    /**
     * @return The Robot's number of Reboots remaining
     */
    int getReboots();

    /**
     * This returns different message according to the remaining health
     * for the Robot.
     * The different messages are the following:
     * - Badly damaged
     * - Destroyed
     * - Everything is OK!
     * - RobotName is gone
     *
     * @return A status message based on the Robot's state
     */
    String getStatus();

    /**
     * @return The Robot's position
     */
    GridPoint2 getPosition();

    /**
     * Sets a new position for the Robot
     *
     * @param newPosition A new position
     */
    void setPosition(GridPoint2 newPosition);

    /**
     * @return The Robot's Archive Marker position
     */
    GridPoint2 getArchiveMarker();

    /**
     * Sets a new position for the Robot's Archive Marker
     *
     * @param newArchiveMarkerPosition The new position of the Archive Marker
     */
    void setArchiveMarker(GridPoint2 newArchiveMarkerPosition);

    /**
     * Returns the Robot to its Archive Marker.
     */
    void backToArchiveMarker();

    /**
     * Rotates the Robot in the correct direction according to
     * the new direction.
     *
     * @param newDirection The new direction the Robot is facing
     */
    void rotate(Direction newDirection);

    /**
     * @return The direction the Robot is facing
     */
    Direction getDirection();

    /**
     * Adds the inflicted damage to the Robot's health and checks
     * if the Robot's health is less than or equal to 0 and that
     * it has more reboots remaining.
     *
     * @param damageInflicted The damage inflicted
     * @return True if the Robot can reboot, false otherwise
     */
    boolean takeDamage(int damageInflicted);


    /**
     * Defines which cards are on Robot's hand
     *
     * @param newCardsInHand The new cards that are going in the Robot's hand
     */
    void setCardsInHand(CardsInHand newCardsInHand);

    /**
     * @return The Cards in the hand of the Robot
     */
    ArrayList<IProgramCards.Card> getCardsInHand();

    /**
     * Arranges the cards in the hand of the Robot in a new order.
     *
     * @param newOrder The new order that the cards are to be arranged in.
     */
    void arrangeCardsInHand(int[] newOrder);

    /**
     * @return The next card in the Robot's hand and removes it
     */
    IProgramCards.Card getNextCardInHand();

    /**
     * @return The next card in the Robot's hand
     */
    IProgramCards.Card peekNextCardInHand();

    /**
     * Initiate that the Robot has selected its cards.
     *
     * @param value True if selected, false otherwise
     */
    void setHasSelectedCards(boolean value);

    /**
     * @return True if cards has been selected, false otherwise
     */
    boolean isCardsSelected();

    /**
     * @param deckOfProgramCards all the cards in the deck
     * @return the cards drawn depending on your health.
     */
    IProgramCards drawCards(IProgramCards deckOfProgramCards);

    void autoArrangeCardsInHand();

    //region Flag

    void cleanRegister();

    /**
     * sets the number of flags on the board
     * @param flags flags
     */
    void setNumberOfFlags(int flags);

    /**
     * @return the next flag in the list to be visited
     */
    int getNextFlag();

    /**
     * checks if you have visited all the flags
     * @return true or false
     */
    boolean hasVisitedAllFlags();

    /**
     * Checks what flags you need to visit next
     * If all flags have been collected press W
     */
    void visitNextFlag();

    void putChosenCardsIntoRegister();
    //endregion
}
