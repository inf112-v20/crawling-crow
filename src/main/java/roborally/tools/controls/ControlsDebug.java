package roborally.tools.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;
import java.util.HashMap;

public class ControlsDebug implements IControls{
    private HashMap<Integer, Runnable> menuControlMap;

    public ControlsDebug(IGame game){
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.ENTER, game::startGame);
        menuControlMap.put(Input.Keys.UP, () -> game.getRobots().moveForward());
        menuControlMap.put(Input.Keys.DOWN, () -> game.getRobots().moveBackward());
        menuControlMap.put(Input.Keys.LEFT, () -> game.getRobots().turnLeft());
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getRobots().turnRight());
        menuControlMap.put(Input.Keys.F, () -> game.getRobots().fireLaser());
        menuControlMap.put(Input.Keys.SPACE, game::registerFlagPositions);
        menuControlMap.put(Input.Keys.W, game::checkIfSomeoneWon);
        menuControlMap.put(Input.Keys.ESCAPE, game::exitGame);
    }

    @Override
    public Runnable getAction(int keycode){
        if(!menuControlMap.containsKey(keycode)){
            return ()-> doNothing();
        }

        return menuControlMap.get(keycode);
    }

    private void doNothing() {
        // Ok!
    }
}
