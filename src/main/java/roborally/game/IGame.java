package roborally.game;

import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.RobotCore;
import roborally.ui.gameboard.Layers;

public interface IGame {
    /**
     * Serves ONLY feed the keyUp method..
     */

    Layers getLayers();

    /**
     * Serves ONLY feed the keyUp method..
     */

    AI[] getAirobots();

    /**
     * Serves ONLY feed the keyUp method..
     */

    IGameBoard getGameBoard();

    void startGame();

    void startNewRound();

    RoundStep currentRoundStep();

    boolean isRunning();

    PhaseStep currentPhaseStep();

    void moveAllConveyorBelts();

    void moveExpressConveyorBelts();

    void moveCogs();

    void fireLasers();

    void allowMovingBackupPoints();

    void registerFlagPositons();

    boolean checkIfSomeoneWon();

    RobotCore getWinner();

    void endGame();

    void moveRobots();

    void revealProgramCards();

    void programRobots();

    void dealCards();

    void announcePowerDown();
}
