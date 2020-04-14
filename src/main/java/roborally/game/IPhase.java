package roborally.game;

import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;

public interface IPhase {

    /**
     * Goes through a phase and does the appropriate actions
     *
     * @param layers layers
     */
    void run(ILayers layers);

    void revealProgramCards();

    /**
     * Plays the next registered card
     */
    void playNextRegisterCard();

    /**
     * Moves the conveyor belts
     *
     * @param layers layers
     */
    void moveAllConveyorBelts(ILayers layers);

    /**
     * Moves the cogs
     *
     * @param layers layers
     */
    void moveCogs(ILayers layers);

    /**
     * All robots alive fire their lasers
     */
    void fireLasers();

    /**
     * Checks if a robot has arrived at the repair site,
     * if so then it updates its checkpoint/archive marker
     * and gives the back one health/removes one damage token
     */
    void registerRepairSitePositionsAndUpdateArchiveMarker();

    /**
     * Checks if a robot has arrived at the correct flag,
     * if so then it updates its checkpoint/archive marker
     * and registers the flag
     */
    void registerFlagPositionsAndUpdateArchiveMarker();

    /**
     * Checks if a robot has collected all the flags
     * @return true or false
     */
    boolean checkForWinner();

    /**
     * @return the robot that has collected all flags first
     */
    Robot getWinner();
}
