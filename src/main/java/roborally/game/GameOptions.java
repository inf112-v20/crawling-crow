package roborally.game;

import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class GameOptions {
    private boolean menu;
    private ArrayList<Robot> robots;

    public GameOptions(ArrayList<Robot> robots) {
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
                robots.add(new Robot(j, k, k % 8));
                this.robots.get(it).setNumberOfFlags(flags.size());
                it++;
            }
        }
        AssetManagerUtil.setRobots(this.robots);
    }

    public ArrayList<Robot> getRobots() {
        return this.robots;
    }

    public void setRobots(ArrayList<Robot> robots) {
        this.robots = robots;
    }
}
