package roborally.game.gameboard;

import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
import java.util.List;

public interface IGameBoard {
	/**
	 * @return All the flags on the game board
	 */
	ArrayList<IFlag> findAllFlags();

	ArrayList<BoardObject> findAllRepairSites();

	/**
	 * Makes a list of pushers, one for each phase.
	 * Stores each pusher in all positions it's included in, in the list of the pushers.
	 *
	 * @return the list of pushers;
	 */
	List<List<TileName>> addPushers();

	/**
	 * Different maps may or may not have pushers.
	 *
	 * @return true if the map has pushers.
	 */
	boolean hasPushers();
}
