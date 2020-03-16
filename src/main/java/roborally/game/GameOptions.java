package roborally.game;

import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.robot.RobotPresenter;
import roborally.ui.ILayers;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class GameOptions {
    private boolean menu;
    private ArrayList<RobotPresenter> robots;

    public GameOptions(ArrayList<RobotPresenter> robots) {
        this.robots = robots;
        this.menu = false;
    }

    public void enterMenu() {
        this.menu = !this.menu;
    }

    public void enterMenu(boolean value) {
        this.menu = value;
    }

    public boolean getMenu() {
        return this.menu;
    }

    public void funMode(ILayers layers, ArrayList<IFlag> flags) {
        robots = new ArrayList<>();
        int it = 0;
        for (int j = 0; j < layers.getWidth(); j++) {
            for (int k = 0; k < layers.getHeight(); k++) {
                robots.add(new RobotPresenter(j, k, k % 8));
                this.robots.get(it).setNumberOfFlags(flags.size());
                it++;
            }
        }
        AssetManagerUtil.setRobots(this.robots);
        System.out.println("Fun mode activated, click 'A' to fire all lasers, move all robots has been " +
                "removed but playing with cards works just as well");
    }

    public ArrayList<RobotPresenter> getRobots() {
        return this.robots;
    }

    public void setRobots(ArrayList<RobotPresenter> robots) {
        this.robots = robots;
    }
}
