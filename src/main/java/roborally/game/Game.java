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
    private boolean funMode;
    private boolean menu;
    private Events events;

    public Game(Events events) {
        robotPointerID = 0;
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        flags = gameBoard.findAllFlags();
        menu = false;
        this.events = events;
        robots = AssetManagerUtil.makeRobots();
        for (RobotPresenter robot : robots)
            robot.setNumberOfFlags(flags.size());
    }


    public Game(boolean runAIGame) {
        assert (runAIGame);
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        AssetManagerUtil.makeAIRobots();
        aiRobots = AssetManagerUtil.getAIRobots();
    }

    @Override
    public void enterMenu() {
        this.menu = !this.menu;
    }

    @Override
    public void enterMenu(boolean value) {
        this.menu = value;
    }

    @Override
    public boolean getMenu() {
        return this.menu;
    }

    @Override
    public boolean funMode() {
        if (!funMode) {
            funMode = true;
            return false;
        }
        robots = null;
        robots = new RobotPresenter[layers.getHeight() * layers.getWidth()];
        int it = 0;
        for (int j = 0; j < layers.getWidth(); j++) {
            for (int k = 0; k < layers.getHeight(); k++) {
                robots[it] = new RobotPresenter(j, k, k % 8);
                robots[it].setNumberOfFlags(flags.size());
                it++;
            }
        }
        AssetManagerUtil.setRobots(robots);
        System.out.println("Fun mode activated, click 'A' to fire all lasers, 'M' to randomly move all robots");
        return funMode;
    }

    @Override
    public void checkForDestroyedRobots() {
        for (int i = 0; i < 8; i++) {
            if (robots[i].getModel().getStatus().equals("Destroyed")) {
                events.fadeRobot(robots[i].getPos(), robots[i].getTexture());
                layers.setRobotCell(robots[i].getPos().x, robots[i].getPos().y, null);
                robots[i].setPos(new GridPoint2(-1,-1));
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
        if (this.robotPointerID == 8) {
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
            events.getLaserEvent().laserEvent(robots[0].getPos(), coords.get(coords.size()-1));
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
        MakeCards makeCards = new MakeCards();
        for (IProgramCards.Card card : robots[0].getModel().getCards()) {
            if (card.getCardType() == IProgramCards.CardTypes.MOVE_1 || card.getCardType() == IProgramCards.CardTypes.MOVE_2
                    || card.getCardType() == IProgramCards.CardTypes.MOVE_3) {
                makeCards.makeMove(card.getPriority());
            } else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_LEFT || card.getCardType() == IProgramCards.CardTypes.U_TURN)
                makeCards.makeRotateLeft(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.BACKUP)
                makeCards.makeBackup(card.getPriority());
            else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_RIGHT)
                makeCards.makeRotateRight(card.getPriority());
        }
        return makeCards;
    }

    @Override
    public void playNextCard() {
        robots[0].playNextCard();
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
        for (RobotPresenter robot : robots)
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
