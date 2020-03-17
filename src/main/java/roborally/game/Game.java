package roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.PlayCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.MakeCards;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

import java.util.ArrayList;
import java.util.Random;

public class Game implements IGame {
    private final boolean DEBUG = true;

    //region gameobjects
    private IGameBoard gameBoard;
    private ILayers layers;
    private AI[] aiRobots;
    private ArrayList<Robot> robots;
    private ArrayList<IFlag> flags;
    //endregion

    private Robot winner;
    private boolean gameRunning = false;
    private RoundStep roundStep = RoundStep.NULL_STEP;
    private PhaseStep phaseStep = PhaseStep.NULL_PHASE;
    private int currentRobotID;
    private Events events;
    private GameOptions gameOptions;
    private boolean fun;

    public Game(Events events) {
        currentRobotID = 0;
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        flags = gameBoard.findAllFlags();
        this.events = events;
        this.gameOptions = new GameOptions();
    }


    public Game(boolean runAIGame) {
        assert (runAIGame);
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        AssetManagerUtil.makeAIRobots();
        aiRobots = AssetManagerUtil.getAIRobots();
    }

    @Override
    public void startUp() {
        robots = AssetManagerUtil.makeRobots();
    }

    @Override
    public void funMode() {
        robots = gameOptions.funMode(layers, flags);
        this.events.setGameSpeed("fastest");
        fun = true;
    }

    @Override
    public void checkForDestroyedRobots() {
        for (Robot robot : robots) {
            if (robot.getModel().getStatus().equals("Destroyed")) {
                removeFromUI(robot, true);
            }
        }
    }

    private void removeFromUI(Robot robot, boolean fade) {
        events.fadeRobot(robot.getPos(), robot.getTexture());
        layers.setRobotCell(robot.getPos().x, robot.getPos().y, null);
        robot.setPos(SettingsUtil.GRAVEYARD);
        robot.clearRegister();
        this.events.setFadeRobot(fade);
    }

    @Override
    public ILayers getLayers() {
        return this.layers;
    }

    @Override
    public AI[] getAIRobots() {
        return aiRobots;
    }

    @Override
    public Robot getFirstRobot() {

        if (this.currentRobotID == robots.size()) {
            this.currentRobotID = 0;
        }
        checkForDestroyedRobots();
        return robots.get(0);
    }

    @Override
    public ArrayList<Robot> getRobots(){
        return this.robots;
    }

    @Override
    public void startGame() {
        assert (!gameRunning);
        if (DEBUG) {
            System.out.println("\nGame started...");
        }
        gameRunning = true;
        startNewRound();
    }

    @Override
    public void restartGame() {
        System.out.println("Restarting game...");
        for (Robot robot : robots) {
            removeFromUI(robot, false);
        }
        setRobots(AssetManagerUtil.makeRobots());
    }

    @Override
    public void startNewRound() {
        assert (gameRunning);
        assert (roundStep == RoundStep.NULL_STEP);
        assert (phaseStep == PhaseStep.NULL_PHASE);

        roundStep = RoundStep.ANNOUNCE_POWERDOWN;

        if (DEBUG) {
            System.out.println("\nRound started...");
            System.out.println("Entering " + roundStep + "...");
            System.out.println("Waiting for input..");
        }
    }

    @Override
    public RoundStep currentRoundStep() {
        return roundStep;
    }

    @Override
    public boolean isRunning() {
        return gameRunning;
    }

    @Override
    public GameOptions getGameOptions() {
        return this.gameOptions;
    }

    @Override
    public PhaseStep currentPhaseStep() {
        return phaseStep;
    }

    // It reset game states at the end of a round
    private void cleanUp() {
        assert (gameRunning);
        roundStep = RoundStep.NULL_STEP;
        phaseStep = PhaseStep.NULL_PHASE;
    }

