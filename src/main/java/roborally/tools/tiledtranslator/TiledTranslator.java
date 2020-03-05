package roborally.tools.tiledtranslator;

import java.util.HashMap;

public class TiledTranslator implements ITiledTranslator {

    private HashMap<Integer, TileName> tiles;

    TiledTranslator() {
        tiles = new HashMap<>();

        for (TileName tileName : TileName.values()) {
            tiles.put(tileName.getTileID(), tileName);
        }
    }

    @Override
    public TileName getTileName(int tiledID) {
        return tiles.get(tiledID);
    }
}
