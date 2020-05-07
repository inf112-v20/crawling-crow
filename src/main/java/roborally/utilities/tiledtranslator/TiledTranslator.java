package roborally.utilities.tiledtranslator;

import roborally.utilities.enums.TileName;

import java.util.HashMap;

public final class TiledTranslator {

    private final HashMap<Integer, TileName> tiles = new HashMap<>();

    public TiledTranslator() {
        for (TileName tileName : TileName.values()) {
            tiles.put(tileName.getTileID(), tileName);
        }
    }

    /**
     * @param tiledID The int ID of the Tile-element
     * @return The name of the tile as TileName enum using the corresponding tileID
     */
    public TileName getTileName(int tiledID) {
        return tiles.get(tiledID);
    }
}
