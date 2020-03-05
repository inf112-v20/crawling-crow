package roborally.tools.tiledtranslator;

import java.util.HashMap;

public class TiledTranslator implements ITiledTranslator {

    private static HashMap<Integer, TileName> tiles;

    public TiledTranslator() {
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
