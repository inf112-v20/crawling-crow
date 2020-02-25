package roborally.tools.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;

import java.util.HashMap;

public class ControlsMenu implements IControls{
    IGame game;
    HashMap<Integer, Runnable> menuControlMap;

    public ControlsMenu(IGame game){
        this.game = game;
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.ENTER, () -> game.startGame());
        menuControlMap.put(Input.Keys.UP, () -> game.getRobots()[0].moveForward());
        menuControlMap.put(Input.Keys.DOWN, () -> game.getRobots()[0].moveBackward());
        menuControlMap.put(Input.Keys.LEFT, () -> game.getRobots()[0].turnLeft());
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getRobots()[0].turnRight());
    }

    @Override
    public Runnable getAction(int keycode){
        if(!menuControlMap.containsKey(keycode)){
            return ()-> doNothing();
        }

        return menuControlMap.get(keycode);
    }

    private void doNothing() {

    }
}
