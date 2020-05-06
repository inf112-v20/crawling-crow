package roborally.utilities.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;
import roborally.utilities.enums.Direction;

import java.util.HashMap;

public class KeyboardInput implements IControls {
    private HashMap<Integer, Runnable> menuControlMap;

    public KeyboardInput(IGame game) {
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.R, game::restartGame);
        menuControlMap.put(Input.Keys.ESCAPE, game::exitGame);
        menuControlMap.put(Input.Keys.M, game.getGameOptions()::enterMenu);
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
    public void addDebugControls(IGame game){
        menuControlMap.put(Input.Keys.UP, () -> game.getUserRobot().move(1));
        menuControlMap.put(Input.Keys.DOWN, () -> game.getUserRobot().move(-1));
        menuControlMap.put(Input.Keys.LEFT, () -> game.getUserRobot().rotate(Direction.turnLeftFrom(game.getUserRobot().getLogic().getDirection())));
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getUserRobot().rotate(Direction.turnRightFrom((game.getUserRobot().getLogic().getDirection()))));
        menuControlMap.put(Input.Keys.W, () -> game.getUserRobot().move(1));
        menuControlMap.put(Input.Keys.S, () -> game.getUserRobot().move(-1));
        menuControlMap.put(Input.Keys.A, () -> game.getUserRobot().rotate(Direction.turnLeftFrom(game.getUserRobot().getLogic().getDirection())));
        menuControlMap.put(Input.Keys.D, () -> game.getUserRobot().rotate(Direction.turnRightFrom((game.getUserRobot().getLogic().getDirection()))));
        menuControlMap.put(Input.Keys.F, game::manuallyFireOneLaser);
        menuControlMap.put(Input.Keys.G, game.getRound().getPhase()::fireLasers);
        menuControlMap.put(Input.Keys.SPACE, () -> game.getRound().getPhase().run(game.getLayers()));
        menuControlMap.put(Input.Keys.E, () -> simulateRoundWithoutMovement(game));
        menuControlMap.put(Input.Keys.C, () -> game.getRound().cleanUp());
    }

    private void simulateRoundWithoutMovement(IGame game) {
        for (int i = 0; i < 5; i++) {
            game.getRound().getPhase().run(game.getLayers());
        }
        game.getRound().run(game.getLayers());
    }
}
