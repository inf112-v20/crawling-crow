package roborally.utilities.controls;

import roborally.game.IGame;

public interface IControls {

    /**
     * This method makes sure that the keys not in use don't do anything.
     *
     * @param keycode any keycode
     * @return all the keys used
     */
    Runnable getAction(int keycode);

    /**
     * Adds the rest of the keys used in the game.
     * space -> register flags
     * w     -> end game
     * a     -> fire all lasers
     * o     -> play next card
     * t     -> run one phase
     *
     * @param game game
     */
    void addDebugControls(IGame game);
}
