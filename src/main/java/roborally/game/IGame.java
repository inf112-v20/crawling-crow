package roborally.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.RobotPresenter;
import roborally.ui.ILayers;
import roborally.ui.MakeCards;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

import java.util.ArrayList;

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

    RobotPresenter getRobots();

    boolean funMode();

    /**
     * Serves ONLY feed the keyUp method..
     */

    IGameBoard getGameBoard();

    void restartGame();

    void startGame();

    boolean getMenu();

    void enterMenu();

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

    RobotPresenter getWinner();

    public MakeCards getCards();

    public void shuffleTheRobotsCards(int[] order);

    void endGame();

    void exitGame();
}
