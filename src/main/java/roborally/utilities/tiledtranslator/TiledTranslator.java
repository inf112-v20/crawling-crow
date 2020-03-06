package roborally.utilities.tiledtranslator;

import roborally.utilities.enums.TileName;

import java.util.HashMap;

public class TiledTranslator implements ITiledTranslator {

    private HashMap<Integer, TileName> tiles;

    public TiledTranslator() {
        this.tiles = new HashMap<>();

        for (TileName tileName : TileName.values()) {
            tiles.put(tileName.getTileID(), tileName);
        }
    }

    @Override
    public TileName getTileName(int tiledID) {
        return tiles.get(tiledID);
    }
}
