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
import roborally.gameview.menu.Menu;
import roborally.gameview.ui.ProgramCardsView;
import roborally.gameview.ui.UIElements;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.controls.KeyboardInput;
import roborally.utilities.enums.UIElement;

public class GameView extends InputAdapter implements ApplicationListener {
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
    private final ProgramCardsView programCardsView;
    private final UIElements uiElements;
    private final Events events;
    private final AnimateEvent animateEvent;
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
        loadAssets();
        setBackground(mapID);
        tiledMap = AssetManagerUtil.getMap(mapID);
        updateSettingsUtilMapSize();

        game = new Game(events, uiElements);
        keyboardControls = new KeyboardInput(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((SettingsUtil.MAP_WIDTH) / 2f, (SettingsUtil.MAP_HEIGHT) / 2f, 0); // Center map in game window
        camera.update();

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, SettingsUtil.UNIT_SCALE);
        mapRenderer.setView(camera);

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        menu = new Menu(stage, events);
        game.getGameOptions().enterMenu(true);
    }

    private void loadAssets() {
        AssetManagerUtil.ASSET_MANAGER.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetManagerUtil.load();
        AssetManagerUtil.loadAssetsToMap();
        AssetManagerUtil.ASSET_MANAGER.finishLoading();
    }

    private void updateSettingsUtilMapSize() {
        MapProperties mapProperties = tiledMap.getProperties();

        int mapWidth = mapProperties.get("width", Integer.class);
        int mapHeight = mapProperties.get("height", Integer.class);
        int tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
        int tilePixelHeight = mapProperties.get("tileheight", Integer.class);
        float renderedTileWidth = tilePixelWidth * SettingsUtil.UNIT_SCALE;
        float renderedTileHeight = tilePixelHeight * SettingsUtil.UNIT_SCALE;

        SettingsUtil.MAP_WIDTH = renderedTileWidth * mapWidth;
        SettingsUtil.MAP_HEIGHT = renderedTileHeight * mapHeight;
    }

    private void setBackground(int mapID) {
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

    private void renderBackground() {
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(33/255f, 33/255f, 33/255f, 1f); // HEX color #212121
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkForPowerDownNextRound();
        checkForWaitEvent();
        renderBackground();

        camera.update();
        mapRenderer.render();

        checkIfGameIsWon();

        if (paused) {
            pause();
        }

        if (!game.inDebugMode()){
            tryToStartNewRound();
        }

        if (game.hasStarted() && game.getRound().inProgress() && !game.getUserRobot().getLogic().getPowerDown()) {
            animateEvent.initiateRegister(game.getRegisterCardsView());
        }

        if(!game.getGameOptions().inMenu())
            animateEvent.drawEvents(batch, game, stage);
        if(animateEvent.getCardPhase() && Gdx.input.isKeyPressed(Input.Keys.M)){
            paused = true;
            game.getGameOptions().enterMenu(true);
            checkIfInMenu();
        }

        if (game.hasAllPlayersChosenCards())
            Gdx.input.setInputProcessor(this);
        if (game.hasRestarted()) {
            Gdx.input.setInputProcessor(this);
            game.setHasRestarted(false);
        }
    }

    private void checkIfGameIsWon() {
        if (events.wonGame()) {
            events.setWonGame(false);
            menu.reloadStage(stage);
            menu.setStartGame();
            paused = true;
        }
    }

    private void checkForPowerDownNextRound() {
        if (uiElements.getPowerDownButton().isForNextRound()) {
            game.getUserRobot().getLogic().setPowerDownNextRound(true);
            uiElements.getPowerDownButton().setForNextRound(false);
        }
    }

    private void checkForWaitEvent() {
        if (events.hasWaitEvent() && !events.hasLaserEvent()) {
            events.waitMoveEvent(Gdx.graphics.getDeltaTime(), game);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        SettingsUtil.STAGE_WIDTH = stage.getWidth();
        SettingsUtil.STAGE_HEIGHT = stage.getHeight();
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(stage);
        batch.begin();
        menu.drawMenu(batch, stage);
        batch.end();
        checkMenuStates();
    }

    @Override
    public void resume() {
        if (!animateEvent.getCardPhase())
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

        checkIfInMenu();
        return true;
    }

    private void checkIfInMenu() {
        if (game.getGameOptions().inMenu()) {
            menu.reloadStage(stage);
            if (game.getRobots() != null) {
                menu.addActiveGameButtonsToStage(stage);
            }
            paused = true;
        }
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
        if(!events.hasWaitEvent() && !events.hasLaserEvent() && game.hasStarted() && !game.getRound().inProgress()) {
            startNewRound();
        }
    }

    private void startNewRound(){
        game.getRound().setInProgress(true);
        setPowerDownButton();

        uiElements.update(game.getUserRobot());
        game.dealCards();
        if (programCardsView != null) {
            animateEvent.initiateCards(stage, game.getProgramCardsView());
        } else {
            events.setWaitMoveEvent(true);
        }
    }

    private void setPowerDownButton() {
        if (!uiElements.getPowerDownButton().isActivated()) {
            uiElements.getPowerDownButton().set(UIElement.POWERED_ON);
        } else {
            uiElements.getPowerDownButton().set(UIElement.POWERED_DOWN);
            uiElements.getPowerDownButton().setActivated(false);
        }
    }

    private void checkMenuStates() {
        if (menu.isChangeMap())
            changeMap();
        if (menu.isResume(game)) {
            paused = false;
            Gdx.input.setInputProcessor(this);
            game.getGameOptions().enterMenu(false);
        }
        if(menu.isEndGame()) {
            game.endGame();
            events.dispose();
            events.setWaitMoveEvent(false);
            events.setCardPhase(false);
            programCardsView.clear();
            menu.reloadStage(stage);
        }
    }
}