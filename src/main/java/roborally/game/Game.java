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
import roborally.game.objects.laser.LaserRegister;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.ProgramCardsView;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class Game implements IGame {
    private final boolean DEBUG = true;

    //region Game Objects
    private IGameBoard gameBoard;
    private ILayers layers;
    private ArrayList<Robot> robots;
    private ArrayList<IFlag> flags;
    private IProgramCards deckOfProgramCards;
    private LaserRegister laserRegister;
    private Robot userRobot;
    //endregion

    private Robot winner;
    private boolean gameRunning = false;
    private int currentRobotID;
    private Events events;
    private GameOptions gameOptions;
    private IRound round;

    private boolean fun;

    private HashMap<IProgramCards.CardType, Runnable> cardTypeMethod;

    public Game(Events events) {
        currentRobotID = 0;
        deckOfProgramCards = new ProgramCards();
        this.events = events;
        this.gameOptions = new GameOptions();
        this.laserRegister = new LaserRegister();
    }

    public Game(boolean runAIGame) {
        assert (runAIGame);
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
    }

    @Override
    public void startUp() {
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        flags = gameBoard.findAllFlags();
        this.robots = gameOptions.makeRobots(layers, laserRegister);
        this.round = new Round(events, robots, flags);
        userRobot = robots.get(0);
    }

    @Override
    public void funMode() {
        gameBoard = new GameBoard();
        layers = gameBoard.getLayers();
        flags = gameBoard.findAllFlags();
        robots = gameOptions.funMode(layers, flags, laserRegister);
        this.events.setGameSpeed("fastest");
        this.round = new Round(events, robots, flags);
        fun = true;
        userRobot = robots.get(0);
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
        userRobot.backToCheckPoint();
        events.checkForDestroyedRobots(this.robots);
        return userRobot;
    }

    @Override
    public ArrayList<Robot> getRobots() {
        return this.robots;
    }

    private void setRobots(ArrayList<Robot> newRobots) {
        this.robots = newRobots;
        this.round = new Round(events, robots, flags);
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
        setRobots(gameOptions.makeRobots(layers, laserRegister));
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
        round = new Round(events, robots, flags);
        //TODO Refactor for readability
        if (fun)
            removeDeadRobots();

        ArrayList<IProgramCards.Card> cardsDrawn;
        CardsInHand cardsInHand;

        int numberOfCardsDrawnFromDeck = 0;
        deckOfProgramCards.shuffleCards();
        int sizeOfDeck = deckOfProgramCards.getDeck().size();
        for (int robotID = 0; robotID < robots.size(); robotID++) {
            cardsDrawn = new ArrayList<>();

            //TODO FIX. Bugs with drawing cards...
            int robotHealth = robots.get(robotID).getLogic().getHealth() - 1;
            int cardsToDraw = Math.max(0, robotHealth);

            for (int j = 0; j < cardsToDraw; j++) {
                if (numberOfCardsDrawnFromDeck == sizeOfDeck) {
                    deckOfProgramCards.shuffleCards();
                    numberOfCardsDrawnFromDeck = 0;
                }
                cardsDrawn.add(deckOfProgramCards.getDeck().get(numberOfCardsDrawnFromDeck++));
            }
            cardsInHand = new CardsInHand(cardsDrawn);
            robots.get(robotID).getLogic().newCards(cardsInHand);


            // This codesnippet lets all robots except the first one play their cards in default order.

            if (robotID > 0) {
                int[] newOrder = new int[cardsToDraw];

                for (int i = 0; i < Math.min(cardsToDraw, 5); i++) {
                    newOrder[i] = i;
                }
                robots.get(robotID).getLogic().arrangeCards(newOrder);
            }
        }

        ProgramCardsView programCardsView = new ProgramCardsView();
        for (IProgramCards.Card card : userRobot.getLogic().getCards()) {
            programCardsView.makeCard(card);
        }
        return programCardsView;
    }

    //endregion

    private void checkForLasers() {
        for(Robot robot : robots)
            if (robot.checkForLaser())
                robot.takeDamage(1);
    }


    @Override
    public void shuffleTheRobotsCards(int[] order) {
        userRobot.getLogic().arrangeCards(order);
    }

    //region Conveyor belts
    // Might consider adding this to a wait event once we get it to function properly.
    // Have to have its own move method, moving the robot first in the line first and so on.
    // Additionally, I think the check for lasers are supposed to happen after all of this.


    //endregion

    @Override
    public void endGame() {
        Robot winner = round.getPhase().getWinner();
        System.out.println(winner);
/*        if (winner == null){
            System.out.println("Did not find a winner...");
            return;
        }*/

        assert (gameRunning);
        if(DEBUG){
            System.out.println("Stopping game...");
        }
        events.setPauseEvent(false);
        for (Robot robot : robots) {
            layers.setRobotTexture(robot.getPosition(), null);
            events.removeFromUI(robot);
        }
        robots.clear();
        gameRunning = false;
        gameOptions.enterMenu(true);
        System.out.println("Press W to win");
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public IRound getRound() {
        return this.round;
    }

    private boolean isNotInGraveyard(Robot robot) {
        return !robot.getPosition().equals(SettingsUtil.GRAVEYARD);
    }
}
