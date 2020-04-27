package roborally.game.gameboard;

import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.Grid;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
import java.util.List;

public interface IGameBoard {
	/**
	 * @return All the flags on the game board
	 */
	ArrayList<IFlag> findAllFlags();

	/**
	 * @return all the repair sites on the game board.
	 */
	ArrayList<BoardObject> findAllRepairSites();

	/**
	 * @return return all cogs on the game board
	 */
	ArrayList<BoardObject> findAllCogs();

	/**
	 * @return all normal conveyor belts on the board
	 */
	ArrayList<BoardObject> findAllNormalConveyorBelts();

	/**
	 * @return all express conveyor belts on the board
	 */
	ArrayList<BoardObject> findAllExpressConveyorBelts();

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

	/**
	 *
	 * @return the grid for the current map.
	 */
	Grid getGrid();
}
