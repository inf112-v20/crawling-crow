package roborally.utilities.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;

import java.util.HashMap;

public class ControlsDebug implements IControls {
    private HashMap<Integer, Runnable> menuControlMap;

    public ControlsDebug(IGame game) {
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.UP, () -> game.getFirstRobot().move(1));
        menuControlMap.put(Input.Keys.DOWN, () -> game.getFirstRobot().move(-1));
        menuControlMap.put(Input.Keys.LEFT, () -> game.getFirstRobot().rotate("L", 1));
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getFirstRobot().rotate("R", 1));
        menuControlMap.put(Input.Keys.F, game::fireLaser);
        menuControlMap.put(Input.Keys.SPACE, game::registerFlagPositions);
        menuControlMap.put(Input.Keys.W, game::checkIfSomeoneWon);
        menuControlMap.put(Input.Keys.R, game::restartGame);
        menuControlMap.put(Input.Keys.ESCAPE, game::exitGame);
        menuControlMap.put(Input.Keys.A, game::fireLasers);
        menuControlMap.put(Input.Keys.M, game.getGameOptions()::enterMenu);
        menuControlMap.put(Input.Keys.O, game::playNextCard);
    }

    @Override
    public Runnable getAction(int keycode) {
        if (!menuControlMap.containsKey(keycode)) {
            return this::doNothing;
        }

        return menuControlMap.get(keycode);
    }

    private void doNothing() {
        // Ok!
    }
}
