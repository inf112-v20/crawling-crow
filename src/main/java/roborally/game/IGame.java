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

    boolean checkIfSomeoneWon();

    RobotCore getWinner();

    void endGame();

    void revealProgramCards();
}
