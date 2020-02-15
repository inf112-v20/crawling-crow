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
    RobotCore[] robots;
    private AI ai1;
    private AI ai2;
    private AI ai3;
    private AI ai4;

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
        ai2 = (AI)robots[2];
        ai3 = (AI)robots[3];
        ai4 = (AI)robots[4];


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
        double d = System.currentTimeMillis();
        if(i==2)
            keycode = ai2.runCore();
        if(i==3)
            keycode = ai3.runCore();
        if(i==4)
            keycode = ai4.runCore();


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
       // Prints the whole movement operation in milliseconds. Lets try to keep this low!
       System.out.println(System.currentTimeMillis()-d);
       if (i >= 2 && i < 5)
           keyUp(123);
       else if (i == 5)
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
