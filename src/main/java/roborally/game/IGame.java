package roborally.game;

import roborally.game.objects.robot.AIPlayer;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.ProgramCardsView;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

import java.util.ArrayList;

public interface IGame {

    void testEndPhase();

    void rotateConveyorBelts(ArrayList<Robot> rotateRobots);

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
    void fireLaser();

    void restartGame();

    ArrayList<Robot> getRobots();

    void startGame();

    void checkForDestroyedRobots();

    void startNewRound();

    boolean isRunning();

    GameOptions getGameOptions();

    RoundStep currentRoundStep();

    PhaseStep currentPhaseStep();

    void announcePowerDown();

    void dealCards();

    void programRobots();

    void revealProgramCards();

    void moveAllConveyorBelts();

    void moveNormalConveyorBelts();

    void moveExpressConveyorBelts();

    void moveCogs();

    void fireLasers();

    void restoreRebootedRobots();

    void registerFlagPositions();

    boolean checkIfSomeoneWon();

    Robot getWinner();

    ProgramCardsView getCards();

    void shuffleTheRobotsCards(int[] order);

    void robotPlayNextCard();

    void endGame();

    void exitGame();
}
