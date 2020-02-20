package roborally.game;

import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.game.objects.robot.Robot;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.Layers;

public class Game implements IGame {
    private final boolean DEBUG = true;
    private final int NUMBER_OF_PHASES = 5;
    private final int NUMBER_OF_PLAYERS = 8;
    private IGameBoard gameBoard;
    private Layers layers;
    private AI[] airobots;
    private IRobot[] robots;
    private IRobot winner;
    private boolean gameRunning = false;
    private RoundStep roundStep = RoundStep.NULL_STEP;
    private PhaseStep phaseStep = PhaseStep.NULL_PHASE;


    public Game(){
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        AssetsManager.makeRobots();
        robots = AssetsManager.getRobots();
    }


    public Game(boolean runAIGame){
        assert(runAIGame);
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        AssetsManager.makeAIRobots();
        airobots = AssetsManager.getAIRobots();

    }

    @Override
    public Layers getLayers(){
        return this.layers;
    }

    @Override
    public AI[] getAirobots() {
        return airobots;
    }

    @Override
    public IGameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void startGame(){
        assert(!gameRunning);
        if(DEBUG){
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

        // For bad testing
        roundStep = RoundStep.PHASES;
        phaseStep = PhaseStep.CHECK_FOR_WINNER;
        winner = new Robot("Crazy");

        if(DEBUG) {
            System.out.println("Round started...");
            System.out.println("Entering " + roundStep + "...");
            System.out.println("Waiting for input..");
        }


        /*
        System.out.println("Round has started");
        announcePowerDown(); // Depends upon user input
        dealCards();
        programRobots(); // Depends upon user input
        winner = completeGameFases(); // Depends upon user input
        cleanUp();

         */
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

    private IRobot completeGameFases() {

        // TODO This is depricate...
        /*
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

        */
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

        // TODO: Implement a real check...
        winner = new Robot("BestRobot");

        return(winner != null);
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
        }
        cleanUp();
        gameRunning = false;
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
