package roborally.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.Layers;

public class Game implements IGame {
    private final int NUMBER_OF_PHASES = 5;
    private IGameBoard gameBoard;
    private Layers layers;
    private AI[] robots;
    private IRobot winner;


    public Game(){
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        AssetsManager.makeRobots();
        robots = AssetsManager.getRobots();
    }

    @Override
    public Layers getLayers(){
        return this.layers;
    }

    @Override
    public AI[] getRobots() {
        return robots;
    }

    @Override
    public IGameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void startRound() {
        System.out.println("Round has started");
        announcePowerDown(); // Depends upon user input
        dealCards();
        programRobots(); // Depends upon user input
        winner = completeGameFases(); // Depends upon user input
        cleanUp();
    }

    private void cleanUp() {
    }

    private IRobot completeGameFases() {

        for(int i = 0; i < NUMBER_OF_PHASES; i++) {
            revealProgramCard(i);
            moveRobots();
            moveAllConveyorBelts();
            moveExpressConveyorBelts();
            moveCogs();
            fireLasers();
            allowMovingBackupPoints();
            registerFlagPositons();
            winner = checkIfSomeoneWon();

            // Abort loop if winner is detected
            if(winner != null) return winner;
        }
        return null;
    }

    private void moveAllConveyorBelts() {
    }

    private void moveExpressConveyorBelts() {
    }

    private void moveCogs() {
    }

    private void fireLasers() {
    }

    private void allowMovingBackupPoints() {
    }

    private void registerFlagPositons() {
    }

    private IRobot checkIfSomeoneWon() {
        return null;
    }

    private void moveRobots() {

    }

    private void revealProgramCard(int i) {
    }

    private void programRobots() {
    }

    private void dealCards() {
    }

    private void announcePowerDown() {
    }
}
