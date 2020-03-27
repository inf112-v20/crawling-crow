package roborally.game;

import roborally.ui.ILayers;

public interface IRound {
    void run(ILayers layers);
    void announcePowerDown();
    void dealCards();
    void programRobots();
    void startPhases(ILayers layers);
    void cleanUp();
    void checkForDestroyedRobots();
    void restoreRebootedRobots();
    IPhase getPhase();
}
