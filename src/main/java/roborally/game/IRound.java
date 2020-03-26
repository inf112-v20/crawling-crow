package roborally.game;

import roborally.game.objects.robot.Robot;

import java.util.ArrayList;

public interface IRound {
    void announcePowerDown();
    void dealCards();
    void programRobots();
    void startPhases();
    void cleanUp();

    void checkForDestroyedRobots();

    void restoreRebootedRobots();

    IPhase getPhase();
}
