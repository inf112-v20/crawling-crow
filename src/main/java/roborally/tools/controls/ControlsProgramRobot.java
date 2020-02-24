package roborally.tools.controls;

import roborally.game.IGame;

import java.util.HashMap;

public class ControlsProgramRobot implements IControls {
    IGame game;
    HashMap<Integer, Runnable> controlMap;

    public ControlsProgramRobot(IGame game){
        this.game = game;
        controlMap = new HashMap<>();
        // TODO: Add controls for programming robot

    }

    @Override
    public Runnable getAction(int keycode) {
        if(!controlMap.containsKey(keycode)){
            return ()-> doNothing();
        }

        return controlMap.get(keycode);
    }

    private void doNothing() {
    }
}
