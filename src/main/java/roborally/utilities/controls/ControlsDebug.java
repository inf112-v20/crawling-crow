package roborally.utilities.controls;

import com.badlogic.gdx.Input;
import roborally.game.IGame;
import roborally.utilities.enums.Direction;

import java.util.HashMap;

public class ControlsDebug implements IControls {
    private HashMap<Integer, Runnable> menuControlMap;
    private boolean powerDownMode;

    public ControlsDebug(IGame game) {
        menuControlMap = new HashMap<>();
        menuControlMap.put(Input.Keys.UP, () -> game.getUserRobot().move(1));
        menuControlMap.put(Input.Keys.DOWN, () -> game.getUserRobot().move(-1));
        menuControlMap.put(Input.Keys.LEFT, () -> game.getUserRobot().rotate(Direction.turnLeftFrom(game.getUserRobot().getLogic().getDirection())));
        menuControlMap.put(Input.Keys.RIGHT, () -> game.getUserRobot().rotate(Direction.turnRightFrom((game.getUserRobot().getLogic().getDirection()))));
        menuControlMap.put(Input.Keys.F, game::manuallyFireOneLaser);
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
    public void addInGameControls(IGame game){
        menuControlMap.put(Input.Keys.SPACE, game.getRound().getPhase()::registerFlagPositionsAndUpdateArchiveMarker);
        menuControlMap.put(Input.Keys.W, game::endGame);
        menuControlMap.put(Input.Keys.A, game.getRound().getPhase()::fireLasers);
        menuControlMap.put(Input.Keys.O, game.getRound().getPhase()::playNextRegisterCard);
        menuControlMap.put(Input.Keys.T, () -> game.getRound().getPhase().run(game.getLayers()));
        menuControlMap.put(Input.Keys.E, () -> simulateRoundWithoutMovement(game));
        menuControlMap.put(Input.Keys.C, () -> game.getRound().cleanUp());
        menuControlMap.put(Input.Keys.P, () -> activateAnnouncePowerDownMode(game));
        menuControlMap.put(Input.Keys.Y, () -> setUserRobotInPowerDown(game, true));
        menuControlMap.put(Input.Keys.N, () -> setUserRobotInPowerDown(game, false));
    }

    private void simulateRoundWithoutMovement(IGame game) {
        for (int i = 0; i < 5; i++) {
            game.getRound().getPhase().run(game.getLayers());
        }
        game.getRound().run(game.getLayers());
    }

    private void activateAnnouncePowerDownMode(IGame game) {
        this.powerDownMode = true;
        System.out.println("Announce PowerDownMode");
        game.announcePowerDown();
    }

    private void setUserRobotInPowerDown(IGame game, boolean powerDown) {
        this.powerDownMode = true;
        System.out.println("setting robot in powerDown" + powerDown);
        if(powerDownMode){
            game.getUserRobot().setPowerDownNextRound(powerDown);
        }
    }
}
