package roborally.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

public interface ILayers {
    /**
     * @return the width of the map.
     */
    int getWidth();

    /**
     * @return the height of the map.
     */
    int getHeight();

    /**
     * Returns TileMapTileLayer based on LayerName.
     *
     * @param layerName The LayerName to be returned
     * @return The layer
     */
    TiledMapTileLayer getLayer(LayerName layerName);

    /**
     *
     * @param layerName The LayerName of the TileName to be returned.
     * @param position The position of the Layer
     * @return The TileName
     */
    TileName getTileName(LayerName layerName, GridPoint2 position);

    /**
     * Returns ID of the layer based on LayerName and its position.
     *
     * @param layerName The LayerName of the ID
     * @param position The LayerNames positions
     * @return The ID of the layer
     */
    int getLayerID(LayerName layerName, GridPoint2 position);

    /**
     * Checks that the layer exists.
     *
     * @param layerName LayerName to be checked
     * @param position The position of the LayerName to be checked
     * @return True if the layer exists, false otherwise
     */
    boolean layerNotNull(LayerName layerName, GridPoint2 position);

    /**
     * Sets a Cell to specific LayerName
     *
     * @param layerName Layer to be assigned Cell
     * @param position Position of the Cell
     * @param cell The Cell in question
     */
    void setLayerCell(LayerName layerName, GridPoint2 position, TiledMapTileLayer.Cell cell);

    /**
     * @param pos position
     * @return The robotTexture at the position
     */
    TiledMapTileLayer.Cell getRobotTexture(GridPoint2 pos);

    /**
     * Set a Cell in the robotLayer to a new cell value.
     *
     * @param pos  position to change the Robot
     * @param cell type
     */
    void setRobotTexture(GridPoint2 pos, TiledMapTileLayer.Cell cell);
}