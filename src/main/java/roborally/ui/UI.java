package roborally.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.ui.gdx.events.AnimateEvent;
import roborally.ui.gdx.events.Events;
import roborally.ui.menu.Menu;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.controls.ControlsDebug;

public class UI extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    public static final float TILE_UNIT_SCALE = 3 / 16f;
    private IGame game;
    private TiledMap tiledMap;
    private MapProperties mapProperties;
    private int mapID;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Menu menu;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ControlsDebug debugControls;
    private boolean paused;
    private Stage stage;
    private ProgramCardsView programCardsView;
    private Events events;
    private AnimateEvent animateEvent;


    public UI() {
        this.paused = true;
        this.mapID = 1;
        this.events = new Events();
        this.programCardsView = new ProgramCardsView();
        this.animateEvent = new AnimateEvent(events);
    }

    @Override
    public void create() {
        AssetManagerUtil.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetManagerUtil.load();
        AssetManagerUtil.manager.finishLoading();
        tiledMap = AssetManagerUtil.getMap(mapID);
        mapProperties = tiledMap.getProperties();

        int mapWidth = mapProperties.get("width", Integer.class);
        int mapHeight = mapProperties.get("height", Integer.class);
        int tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
        int tilePixelHeight = mapProperties.get("tileheight", Integer.class);
        float renderedTileWidth = tilePixelWidth * TILE_UNIT_SCALE;
        float renderedTileHeight = tilePixelHeight * TILE_UNIT_SCALE;


        game = new Game(this.events);
        debugControls = new ControlsDebug(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((renderedTileWidth * mapWidth) / 2f, (renderedTileHeight * mapHeight) / 2f, 0); // Center map in game window
        camera.update();

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, TILE_UNIT_SCALE);
        mapRenderer.setView(camera);

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(SettingsUtil.WINDOW_WIDTH, SettingsUtil.WINDOW_HEIGHT));
        menu = new Menu(stage, events);
        game.getGameOptions().enterMenu(true);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        events.dispose();
        stage.dispose();
        AssetManagerUtil.dispose();
    }

    @Override
    public void render() {
        if (events.hasWaitEvent() && !events.hasLaserEvent())
            events.waitMoveEvent(Gdx.graphics.getDeltaTime(), game);
        Gdx.gl.glClearColor(33/255f, 33/255f, 33/255f, 1f); // HEX color #212121
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mapRenderer.render();
        if (paused) { // Menu
            pause();
        }
        animateEvent.drawEvents(batch, game, stage);
        if (game.hasAllPlayersChosenCards())
            Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        System.out.println("Real window size: " + width + ", " + height);
        System.out.println("Stage viewport: " + stage.getViewport().getScreenWidth() + ", " + stage.getViewport().getScreenHeight());
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(stage);
        batch.begin();
        menu.drawMenu(batch, stage);
        batch.end();
        if (menu.isChangeMap())
            changeMap();
        if (menu.isResume(game)) {
            paused = false;
            Gdx.input.setInputProcessor(this);
            game.getGameOptions().enterMenu(false);
        }
    }

    @Override
    public void resume() {
        if(!animateEvent.getCardPhase())
            Gdx.input.setInputProcessor(this);
    }

    // Temporary checks for input from user to play cards instead of moving manually (Enter).
    public boolean keyUp(int keycode) {
        if (game.getRound() != null) {
            debugControls.addInGameControls(game);
        }

        if (keycode == Input.Keys.ENTER && !events.hasWaitEvent()) {
            programCardsView = game.dealCards();
            animateEvent.initiateCards(programCardsView, stage);
            return true;
        }
        debugControls.getAction(keycode).run();

        if (game.getGameOptions().getMenu()) {
            menu.reloadStage(stage);
            paused = true;
        }
        return true;
    }

    public void changeMap() {
        this.mapID = menu.getMapId();
        if (game.getRobots() != null)
            game.endGame();
        mapRenderer.setMap(AssetManagerUtil.getMap(mapID));
    }
}