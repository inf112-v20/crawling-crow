package roborally.game;

import roborally.game.robot.Robot;
import roborally.game.structure.IRound;
import roborally.gameview.layout.ILayers;
import roborally.gameview.ui.ProgramCardsView;

import java.util.ArrayList;

public interface IGame {

    /**
     * Adds all elements to the game.
     * GameBoard, layers, flags, lasers, robots.
     * It also starts a new round and
     * sets the first robot in list as userRobot.
     */
    void startUp(String name);

    /**
     * @return the layers of the gameBoard
     */
    ILayers getLayers();

    void setUserRobot();

    /**
     * @return The first of the robots
     */
    Robot getUserRobot();

    /**
     * @return a list of all robots
     */
    ArrayList<Robot> getRobots();

    /**
     * Restarts the game, removes old robots and adds new ones
     */
    void restartGame();

    /**
     * @return the gameOptions in game.
     */
    GameOptions getGameOptions();

    /**
     * Fire the laser of the first robot.
     *
     * Only used for debugging.
     */
    void manuallyFireOneLaser();

    /**
     * Deal cards to all robots.
     */
    void dealCards();

    /**
     * Sets a robot's registered card view
     *
     * @param robot in question
     */
    void setRegisterCardsView(Robot robot);

    /**
     * @param order shuffles the cards drawn for the user robot.
     */
    void orderTheUserRobotsCards(int[] order);

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

    /**
     * @return a programCardView
     */
    ProgramCardsView getProgramCardsView();

    /**
     * Announces PowerDown for other robots.
     * This happens automatically if the robot has less than 3 HP.
     */
    void announcePowerDown();

    /**
     * @return true if a game has been restarted
     */
    boolean hasRestarted();

    /**
     * @param isRestarted restarts the game if true, otherwise false
     */
    void setHasRestarted(boolean isRestarted);

    /**
     * @return true if game is in debugMode
     */
    boolean inDebugMode();

    /**
     * @return true if a game has started
     */
    boolean hasStarted();

    boolean checkIfSomeoneWon();

    /**
     * @return the visual aspect of the registered program cards
     */
    ProgramCardsView getRegisterCardsView();
}