    @Override
    public void fireLaser() {
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.2);
        robots.get(0).fireLaser();
        ArrayList<GridPoint2> coords = robots.get(0).getLaser().getCoords();
        if (!coords.isEmpty())
            events.createNewLaserEvent(robots.get(0).getPos(), coords.get(coords.size() - 1));
    }

    @Override
    public MakeCards getCards() {
        //TODO Refactor for readability
        checkForDestroyedRobots();
        if (fun)
            removeDeadRobots();
        ProgramCards programCards = new ProgramCards();
        ArrayList<IProgramCards.Card> temp;
        PlayCards playCards;
        int it = 0;
        for (int i = 0; i < robots.size(); i++) {
            temp = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (it == 84) {
                    programCards.shuffleCards();
                    it = 0;
                }
                temp.add(programCards.getDeck().get(it++));
            }
            playCards = new PlayCards(temp);
            robots.get(i).getModel().newCards(playCards);
            if (i > 0)
                robots.get(i).getModel().arrangeCards(new int[]{0, 1, 2, 3, 4});
        }
        MakeCards makeCards = new MakeCards();
        for (IProgramCards.Card card : robots.get(0).getModel().getCards())
            makeCards.makeCard(card);
        return makeCards;
    }

    private void removeDeadRobots() {
        ArrayList<Robot> aliveRobots = new ArrayList<>();
        for (Robot robot : getRobots()) {
            if (isNotInGraveyard(robot))
                aliveRobots.add(robot);
        }
        setRobots(aliveRobots);

        returnToMenuIfOnlyOneRobotLeft();
    }

    private void returnToMenuIfOnlyOneRobotLeft() {
        if (getRobots().size() < 2) {
            System.out.println("Entering menu");
            gameOptions.enterMenu();
        }
    }

    private void setRobots(ArrayList<Robot> newRobots) {
        this.robots = newRobots;
    }

    @Override
    public void playNextCard() {
        Robot robot = getRobots().get(currentRobotID);
        if (isNotInGraveyard(robot)) {
            getRobots().get(currentRobotID).playNextCard();
        }
        incrementCurrentRobotID();
        checkForDestroyedRobots();
    }

    private void incrementCurrentRobotID() {
        currentRobotID++;
        if (currentRobotID == getRobots().size())
            currentRobotID = 0;
    }

    private boolean isNotInGraveyard(Robot robot) {
        return robot.getPos() != SettingsUtil.GRAVEYARD;
    }

    @Override
    public void shuffleTheRobotsCards(int[] order) {
        robots.get(0).getModel().arrangeCards(order);
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
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.2);
        for (Robot robot : robots) {
            robot.fireLaser();
            ArrayList<GridPoint2> coords = robot.getLaser().getCoords();
            if (!coords.isEmpty())
                events.createNewLaserEvent(robot.getPos(), coords.get(coords.size() - 1));
        }
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
            for (Robot robot : robots) {
                int robotX = robot.getPos().x;
                int robotY = robot.getPos().y;
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
        assert (gameRunning);
        assert (roundStep == RoundStep.PHASES);
        assert (phaseStep == PhaseStep.CHECK_FOR_WINNER);
        if (DEBUG) System.out.println("\nChecking if someone won...");

        boolean someoneWon = checkAllRobotsForWinner();
        if (someoneWon) {
            endGame();
        }

        if (DEBUG) System.out.println("Found winner: " + someoneWon);
        return someoneWon;
    }

    private boolean checkAllRobotsForWinner() {
        assert (gameRunning);
        assert (roundStep == RoundStep.PHASES);
        assert (phaseStep == PhaseStep.CHECK_FOR_WINNER);
        checkAllRobotsAreCreated();

        for (Robot robot : robots) {
            if (robot.hasVisitedAllFlags()) {
                winner = robot;
            }
        }

        return (winner != null);
    }

    private boolean checkAllRobotsAreCreated() {
        boolean robotsAreCreated = true;
        if (robots == null) {
            robotsAreCreated = false;
        } else {
            for (Robot robot : robots) {
                if (robot == null) {
                    robotsAreCreated = false;
                    break;
                }
            }
        }
        if (!robotsAreCreated) {
            throw new IllegalStateException("Robots are not created");
        }
        return true;
    }

    @Override
    public Robot getWinner() {
        return winner;
    }

    @Override
    public void endGame() {
        assert (gameRunning);
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
        for (Robot robot : robots) {
            m = r.nextInt(4);
            if (m == 0)
                robot.rotate("L", 1);
            else if (m == 1)
                robot.move(1);
            else if (m == 2)
                robot.move(-1);
            else if (m == 3)
                robot.rotate("R", 1);
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
