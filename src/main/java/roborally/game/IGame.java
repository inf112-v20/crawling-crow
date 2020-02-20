package roborally.game;

import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.ui.gameboard.Layers;

public interface IGame {
    Layers getLayers();

    AI[] getRobots();

    IGameBoard getGameBoard();

    void startRound();
}
