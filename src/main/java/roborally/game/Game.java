package roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import org.jetbrains.annotations.NotNull;
import roborally.events.Events;
import roborally.game.cards.IProgramCards;
import roborally.game.cards.ProgramCards;
import roborally.game.gameboard.GameBoard;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.flag.IFlag;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.game.robot.AI.AI;
import roborally.game.robot.IRobot;
import roborally.game.robot.Robot;
import roborally.game.structure.IRound;
import roborally.game.structure.Round;
import roborally.gameview.elements.LeaderBoard;
import roborally.gameview.elements.ProgramCardsView;
import roborally.gameview.elements.UIElements;
import roborally.gameview.layout.ILayers;
import roborally.gameview.layout.Layers;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class Game implements IGame {
	private ProgramCardsView programCardsView;

	//region Game Objects
	private IGameBoard gameBoard;
	private ILayers layers;
	private AI ai;
	private ArrayList<Robot> robots;
	private ArrayList<IFlag> flags;
	private ArrayList<BoardObject> repairSites;
	private IProgramCards deckOfProgramCards;
	private LaserRegister laserRegister;
	private Robot userRobot;
	//endregion

	//private Robot winner;
	private int currentRobotID;
	private Events events;
	private GameOptions gameOptions;
	private IRound round;

	private boolean funMode;
	private int robotPlayedCounter;
	private int currentPhaseIndex;

	private UIElements uiElements;
	private boolean isRoundFinished;
	private boolean hasRestarted;

	public Game(Events events, UIElements uiElements) {
		currentRobotID = 0;
		deckOfProgramCards = new ProgramCards();
		this.events = events;
		this.gameOptions = new GameOptions();
		this.uiElements = uiElements;
		this.programCardsView = new ProgramCardsView(this);
	}

	@Override
	public void startUp() {
		this.gameBoard = new GameBoard(AssetManagerUtil.ASSET_MANAGER.getAssetFileName(AssetManagerUtil.getLoadedMap()));
		this.layers = new Layers();
		this.flags = gameBoard.findAllFlags();
		this.laserRegister = new LaserRegister(layers);
		this.robots = gameOptions.makeRobots(layers, laserRegister, flags);
		this.round = new Round(events, robots, gameBoard, uiElements);
		this.ai = new AI(gameBoard);
        setUserRobot();
        uiElements.setMessageLabel(""); // FIXME: temp for resetting the label on startUp
	}

	@Override
	public void funMode() {
		this.gameBoard = new GameBoard(AssetManagerUtil.ASSET_MANAGER.getAssetFileName(AssetManagerUtil.getLoadedMap()));
		this.layers = new Layers();
		this.laserRegister = new LaserRegister(layers);
		this.flags = gameBoard.findAllFlags();
		this.robots = gameOptions.funMode(layers, flags, laserRegister);
		this.events.setGameSpeed("fastest");
		this.round = new Round(events, robots, gameBoard, uiElements);
		this.funMode = true;
		this.ai = new AI(gameBoard);
        setUserRobot();
	}

	@Override
	public ILayers getLayers() {
		return this.layers;
	}

	//region Robots
    @Override
    public void setUserRobot() {
	    for (Robot robot : getRobots()) {
	        if (robot.getLogic().isUserRobot())
	            throw new IllegalStateException("Can only be one user controlled robot");
        }
	    this.userRobot = robots.get(0);
	    this.userRobot.getLogic().setUserRobot();
    }

	@Override
	public Robot getUserRobot() {
		// FIXME: UNCOMMENT for debugging
		/*events.checkForDestroyedRobots(this.robots);
		userRobot.backToArchiveMarker();*/

		//uiElements.update(userRobot); // Just for debugging UI

		return userRobot;
	}

	@Override
	public ArrayList<Robot> getRobots() {
		return this.robots;
	}

	private void setRobots(ArrayList<Robot> newRobots) {
		this.robots = newRobots;
		round = new Round(events, robots, gameBoard, uiElements);
	}
	//endregion

	@Override
	public void restartGame() {
		if (events.hasWaitEvent())
			return;
		System.out.println("Restarting game...");
		for (Robot robot : robots) {
			events.removeFromUI(robot);
		}
		setRobots(gameOptions.makeRobots(layers, laserRegister, flags));
        setUserRobot();
        setHasRestarted(true);
	}

	@Override
	public GameOptions getGameOptions() {
		return this.gameOptions;
	}

	@Override
	public void manuallyFireOneLaser() {
		// This method is only for bugtesting...
		Sound sound = AssetManagerUtil.ASSET_MANAGER.get(AssetManagerUtil.SHOOT_LASER);
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
	public void dealCards() {
		if (funMode)
			removeDeadRobots();
		deckOfProgramCards.shuffleCards();
		for (IRobot robot : getRobots()) {
			robot.getLogic().drawCards(deckOfProgramCards);
			if (!robot.equals(userRobot)) {
				ai.controlRobot(robot.getLogic());
				robot.getLogic().arrangeCardsInHand(ai.getOrder());
			}
		}
		if (userRobot.getLogic().getPowerDown()){
			userRobot.getLogic().autoArrangeCardsInHand();
		} else {
			programCardsView = makeProgramCardsView(userRobot);
		}
	}

	private ProgramCardsView makeProgramCardsView(Robot robot) {
		ProgramCardsView programCardsView = new ProgramCardsView(this);
		for (IProgramCards.Card card : robot.getLogic().getCardsInHand()) {
			programCardsView.setCard(card);
		}
		return programCardsView;
	}
	//endregion

	@Override
	public void shuffleTheRobotsCards(int[] order) {
		userRobot.getLogic().arrangeCardsInHand(order);
		userRobot.getLogic().putChosenCardsIntoRegister();
		userRobot.getLogic().setHasSelectedCards(true);
	}

	@Override
	public boolean hasAllPlayersChosenCards() {
		if (userRobot != null && userRobot.getLogic().isCardsSelected()) {
			userRobot.getLogic().setHasSelectedCards(false);
			return true;
		}
		return false;
	}

	@Override
	public void endGame() {
		Robot winner = round.getPhase().getWinner();
		System.out.println(winner);
		System.out.println("Stopping game...");
		events.setWaitMoveEvent(false);
		for (Robot robot : robots) {
			layers.setRobotTexture(robot.getPosition(), null);
			events.removeFromUI(robot);
		}
		robots.clear();
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
		uiElements.update(getUserRobot());
		if (isRoundFinished) {
			System.out.println("Event: " + events.hasLaserEvent());
			this.events.setWaitMoveEvent(false);
			getRound().run(getLayers());
			this.isRoundFinished = false;
			return 0;
		}
		float deltaTime = dt;
		if (deltaTime >= gameSpeed) {
			getRound().getPhase().playNextRegisterCard();
			deltaTime = 0f;
			this.robotPlayedCounter++;
		}
		if (this.robotPlayedCounter == getRobots().size()) {
			getRound().getPhase().run(getLayers());
			this.currentPhaseIndex++;
			this.robotPlayedCounter = 0;
		}
		if (this.currentPhaseIndex == SettingsUtil.NUMBER_OF_PHASES) {
			this.currentPhaseIndex = 0;
			this.isRoundFinished = true;
		}
		return deltaTime;
	}

	@Override
	public ProgramCardsView getProgramCardsView() {
		return programCardsView;
	}

	@Override
	public void announcePowerDown() {
		for (Robot robot : getRobots()) {
			if (!robot.equals(userRobot)) {
				//TODO:: make smarter.
				if (robot.getLogic().getHealth() < 3) {
					robot.getLogic().setPowerDownNextRound(true);
				}
			}
		}
	}

	private boolean isNotInGraveyard(@NotNull Robot robot) {
		return !robot.getPosition().equals(SettingsUtil.GRAVEYARD);
	}

	@Override
	public boolean hasRestarted() {
		return hasRestarted;
	}

	@Override
	public void setHasRestarted(boolean state) {
		this.hasRestarted = state;
	}
}
