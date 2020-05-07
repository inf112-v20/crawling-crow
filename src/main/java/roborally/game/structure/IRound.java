package roborally.game.structure;

import roborally.gameview.layout.ILayers;

public interface IRound {

    /**
     * Runs all actions that is needed for a round to happen
     *
     * @param layers layers
     */
    void run(ILayers layers);

    /**
     * Sets the robot in powerdown mode
     */
    void setRobotInPowerDown();

    /**
     * Goes through a phase 5 times.
     *
     * @param layers layers
     */
    void startPhases(ILayers layers);

    /**
     * Restores rebooted robots back to the board, cleans the register, resets game message and updates ui elements
     */
    void cleanUp();

    /**
     * Check if there are any destroyed robots
     */
    void checkForDestroyedRobots();

    /**
     * Placed all robots that lost all hp back to their archive markers/checkpoints
     */
    void restoreRebootedRobots();

    /**
     * @return the phase.
     */
    IPhase getPhase();

    /**
     * @return true if a round is in progress
     */
    boolean inProgress();

    /**
     * @param inProgress set true if round is running, false otherwise
     */
    void setInProgress(boolean inProgress);
}
