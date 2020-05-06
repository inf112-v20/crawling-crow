package roborally.gameview;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.events.AnimateEvent;
import roborally.events.Events;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.gameview.elements.ProgramCardsView;
import roborally.gameview.elements.UIElements;
import roborally.gameview.menu.Menu;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.controls.KeyboardInput;
import roborally.utilities.enums.UIElement;

public class GameView extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    public static final float TILE_UNIT_SCALE = 3 / 16f;
    private IGame game;
    private TiledMap tiledMap;
    private int mapID;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Menu menu;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private KeyboardInput keyboardControls;
    private boolean paused;
    private Stage stage;
    private ProgramCardsView programCardsView;
    private UIElements uiElements;
    private Events events;
    private AnimateEvent animateEvent;

    private Sprite backgroundSprite;

    public GameView() {
        this.paused = true;
        this.mapID = 1;
        this.events = new Events();
        this.programCardsView = new ProgramCardsView(game);
        this.uiElements = new UIElements();
        this.animateEvent = new AnimateEvent(events, programCardsView, uiElements);
    }

    @Override
    public void create() {
        AssetManagerUtil.ASSET_MANAGER.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetManagerUtil.load();
        AssetManagerUtil.loadAssetsToMap();
        AssetManagerUtil.ASSET_MANAGER.finishLoading();
        setBackground(mapID);
        tiledMap = AssetManagerUtil.getMap(mapID);
        MapProperties mapProperties = tiledMap.getProperties();

        int mapWidth = mapProperties.get("width", Integer.class);
        int mapHeight = mapProperties.get("height", Integer.class);
        int tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
        int tilePixelHeight = mapProperties.get("tileheight", Integer.class);
        float renderedTileWidth = tilePixelWidth * TILE_UNIT_SCALE;
        float renderedTileHeight = tilePixelHeight * TILE_UNIT_SCALE;


        game = new Game(events, uiElements);
        keyboardControls = new KeyboardInput(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((renderedTileWidth * mapWidth) / 2f, (renderedTileHeight * mapHeight) / 2f, 0); // Center map in game window
        camera.update();

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, TILE_UNIT_SCALE);
        mapRenderer.setView(camera);

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        menu = new Menu(stage, events);
        game.getGameOptions().enterMenu(true);
    }

    public void setBackground(int mapID) {
        this.backgroundSprite = new Sprite(AssetManagerUtil.getBackground(mapID));
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        events.dispose();
        stage.dispose();
        AssetManagerUtil.dispose();
    }

    public void renderBackground() {
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
    }

    @Override
    public void render() {
        events.setStage(stage);
        if (uiElements.isPowerDownSetForNextRound()) {
            game.getUserRobot().getLogic().setPowerDownNextRound(true);
            uiElements.setPowerDownForNextRound(false);
        }

        if (events.hasWaitEvent() && !events.hasLaserEvent())
            events.waitMoveEvent(Gdx.graphics.getDeltaTime(), game);
        Gdx.gl.glClearColor(33/255f, 33/255f, 33/255f, 1f); // HEX color #212121
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderBackground();
        camera.update();
        mapRenderer.render();
        if(events.wonGame()) {
            events.setWonGame(false);
            menu.reloadStage(stage);
            menu.setStartGame();
            paused = true;
        }
        if (paused) { // Menu
            pause();
        }

        if (!game.inDebugMode()){
            tryToStartNewRound();
        }

        if (game.roundInProgress() && game.hasStarted()){
            animateEvent.initiateRegister(stage, game.getRegisterCardsView());
        }

        animateEvent.drawEvents(batch, game, stage);
        if (game.hasAllPlayersChosenCards())
            Gdx.input.setInputProcessor(this);
        if (game.hasRestarted()) {
            Gdx.input.setInputProcessor(this);
            game.setHasRestarted(false);
        }
        uiElements.setStage(stage);
    }

    @Override
    public void resize(int width, int height) {
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
        }
    }

    @Override
    public void resume() {
        if(!animateEvent.getCardPhase())
            Gdx.input.setInputProcessor(this);
    }

    // Temporary checks for input from user to play cards instead of moving manually (Enter).
    public boolean keyUp(int keycode) {
        if (game.inDebugMode()) {
            if (game.getRound() != null) {
                keyboardControls.addDebugControls(game);
            }

            if (keycode == Input.Keys.ENTER) {
                tryToStartNewRound();
                return true;
            }
        }
        keyboardControls.getAction(keycode).run();

        if (game.getGameOptions().inMenu()) {
            menu.reloadStage(stage);
            if(game.getRobots() != null) {
                menu.addContinueButtonToStage(stage);
            }
            paused = true;
        }
        return true;
    }

    public void changeMap() {
        this.mapID = menu.getMapId();
        setBackground(this.mapID);
        if (game.getRobots() != null) {
            game.endGame();
            uiElements.createLeaderboard(game.getRobots());
            events.dispose();
        }
        mapRenderer.setMap(AssetManagerUtil.getMap(mapID));
    }

    private void tryToStartNewRound(){
        if(!events.hasWaitEvent() && !events.hasLaserEvent() && game.hasStarted() && !game.roundInProgress()) {
            startNewRound();
        }
    }

    private void startNewRound(){
        game.getRound().setRoundInProgress(true);
        setPowerDownUI();

        uiElements.update(game.getUserRobot());
        game.dealCards();
        if (programCardsView != null && !game.getUserRobot().getLogic().getPowerDown() && game.getUserRobot().getLogic().getNumberOfLockedCards() < SettingsUtil.REGISTER_SIZE) {
            animateEvent.initiateCards(stage, game.getProgramCardsView());
        } else {
            events.setWaitMoveEvent(true);
        }
    }

    private void setPowerDownUI() {
        if (!uiElements.hasPowerDownBeenActivated()) {
            uiElements.setPowerDownButton(UIElement.POWERED_ON);
        } else {
            uiElements.setPowerDownButton(UIElement.POWERED_DOWN);
            uiElements.hasPowerDownBeenActivated(false);
        }
    }
}