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
	// TODO: Ref the TODO on bottom
	private Grid grid;
	private boolean pushers;

	public GameBoard(String mapPath) {
		this.grid = new Grid("/" + mapPath);
		if (grid.getGridLayer(LayerName.PUSHERS) != null)
			pushers = true;
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

	public boolean hasPushers() {
		return this.pushers;
	}

	// TODO : find conveyorbelts, cogs, archive markers etc.
}
