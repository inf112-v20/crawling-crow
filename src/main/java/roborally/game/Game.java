package roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.IFlag;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.game.objects.robot.Robot;
import roborally.tools.AssetManagerUtil;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import java.util.ArrayList;
import java.util.Random;

public class Game implements IGame {
    private final boolean DEBUG = true;

    private IGameBoard gameBoard;
    private ILayers layers;
    private AI[] aiRobots;
    private IRobot[] robots;
    private ArrayList<IFlag> flags;

    private IRobot winner;
    private boolean gameRunning = false;
    private RoundStep roundStep = RoundStep.NULL_STEP;
    private PhaseStep phaseStep = PhaseStep.NULL_PHASE;
    private int i;
    private boolean funMode;

    public Game() {
        i = 0;
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        flags = gameBoard.findAllFlags();
        robots = AssetManagerUtil.makeRobots();
    }

    @Override
    public boolean funMode() {
        if(!funMode) {
            funMode = true;
            return false;
        }
        robots = null;
        robots = new Robot[layers.getHeight()*layers.getWidth()];
        int it = 0;
        for(int j = 0; j < layers.getWidth(); j++) {
            for (int k = 0; k < layers.getHeight(); k++) {
                robots[it] = new Robot(j, k, k % 8);
                it++;
            }
        }
        AssetManagerUtil.setRobots(robots);
        System.out.println("Fun mode activated, click 'A' to fire all lasers, 'M' to randomly move all robots");
        return funMode;
    }

    public Game(boolean runAIGame){
        assert(runAIGame);
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        AssetManagerUtil.makeAIRobots();
        aiRobots = AssetManagerUtil.getAIRobots();
    }

    @Override
    public ILayers getLayers(){
        return this.layers;
    }

    @Override
    public AI[] getAIRobots() {
        return aiRobots;
    }

    @Override
    public IRobot getRobots() {
        if (this.i == 8) {
            this.i = 0;
        }
        return robots[0];
    }

    @Override
    public IGameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void startGame(){
        assert(!gameRunning);
        if(DEBUG){
            System.out.println("\nGame started...");
        }
        gameRunning = true;
        startNewRound();
    }

    @Override
    public void restartGame() {
        for (IRobot robot : robots) {
            robot.clearLaser();
            robot.backToCheckPoint();
            GridPoint2 robotPos = new GridPoint2((int)robot.getPosition().x, (int)robot.getPosition().y);
        }
    }

    @Override
    public void startNewRound() {
        assert(gameRunning);
        assert(roundStep == RoundStep.NULL_STEP);
        assert(phaseStep == PhaseStep.NULL_PHASE);

        roundStep = RoundStep.ANNOUNCE_POWERDOWN;

        if(DEBUG) {
            System.out.println("\nRound started...");
            System.out.println("Entering " + roundStep + "...");
            System.out.println("Waiting for input..");
        }
    }

    @Override
    public RoundStep currentRoundStep(){
        return roundStep;
    }

    @Override
    public boolean isRunning(){
        return gameRunning;
    }

    @Override
    public PhaseStep currentPhaseStep() {
        return phaseStep;
    }

    // It reset game states at the end of a round
    private void cleanUp() {
        assert(gameRunning);
        roundStep = RoundStep.NULL_STEP;
        phaseStep = PhaseStep.NULL_PHASE;
    }

    @Override
    public void moveAllConveyorBelts() {
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void moveExpressConveyorBelts() {
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void moveCogs() {
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void fireLasers() {
        for (IRobot robot : robots)
            robot.fireLaser();
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void allowMovingBackupPoints() {
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void registerFlagPositions() {
        System.out.println("\nChecking if any robots have currently arrived at their next flag position...");
        for (IFlag flag : flags) {
            int flagX = flag.getPos().x;
            int flagY = flag.getPos().y;
            for (IRobot robot : robots) {
                int robotX = (int) robot.getPosition().x;
                int robotY = (int) robot.getPosition().y;
                if (flagX == robotX && flagY == robotY) {
                    int nextFlag = robot.getNextFlag();
                    if (flag.getId() == nextFlag) {
                        robot.visitNextFlag();
                        System.out.println("A flag has been visited");
                    }
                }
            }
        }
    }

    @Override
    public boolean checkIfSomeoneWon() {
        assert(gameRunning);
        assert(roundStep == RoundStep.PHASES);
        assert(phaseStep == PhaseStep.CHECK_FOR_WINNER);
        if(DEBUG) System.out.println("\nChecking if someone won...");

        boolean someoneWon = checkAllRobotsForWinner();
        if(someoneWon){
            endGame();
        }

        if(DEBUG) System.out.println("Found winner: " + someoneWon);
        return someoneWon;
    }

    private boolean checkAllRobotsForWinner() {
        assert(gameRunning);
        assert(roundStep == RoundStep.PHASES);
        assert(phaseStep == PhaseStep.CHECK_FOR_WINNER);
        checkAllRobotsAreCreated();

        for(IRobot robot : robots){
            if (robot.hasVisitedAllFlags()){
                winner = robot;
            }
        }

        return(winner != null);
    }

    private boolean checkAllRobotsAreCreated() {
        boolean robotsAreCreated =true;
        if(robots == null) {
            robotsAreCreated = false;
        } else {
            for (IRobot robot : robots) {
                if (robot == null) {
                    robotsAreCreated = false;
                    break;
                }
            }
        }
        if(!robotsAreCreated){
            throw new IllegalStateException("Robots are not created");
        }
        return true;
    }

    @Override
    public IRobot getWinner() {
        return winner;
    }

    @Override
    public void endGame() {
        assert(gameRunning);
        //if(DEBUG){
            //System.out.println("Stopping game...");
        //}
        cleanUp();
        gameRunning = false;
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public void moveRobots() {
        Random r = new Random();
        int m;
        for(IRobot robot : robots) {
            m = r.nextInt(4);
            if(m == 0)
                robot.turnLeft();
            else if(m == 1)
                robot.moveForward();
            else if(m == 2)
                robot.moveBackward();
            else if(m == 3)
                robot.turnRight();
        }
    }

    @Override
    public void revealProgramCards() {
        // TODO: Implement simple method to make some use of our ProgramCards class.
    }

    @Override
    public void programRobots() {
        // TODO: Implement some simple method to make some use of ProgramCards.
    }

    @Override
    public void dealCards() {
        // TODO: Implement some simple method to make some use of ProgramCards.
    }

    @Override
    public void announcePowerDown() {
        // TODO: Implement some damage system.
    }
}
