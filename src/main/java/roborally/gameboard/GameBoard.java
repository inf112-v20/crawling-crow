package roborally.gameboard;

import roborally.objects.Robot;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameBoard extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;

    private TiledMap tiledMap;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer robotLayer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Robot robot;

    @Override
    public void create() {
        // Initialize map from file
        tiledMap = new TmxMapLoader().load("testMap001.tmx");

        // Initialize layers
        holeLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Flags");
        robotLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Player");
        robot = new Robot();

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);
        robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, robot.getCell());

        // Input
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Keeps track of flagLayer to see if the robot ever steps over the flag.
        if (flagLayer.getCell((int) robot.getPosition().x, (int) robot.getPosition().y) != null) {
            robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, robot.getWinCell());
        }

        // Update and Render Map
        camera.update();
        mapRenderer.render();
    }

    //navigation keys to move robot - only temporary
    // TODO move to Robot class and refactor into rotation cards and movement. (See programcards in rulebook)
    public boolean keyUp(int keycode) {
        float x = robot.getPosition().x, y = robot.getPosition().y;
        boolean onMap = true;

        if (keycode == Input.Keys.UP)
            onMap = robot.moveRobot(0,1,robotLayer, robot.getCell());
        else if(keycode == Input.Keys.RIGHT)
            onMap = robot.moveRobot(1,0,robotLayer, robot.getCell());
        else if(keycode == Input.Keys.DOWN)
            onMap = robot.moveRobot(0,-1,robotLayer, robot.getCell());
        else if(keycode == Input.Keys.LEFT)
            onMap = robot.moveRobot(-1,0,robotLayer, robot.getCell());

        // Reboots the robot if it moves outside the map or falls down a hole.
        if (!onMap || holeLayer.getCell((int) robot.getPosition().x, (int) robot.getPosition().y) != null) {
            robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, null);
            robotLayer.setCell((int) x, (int) y, robot.getLostCell());
            robot.setPosition(x, y);
        }
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
