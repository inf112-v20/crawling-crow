package roborally.game;

import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;

import java.util.ArrayList;

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
