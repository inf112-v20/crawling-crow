package roborally.ui.gameboard;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import roborally.game.gameboard.GameBoard;
import roborally.game.gameboard.IGameBoard;
import roborally.game.objects.Robot;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import roborally.game.objects.RobotCore;
import roborally.tools.AssetsManager;
import roborally.ui.robot.UIRobot;

public class UIGameBoard extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    // Map with layers
    private Layers layers;
    private IGameBoard gameBoard;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private RobotCore robotCore1;
    private RobotCore robotCore2;
    private RobotCore robotCore3;
    private RobotCore robotCore4;
    private RobotCore robotCore5;
    RobotCore[] robots;
    @Override
    public void create() {
        // Initialize map, layers and robot
        AssetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetsManager.load();
        AssetsManager.manager.finishLoading();
        tiledMap = AssetsManager.getMap();
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        robotCore1 = new RobotCore(new Robot(), new UIRobot(3,0));
        robotCore2 = new RobotCore(new Robot(), new UIRobot(0,1));
        robotCore2 = new RobotCore(new Robot(), new UIRobot(5,2));
        robotCore3 = new RobotCore(new Robot(), new UIRobot(4,8));
        robotCore4 = new RobotCore(new Robot(), new UIRobot(5,5));
        robots = new RobotCore[5];
        robots[0] = robotCore1;
        robots[1] = robotCore2;
        robots[2] = robotCore3;
        robots[3] = robotCore4;
        robots[4] = robotCore5;


        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        AssetsManager.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Keeps track of flagLayer to see if the robot ever steps over the flag.

        // Update and Render Map
        camera.update();
        mapRenderer.render();
    }

    int i = 0;
    // Moved all methods to go through robotCore!
    public boolean keyUp(int keycode) {
        int x = (int)robots[i].getPosition().x, y = (int)robots[i].getPosition().y;
        boolean onMap = true;
        if (keycode == Input.Keys.W)
            onMap = robots[i].move(0,1);
        else if(keycode == Input.Keys.D)
            onMap = robots[i].move(1,0);
        else if(keycode == Input.Keys.S)
            onMap = robots[i].move(0,-1);
        else if(keycode == Input.Keys.A)
            onMap = robots[i].move(-1,0);
        if(onMap)
            layers.getRobots().setCell(x,y,null);
        if(gameBoard.onFlag(robots[i]))
            robots[i].getWinCell();
        if(gameBoard.onHole(robots[i]))
            robots[i].getLoseCell();
        // Reboots the robot if it moves outside the map or falls down a hole.
       i++;
       if (i > 3)
           i = 0;
        return onMap;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
