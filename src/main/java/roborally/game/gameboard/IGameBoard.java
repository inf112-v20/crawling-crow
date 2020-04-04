package roborally.game.gameboard;

import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.IFlag;
import java.util.ArrayList;

public interface IGameBoard {
    /**
     * @return All the flags on the game board
     */
    ArrayList<IFlag> findAllFlags();

    ArrayList<BoardObject> findAllRepairSites();
}
