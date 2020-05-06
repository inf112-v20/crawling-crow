package roborally.utilities.tiledtranslator;

import roborally.utilities.enums.TileName;

import java.util.HashMap;

public final class TiledTranslator {

    private static HashMap<Integer, TileName> tiles;

    public TiledTranslator() {
        tiles = new HashMap<>();

        for (TileName tileName : TileName.values()) {
            tiles.put(tileName.getTileID(), tileName);
        }
    }

    /**
     * @param tiledID The int ID of the Tile-element
     * @return The name of the tile as TileName enum using the corresponding tileID
     */
    public static TileName getTileName(int tiledID) {
        return tiles.get(tiledID);
    }
}
