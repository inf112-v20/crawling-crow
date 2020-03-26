package roborally.game;

import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.events.Events;

import java.util.ArrayList;

public class Round implements IRound {

    private ArrayList<Robot> robots;
    private IPhase phase;

    public Round(Events events, ArrayList<Robot> robots, ArrayList<IFlag> flags){
        this.robots = robots;
        restoreRebootedRobots();
        this.phase = new Phase(this.robots, flags, events);
    }

    @Override
    public void run(ILayers layers){
        announcePowerDown();
        dealCards();
        programRobots();
        startPhases(layers);
        checkForDestroyedRobots();
        restoreRebootedRobots();
    }

    @Override
    public void announcePowerDown() {

    }

    @Override
    public void dealCards() {

    }

    @Override
    public void programRobots() {

    }

    @Override
    public void startPhases(ILayers layers) {
        getPhase().run(layers);
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void checkForDestroyedRobots() {

    }

    @Override
    public void restoreRebootedRobots() {
        for (Robot robot : robots)
            robot.backToCheckPoint();
    }

    @Override
    public IPhase getPhase() {
        return this.phase;
    }
}
