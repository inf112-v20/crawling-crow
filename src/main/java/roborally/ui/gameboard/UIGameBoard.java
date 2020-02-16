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
import roborally.game.objects.AI;
import roborally.tools.AssetsManager;

public class UIGameBoard extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    private Layers layers;
    private IGameBoard gameBoard;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    AI[] robots;

    @Override
    public void create() {
        // Initialize map, layers and robot
        AssetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetsManager.load();
        AssetsManager.manager.finishLoading();
        tiledMap = AssetsManager.getMap();
        layers = new Layers();
        gameBoard = new GameBoard(layers);
        AssetsManager.makeRobots();
        robots = AssetsManager.getRobots();


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
        keyUp(123);
    }

    int i = 0;
    // Checks for colliding robot if moving to an occupied cell in the layer of the Robots.
    // Temporary some AIs are testing the map!
    public boolean keyUp(int keycode) {
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
       i++;
       if (i == 8)
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
