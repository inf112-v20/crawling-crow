package roborally.tools.controls;

import roborally.game.IGame;
import java.util.HashMap;

public class ControlsProgramRobot implements IControls {
    private HashMap<Integer, Runnable> controlMap;

    // Meant to take constructor variable game, but leaving it empty for codacy.
    public ControlsProgramRobot(){
        controlMap = new HashMap<>();
        // TODO: Add controls for programming robot
    }

    @Override
    public Runnable getAction(int keycode) {
        if(!controlMap.containsKey(keycode)){
            return this::doNothing;
        }
        return controlMap.get(keycode);
    }

    private void doNothing() {
        // Ok!
    }
}
