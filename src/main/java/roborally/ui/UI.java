package roborally.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private IGame game;
    private TiledMap tiledMap;
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
        // Prepare assets manager
        AssetManagerUtil.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetManagerUtil.load();
        AssetManagerUtil.manager.finishLoading();

        // Initialize the uimap
        tiledMap = AssetManagerUtil.getMap(mapID);

        // Start a new game
        //boolean runAIGame = true;
        //game = new Game(runAIGame);
        game = new Game(this.events);

        // Setup controls for the game
        debugControls = new ControlsDebug(game);

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 3 / 16f);
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
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        // FitViewport, to keep aspect ratio when scaling game window
        // see @link{https://github.com/libgdx/libgdx/wiki/Viewports}
        stage.getViewport().update(width, height, true);
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
            resume();
        }
    }

    @Override
    public void resume() {
        if(paused)
            return;
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
        if (!game.isRunning()) {
            debugControls.getAction(keycode).run();
        }

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