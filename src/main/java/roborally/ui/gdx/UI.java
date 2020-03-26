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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.ui.gdx.events.Events;
import roborally.ui.gdx.events.LaserEvent;
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
        if (cardPhase) { // Enter has been clicked, and the cards have been initialized.
            roundRun();
            stage.act();
        }
        if (paused) { // Menu
            pause();
        }
        batch.begin();
        // Fades robots if the game is not currently paused and if there is robots to be faded.
        if (events.getFadeRobot() && !paused)
            events.fadeRobots(batch);
        // Draws laaser if the game is not currently paused and there has been fired lasers.
        if (events.hasLaserEvent() && !paused)
            for (LaserEvent laserEvent : events.getLaserEvents())
                laserEvent.drawLaserEvent(batch, game.getRobots());
        batch.end();
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
        if (menu.isResume(game))
            resume();
    }

    @Override
    public void resume() {
        game.getGameOptions().enterMenu(false);
        paused = false;
        Gdx.input.setInputProcessor(this);
    }

    // Temporary checks for input from user to play cards instead of moving manually (Enter).
    public boolean keyUp(int keycode) {
        if(game.getRound() != null){
            debugControls.addInGameControls(game);
        }

        if (keycode == Input.Keys.ENTER && !events.hasWaitEvent()) {
            startRound(game.getCards());
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
        if(game.getRobots()!=null)
            game.endGame();
        mapRenderer.setMap(AssetManagerUtil.getMap(mapID));
    }

    // TODO: Change method name
    // TODO: Move these last methods to Game
    public void roundRun() {
        // Draws cards while cardPhase is true.
        batch.begin();
        programCardsView.getDoneLabel().draw(batch, stage.getWidth() / 2);
        for (Group group : programCardsView.getGroups()) {
            group.draw(batch, 1);
        }
        batch.end();
        if (programCardsView.done()) { // The user(s) has chosen the cards he or she wants to play, the event starts.
            Gdx.input.setInputProcessor(this);
            cardPhase = false; // TODO: Move to Game
            stage.clear();
            game.shuffleTheRobotsCards(programCardsView.getOrder()); // TODO: Move to Game
            programCardsView.clearStuff();
            menu.reloadStage(stage);
            events.setPauseEvent(true); //Starts the event
        }
    }

    // Called by user with key input, initializes the cards into fixed positions relative to the number of cards.
    // TODO: Change method name
    public void startRound(ProgramCardsView programCardsView) {
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