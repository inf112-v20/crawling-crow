package roborally.tools.controls;

import roborally.game.IGame;

import java.util.HashMap;

public class ControlsProgramRobot implements IControls {
    private HashMap<Integer, Runnable> controlMap;

    public ControlsProgramRobot(IGame game){
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
