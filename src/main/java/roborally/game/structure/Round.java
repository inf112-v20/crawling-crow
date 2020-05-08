package roborally.game.structure;

import roborally.events.Events;
import roborally.game.gameboard.IGameBoard;
import roborally.game.robot.IRobot;
import roborally.game.robot.Robot;
import roborally.gameview.ui.UIElements;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

/**
 * This class initializes the round system in the game.
 * Practically it is created once, and then called on to run
 * multiple times to keep the game going. Creates a new
 * {@link Phase} to handle the phase system of the game.
 * A round consists of five phases
 */

public class Round implements IRound {

	private final ArrayList<Robot> robots;
	private final IPhase phase;
	private boolean inProgress;

	private final UIElements uiElements;

	public Round(Events events, ArrayList<Robot> robots, IGameBoard gameBoard, UIElements uiElements) {
		this.robots = robots;
		this.phase = new Phase(this.robots, gameBoard, events, uiElements);
		this.uiElements = uiElements;
		restoreRebootedRobots();
	}

	@Override
	public void run() {
		setRobotInPowerDown();
		cleanUp();
		inProgress = false;

		if (SettingsUtil.DEBUG_MODE) System.out.println("\t- ROUND COMPLETED");
	}


	@Override
	public void setRobotInPowerDown() {
		for (Robot robot : robots){
			if (robot.getLogic().getPowerDownNextRound()){
				robot.getLogic().setPowerDown(true);
				robot.getLogic().setPowerDownNextRound(false);
			}
			else {
				robot.getLogic().setPowerDown(false);
			}
		}
	}

	@Override
	public void cleanUp() {
		restoreRebootedRobots();
		clearRegisters();
		resetRobotTextures();
		uiElements.getMessage().clear();
	}

	private void resetRobotTextures() {
		for (IRobot robot : robots) {
			if(robot.getView().isVirtual())
				continue;
			robot.getView().setDefaultTexture(robot.getPosition());
			robot.getView().setDirection(robot.getPosition(), robot.getLogic().getDirection());
		}
	}

	private void clearRegisters() {
		if (SettingsUtil.DEBUG_MODE) System.out.println("\t- Cleaning Registers...");
		for (IRobot robot : robots){
			robot.getLogic().cleanRegister();
		}
	}

	@Override
	public void restoreRebootedRobots() {
		if (SettingsUtil.DEBUG_MODE) System.out.println("\t- Restoring robots...");
		for (Robot robot : robots) {
			robot.backToArchiveMarker();
			robot.setFalling(false);
			updateUserRobotUIElements(robot);
		}
	}

	private void updateUserRobotUIElements(Robot robot) {
		if (robot.getLogic().isUserRobot()) {
			uiElements.update(robot);
		}
	}

	@Override
	public IPhase getPhase() {
		return phase;
	}

	@Override
	public boolean inProgress() {
		return inProgress;
	}

	@Override
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
}