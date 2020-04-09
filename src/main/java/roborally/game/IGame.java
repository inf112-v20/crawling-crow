package roborally.game;

import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.ProgramCardsView;

import java.util.ArrayList;

public interface IGame {


    /**
     * Serves ONLY feed the keyUp method..
     *
     * @return the layers of the gameboard
     */
    ILayers getLayers();

    /**
     * Exists only for debugging.
     *
     * @return The first of the robots
     */
    Robot getUserRobot();

    void funMode();

    void startUp();

    /**
     * Fire the laser of the first robot. Only used for debugging.
     */
    void manuallyFireOneLaser();

    void restartGame();

    ArrayList<Robot> getRobots();

    GameOptions getGameOptions();

    ProgramCardsView dealCards();

    void shuffleTheRobotsCards(int[] order);

    boolean hasAllPlayersChosenCards();

    void endGame();

    void exitGame();

    IRound getRound();

    float continueGameLoop(float dt, double gameSpeed);
}
