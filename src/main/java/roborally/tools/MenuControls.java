package roborally.tools;

import com.badlogic.gdx.Input;
import roborally.game.IGame;

import java.util.HashMap;

public class MenuControls {
    IGame game;
    HashMap<Input.Keys, Runnable> menuControlMap;

    public MenuControls(IGame game){
        this.game = game;
        menuControlMap = new HashMap<>();
        // menuControlMap.put(Input.Keys.ENTER, () -> game.startGame());
        // TODO: Ensure we can use lambda expressions. Otherwise this will not work
    }
}
