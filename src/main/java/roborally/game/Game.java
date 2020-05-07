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
import roborally.game.gameboard.objects.flag.IFlag;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.game.robot.AI.AI;
import roborally.game.robot.IRobot;
import roborally.game.robot.Robot;
import roborally.game.structure.IRound;
import roborally.game.structure.Round;
import roborally.gameview.layout.ILayers;
import roborally.gameview.layout.Layers;
import roborally.gameview.ui.ProgramCardsView;
import roborally.gameview.ui.UIElements;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.asset.SoundAsset;

import java.util.ArrayList;

public class Game implements IGame {
	private ProgramCardsView registerCardsView;
	private ProgramCardsView programCardsView;

	//region Game Objects
	private IGameBoard gameBoard;
	private ILayers layers;
	private AI ai;
	private ArrayList<Robot> robots;
	private ArrayList<IFlag> flags;
	private final IProgramCards deckOfProgramCards;
	private LaserRegister laserRegister;
	private Robot userRobot;
	//endregion

	private Events events;
	private GameOptions gameOptions;
	private IRound round;

	private int robotPlayedCounter;
	private int currentPhaseIndex;

	private UIElements uiElements;
	private boolean isRoundFinished;
	private boolean hasRestarted;

	public Game(Events events, UIElements uiElements) {
		deckOfProgramCards = new ProgramCards();
		this.events = events;
		this.gameOptions = new GameOptions();
		this.uiElements = uiElements;
		this.programCardsView = new ProgramCardsView(this);
		this.registerCardsView = new ProgramCardsView(this);
	}

	@Override
	public void startUp(String name) {
		this.gameBoard = new GameBoard(AssetManagerUtil.ASSET_MANAGER.getAssetFileName(AssetManagerUtil.getLoadedMap()));
		this.layers = new Layers();
		this.flags = gameBoard.findAllFlags();
		this.laserRegister = new LaserRegister(layers);
		this.robots = gameOptions.makeRobots(layers, laserRegister, flags);
		this.round = new Round(events, robots, gameBoard, uiElements);
		this.ai = new AI(gameBoard);
		uiElements.createLeaderboard(getRobots());
        setUserRobot();
        userRobot.getLogic().setName(name);
        uiElements.update(userRobot);
        uiElements.getMessage().set(""); // FIXME: temp for resetting the label on startUp
		resetFieldVariables();
	}

	@Override
	public ILayers getLayers() {
		return layers;
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
		return userRobot;
	}

	@Override
	public ArrayList<Robot> getRobots() {
		return robots;
	}

	private void setRobots(ArrayList<Robot> newRobots) {
		this.robots = newRobots;
		this.round = new Round(events, robots, gameBoard, uiElements);
	}
	//endregion

	@Override
	public void restartGame() {
		String name = userRobot.getName();
		if (events.hasWaitEvent())
			return;
		if (SettingsUtil.DEBUG_MODE) System.out.println("Restarting game...");
		for (Robot robot : robots) {
			this.events.removeFromUI(robot);
		}
		setRobots(gameOptions.makeRobots(layers, laserRegister, flags));
        setUserRobot();
        setHasRestarted(true);
        this.uiElements.createLeaderboard(getRobots());
		this.events.dispose();
        getRound().cleanUp();
		this.registerCardsView.clear();
		this.userRobot.getLogic().setName(name);
		this.uiElements.update(userRobot);
	}

	@Override
	public GameOptions getGameOptions() {
		return gameOptions;
	}

	@Override
	public void manuallyFireOneLaser() {
		// This method is only for bugtesting...
		Sound sound = AssetManagerUtil.ASSET_MANAGER.get(SoundAsset.SHOOT_LASER);
		sound.play((float) 0.08 * SettingsUtil.VOLUME);
		userRobot.fireLaser();
		ArrayList<GridPoint2> coords = userRobot.getLaser().getCoords();
		if (!coords.isEmpty())
			events.createNewLaserEvent(userRobot.getPosition(), coords.get(coords.size() - 1));
	}

	@Deprecated
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
			if (SettingsUtil.DEBUG_MODE) System.out.println("Entering menu");
			gameOptions.enterMenu();
		}
	}

	//region Cards
	@Override
	public void dealCards() {
		deckOfProgramCards.shuffleCards();
		announcePowerDown();
		for (IRobot robot : getRobots()) {
			robot.getLogic().drawCards(deckOfProgramCards);
			if (!robot.equals(userRobot)) {
				ai.controlRobot(robot.getLogic());
				robot.getLogic().arrangeCardsInHand(ai.getOrder());
			}
		}
		setProgramCardsView(userRobot);
	}

	private void setProgramCardsView(Robot robot) {
		this.programCardsView = new ProgramCardsView(this);

		for (IProgramCards.Card card : robot.getLogic().getCardsInHand()) {
			programCardsView.addCard(card, true);
		}
		if (robot.getLogic().getCardsInHand().size() < SettingsUtil.REGISTER_SIZE) {
			for (IProgramCards.Card cardInRegister : robot.getLogic().getRegister().getCards()) {
				if (cardInRegister != null) {
					programCardsView.addCard(cardInRegister, false);
				}
			}
		}
	}

	@Override
	public void setRegisterCardsView(Robot robot){
		this.registerCardsView = new ProgramCardsView(this);
		for (IProgramCards.Card card : robot.getLogic().getRegister().getCards()) {
			registerCardsView.addCard(card, false);
		}
	}
	//endregion

	@Override
	public void orderTheUserRobotsCards(int[] order) {
		getUserRobot().getLogic().arrangeCardsInHand(order);
		setRegisterCardsView(getUserRobot());
		getUserRobot().getLogic().setHasSelectedCards(true);
	}

	@Override
	public boolean hasAllPlayersChosenCards() {
		if (getUserRobot() != null && getUserRobot().getLogic().isCardsSelected()) {
			getUserRobot().getLogic().setHasSelectedCards(false);
			return true;
		}
		return false;
	}

	@Override
	public void endGame() {
		if (SettingsUtil.DEBUG_MODE) System.out.println("Stopping game...");
		this.events.setWaitMoveEvent(false);
		for (Robot robot : robots) {
			this.layers.setRobotTexture(robot.getPosition(), null);
			this.events.removeFromUI(robot);
		}
		this.getRobots().clear();
		this.getGameOptions().enterMenu(true);
	}

	@Override
	public void exitGame() {
		Gdx.app.exit();
	}

	@Override
	public IRound getRound() {
		return round;
	}

	@Override
	public float continueGameLoop(float dt, double gameSpeed) {
		this.uiElements.update(getUserRobot());
		if (isRoundFinished) {
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
	public void setHasRestarted(boolean isRestarted) {
		this.hasRestarted = isRestarted;
	}

	@Override
	public boolean inDebugMode() {
		return SettingsUtil.DEBUG_MODE;
	}

	@Override
	public boolean hasStarted(){
		return getRound() != null;
	}

	@Override
	public boolean checkIfSomeoneWon(){
		for (IRobot robot : getRobots()){
			if (robot.getLogic().hasWon()){
				return true;
			}
		}
		return false;
	}

	private void resetFieldVariables() {
		this.robotPlayedCounter = 0;
		this.currentPhaseIndex = 0;
		this.isRoundFinished = false;
	}

	@Override
	public ProgramCardsView getRegisterCardsView() {
		return registerCardsView;
	}
}
