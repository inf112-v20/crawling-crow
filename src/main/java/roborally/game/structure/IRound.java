package roborally.game.structure;

public interface IRound {

    /**
     * Runs all actions that is needed for a round to happen
     *
     */
    void run();

    /**
     * Sets the robot in "power down"-mode
     */
    void setRobotInPowerDown();

    /**
     * Restores rebooted robots back to the board, cleans the register, resets game message and updates ui elements
     */
    void cleanUp();

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
