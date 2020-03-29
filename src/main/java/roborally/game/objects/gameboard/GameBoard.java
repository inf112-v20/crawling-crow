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

    // TODO: Ref the TODO on bottom
    private ArrayList<BoardObject> conveyorBelts;
    private ArrayList<BoardObject> conveyorExpressBelts;
    private ArrayList<BoardObject> cogs;

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

        addFlags(flags, flagLayer);
        return flags;
    }

    private void addFlags(ArrayList<IFlag> flags, TiledMapTileLayer flagLayer) {
        for (int x = 0; x < flagLayer.getWidth(); x++) {
            for (int y = 0; y < flagLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = flagLayer.getCell(x, y);
                if (cell != null) {
                    int tileID = cell.getTile().getId();
                    TileName tileName = tiledTranslator.getTileName(tileID);
                    int flagId = flagIdMap.get(tileName);

                    flags.add(new Flag(flagId, new GridPoint2(x, y)));
                }
            }
        }
    }

    @Override
    public ArrayList<BoardObject> findAllRepairSites() {
        ArrayList<BoardObject> repairSites = new ArrayList<>();
        TiledMapTileLayer wrench = layers.getWrench();
        TiledMapTileLayer wrenchHammer = layers.getWrenchHammer();

        addRepairSites(repairSites, wrench);
        addRepairSites(repairSites, wrenchHammer);

        return repairSites;
    }

    private void addRepairSites(ArrayList<BoardObject> repairSites, TiledMapTileLayer layer) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell layerCell = layer.getCell(x, y);
                if (layerCell != null) {
                    int tileID = layerCell.getTile().getId();
                    TileName type = tiledTranslator.getTileName(tileID);
                    repairSites.add(new BoardObject(type, new GridPoint2(x, y)));
                }
            }
        }
    }

    @Override
    public ILayers getLayers() {
        return this.layers;
    }

    // TODO : find conveyorbelts, cogs, archive markers etc.
}
