package roborally.ui;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import roborally.game.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import roborally.tools.AssetManagerUtil;
import roborally.tools.controls.ControlsDebug;
import roborally.tools.controls.ControlsProgramRobot;

public class UI extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    private IGame game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private ControlsDebug debugControls;
    private ControlsProgramRobot programRobotControls;

    @Override
    public void create() {
        // Prepare assets manager
        AssetManagerUtil.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetManagerUtil.load();
        AssetManagerUtil.manager.finishLoading();

        // Initialize the uimap
        tiledMap = AssetManagerUtil.getMap();

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
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/8f);
        mapRenderer.setView(camera);
        Gdx.input.setInputProcessor(this);
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
    }

    @Override
    public void resize(int width, int height) {
        // Maybe this can help us run the game even on smaller screen than Tim Cooks.
        Vector2 size = Scaling.fit.apply(SettingsUtil.WINDOW_WIDTH, SettingsUtil.WINDOW_HEIGHT, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        //stage.setViewport(800, 480, true, viewportX, viewportY, viewportWidth, viewportHeight);
    }

    @Override
    public void pause() {
        // TODO: Integrate with menu for a pause function.
    }

    @Override
    public void resume() {
        // TODO: Integrate with menu for a  pause function.
    }

    public boolean keyUp(int keycode) {

        if(!game.isRunning()){
            debugControls.getAction(keycode).run();
        }

        if(game.isRunning()){
            // Start new round if no round is currently active
            if(game.currentRoundStep() == RoundStep.NULL_STEP){
                game.startNewRound();
            }

            if(game.currentRoundStep() == RoundStep.PROGRAM_ROBOT){
                programRobotControls.getAction(keycode).run();
            }


            // Decides what happens when we are running through phases
            if(game.currentRoundStep() == RoundStep.PHASES){

                if(game.currentPhaseStep() == PhaseStep.REVEAL_CARDS){
                    game.revealProgramCards();
                }

                // Handles logic when in "Check for winner step
                if(game.currentPhaseStep() == PhaseStep.CHECK_FOR_WINNER){
                    game.checkIfSomeoneWon();
                }
            }
        }
        return true;
    }
}

