package roborally.gameboard;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
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
import roborally.tools.AssMan;
import java.util.HashMap;

public class GameBoard extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    // Map with layers
    private HashMap<String,TiledMapTileLayer> layers;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Robot robot;

    @Override
    public void create() {
        // Initialize map from file
        AssMan.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssMan.load();
        AssMan.manager.finishLoading();
        tiledMap = AssMan.getMap();

        // Initialize layers
        createLayers();
        robot = new Robot();

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);
        layers.get("Robot").setCell(robot.getPositionX(), robot.getPositionY(), robot.getCell());

        // Input
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        AssMan.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Keeps track of flagLayer to see if the robot ever steps over the flag.
        if (onFlag()) {
            layers.get("Robot").setCell(robot.getPositionX(), robot.getPositionY(), robot.getWinCell());
        }

        // Update and Render Map
        camera.update();
        mapRenderer.render();
    }

    // TODO move to Robot class and refactor into rotation cards and movement. (See programcards in rulebook)
    public boolean keyUp(int keycode) {
        int x = robot.getPositionX(), y = robot.getPositionY();
        boolean onMap = true;

        if (keycode == Input.Keys.UP)
            onMap = robot.moveRobot(0,1, layers.get("Robot"));
        else if(keycode == Input.Keys.RIGHT)
            onMap = robot.moveRobot(1,0, layers.get("Robot"));
        else if(keycode == Input.Keys.DOWN)
            onMap = robot.moveRobot(0,-1, layers.get("Robot"));
        else if(keycode == Input.Keys.LEFT)
            onMap = robot.moveRobot(-1,0, layers.get("Robot"));

        // Reboots the robot if it moves outside the map or falls down a hole.
        if (!onMap || onHole()) {
            layers.get("Robot").setCell(robot.getPositionX(), robot.getPositionY(), null);
            layers.get("Robot").setCell(x, y, robot.getLostCell());
            robot.setPosition(x, y);
        }

        return onMap && !onHole();
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

    //Puts all the layers for the current map into a Map, accessible by their given layer names in creation of the map.
    private void createLayers() {
        layers = new HashMap<>();
        String s;
        String[][] layernames = AssMan.readLayerNames();
        for(MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer key = (TiledMapTileLayer) layer;
            s = key.getName().toLowerCase();
            for(int i = 0; i < layernames.length; i++) {
                if (s.contains(layernames[i][0]))
                    layers.put(layernames[i][1], key);
            }
        }
    }

    private boolean onHole() {
        return layers.get("Hole").getCell(robot.getPositionX(), robot.getPositionY())!=null;
    }
    private boolean onFlag() {
        return layers.get("Flag").getCell(robot.getPositionX(), robot.getPositionY()) != null;
    }
}
