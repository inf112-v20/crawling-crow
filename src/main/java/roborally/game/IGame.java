package roborally.game;

import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.ProgramCardsView;

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
    Robot getFirstRobot();

    void funMode();

    void startUp();

    /**
     * Fire the laser of the first robot. Only used for debugging.
     */
    void manuallyFireOneLaser();

    void restartGame();

    ArrayList<Robot> getRobots();

    boolean isRunning();

    GameOptions getGameOptions();

    ProgramCardsView dealCards();

    void shuffleTheRobotsCards(int[] order);

    boolean hasAllPlayersChosenCards();

    void endGame();

    void exitGame();

    IRound getRound();

    float continueGameLoop(float dt, double gameSpeed);
}
