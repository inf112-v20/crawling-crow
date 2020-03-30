package roborally.game;

import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.gameboard.BoardObject;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.events.Events;

import java.util.ArrayList;

public class Round implements IRound {

    private ArrayList<Robot> robots;
    private IPhase phase;

    public Round(Events events, ArrayList<Robot> robots, ArrayList<IFlag> flags, ArrayList<BoardObject> repairSites) {
        this.robots = robots;
        this.phase = new Phase(this.robots, flags, repairSites, events);
        restoreRebootedRobots();
    }

    @Override
    public void run(ILayers layers) {
        //announcePowerDown();
        //dealCards();
        //programRobots();
        //startPhases(layers);
        checkForDestroyedRobots();
        cleanUp();
        System.out.println("\t- RAN A ROUND");
    }

    @Override
    public void announcePowerDown() {
        // Not implemented.
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
        // FIXME: Should run like this instead
        for (int i = 0; i < 5; i++) {
            getPhase().run(layers);
        }
        //getPhase().run(layers);
    }

    @Override
    public void cleanUp() {
        //Done in robot and in rebootRobots
        for (Robot currentRobot : robots) {
            currentRobot.backToArchiveMarker();
        }
    }

    @Override
    public void checkForDestroyedRobots() {
        //Done through events.
    }

    @Override
    public void restoreRebootedRobots() {
        for (Robot robot : robots)
            robot.backToArchiveMarker();
    }

    @Override
    public IPhase getPhase() {
        return this.phase;
    }
}
