package roborally.ui.gameboard;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.Robot;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import roborally.tools.AssetsManager;
import roborally.ui.robot.IUIRobot;
import roborally.ui.robot.UIRobot;

public class UIGameBoard extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    // Map with layers
    private Layers layers;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Robot robot;
    private IUIRobot uiRobot;

    @Override
    public void create() {
        // Initialize map, layers and robot
        AssetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetsManager.load();
        AssetsManager.manager.finishLoading();
        tiledMap = AssetsManager.getMap();
        layers = new Layers();
        robot = new Robot();
        uiRobot = new UIRobot(robot);
        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);
        layers.getRobots().setCell(robot.getPositionX(), robot.getPositionY(), uiRobot.getCell());
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
        if (onFlag()) {
            layers.getRobots().setCell(robot.getPositionX(), robot.getPositionY(), uiRobot.getWinCell());
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
            onMap = move(uiRobot, new Vector2(0, 1));
        else if(keycode == Input.Keys.RIGHT)
            onMap = move(uiRobot, new Vector2(1, 0));
        else if(keycode == Input.Keys.DOWN)
            onMap = move(uiRobot, new Vector2(0, -1));
        else if(keycode == Input.Keys.LEFT)
            onMap = move(uiRobot, new Vector2(-1, 0));

        // Reboots the robot if it moves outside the map or falls down a hole.
        if (!onMap || onHole()) {
            layers.getRobots().setCell(robot.getPositionX(), robot.getPositionY(), null);
            layers.getRobots().setCell(x, y, uiRobot.getLostCell());
            robot.setPosition(x, y);
        }
        return onMap && !onHole();
    }

    // TODO: Need to find a method to store layers within UIRobot itself
    public boolean move(IUIRobot uiRobot, Vector2 pos) {
        layers.getRobots().setCell(uiRobot.getRobot().getPositionX(), uiRobot.getRobot().getPositionY(), null);
        uiRobot.getRobot().setPosition(uiRobot.getRobot().getPositionX() + pos.x,uiRobot.getRobot().getPositionY() + pos.y);
        layers.getRobots().setCell(uiRobot.getRobot().getPositionX(), uiRobot.getRobot().getPositionY(), uiRobot.getCell());
        return uiRobot.getRobot().getPositionX() >= 0 && uiRobot.getRobot().getPositionY() >= 0
                && uiRobot.getRobot().getPositionX() < layers.getRobots().getWidth()
                && uiRobot.getRobot().getPositionY() < layers.getRobots().getHeight();
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

    // Checks if robot is stepping on a bug or a hole. (Bug being a layer which is not implemented)
    public boolean onHole() {
        if(layers.contains("bug"))
            if(layers.getBug().getCell(robot.getPositionX(), robot.getPositionY())!=null)
                return true;
        if(!layers.contains("Hole"))
            return false;
        return layers.getHole().getCell(robot.getPositionX(), robot.getPositionY())!=null;
    }

    // Checks if a robot visits a flag.
    public boolean onFlag() {
        if(!layers.contains("Flag"))
            return false;
        return layers.getFlag().getCell(robot.getPositionX(), robot.getPositionY()) != null;
    }
}
