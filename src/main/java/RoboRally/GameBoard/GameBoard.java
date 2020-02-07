package RoboRally.GameBoard;

import RoboRally.Objects.Robot;
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

        robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, robot.getCell());

        // Keeps track of flagLayer and holeLayer to see if the robot ever steps over them.
        if (flagLayer.getCell((int) robot.getPosition().x, (int) robot.getPosition().y) != null) {
            robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, robot.getWinCell());
        }
        if (holeLayer.getCell((int) robot.getPosition().x, (int) robot.getPosition().y) != null) {
            robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, robot.getLostCell());
        }

        // Update and Render Map
        camera.update();
        mapRenderer.render();
    }

    //navigation keys to move robot - only temporary
    public boolean keyUp(int keycode) {
        int x = (int)robot.getPosition().x, y = (int)robot.getPosition().y;
        boolean onMap = false;
        if (keycode == Input.Keys.UP)
            onMap = robot.moveRobot(x,y,0,1,robotLayer, robot.getCell());
        else if(keycode == Input.Keys.RIGHT)
            onMap = robot.moveRobot(x,y,1,0,robotLayer, robot.getCell());
        else if(keycode == Input.Keys.DOWN)
            onMap = robot.moveRobot(x,y,0,-1,robotLayer, robot.getCell());
        else if(keycode == Input.Keys.LEFT)
            onMap = robot.moveRobot(x,y,-1,0,robotLayer, robot.getCell());
        // Keeps the robot inside the map.
        if (!onMap) {
            robotLayer.setCell((int) robot.getPosition().x, (int) robot.getPosition().y, null);
            robotLayer.setCell(x, y, robot.getCell());
            robot.setPosition(new Vector2(x, y));
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
