package roborally.ui.gameboard;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import roborally.game.gameboard.GameBoard;
import roborally.game.gameboard.IGameBoard;
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

public class UIGameBoard extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    private Layers layers;
    private IGameBoard gameBoard;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
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
        RobotCore robotCore1 = new RobotCore(3,0);
        RobotCore robotCore2 = new RobotCore(0,1);
        RobotCore robotCore3 = new RobotCore(5, 2);
        RobotCore robotCore4 = new RobotCore(8, 3);
        RobotCore robotCore5 = new RobotCore(4, 6);
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
        camera.update();
        mapRenderer.render();
    }

    int i = 0;
    // Checks for colliding robot if moving to an occupied cell in the layer of the Robots.
    public boolean keyUp(int keycode) {
        int x = (int) robots[i].getPosition().x, y = (int) robots[i].getPosition().y;
        boolean onMap = true;
        boolean blocked = false;
        if (keycode == Input.Keys.W) {
            if (layers.getRobots().getCell(x, y+1) != null)
                blocked = checkOnMap(x,y+1,0,1);
            if (!blocked)
                onMap = robots[i].move(0, 1);
        }
        else if(keycode == Input.Keys.D) {
            if (layers.getRobots().getCell(x+1, y) != null)
                blocked = checkOnMap(x+1,y,1,0);
            if (!blocked)
                onMap = robots[i].move(1, 0);
        }
        else if(keycode == Input.Keys.S) {
            if (layers.getRobots().getCell(x, y - 1) != null)
                blocked = checkOnMap(x,y-1,0,-1);
            if (!blocked)
                onMap = robots[i].move(0, -1);
        }
        else if(keycode == Input.Keys.A) {
            if (layers.getRobots().getCell(x-1, y ) != null)
                blocked = checkOnMap(x-1,y,-1,0);
            if (!blocked)
                onMap = robots[i].move(-1, 0);
        }
        if(onMap && !blocked)
            layers.getRobots().setCell(x,y,null);
        gameBoard.getCheckPoint(robots[i].getPosition(), robots[i]);
       i++;
       if (i > 3)
           i = 0;
        return onMap;
    }

    @Override
    public void resize(int width, int height) {
    }

    public boolean robotNextToRobot(int x, int y, int dx, int dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            if (x > 0 && x < layers.getRobots().getWidth() - 1) {
                if (layers.getRobots().getCell(x + dx, y + dy) == null)
                    return false;
            }
        }
        else if (Math.abs(dy) > Math.abs(dx)) {
            if (y > 0 && y < layers.getRobots().getHeight() - 1) {
                if (layers.getRobots().getCell(x + dx, y + dy) == null)
                    return false;
            }
        }
        return true;
    }

    // Temporary to defuse intersecting robots.
    public boolean checkOnMap(int x, int y, int dx, int dy) {
        if(robotNextToRobot(x,y,dy,dx))
            return true;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (!(x <= 0) && !(x >= (layers.getRobots().getWidth() - 1))) {
                System.out.println("uh" + dy + " " + dx);
                checkRobots(x, y, dx, dy);
                return false;
            }
        }
        else if (Math.abs(dy) > dx) {
            if (!(y <= 0) && !(y >= (layers.getRobots().getHeight() - 1))) {
                checkRobots(x, y, dx, dy);
                return false;
            }
        }
        return true;
    }
    // Finds the given robot at the colliding position and moves it one position in the bumping direction
    // then clears its old position.
    public void checkRobots(int x, int y, int dx, int dy) {
        for (RobotCore robot : robots) {
            if (robot!=null) {
                if((int)robot.getPosition().x==x && (int)robot.getPosition().y==y) {
                    System.out.println("Bump! Screw you " + robot.getName());
                    int oldX = (int)robot.getPosition().x;
                    int oldY = (int)robot.getPosition().y;
                    robot.move(dx,dy);
                    layers.getRobots().setCell(oldX,oldY,null);
                }
            }
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
