package roborally.game;

import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;

public interface IPhase {
    void revealProgramCards();
    void playNextRegisterCard();
    void moveAllConveyorBelts(ILayers layers);
    void moveCogs(ILayers layers);
    void fireLasers();
    void registerRepairSitePositionsAndUpdateArchiveMarker();
    void registerFlagPositionsAndUpdateArchiveMarker();
    boolean checkForWinner();
    void run(ILayers layers);
    Robot getWinner();
}
