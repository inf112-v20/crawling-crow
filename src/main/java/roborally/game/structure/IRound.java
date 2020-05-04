package roborally.game.structure;

import roborally.gameview.layout.ILayers;

public interface IRound {

    /**
     * Runs all actions that is needed for a round to happen
     *
     * @param layers layers
     */
    void run(ILayers layers);

    void setRobotInPowerDown();
    void dealCards();
    void programRobots();

    /**
     * Goes through a phase 5 times.
     *
     * @param layers layers
     */
    void startPhases(ILayers layers);

    /**
     * Restores rebooted robots back to the board
     */
    void cleanUp();

    void checkForDestroyedRobots();

    /**
     * Placed all robots that lost all hp back to their archive markers/checkpoints
     */
    void restoreRebootedRobots();

    /**
     * @return the phase.
     */
    IPhase getPhase();

    boolean isRoundInProgress();

    void setRoundInProgress(boolean roundInProgress);
}
