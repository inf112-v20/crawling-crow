package roborally.game;

import roborally.game.objects.robot.AIPlayer;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.ProgramCardsView;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

import java.util.ArrayList;

public interface IGame {


    /**
     * Serves ONLY feed the keyUp method..
     *
     * @return the layers of the gameboard
     */
    ILayers getLayers();

    /**
     * Serves ONLY feed the keyUp method..
     *
     * @deprecated serves no purpose at the moment
     */
    @Deprecated
    AIPlayer[] getAIRobots();

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

    void startGame();

    void startNewRound();

    boolean isRunning();

    GameOptions getGameOptions();

    RoundStep currentRoundStep();

    PhaseStep currentPhaseStep();

    ProgramCardsView getCards();

    void shuffleTheRobotsCards(int[] order);

    void endGame();

    void exitGame();

    IRound getRound();
}
