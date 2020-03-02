package roborally.game;

import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.ui.ILayers;

public interface IGame {
    /**
     * Serves ONLY feed the keyUp method..
     * @return
     */

    ILayers getLayers();

    /**
     * Serves ONLY feed the keyUp method..
     */

    AI[] getAIRobots();

    IRobot getRobots();

    boolean funMode();

    /**
     * Serves ONLY feed the keyUp method..
     */

    IGameBoard getGameBoard();

    void restartGame();

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

    IRobot getWinner();

    void endGame();

    void exitGame();
}
