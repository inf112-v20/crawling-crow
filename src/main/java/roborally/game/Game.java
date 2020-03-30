package roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.cards.CardsInHand;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.gameboard.BoardObject;
import roborally.game.objects.laser.LaserRegister;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.ProgramCardsView;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class Game implements IGame {
    private final boolean DEBUG = true;

    //region Game Objects
    private IGameBoard gameBoard;
    private ILayers layers;
    private ArrayList<Robot> robots;
    private ArrayList<IFlag> flags;
    private ArrayList<BoardObject> repairSites;
    private IProgramCards deckOfProgramCards;
    private LaserRegister laserRegister;
    private Robot userRobot;
    //endregion

    //private Robot winner;
    private boolean gameRunning = false;
    private int currentRobotID;
    private Events events;
    private GameOptions gameOptions;
    private IRound round;

    private boolean funMode;
    private int robotPlayedCounter;
    private int currentPhaseIndex;

    //private HashMap<IProgramCards.CardType, Runnable> cardTypeMethod;

    public Game(Events events) {
        currentRobotID = 0;
        deckOfProgramCards = new ProgramCards();
        this.events = events;
        this.gameOptions = new GameOptions();
    }

    public Game(boolean runAIGame) {
        assert (runAIGame);
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
    }

    @Override
    public void startUp() {
        this.gameBoard = new GameBoard();
        this.layers = gameBoard.getLayers();
        this.laserRegister = new LaserRegister(layers);
        this.flags = gameBoard.findAllFlags();
        this.repairSites = gameBoard.findAllRepairSites();
        this.robots = gameOptions.makeRobots(layers, laserRegister, flags);
        this.round = new Round(events, robots, flags, repairSites);
        this.userRobot = robots.get(0);
    }

    @Override
    public void funMode() {
        this.gameBoard = new GameBoard();
        this.layers = gameBoard.getLayers();
        this.laserRegister = new LaserRegister(layers);
        this.flags = gameBoard.findAllFlags();
        this.robots = gameOptions.funMode(layers, flags, laserRegister);
        this.events.setGameSpeed("fastest");
        this.round = new Round(events, robots, flags, repairSites);
        this.funMode = true;
        this.userRobot = robots.get(0);
    }

    @Override
    public ILayers getLayers() {
        return this.layers;
    }

    //region Robots
    @Override
    public Robot getFirstRobot() {

        if (this.currentRobotID == robots.size()) {
            this.currentRobotID = 0;
        }

        events.checkForDestroyedRobots(this.robots);
        userRobot.backToArchiveMarker();
        return userRobot;
    }

    @Override
    public ArrayList<Robot> getRobots() {
        return this.robots;
    }

    private void setRobots(ArrayList<Robot> newRobots) {
        this.robots = newRobots;
    }
    //endregion

    @Override
    public void restartGame() {
        if(events.hasWaitEvent())
            return;
        System.out.println("Restarting game...");
        for (Robot robot : robots) {
            events.removeFromUI(robot);
        }
        setRobots(gameOptions.makeRobots(layers, laserRegister, flags));
        userRobot = robots.get(0);
    }
    //region Rounds
    //endregion

    @Override
    public boolean isRunning() {
        return gameRunning;
    }

    @Override
    public GameOptions getGameOptions() {
        return this.gameOptions;
    }

    @Override
    public void manuallyFireOneLaser() {
        // This method is only for bugtesting...
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.08 * AssetManagerUtil.volume);
        userRobot.fireLaser();
        ArrayList<GridPoint2> coords = userRobot.getLaser().getCoords();
        if (!coords.isEmpty())
            events.createNewLaserEvent(userRobot.getPosition(), coords.get(coords.size() - 1));
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

    //region Cards
    @Override
    public ProgramCardsView getCards() {
        //TODO Refactor for readability
        if (funMode)
            removeDeadRobots();

        deckOfProgramCards.shuffleCards();
        makeCards();

        // FIXME: Should this actually be done for all robots in the end?
        return makeProgramCardsView(userRobot);
    }

    private void makeCards() {
        int sizeOfDeck = deckOfProgramCards.getDeck().size();

        int numberOfCardsDrawnFromDeck = 0;
        for (Robot currentRobot : getRobots()) {
            ArrayList<IProgramCards.Card> cardsDrawn = new ArrayList<>();

            int currentRobotHealth = currentRobot.getLogic().getHealth() - 1; // For damage tokens, see rulebook page 9
            int cardsToDraw = Math.max(0, currentRobotHealth);

            for (int i = 0; i < cardsToDraw; i++) {
                // Resets counter to 0 and shuffles cards
                /*if (numberOfCardsDrawnFromDeck == sizeOfDeck) {
                    deckOfProgramCards.shuffleCards();
                    numberOfCardsDrawnFromDeck = 0; //TODO: Refactor
                }*/
                cardsDrawn.add(deckOfProgramCards.getDeck().get(numberOfCardsDrawnFromDeck++));
            }
            CardsInHand cardsInHand = new CardsInHand(cardsDrawn);
            currentRobot.getLogic().setCardsInHand(cardsInHand);

            // All Robots, except userRobot, play their cards in a new assigned order
            if (!currentRobot.equals(userRobot)) {
                int[] newOrder = new int[cardsToDraw];

                for (int i = 0; i < Math.min(cardsToDraw, 5); i++) {
                    newOrder[i] = i;
                }
                currentRobot.getLogic().arrangeCardsInHand(newOrder);
            }
        }
    }

    private ProgramCardsView makeProgramCardsView(Robot robot) {
        ProgramCardsView programCardsView = new ProgramCardsView();
        for (IProgramCards.Card card : robot.getLogic().getCardsInHand()) {
            programCardsView.makeCard(card);
        }
        return programCardsView;
    }

    //endregion

    @Override
    public void shuffleTheRobotsCards(int[] order) {
        userRobot.getLogic().arrangeCardsInHand(order);
        userRobot.getLogic().setHasSelectedCards(true);
    }

    @Override
    public boolean hasAllPlayersChosenCards() {
        if(userRobot != null && userRobot.getLogic().isCardsSelected()) {
                userRobot.getLogic().setHasSelectedCards(false);
                return true;
            }
        return false;
    }

    @Override
    public void endGame() {
        Robot winner = round.getPhase().getWinner();
        System.out.println(winner);
        /*if (winner == null){
            System.out.println("Did not find a winner...");
            return;
        }*/

        assert (gameRunning);
        if (DEBUG) {
            System.out.println("Stopping game...");
        }
        events.setWaitMoveEvent(false);
        for (Robot robot : robots) {
            layers.setRobotTexture(robot.getPosition(), null);
            events.removeFromUI(robot);
        }
        robots.clear();
        gameRunning = false;
        gameOptions.enterMenu(true);
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public IRound getRound() {
        return this.round;
    }

    @Override
    public float continueGameLoop(float dt, double gameSpeed) {
        if (dt >= gameSpeed) {
            getRound().getPhase().playNextRegisterCard();
            dt = 0f;
            this.robotPlayedCounter++;
        }

        if (this.robotPlayedCounter == getRobots().size()) {
            getRound().getPhase().run(getLayers());
            this.currentPhaseIndex++;
            this.robotPlayedCounter = 0;
        }

        // If last phase
        if (this.currentPhaseIndex == SettingsUtil.NUMBER_OF_PHASES) {
            dt = 0f;
            this.currentPhaseIndex = 0;
            this.events.setWaitMoveEvent(false);
            getRound().run(getLayers());
        }
        return dt;
    }

    private boolean isNotInGraveyard(Robot robot) {
        return !robot.getPosition().equals(SettingsUtil.GRAVEYARD);
    }
}
