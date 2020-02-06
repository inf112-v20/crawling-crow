package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class RoboRally extends InputAdapter implements ApplicationListener {
    // Legacy Code
    //private SpriteBatch batch;
    //private BitmapFont font;

    // We added these to represent the map and the maps layers
    private TiledMap tiledMap;
    private TiledMapTileLayer robotLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    // Robot parameters
    private Texture robotTexture;
    private TextureRegion[][] robotTextureRegion;
    private Vector2 robotPosition;

    // Not needed yet (or maybe ever)
    private TiledMapTileLayer.Cell robotCell;
    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;

    @Override
    public void create() {

        // Initialize map from file
        tiledMap = new TmxMapLoader().load("testMap001.tmx");

        // Initialize layers
        holeLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Flags");
        robotLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);

        // Initialize robot
        robotTexture = new Texture("player.png");
        robotTextureRegion = TextureRegion.split(robotTexture, 300, 300);
        robotWonCell = new TiledMapTileLayer.Cell();
        robotLostCell = new TiledMapTileLayer.Cell();
        robotCell = new TiledMapTileLayer.Cell();
        robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        robotLostCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][1]));
        robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        robotPosition = new Vector2(0,0);

        // Input
        Gdx.input.setInputProcessor(this);
    }

    // Meant to contain all the keycodes:
    @Override
    public boolean keyUp(int keycode) {
        int x = (int)robotPosition.x, y = (int)robotPosition.y;
        if (keycode == Input.Keys.UP) {
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
            robotLayer.setCell((int) robotPosition.x, (int) ++robotPosition.y, robotCell);

        }
        else if(keycode == Input.Keys.RIGHT){
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
            robotLayer.setCell((int) ++robotPosition.x, (int) robotPosition.y, robotCell);
        }
        else if(keycode == Input.Keys.DOWN){
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
            robotLayer.setCell((int) robotPosition.x, (int) --robotPosition.y, robotCell);
        }
        else if(keycode == Input.Keys.LEFT){
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
            robotLayer.setCell((int) --robotPosition.x, (int) robotPosition.y, robotCell);
        }
        // Keeps the robot inside the map.
        if (robotPosition.x < 0 || robotPosition.y < 0 || robotPosition.x >= robotLayer.getWidth() ||
                robotPosition.y >= robotLayer.getHeight())
        {
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
            robotLayer.setCell(x, y, robotCell);
            robotPosition.x = x;
            robotPosition.y = y;
        }
        return true;
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

        // Keeps track of flagLayer and holeLayer to see if the robot ever steps over them.
        robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, robotCell);
        if (flagLayer.getCell((int) robotPosition.x, (int) robotPosition.y) != null) {
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, robotWonCell);
        }
        if (holeLayer.getCell((int) robotPosition.x, (int) robotPosition.y) != null) {
            robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, robotLostCell);
        }

        // Update and Render Map
        camera.update();
        mapRenderer.render();
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
