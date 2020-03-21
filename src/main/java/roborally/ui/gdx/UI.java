package roborally.ui.gdx;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.ui.gdx.events.Events;
import roborally.ui.gdx.events.LaserEvent;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.controls.ControlsDebug;

import java.util.Set;

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
    private boolean cardPhase;
    private ProgramCardsView programCardsView;
    private Events events;


    public UI() {
        this.paused = true;
        this.mapID = 1;
        this.cardPhase = false;
        this.events = new Events();
        this.programCardsView = new ProgramCardsView();
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
        stage = new Stage();
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
        if (cardPhase) {
            cardPhaseRun();
            stage.act();
        }
        if (paused) {
            pause();
        }
        batch.begin();
        if (events.getFadeRobot() && !paused)
            events.fadeRobots(batch);
        if (events.hasLaserEvent() && !paused)
            for (LaserEvent laserEvent : events.getLaserEvents())
                laserEvent.drawLaserEvent(batch, game.getRobots());
        batch.end();
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
        stage.getViewport().update(width, height, true);
        //stage.setViewport(800, 480, true, viewportX, viewportY, viewportWidth, viewportHeight);
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(stage);
        batch.begin();
        menu.drawMenu(batch, stage);
        batch.end();
        if (menu.isChangeMap())
            changeMap();
        if (menu.isResume(game))
            resume();
    }

    @Override
    public void resume() {
        game.getGameOptions().enterMenu(false);
        paused = false;
        Gdx.input.setInputProcessor(this);
    }

    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ENTER && !events.hasWaitEvent()) {
            runCardPhase(game.getCards());
            return true;
        }
        if (!game.isRunning()) {
            debugControls.getAction(keycode).run();
        }

        /*if (game.isRunning()) {
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
        }*/
        if (game.getGameOptions().getMenu())
            paused = true;
        return true;
    }

    public void changeMap() {
        this.mapID = menu.getMapId();
        dispose();
        create();
        menu.setMapId(this.mapID);
    }

    public void cardPhaseRun() {
        batch.begin();
        programCardsView.getDoneLabel().draw(batch, stage.getWidth() / 2);
        for (Group group : programCardsView.getGroups()) {
            group.draw(batch, 1);
        }
        batch.end();
        if (programCardsView.done()) {
            Gdx.input.setInputProcessor(this);
            cardPhase = false;
            stage.clear();
            game.shuffleTheRobotsCards(programCardsView.getOrder());
            programCardsView.clearStuff();
            game.playNextCard();
            menu.reloadStage(stage);
            events.setPauseEvent(true);
        }
    }

    public void runCardPhase(ProgramCardsView programCardsView) {
        this.programCardsView = programCardsView;
        programCardsView.makeDoneLabel();
        stage.addActor(programCardsView.getDoneLabel());
        float i = stage.getWidth() - programCardsView.getGroups().size() * programCardsView.getCardWidth();
        programCardsView.getDoneLabel().setX(stage.getWidth() / 2);
        i = i / 2 - programCardsView.getCardWidth();
        for (Group group : this.programCardsView.getGroups()) {
            group.setX(i += programCardsView.getCardWidth());
            stage.addActor(group);
        }
        cardPhase = true;
        Gdx.input.setInputProcessor(stage);
    }

}