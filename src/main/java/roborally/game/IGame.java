package roborally.game;

import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.ProgramCardsView;

import java.util.ArrayList;

public interface IGame {

    /**
     * Adds all elements to the game.
     * Gameboard, layers, flags, lasers, robots.
     * It also starts a new round and
     * sets the first robot in list as userRobot.
     */
    void startUp();

    /**
     *  Adds all elements to the game
     *  Gameboard, layers, flags, lasers, robots.
     *  Gamespeed is also set to fastest.
     *  Starts a new round and
     *  sets userRobot as the first robot in the list.
     */
    void funMode();

    /**
     * Serves ONLY feed the keyUp method..
     *
     * @return the layers of the gameboard
     */
    ILayers getLayers();

    //region Robots
    void setUserRobot();

    /**
     * Exists only for debugging.
     *
     * @return The first of the robots
     */
    Robot getUserRobot();

    /**
     * @return a list of all robots
     */
    ArrayList<Robot> getRobots();

    /**
     * restars the game, removes old robots and adds new ones
     */
    void restartGame();

    /**
     * @return the gameoptions
     */
    GameOptions getGameOptions();

    /**
     * Fire the laser of the first robot. Only used for debugging.
     */
    void manuallyFireOneLaser();

    /**
     * Deal cards
     */
    void dealCards();

    /**
     * @param order shuffles the cards drawn for the userrobot.
     */
    void shuffleTheRobotsCards(int[] order);

    /**
     * @return checks if all players have registered cards.
     */
    boolean hasAllPlayersChosenCards();

    /**
     * If there is a winner , their name gets displayed
     * Then robots gets removed from the game.
     * And finally goes back to the main menu.
     */
    void endGame();

    /**
     * Exits the game by closing the window.
     */
    void exitGame();

    /**
     * @return the round.
     */
    IRound getRound();

    /**
     * Plays next registered card for each robot,
     * then continues to next phase until all phases are played.
     *
     * @param dt difference in time
     * @param gameSpeed of the game
     * @return a difference in time
     */
    float continueGameLoop(float dt, double gameSpeed);

    ProgramCardsView getProgramCardsView();

    /**
     *
     */
    void announcePowerDown();
}
