package roborally.tools;

import com.badlogic.gdx.Input;
import roborally.game.IGame;

import java.util.HashMap;

public class ProgramRobotControls implements IControls {
    IGame game;
    HashMap<Integer, Runnable> controlMap;

    public ProgramRobotControls(IGame game){
        this.game = game;
        controlMap = new HashMap<>();
        // TODO: Add controlls for programming robot

    }


    @Override
    public Runnable getAction(int keycode) {
        return controlMap.get(keycode);
    }
}
