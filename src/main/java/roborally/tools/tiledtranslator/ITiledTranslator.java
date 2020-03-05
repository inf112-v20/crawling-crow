package roborally.tools.tiledtranslator;

public interface ITiledTranslator {
    /**
     * @param tiledID The int ID of the Tile-element
     * @return The name of the tile as TileName enum using the corresponding tileID
     */
    TileName getTileName(int tiledID);
}
