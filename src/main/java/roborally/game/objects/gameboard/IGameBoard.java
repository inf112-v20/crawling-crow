package roborally.game.objects.gameboard;

import roborally.game.objects.IFlag;
import java.util.ArrayList;

public interface IGameBoard {
    /**
     * @return All the flags on the game board
     */
    ArrayList<IFlag> findAllFlags();
}
