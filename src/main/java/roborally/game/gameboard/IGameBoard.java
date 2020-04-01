package roborally.game.gameboard;

import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.IFlag;
import roborally.ui.ILayers;
import java.util.ArrayList;

public interface IGameBoard {
    /**
     * @return All the flags on the game board
     */
    ArrayList<IFlag> findAllFlags();

    ArrayList<BoardObject> findAllRepairSites();

    /**
     * @return All the layers on the game board
     */
    ILayers getLayers();
}
