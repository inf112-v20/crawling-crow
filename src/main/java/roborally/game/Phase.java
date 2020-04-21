package roborally.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.IFlag;
import roborally.game.gameboard.objects.conveyorbelts.ConveyorBelt;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.UIElements;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.*;

/**
 * This class handles all the details of the game.
 * Each phase consists of a number of events handled
 * in {@link #run}, and robots play their card through
 * the method {@link #playNextRegisterCard}, which is
 * iterated through an actual timed event handled in
 * {@link Events}.
 */

public class Phase implements IPhase {
	private ArrayList<Robot> robots;
	private Events events;
	private Robot winner;
	private ArrayList<IFlag> flags;
	private ArrayList<BoardObject> repairSites;
	private List<List<TileName>> pushers;
	private Queue<Robot> robotQueue;
	private ConveyorBelt conveyorBelt;
	private boolean pusher;
	private int phaseNumber;

	private UIElements uiElements;

	public Phase(ArrayList<Robot> robots, IGameBoard gameBoard, Events events, UIElements uiElements) {
		this.robots = robots;
		this.events = events;
		this.flags = gameBoard.findAllFlags();
		this.repairSites = gameBoard.findAllRepairSites();
		if (gameBoard.hasPushers()) {
			this.pushers = gameBoard.addPushers();
			pusher = true;
		}
		this.robotQueue = new LinkedList<>();
		this.conveyorBelt = new ConveyorBelt();
		this.phaseNumber = 1;
		this.uiElements = uiElements;
	}

	@Override
	public void run(ILayers layers) {
		moveAllConveyorBelts(layers);
		if (pusher)
			pushActivePushers(phaseNumber, layers);
		moveCogs(layers);
		fireLasers();
		checkForLasers();
		registerRepairSitePositionsAndUpdateArchiveMarker();
		registerFlagPositionsAndUpdateArchiveMarker();
		checkForWinner();
		phaseNumber++;
		if (phaseNumber == 6)
			phaseNumber = 1;
		System.out.println("\t- COMPLETED ONE PHASE");
	}

	private void pushActivePushers(int phaseNumber, ILayers layers) {
		TileName tileName;
		for (Robot robot : robots) {
			if (layers.layerNotNull(LayerName.PUSHERS, robot.getPosition())) {
				tileName = layers.getTileName(LayerName.PUSHERS, robot.getPosition());
				if (pushers.get(phaseNumber).contains(tileName)) {
					String[] splitted = tileName.toString().split("_");
					Direction dir = Direction.valueOf(splitted[splitted.length - 1]);
					robot.tryToMove(dir.getStep());
				}
			}
		}
	}

	@Override
	public void revealProgramCards() {
		//123
	}

	@Override
	public void playNextRegisterCard() {
		if (robotQueue.isEmpty()) {
			this.robots.sort(Comparator.comparing(Robot::peekNextCardInRegister, Comparator.reverseOrder()));
			robotQueue.addAll(robots);
		}
		Objects.requireNonNull(robotQueue.poll()).playNextCard();
		events.checkForDestroyedRobots(this.robots);
	}

	@Override
	public void moveAllConveyorBelts(ILayers layers) {
		//TODO: Rather send in a list of relevant coordinates to separate UI from backend
		conveyorBelt.activateConveyorBelt(layers, LayerName.CONVEYOR_EXPRESS, robots);
		conveyorBelt.activateConveyorBelt(layers, LayerName.CONVEYOR_EXPRESS, robots);
		conveyorBelt.activateConveyorBelt(layers, LayerName.CONVEYOR, robots);
	}

	@Override
	public void moveCogs(ILayers layers) {
		//TODO: Rather send in a list of relevant coordinates to separate UI from backend
		for (Robot robot : robots) {
			GridPoint2 pos = robot.getPosition();
			if (layers.layerNotNull(LayerName.COG, pos)) {
				TileName tileName = layers.getTileName(LayerName.COG, pos);
				if (tileName == TileName.COG_CLOCKWISE)
					robot.rotate(Direction.turnLeftFrom(robot.getLogic().getDirection()));
				else if (tileName == TileName.COG_COUNTER_CLOCKWISE)
					robot.rotate(Direction.turnRightFrom(robot.getLogic().getDirection()));
			}
		}
	}

	@Override
	public void fireLasers() {
		Sound sound = AssetManagerUtil.ASSET_MANAGER.get(AssetManagerUtil.SHOOT_LASER);
		sound.play((float) 0.08 * AssetManagerUtil.volume);
		for (Robot robot : robots) {
			robot.fireLaser();
			ArrayList<GridPoint2> coords = robot.getLaser().getCoords();
			if (!coords.isEmpty())
				events.createNewLaserEvent(robot.getPosition(), coords.get(coords.size() - 1));
			if (robot.getLogic().isUserRobot()) {
				uiElements.updateHealth(robot);
			}
		}
	}

	@Override
	public void registerRepairSitePositionsAndUpdateArchiveMarker() {
		System.out.println("\nChecking if any robots have arrived at a repair site...");
		for (BoardObject repairSite : repairSites) {
			for (Robot robot : robots) {
				if (robot.getPosition().equals(repairSite.getPosition())) {
					robot.getLogic().setArchiveMarker(repairSite.getPosition());
					System.out.println("- Type of repair site: " + repairSite.getType().name());
				}
			}
		}
	}

	@Override
	public void registerFlagPositionsAndUpdateArchiveMarker() {
		System.out.println("\nChecking if any robots have arrived at their next flag position...");
		for (IFlag flag : flags) {
			for (Robot robot : robots) {
				if (robot.getLogic().getPosition().equals(flag.getPosition())) {
					int nextFlag = robot.getLogic().getNextFlag();
					if (flag.getID() == nextFlag) {
						robot.visitNextFlag();
						robot.getLogic().setArchiveMarker(flag.getPosition());
						System.out.println("- " + robot.getName() + " has visited flag no. " + flag.getID());
					}
				}
			}
		}
	}

	@Override
	public boolean checkForWinner() {
		System.out.println("\nChecking if someone won...");
		boolean someoneWon = checkAllRobotsForWinner();
		System.out.println("- Did someone win? " + someoneWon);
		if (someoneWon)
			System.out.println("- Found winner: " + winner.getName());
		return someoneWon;
	}

	private void checkForLasers() {
		for (Robot robot : robots) {
			if (robot.checkForStationaryLaser()) {
				robot.takeDamage(1);
				System.out.println("- Hit by stationary laser");
			}
			if (robot.getLogic().isUserRobot()) {
				uiElements.updateHealth(robot);
			}
		}
	}

	private boolean checkAllRobotsForWinner() {
		for (Robot robot : robots) {
			if (robot.getLogic().hasVisitedAllFlags()) {
				winner = robot;
			}
		}
		return (winner != null);
	}

	@Override
	public Robot getWinner() {
		return this.winner;
	}
}
