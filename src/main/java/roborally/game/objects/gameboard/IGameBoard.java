package roborally.game.objects.gameboard;

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
