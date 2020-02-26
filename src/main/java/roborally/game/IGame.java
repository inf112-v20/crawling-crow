package roborally.game;

import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.Robot;
import roborally.ui.gameboard.Layers;

public interface IGame {
    /**
     * Serves ONLY feed the keyUp method..
     */

    Layers getLayers();

    /**
     * Serves ONLY feed the keyUp method..
     */

    AI[] getAIRobots();

    Robot getRobots();

    /**
     * Serves ONLY feed the keyUp method..
     */

    IGameBoard getGameBoard();

    void startGame();

    void startNewRound();

    boolean isRunning();

    RoundStep currentRoundStep();

    PhaseStep currentPhaseStep();

    void announcePowerDown();

    void dealCards();

    void programRobots();

    void revealProgramCards();

    void moveRobots();

    void moveAllConveyorBelts();

    void moveExpressConveyorBelts();

    void moveCogs();

    void fireLasers();

    void allowMovingBackupPoints();

    void registerFlagPositions();

    boolean checkIfSomeoneWon();

    Robot getWinner();

    void endGame();

    void exitGame();
}
