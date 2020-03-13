package roborally.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.controls.ControlsDebug;
import roborally.utilities.controls.ControlsProgramRobot;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

import java.util.ArrayList;

public class UI extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    private IGame game;
    private TiledMap tiledMap;
    private int mapID;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Texture menuTexture;
    private Menu menu;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ControlsDebug debugControls;
    private ControlsProgramRobot programRobotControls;
    private boolean paused;
    private Image[] startImage;
    private Stage stage;
    private boolean cardPhase;
    private ArrayList<Image> images;
    private MakeCards makeCards;

    public UI() {
        this.mapID = 0;
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
        game = new Game();

        // Setup controls for the game
        debugControls = new ControlsDebug(game);
        programRobotControls = new ControlsProgramRobot(game);

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 3 / 16f);
        mapRenderer.setView(camera);
        Gdx.input.setInputProcessor(this);
        paused = true;
        menuTexture = AssetManagerUtil.getMenu();
        batch = new SpriteBatch();
        menu = new Menu(tiledMap);
        stage = new Stage();
        cardPhase = false;
        images = new ArrayList<>();
        makeCards = new MakeCards();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        AssetManagerUtil.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mapRenderer.render();
        if (cardPhase) {
            batch.begin();
            for (Image image : images) {
                image.draw(batch, 10);
            }
            batch.end();
            Gdx.input.setInputProcessor(stage);
            if (makeCards.fiveCards()) {
                Gdx.input.setInputProcessor(this);
                cardPhase = false;
                stage.clear();
                game.shuffleTheRobotsCards(makeCards.getOrder());
                makeCards.getImages().clear();
                makeCards.clearStuff();
                return;
            }
            stage.act();
        }
        if (paused) {
            pause();
            if (menu.changeMap()) {
                game.restartGame();
                tiledMap = menu.getMap();
                AssetManagerUtil.getLoadedLayers();
                game = new Game();
                debugControls = new ControlsDebug(game);
                programRobotControls = new ControlsProgramRobot(game);
                mapRenderer.setMap(tiledMap);
                camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                camera.update();
                game.restartGame();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Maybe this can help us run the game even on smaller screen than Tim Cooks.
        Vector2 size = Scaling.fit.apply(SettingsUtil.WINDOW_WIDTH, SettingsUtil.WINDOW_HEIGHT, width, height);
        int viewportX = (int) (width - size.x) / 2;
        int viewportY = (int) (height - size.y) / 2;
        int viewportWidth = (int) size.x;
        int viewportHeight = (int) size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        //stage.setViewport(800, 480, true, viewportX, viewportY, viewportWidth, viewportHeight);
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(menu);
        menu.render();
        if (menu.resumeGame())
            resume();
    }

    @Override
    public void resume() {
        paused = false;
        Gdx.input.setInputProcessor(this);
        game.enterMenu();
    }

    public void runCardPhase(MakeCards makeCards) {
        this.makeCards = makeCards;
        int i = -75;
        for (Image image : this.makeCards.getImages()) {
            image.setX(i += 75);
            stage.addActor(image);
            this.images.add(image);
        }
        cardPhase = true;
    }

    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            runCardPhase(game.getCards());
            return true;
        }
        if (!game.isRunning()) {
            debugControls.getAction(keycode).run();
        }

        if (game.isRunning()) {
            // Start new round if no round is currently activ1e
            if (game.currentRoundStep() == RoundStep.NULL_STEP) {
                game.startNewRound();
            }

            if (game.currentRoundStep() == RoundStep.PROGRAM_ROBOT) {
                programRobotControls.getAction(keycode).run();
            }


            // Decides what happens when we are running through phases
            if (game.currentRoundStep() == RoundStep.PHASES) {

                if (game.currentPhaseStep() == PhaseStep.REVEAL_CARDS) {
                    game.revealProgramCards();
                }

                // Handles logic when in "Check for winner step
                if (game.currentPhaseStep() == PhaseStep.CHECK_FOR_WINNER) {
                    game.checkIfSomeoneWon();
                }
            }
        }
        if (game.getMenu())
            paused = true;
        return true;
    }
}

