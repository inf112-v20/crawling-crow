package roborally.ui.gameboard;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.game.objects.gameboard.IGameBoard;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import roborally.game.objects.robot.AI;
import roborally.tools.AssetsManager;

public class UI extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    private IGame game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        // Prepare assets manager
        AssetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetsManager.load();
        AssetsManager.manager.finishLoading();

        // Initialize the uimap
        tiledMap = AssetsManager.getMap();

        // Start a new game
        game = new Game();

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
        //keyUp(123);
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

    int i = 0;
    // Checks for colliding robot if moving to an occupied cell in the layer of the Robots.
    // Temporary some AIs are testing the map!
    public boolean keyUp(int keycode) {
        AI[] robots = game.getRobots();
        Layers layers = game.getLayers();
        IGameBoard gameBoard = game.getGameBoard();

        if(keycode == 62){
            game.startRound();
        }

        System.out.print(robots[i]);
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
}
