package roborally.game;

import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.RobotPresenter;
import roborally.ui.ILayers;
import roborally.ui.gdx.MakeCards;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

public interface IGame {
    /**
     * Serves ONLY feed the keyUp method..
     *
     * @return
     */

    ILayers getLayers();

    /**
     * Serves ONLY feed the keyUp method..
     */

    AI[] getAIRobots();

    RobotPresenter getRobots();

    void funMode();

    void startUp();

    /**
     * Serves ONLY feed the keyUp method..
     */

    IGameBoard getGameBoard();

    void fireLaser();

    void restartGame();

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

    void moveRobots();

    void moveAllConveyorBelts();

    void moveExpressConveyorBelts();

    void moveCogs();

    void fireLasers();

    void allowMovingBackupPoints();

    void registerFlagPositions();

    boolean checkIfSomeoneWon();

    RobotPresenter getWinner();

    MakeCards getCards();

    void shuffleTheRobotsCards(int[] order);

    void playNextCard();

    void endGame();

    void exitGame();
}
