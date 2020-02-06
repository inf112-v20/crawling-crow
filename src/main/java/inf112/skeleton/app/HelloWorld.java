package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class HelloWorld implements ApplicationListener {
    // Legacy Code
    //private SpriteBatch batch;
    //private BitmapFont font;

    // We added these to represent the map and the maps layers
    private TiledMap tiledMap;
    // TODO: implement this
    //private TiledMapTileLayer floorLayer;
    //private TiledMapTileLayer robot;
    //private TiledMapTileLayer hole;
    //private TiledMapTileLayer flag;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        // Legacy Code
        //batch = new SpriteBatch();
        //font = new BitmapFont();
        //font.setColor(Color.RED);

        // Initialize map from file
        tiledMap = new TmxMapLoader().load("testMap001.tmx");

        // Initialize layers
        //TODO: Implement
        //floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FloorLayer");

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);
    }

    @Override
    public void dispose() {
        // Legacy Code
        //batch.dispose();
        //font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and Render Map
        camera.update();
        mapRenderer.render();

        // Legacy Code
        //batch.begin();
        //font.draw(batch, "Hello World", 200, 200);
        //batch.end();
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
