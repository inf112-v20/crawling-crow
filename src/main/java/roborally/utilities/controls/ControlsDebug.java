package roborally.utilities.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;
import java.util.HashMap;

public class ControlsDebug implements IControls{
    private HashMap<Integer, Runnable> menuControlMap;
    private IGame game;

    public ControlsDebug(IGame game){
        this.game = game;
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.ENTER, game::startGame);
        menuControlMap.put(Input.Keys.UP, () -> game.getRobots().move(1));
        menuControlMap.put(Input.Keys.DOWN, () -> game.getRobots().move(-1));
        menuControlMap.put(Input.Keys.LEFT, () -> game.getRobots().rotate("L", 1));
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getRobots().rotate("R", 1));
        menuControlMap.put(Input.Keys.F, () -> game.getRobots().fireLaser());
        menuControlMap.put(Input.Keys.SPACE, game::registerFlagPositions);
        menuControlMap.put(Input.Keys.W, game::checkIfSomeoneWon);
        menuControlMap.put(Input.Keys.R, game::restartGame);
        menuControlMap.put(Input.Keys.ESCAPE, game::exitGame);
        menuControlMap.put(Input.Keys.A, game::fireLasers);
        menuControlMap.put(Input.Keys.X, this::funMode);
        menuControlMap.put(Input.Keys.M, game::enterMenu);
    }

    @Override
    public Runnable getAction(int keycode){
        if(!menuControlMap.containsKey(keycode)){
            return this::doNothing;
        }

        return menuControlMap.get(keycode);
    }

    private void doNothing() {
        // Ok!
    }
    private void funMode() {
        game.funMode();
        if(game.funMode()) {
            menuControlMap.put(Input.Keys.M, game::moveRobots);
        }
    }
}
