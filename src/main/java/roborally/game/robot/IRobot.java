package roborally.game.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.laser.Laser;
import roborally.gameview.robotview.IRobotView;
import roborally.utilities.enums.Direction;

public interface IRobot {
    /**
     * @return Robot's name
     */
    String getName();

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
     * Returns the Robot to its Archive Marker.
     */
    void backToArchiveMarker();

    /**
     * Moves the Robot in the right direction with the correct
     * amount of steps.
     *
     * @param steps The amount of steps
     */
    void move(int steps);

    /**
     * Checks if Robot can move to specific position, if it
     * collide with another Robot and/or steps on a hole.
     *
     * @param possiblePosition The possible position that is going to be checked
     */
    void tryToMove(GridPoint2 possiblePosition);

    /**
     * Rotates the Robot in the correct direction according to
     * the new direction.
     *
     * @param newDirection The new direction the Robot is facing
     */
    void rotate(Direction newDirection);

    /**
     * Adds the inflicted damage to the Robot's health and checks
     * if the Robot can move back to its Archive marker.
     *
     * @param damageInflicted The damage inflicted
     */
    void takeDamage(int damageInflicted);

    /**
     * @return The Logic part of the Robot
     */
    IRobotLogic getLogic();

    /**
     * @return The UI/Viewable part of the Robot
     */
    IRobotView getView();

    /**
     * Fires a laser in the direction the Robot is facing.
     */
    void fireLaser();

    /**
     * @return The Robot's laser
     */
    Laser getLaser();

    /**
     * @return True if Robot is standing in a stationary laser, false otherwise
     */
    boolean checkForStationaryLaser();

    /**
     * Clears the Robot's laser.
     */
    void clearLaserRegister();

    /**
     * Manually set the texture region of the Robot
     *
     * @param index Texture's index
     */
    void setTextureRegion(int index);

    /**
     * @return The current Robot texture
     */
    TextureRegion[][] getTexture();

    /**
     * Sets the Victory texture to the Robot
     */
    void setVictoryTexture();

    /**
     * Sets the Damage Taken texture to the Robot
     */
    void setDamageTakenTexture();

    /**
     * Deletes Robot and sets its position to the Graveyard.
     */
    void deleteRobot();

    /**
     * Plays the Robot's next card in hand.
     */
    void playNextCard();

    /**
     * @return true if a robot is falling and false if not
     */
	boolean isFalling();

    /**
     * @param falling true if a robot is falling, otherwise false
     */
    void setFalling(boolean falling);

    /**
     * Peeks
     *
     * @return the Robot's next card in hand.
     */
    int peekNextCardInRegister();

    /**
     * @return true if a robot is in a hole and false if not
     */
    boolean isRobotInHole();

    /**
     * @param isInHole true if a robot is in a hole
     */
    void isRobotInHole(boolean isInHole);
}