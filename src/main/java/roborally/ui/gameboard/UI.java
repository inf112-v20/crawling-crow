package roborally.ui.gameboard;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import roborally.game.Game;
import roborally.game.IGame;
import roborally.game.PhaseStep;
import roborally.game.RoundStep;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import roborally.tools.AssetsManager;
import roborally.tools.controls.ControlsMenu;
import roborally.tools.controls.ControlsProgramRobot;

public class UI extends InputAdapter implements ApplicationListener {

    // Size of tile, both height and width
    public static final int TILE_SIZE = 300;
    private IGame game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private ControlsMenu menuControls;
    private ControlsProgramRobot programRobotControls;

    @Override
    public void create() {
        // Prepare assets manager
        AssetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        AssetsManager.load();
        AssetsManager.manager.finishLoading();

        // Initialize the uimap
        tiledMap = AssetsManager.getMap();

        // Start a new game
        //boolean runAIGame = true;
        //game = new Game(runAIGame);
        game = new Game();

        // Setup controls for the game
        menuControls = new ControlsMenu(game);
        programRobotControls = new ControlsProgramRobot(game);

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/6f);
        mapRenderer.setView(camera);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        AssetsManager.dispose();
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
            menuControls.getAction(keycode).run();
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
        game.registerFlagPositions();
        game.checkIfSomeoneWon();
        return true;
    }
}
