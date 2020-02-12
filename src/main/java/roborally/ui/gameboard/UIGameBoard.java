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
    private RobotCore robotCore;

    @Override
    public void create() {
        // Initialize map, layers and robot
        AssetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetsManager.load();
        AssetsManager.manager.finishLoading();
        tiledMap = AssetsManager.getMap();
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        robotCore = new RobotCore(new Robot(), new UIRobot());


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

    // Moved all methods to go through robotCore!
    public boolean keyUp(int keycode) {
        int x = (int)robotCore.getPosition().x, y = (int)robotCore.getPosition().y;
        boolean onMap = true;
        if (keycode == Input.Keys.W)
            onMap = robotCore.move(0,1);
        else if(keycode == Input.Keys.D)
            onMap = robotCore.move(1,0);
        else if(keycode == Input.Keys.S)
            onMap = robotCore.move(0,-1);
        else if(keycode == Input.Keys.A)
            onMap = robotCore.move(-1,0);
        if(onMap)
            layers.getRobots().setCell(x,y,null);
        if(gameBoard.onFlag(robotCore))
            robotCore.getWinCell();
        if(gameBoard.onHole(robotCore))
            robotCore.getLoseCell();
        // Reboots the robot if it moves outside the map or falls down a hole.
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
