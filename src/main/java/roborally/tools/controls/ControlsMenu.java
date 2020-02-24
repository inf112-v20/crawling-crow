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
