package roborally.game.objects.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.Flag;
import roborally.game.objects.IFlag;
import roborally.tools.tiledtranslator.ITiledTranslator;
import roborally.tools.tiledtranslator.TileName;
import roborally.tools.tiledtranslator.TiledTranslator;
import roborally.ui.ILayers;
import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard implements IGameBoard {

    private ILayers layers;
    private HashMap<TileName, Integer> flagIdMap;

    private ITiledTranslator tiledTranslator;

    public GameBoard(ILayers layers) {
        tiledTranslator = new TiledTranslator();

        this.flagIdMap = new HashMap<>();
        this.flagIdMap.put(TileName.FLAG_1, 1);
        this.flagIdMap.put(TileName.FLAG_2, 2);
        this.flagIdMap.put(TileName.FLAG_3, 3);
        this.flagIdMap.put(TileName.FLAG_4, 4);
        this.layers = layers;
    }

    @Override
    public ArrayList<IFlag> findAllFlags() {
        ArrayList<IFlag> flags = new ArrayList<>();
        TiledMapTileLayer flagLayer = layers.getFlag();

        for (int i = 0; i < flagLayer.getWidth(); i++) {
            for (int j = 0; j < flagLayer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = flagLayer.getCell(i, j);
                if (cell != null) {

                    int tileID = cell.getTile().getId();
                    TileName tileName = tiledTranslator.getTileName(tileID);
                    int flagId = flagIdMap.get(tileName);

                    flags.add(new Flag (flagId, new GridPoint2(i,j)));
                }
            }
        }
        return flags;
    }
}
