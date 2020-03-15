package roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.PlayCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.RobotPresenter;
import roborally.ui.ILayers;
import roborally.ui.gdx.events.Events;
import roborally.ui.gdx.MakeCards;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

import java.util.ArrayList;
import java.util.Random;

public class Game implements IGame {
    private final boolean DEBUG = true;

    private IGameBoard gameBoard;
    private ILayers layers;
    private AI[] aiRobots;
    private RobotPresenter[] robots;
    private ArrayList<IFlag> flags;

    private RobotPresenter winner;
    private boolean gameRunning = false;
    private RoundStep roundStep = RoundStep.NULL_STEP;
    private PhaseStep phaseStep = PhaseStep.NULL_PHASE;
    private int robotPointerID;
    private Events events;
    private GameOptions gameOptions;

    public Game(Events events) {
        robotPointerID = 0;
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        flags = gameBoard.findAllFlags();
        this.events = events;
        robots = AssetManagerUtil.makeRobots();
        for (RobotPresenter robot : robots)
            robot.setNumberOfFlags(flags.size());
        this.gameOptions = new GameOptions(robots);
    }


    public Game(boolean runAIGame) {
        assert (runAIGame);
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        AssetManagerUtil.makeAIRobots();
        aiRobots = AssetManagerUtil.getAIRobots();
    }

    @Override
    public boolean funMode() {
        boolean funMode = gameOptions.funMode(layers, flags);
        if (funMode) {
            this.robots = gameOptions.getRobots();
            this.events.setGameSpeed("fastest");
        }
       return funMode;
    }

    @Override
    public void checkForDestroyedRobots() {
        for (RobotPresenter robot : robots) {
            if (robot.getModel().getStatus().equals("Destroyed")) {
                events.fadeRobot(robot.getPos(), robot.getTexture());
                layers.setRobotCell(robot.getPos().x, robot.getPos().y, null);
                robot.setPos(new GridPoint2(-1, -1));
                this.events.setFadeRobot(true);
            }
        }
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
    public RobotPresenter getRobots() {
        if (this.robotPointerID == robots.length) {
            this.robotPointerID = 0;
        }
        checkForDestroyedRobots();
        return robots[0];
    }

    @Override
    public IGameBoard getGameBoard() {
        return gameBoard;
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
        for (RobotPresenter robot : robots) {
            robot.clearLaser();
            robot.backToCheckPoint();
        }
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

    public void fireLaser() {
        robots[0].fireLaser();
        ArrayList<GridPoint2> coords = robots[0].getLaser().getCoords();
        if(!coords.isEmpty())
            events.createNewLaserEvent(robots[0].getPos(), coords.get(coords.size()-1));
        robots[0].clearLaser();
    }

    @Override
    public MakeCards getCards() {
        ProgramCards programCards = new ProgramCards();
        programCards.shuffleCards();
        ArrayList<IProgramCards.Card> temp = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            temp.add(programCards.getDeck().get(i));
        }
        PlayCards playCards = new PlayCards(temp);
        robots[0].getModel().newCards(playCards);
        int it = 0;
        for (int i = 1; i < robots.length; i++) {
            temp = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if(it==84) {
                    programCards.shuffleCards();
                    it = 0;
                }
                temp.add(programCards.getDeck().get(it++));
            }
            playCards = new PlayCards(temp);
            robots[i].getModel().newCards(playCards);
            robots[i].getModel().arrangeCards(new int[]{0, 1, 2, 3, 4});
        }
        MakeCards makeCards = new MakeCards();
        for (IProgramCards.Card card : robots[0].getModel().getCards()) {
            if (card.getCardType() == IProgramCards.CardTypes.MOVE_1)
                makeCards.makeMove1(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.MOVE_2)
                makeCards.makeMove2(card.getPriority());
            else if(card.getCardType() == IProgramCards.CardTypes.MOVE_3)
                makeCards.makeMove3(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_LEFT)
                makeCards.makeRotateLeft(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_RIGHT)
                makeCards.makeRotateRight(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.U_TURN)
                makeCards.makeUTurn(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.BACKUP)
                makeCards.makeBackup(card.getPriority());
        }
        return makeCards;
    }

    @Override
    public void playNextCard() {
        robots[robotPointerID++].playNextCard();
        if(robotPointerID == robots.length)
            robotPointerID = 0;
        checkForDestroyedRobots();
    }

    @Override
    public void shuffleTheRobotsCards(int[] order) {
        robots[0].getModel().arrangeCards(order);
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
        for (RobotPresenter robot : robots) {
            robot.fireLaser();
            ArrayList<GridPoint2> coords = robot.getLaser().getCoords();
            if (!coords.isEmpty())
                events.createNewLaserEvent(robot.getPos(), coords.get(coords.size() - 1));
            robot.clearLaser();
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
            for (RobotPresenter robot : robots) {
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

        for (RobotPresenter robot : robots) {
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
            for (RobotPresenter robot : robots) {
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
    public RobotPresenter getWinner() {
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
        for (RobotPresenter robot : robots) {
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
