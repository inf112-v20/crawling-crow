package roborally.game.gameboard;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.Flag;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.Grid;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GameBoard implements IGameBoard {
	private Grid grid;
	private boolean pushers;
	private ArrayList<IFlag> flags;
	private ArrayList<BoardObject> repairSites;
	private ArrayList<BoardObject> cogs;
	private ArrayList<BoardObject> conveyorBelts;
	private ArrayList<BoardObject> expressConveyorBelts;
	private List<List<TileName>> pusherList;


	public GameBoard(String mapPath) {
		this.grid = new Grid("/" + mapPath);
		this.flags = findAllFlags();
		this.repairSites = findAllRepairSites();
		this.cogs = findAllCogs();
		this.conveyorBelts = findAllNormalConveyorBelts();
		this.expressConveyorBelts = findAllExpressConveyorBelts();
		if (grid.getGridLayer(LayerName.PUSHERS) != null) {
			pushers = true;
			this.pusherList = addPushers();

		}
	}

	@Override
	public ArrayList<IFlag> findAllFlags() {
		ArrayList<IFlag> flags = new ArrayList<>();
		ArrayList<TileName> flagNames = new ArrayList<>();
		HashMap<TileName, GridPoint2> map = new HashMap<>();
		for (GridPoint2 tilePos : grid.getGridLayer(LayerName.FLAG).keySet()) {
			TileName tileName = grid.findTileName(LayerName.FLAG, tilePos);
			map.put(tileName, tilePos);
			flagNames.add(tileName);
		}
		flagNames.sort(Comparator.comparing(TileName::toString));
		for (int i = 1; i < flagNames.size() + 1; i++)
			flags.add(new Flag(i, map.get(flagNames.get(i - 1))));
		return flags;
	}

	@Override
	public ArrayList<BoardObject> findAllRepairSites() {
		ArrayList<BoardObject> repairSites = new ArrayList<>();
		for (GridPoint2 tilePos : grid.getGridLayer(LayerName.WRENCH).keySet())
			repairSites.add(new BoardObject(grid.findTileName(LayerName.WRENCH, tilePos), tilePos));
		for (GridPoint2 tilePos : grid.getGridLayer(LayerName.WRENCH_HAMMER).keySet())
			repairSites.add(new BoardObject(grid.findTileName(LayerName.WRENCH_HAMMER, tilePos), tilePos));
		return repairSites;
	}

	@Override
	public ArrayList<BoardObject> findAllCogs() {
		ArrayList<BoardObject> cogs = new ArrayList<>();
		for (GridPoint2 tilePos : grid.getGridLayer(LayerName.COG).keySet()) {
			cogs.add(new BoardObject(grid.findTileName(LayerName.COG, tilePos), tilePos));
		}
		return cogs;
	}

	@Override
	public ArrayList<BoardObject> findAllNormalConveyorBelts() {
		ArrayList<BoardObject> normalConveyorBelts = new ArrayList<>();
		for (GridPoint2 tilePos : grid.getGridLayer(LayerName.CONVEYOR).keySet()) {
			normalConveyorBelts.add(new BoardObject(grid.findTileName(LayerName.CONVEYOR, tilePos), tilePos));
		}
		return normalConveyorBelts;
	}

	@Override
	public ArrayList<BoardObject> findAllExpressConveyorBelts() {
		ArrayList<BoardObject> expressConveyorBelts = new ArrayList<>();
		for (GridPoint2 tilePos : grid.getGridLayer(LayerName.CONVEYOR_EXPRESS).keySet()) {
			expressConveyorBelts.add(new BoardObject(grid.findTileName(LayerName.CONVEYOR_EXPRESS, tilePos), tilePos));
		}
		return expressConveyorBelts;
	}


	@Override
	public List<List<TileName>> addPushers() {
		List<List<TileName>> pusherList = new ArrayList<>();
		for (int i = 0; i < 6; i++)
			pusherList.add(new ArrayList<>());
		for (GridPoint2 pos : grid.getGridLayer(LayerName.PUSHERS).keySet()) {
			TileName pusher = grid.findTileName(LayerName.PUSHERS, pos);
			fillPusherList(pusher, pusherList);
		}
		return pusherList;
	}

	private void fillPusherList(TileName tileName, List<List<TileName>> pusherList) {
		String[] strings = tileName.toString().split("_");
		for (String string : strings) {
			if (string.length() == 1)
				pusherList.get(Integer.parseInt(string)).add(tileName);
		}
	}

	@Override
	public boolean hasPushers() {
		return this.pushers;
	}

	@Override
	public Grid getGrid() {
		return this.grid;
	}

	@Override
	public ArrayList<BoardObject> getRepairSites() {
		return this.repairSites;
	}

	@Override
	public ArrayList<BoardObject> getCogs() {
		return this.cogs;
	}

	@Override
	public ArrayList<BoardObject> getConveyorBelts() {
		return this.conveyorBelts;
	}

	@Override
	public ArrayList<BoardObject> getExpressConveyorBelts() {
		return this.expressConveyorBelts;
	}

	@Override
	public ArrayList<IFlag> getFlags() {
		return this.flags;
	}

	@Override
	public List<List<TileName>> getPusherList() {
		return this.pusherList;
	}
}
