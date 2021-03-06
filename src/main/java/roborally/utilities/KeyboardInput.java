package roborally.utilities;

import com.badlogic.gdx.Input;
import roborally.game.IGame;
import roborally.utilities.enums.Direction;

import java.util.HashMap;

public class KeyboardInput {
    private final HashMap<Integer, Runnable> menuControlMap;

    public KeyboardInput(IGame game) {
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.R, game::restartGame);
        menuControlMap.put(Input.Keys.ESCAPE, game::exitGame);
        menuControlMap.put(Input.Keys.M, game.getGameOptions()::enterMenu);
    }

    public Runnable getAction(int keycode) {
        if (!menuControlMap.containsKey(keycode)) {
            return (() -> {}); // Does nothing when no key
        }

        return menuControlMap.get(keycode);
    }

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
        menuControlMap.put(Input.Keys.T, () -> game.getRound().getPhase().run(game.getLayers()));
        menuControlMap.put(Input.Keys.E, () -> simulateRoundWithoutMovement(game));
        menuControlMap.put(Input.Keys.C, () -> game.getRound().cleanUp());
        menuControlMap.put(Input.Keys.SPACE, () -> simulateOnePhaseWithoutMovementButWithCleanUp(game));
    }

    private void simulateOnePhaseWithoutMovementButWithCleanUp(IGame game) {
        game.getRound().getPhase().run(game.getLayers());
        delayedMethod(() -> cleanup(game), 2000);
    }

    /**
     * Game crashes if run cleanup after someone won
     * @param game the game
     */
    private void cleanup(IGame game) {
        if (!game.checkIfSomeoneWon()) {
            game.getRound().cleanUp();
        }
    }

    private void simulateRoundWithoutMovement(IGame game) {
        int millisecondsPerPhase = 500;
        int numberOfPhases = 5;
        for (int i = 0; i < numberOfPhases; i++) {
            delayedMethod(
                    () -> game.getRound().getPhase().run(game.getLayers()),
                    i*millisecondsPerPhase
            );
        }
        delayedMethod(
                () -> game.getRound().run(),
                numberOfPhases*millisecondsPerPhase
        );
    }

    private void delayedMethod(Runnable method, int milliseconds){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        method.run();
                    }
                },
                milliseconds
        );

    }
}
