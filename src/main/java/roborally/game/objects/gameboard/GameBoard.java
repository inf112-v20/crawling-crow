package roborally.game.objects.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.tiledtranslator.ITiledTranslator;
import roborally.utilities.enums.TileName;
import roborally.utilities.tiledtranslator.TiledTranslator;
import roborally.ui.ILayers;
import roborally.ui.Layers;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard implements IGameBoard {

    private ILayers layers;
    private HashMap<TileName, Integer> flagIdMap;

    private ITiledTranslator tiledTranslator;

    public GameBoard() {
        tiledTranslator = new TiledTranslator();

        this.flagIdMap = new HashMap<>();
        this.flagIdMap.put(TileName.FLAG_1, 1);
        this.flagIdMap.put(TileName.FLAG_2, 2);
        this.flagIdMap.put(TileName.FLAG_3, 3);
        this.flagIdMap.put(TileName.FLAG_4, 4);
        this.layers = new Layers();
    }

    @Override
    public ArrayList<IFlag> findAllFlags() {
        ArrayList<IFlag> flags = new ArrayList<>();
        TiledMapTileLayer flagLayer = layers.getFlag();

        for (int x = 0; x < flagLayer.getWidth(); x++) {
            for (int y = 0; y < flagLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = flagLayer.getCell(x, y);
                if (cell != null) {
                    int tileID = cell.getTile().getId();
                    TileName tileName = tiledTranslator.getTileName(tileID);
                    int flagId = flagIdMap.get(tileName);

                    flags.add(new Flag (flagId, new GridPoint2(x, y)));
                }
            }
        }
        return flags;
    }

    @Override
    public ArrayList<RepairSite> findAllRepairSites() {
        ArrayList<RepairSite> repairSites = new ArrayList<>();
        TiledMapTileLayer wrench = layers.getWrench();
        TiledMapTileLayer wrenchHammer = layers.getWrenchHammer();

        for (int x = 0; x < wrench.getWidth(); x++) {
            for (int y = 0; y < wrench.getHeight(); y++) {
                TiledMapTileLayer.Cell wrenchCell = wrench.getCell(x, y);
                if (wrenchCell != null) {
                    TileName type = tiledTranslator.getTileName(wrenchCell.getTile().getId());
                    repairSites.add(new RepairSite (type, new GridPoint2(x, y)));
                }
            }
        }

        for (int x = 0; x < wrenchHammer.getWidth(); x++) {
            for (int y = 0; y < wrenchHammer.getHeight(); y++) {
                TiledMapTileLayer.Cell wrenchHammerCell = wrenchHammer.getCell(x, y);
                if (wrenchHammerCell != null) {
                    TileName type = tiledTranslator.getTileName(wrenchHammerCell.getTile().getId());
                    repairSites.add(new RepairSite (type, new GridPoint2(x, y)));
                }
            }
        }

        return repairSites;
    }

    @Override
    public ILayers getLayers() {
        return this.layers;
    }

    // TODO : find conveyorbelts, cogs, archive markers etc.
}
