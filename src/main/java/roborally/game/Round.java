package roborally.game;

import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.events.Events;
import roborally.utilities.SettingsUtil;

import java.lang.invoke.SerializedLambda;
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

	public Round(Events events, ArrayList<Robot> robots, IGameBoard gameBoard) {
		this.robots = robots;
		this.phase = new Phase(this.robots, gameBoard, events);
		restoreRebootedRobots();

		this.currentPhaseIndex = SettingsUtil.NUMBER_OF_PHASES;

		//funke detta?
	}

	@Override
	public void run(ILayers layers) {
		//announcePowerDown();
		//dealCards();
		//programRobots();
		//startPhases(layers);
		checkForDestroyedRobots();
		cleanUp();
		//System.out.println("\t- RAN A ROUND");
	}


	@Override
	public void announcePowerDown() {
		// Not implemented.

		if (currentPhaseIndex == 5){
			for(Robot robot : robots){
				if (robot.getPowerDownNextRound()){
					robot.setPowerDown(true);
					robot.setPowerDownNextRound(false);
				}
				else {
					robot.setPowerDown(false);
				}
			}
		}
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
	}

	@Override
	public void checkForDestroyedRobots() {
		//Done through events.
	}

	@Override
	public void restoreRebootedRobots() {
		for (Robot currentRobot : robots)
			currentRobot.backToArchiveMarker();
	}

	@Override
	public IPhase getPhase() {
		return this.phase;
	}
}
