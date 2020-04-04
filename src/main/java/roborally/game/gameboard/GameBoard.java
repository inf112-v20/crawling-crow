package roborally.game.gameboard;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.Flag;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.Grid;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard implements IGameBoard {

    private HashMap<TileName, Integer> flagIdMap;

    // TODO: Ref the TODO on bottom
    private Grid grid;

    public GameBoard() {
        this.flagIdMap = new HashMap<>();
        this.flagIdMap.put(TileName.FLAG_1, 1);
        this.flagIdMap.put(TileName.FLAG_2, 2);
        this.flagIdMap.put(TileName.FLAG_3, 3);
        this.flagIdMap.put(TileName.FLAG_4, 4);
        this.grid = new Grid("/" + AssetManagerUtil.manager.getAssetFileName(AssetManagerUtil.getLoadedMap()));
    }

    @Override
    public ArrayList<IFlag> findAllFlags() {
        ArrayList<IFlag> flags = new ArrayList<>();
        for(GridPoint2 tilePos : grid.getGridLayer(LayerName.FLAG).keySet()) {
            int id = flagIdMap.get(grid.getGridLayer(LayerName.FLAG).get(tilePos));
            flags.add(new Flag(id, tilePos));
        }
        return flags;
    }

    @Override
    public ArrayList<BoardObject> findAllRepairSites() {
        ArrayList<BoardObject> repairSites = new ArrayList<>();
        for(GridPoint2 tilePos : grid.getGridLayer(LayerName.WRENCH).keySet())
            repairSites.add(new BoardObject(grid.findTileName(LayerName.WRENCH, tilePos), tilePos));
        for(GridPoint2 tilePos : grid.getGridLayer(LayerName.WRENCH_HAMMER).keySet())
            repairSites.add(new BoardObject(grid.findTileName(LayerName.WRENCH_HAMMER, tilePos), tilePos));
        return repairSites;
    }

    // TODO : find conveyorbelts, cogs, archive markers etc.
}
