package roborally.utilities.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;
import roborally.utilities.enums.Direction;

import java.util.HashMap;

public class ControlsDebug implements IControls {
    private HashMap<Integer, Runnable> menuControlMap;

    public ControlsDebug(IGame game) {
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.UP, () -> game.getFirstRobot().move(1));
        menuControlMap.put(Input.Keys.DOWN, () -> game.getFirstRobot().move(-1));
        menuControlMap.put(Input.Keys.LEFT, () -> game.getFirstRobot().rotate(Direction.turnLeftFrom(game.getFirstRobot().getLogic().getDirection())));
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getFirstRobot().rotate(Direction.turnRightFrom((game.getFirstRobot().getLogic().getDirection()))));
        menuControlMap.put(Input.Keys.F, game::manuallyFireOneLaser);
        menuControlMap.put(Input.Keys.R, game::restartGame);
        menuControlMap.put(Input.Keys.ESCAPE, game::exitGame);
        menuControlMap.put(Input.Keys.M, game.getGameOptions()::enterMenu);
        //menuControlMap.put(Input.Keys.T, game::testEndPhase);
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

    @Override
    public void addInGameControls(IGame game){
        menuControlMap.put(Input.Keys.SPACE, game.getRound().getPhase()::registerFlagPositions);
        menuControlMap.put(Input.Keys.W, game.getRound().getPhase()::checkForWinner);
        menuControlMap.put(Input.Keys.A, game.getRound().getPhase()::fireLasers);
        menuControlMap.put(Input.Keys.O, game.getRound().getPhase()::robotPlayNextCard);
    }
}
