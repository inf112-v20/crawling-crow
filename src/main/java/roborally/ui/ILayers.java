package roborally.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

// Brief overview of the methods used to access the tiled map.
public interface ILayers {

    /**
     * @return true if the layers has been loaded.
     */
    boolean isLoaded();

    TiledMapTileLayer getLayer(LayerName layerName);

    /**
     * @param key String
     * @return true if a given layer is within the layers of the loaded map
     */
    boolean contains(String key);

    /**
     * @return the robotLayer.
     */
    TiledMapTileLayer getRobots();

    /**
     * @param pos position
     * @return The robotTexture at position pos
     */
    TiledMapTileLayer.Cell getRobotTexture(GridPoint2 pos);

    /**
     * Set a cell in the robotLayer to a new cell value.
     *
     * @param pos  position to change the cell
     * @param cell type
     */
    void setRobotTexture(GridPoint2 pos, TiledMapTileLayer.Cell cell);

    /**
     * @param pos position
     * @return true if there is a robot at the position x, y in the robotLayer
     */
    boolean assertRobotNotNull(GridPoint2 pos);

    /**
     * @return the wallLayer
     */
    TiledMapTileLayer getWall();

    /**
     * @param pos position
     * @return a Wall Cell at position x, y.
     */
    TiledMapTileLayer.Cell getWallCell(GridPoint2 pos);

    /**
     * @param pos position
     * @return true if there is a wall at the position x, y.
     */
    boolean wallNonNull(GridPoint2 pos);

    /**
     * @param pos position
     * @return the ID for the wall at the position x, y in the wallLayer.
     */
    int getWallID(GridPoint2 pos);

    /**
     * @return the floorLayer
     */
    TiledMapTileLayer getFloor();

    /**
     * @param pos position
     * @return true if there is floor at the position x, y.
     */
    boolean assertFloorNotNull(GridPoint2 pos);

    /**
     * @return the width of the map.
     */
    int getWidth();

    /**
     * @return the height of the map
     */
    int getHeight();

    /**
     * @return the holeLayer.
     */
    TiledMapTileLayer getHole();

    /**
     * @param pos position
     * @return if there is a hole at the position x, y.
     */
    boolean assertHoleNotNull(GridPoint2 pos);

    /**
     * @return the flagLayer.
     */
    TiledMapTileLayer getFlag();

    /**
     * @param pos position
     * @return if there is a flag at position x, y.
     */
    boolean assertFlagNotNull(GridPoint2 pos);

    /**
     * @param pos position
     * @return the ID for the wall at the location x, y in the flagLayer.
     */
    int getFlagID(GridPoint2 pos);

    /**
     * @return the startPosLayer
     */
    TiledMapTileLayer getStartPos();

    /**
     * @param pos position
     * @return if there is a start position at the position x, y.
     */
    boolean assertStartPosNotNull(GridPoint2 pos);

    /**
     * @return the slow conveyor belt layer.
     */
    TiledMapTileLayer getConveyorSlow();

    /**
     * @param pos position
     * @return if there is a Slow conveyor belt at the position x, y.
     */
    boolean assertConveyorSlowNotNull(GridPoint2 pos);

    TileName getConveyorSlowTileName(GridPoint2 pos);

    /**
     * @return the fast conveyor belt layer.
     */
    TiledMapTileLayer getConveyorFast();

    /**
     * @param pos position
     * @return if there is a fast conveyor belt at the position x, y.
     */
    boolean assertConveyorFastNotNull(GridPoint2 pos);

    TileName getConveyorFastTileName(GridPoint2 pos);

    /**
     * @return the wrench layer.
     */
    TiledMapTileLayer getWrench();

    /**
     * @param pos position
     * @return if there is a wrench at position x, y.
     */
    boolean assertWrenchNotNull(GridPoint2 pos);

    /**
     * @return the wrench hammer layer.
     */
    TiledMapTileLayer getWrenchHammer();

    /**
     * @param pos position
     * @return true if there is a wrench hammer at the position x, y.
     */
    boolean assertWrenchHammerNotNull(GridPoint2 pos);

    /**
     * @return the laser layers
     */
    TiledMapTileLayer getLaser();

    /**
     * @param pos position
     * @return true if there is a laser at the position x, y.
     */
    boolean assertLaserNotNull(GridPoint2 pos);

    /**
     * @param pos position
     * @return a Laser Cell at position x, y.
     */
    TiledMapTileLayer.Cell getLaserCell(GridPoint2 pos);

    /**
     * @param pos  position
     * @param cell type
     */
    void setLaserCell(GridPoint2 pos, TiledMapTileLayer.Cell cell);

    /**
     * @param pos position
     * @return the ID for the laser at the position x, y in the laser Layer.
     */
    int getLaserID(GridPoint2 pos);

    /**
     * @param pos position
     * @return the ID for the laser cannon at the position x, y in the cannon Layer.
     */
    int getLaserCannonID(GridPoint2 pos);

    /**
     * @param pos position
     * @return true if there is a laser cannon at the position x, y.
     */
    boolean assertLaserCannonNotNull(GridPoint2 pos);

    /**
     * @return the bug layer
     */
    TiledMapTileLayer getBug();

    /**
     * @param pos position
     * @return true if there is a bug at the position x, y.
     */
    boolean assertBugNotNull(GridPoint2 pos);

    boolean assertGearNotNull(GridPoint2 pos);

    TileName getGearTileName(GridPoint2 pos);
}