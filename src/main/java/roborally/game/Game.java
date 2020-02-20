package roborally.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
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
        AssetsManager.makeRobots();
        robots = AssetsManager.getRobots();



    }

    @Override
    public Layers getLayers(){
        return this.layers;
    }

    @Override
    public AI[] getRobots() {
        return robots;
    }

    @Override
    public IGameBoard getGameBoard() {
        return gameBoard;
    }
}
