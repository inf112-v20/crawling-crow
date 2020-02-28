package roborally.game;

import roborally.game.objects.Flag;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.tools.AssetsManager;
import roborally.ui.Layers;

import java.util.ArrayList;

public class Game implements IGame {
    private final boolean DEBUG = true;

    private IGameBoard gameBoard;
    private Layers layers;
    private AI[] aiRobots;
    private IRobot[] robots;
    private ArrayList<Flag> flags;

    private IRobot winner;
    private boolean gameRunning = false;
    private RoundStep roundStep = RoundStep.NULL_STEP;
    private PhaseStep phaseStep = PhaseStep.NULL_PHASE;
    private int i;


    public Game(){
        i = 0;
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        flags = gameBoard.findAllFlags();
        robots = AssetsManager.makeRobots();
    }


    public Game(boolean runAIGame){
        assert(runAIGame);
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        AssetsManager.makeAIRobots();
        aiRobots = AssetsManager.getAIRobots();

    }

    @Override
    public Layers getLayers(){
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
            System.out.println();
            System.out.println("Game started...");
        }
        gameRunning = true;
        startNewRound();
    }

    @Override
    public void startNewRound() {
        assert(gameRunning);
        assert(roundStep == RoundStep.NULL_STEP);
        assert(phaseStep == PhaseStep.NULL_PHASE);

        roundStep = RoundStep.ANNOUNCE_POWERDOWN;

        // TODO: REMOVE from here. This just makes the robot "Crazt" autowin the game.
        //roundStep = RoundStep.PHASES;
        //phaseStep = PhaseStep.CHECK_FOR_WINNER;
        //winner = new Robot("Crazy");
        // TODO: REMOVE until here

        if(DEBUG) {
            System.out.println("Round started...");
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
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void allowMovingBackupPoints() {
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void registerFlagPositions() {
        for (Flag flag : flags) {
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
        if(DEBUG) System.out.println("Checking if someone won...");

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
        if(DEBUG){
            System.out.println("Stopping game...");
            System.out.println();
        }
        cleanUp();
        gameRunning = false;
    }

    @Override
    public void exitGame() {
        System.exit(0);
    }

    @Override
    public void moveRobots() {
        // Redundant?
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
