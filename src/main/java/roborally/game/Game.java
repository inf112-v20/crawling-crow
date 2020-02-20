package roborally.game;

import com.badlogic.gdx.Input;
import roborally.game.objects.gameboard.GameBoard;
import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.Layers;

public class Game implements IGame {
    private IGameBoard gameBoard;
    private Layers layers;
    private AI[] robots;


    public Game(){
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        robots = AssetsManager.getRobots();
        AssetsManager.makeRobots();
    }

    @Override
    // Checks for colliding robot if moving to an occupied cell in the layer of the Robots.
    // Temporary some AIs are testing the map!
    public boolean keyup(int keycode) {
        int i = 0;
        keycode = robots[i].runCore();
        int x = (int) robots[i].getPosition().x, y = (int) robots[i].getPosition().y;
        boolean onMap = true;
        boolean blocked = false;
        if (keycode == Input.Keys.W) {
            if (layers.getRobots().getCell(x, y+1) != null)
                blocked = robots[i].getCalc().checkIfBlocked(x,y+1,0,1);
            if (!blocked)
                onMap = robots[i].move(0, 1);
        }
        else if(keycode == Input.Keys.D) {
            if (layers.getRobots().getCell(x+1, y) != null)
                blocked = robots[i].getCalc().checkIfBlocked(x+1, y,1,0);
            if (!blocked)
                onMap = robots[i].move(1, 0);
        }
        else if(keycode == Input.Keys.S) {
            if (layers.getRobots().getCell(x, y - 1) != null)
                blocked = robots[i].getCalc().checkIfBlocked(x,y-1,0,-1);
            if (!blocked)
                onMap = robots[i].move(0, -1);
        }
        else if(keycode == Input.Keys.A) {
            if (layers.getRobots().getCell(x-1, y ) != null)
                blocked = robots[i].getCalc().checkIfBlocked(x-1, y,-1,0);
            if (!blocked)
                onMap = robots[i].move(-1, 0);
        }
        if(onMap && !blocked)
            layers.getRobots().setCell(x,y,null);
        gameBoard.getCheckPoint(robots[i].getPosition(), robots[i]);
        return onMap;
    }
}
