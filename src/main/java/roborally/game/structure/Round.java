package roborally.game.structure;

import roborally.game.gameboard.IGameBoard;
import roborally.game.robot.IRobot;
import roborally.game.robot.Robot;
import roborally.gameview.layout.ILayers;
import roborally.gameview.elements.UIElements;
import roborally.events.Events;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

/**
 * This class initializes the round system in the game.
 * Practically it is created once, and then called on to run
 * multiple times to keep the game going. Creates a new
 * {@link Phase} to handle the phase system of the game.
 * A round consists of five phases handled in {@link #startPhases}
 */

public class Round implements IRound {

	private ArrayList<Robot> robots;
	private IPhase phase;
	private int currentPhaseIndex;
	private boolean roundInProgress;

	private UIElements uiElements;

	public Round(Events events, ArrayList<Robot> robots, IGameBoard gameBoard, UIElements uiElements) {
		this.robots = robots;
		this.phase = new Phase(this.robots, gameBoard, events, uiElements);
		this.uiElements = uiElements;
		restoreRebootedRobots();

		this.currentPhaseIndex = SettingsUtil.NUMBER_OF_PHASES;
	}

	@Override
	public void run(ILayers layers) {
		setRobotInPowerDown();
		//dealCards();
		//programRobots();
		//startPhases(layers);
		checkForDestroyedRobots();
		cleanUp();
		roundInProgress = false;
		//System.out.println("\t- RAN A ROUND");
	}


	@Override
	public void setRobotInPowerDown() {
		//if (currentPhaseIndex == 5){
			for(Robot robot : robots){
				if (robot.getLogic().getPowerDownNextRound()){
					robot.getLogic().setPowerDown(true);
					robot.getLogic().setPowerDownNextRound(false);
				}
				else {
					robot.getLogic().setPowerDown(false);
				}
			}
		//}
	}

	@Override
	public void dealCards() {
		//Dealt in game currently.
	}

	@Override
	public void programRobots() {
		//Done in game currently.
	}

	@Override
	public void startPhases(ILayers layers) {
		for (int i = 0; i < 5; i++) {
			getPhase().run(layers);
		}
	}

	@Override
	public void cleanUp() {
		restoreRebootedRobots();
		clearRegisters();
		restRobotTextures();
		uiElements.setMessageLabel("");
	}

	private void restRobotTextures() {
		for (IRobot robot : robots) {
			robot.getView().setDefaultTexture(robot.getPosition());
		}
	}

	private void clearRegisters() {
		System.out.println("\t- Cleaning Registers...");
		for (IRobot robot : robots){
			robot.getLogic().cleanRegister();
		}
	}

	@Override
	public void checkForDestroyedRobots() {
		//Done through events.
	}

	@Override
	public void restoreRebootedRobots() {
		System.out.println("\t- Restoring robots...");
		for (Robot robot : robots) {
			robot.backToArchiveMarker();
			robot.setFalling(false);
			updateUserRobotUIElements(robot);
		}
	}

	// FIXME: Might only be needed temporary, because a proper game loop is not implemented yet
	private void updateUserRobotUIElements(Robot robot) {
		if (robot.getLogic().isUserRobot()) {
			uiElements.update(robot);
		}
	}

	@Override
	public IPhase getPhase() {
		return this.phase;
	}

	@Override
	public boolean isRoundInProgress() {
		return roundInProgress;
	}

	@Override
	public void setRoundInProgress(boolean roundInProgress) {
		this.roundInProgress = roundInProgress;
	}
}