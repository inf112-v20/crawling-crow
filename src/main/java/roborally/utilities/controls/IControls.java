package roborally.utilities.controls;

import roborally.game.IGame;

public interface IControls {

    Runnable getAction(int keycode);

    void addInGameControls(IGame game);
}
