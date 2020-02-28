package roborally.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface ILayers {

    /**
     *
     * @return true if the layers has been loaded.
     */
    boolean isLoaded();

    /**
     *
     * @param key String
     * @return true if a given layer is within the layers of the loaded map
     */
    boolean contains(String key);

    /**
     *
     * @return the robotLayer.
     */
    TiledMapTileLayer getRobots();

    /**
     *
     * @param x position
     * @param y position
     * @return a robotCell at position x, y.
     */
    TiledMapTileLayer.Cell getRobotCell(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @param cell type
     * Set a cell in the robotLayer to a new cell value.
     */
    void setRobotCell(int x, int y, TiledMapTileLayer.Cell cell);

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is a robot at the position x, y in the robotLayer
     */
    boolean assertRobotNotNull(int x, int y);

    /**
     *
     * @return the wallLayer
     */
    TiledMapTileLayer getWall();

    /**
     *
     * @param x position
     * @param y position
     * @return a Wall Cell at position x, y.
     */
    TiledMapTileLayer.Cell getWallCell(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is a wall at the position x, y.
     */
    boolean assertWallNotNull(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @return the ID for the wall at the position x, y in the wallLayer.
     */
    int getWallID(int x, int y);

    /**
     *
     * @return the floorLayer
     */
    TiledMapTileLayer getFloor();

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is floor at the position x, y.
     */
    boolean assertFloorNotNull(int x, int y);

    /**
     *
     * @return the width of the map.
     */
    int getWidth();

    /**
     *
     * @return the height of the map
     */
    int getHeight();

    /**
     *
     * @return the holeLayer.
     */
    TiledMapTileLayer getHole();

    /**
     *
     * @param x position
     * @param y position
     * @return if there is a hole at the position x, y.
     */
    boolean assertHoleNotNull(int x, int y);

    /**
     *
     * @return the flagLayer.
     */
    TiledMapTileLayer getFlag();

    /**
     *
     * @param x position
     * @param y position
     * @return if there is a flag at position x, y.
     */
    boolean assertFlagNotNull(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @return the ID for the wall at the location x, y in the flagLayer.
     */
    int getFlagID(int x, int y);

    /**
     *
     * @return the startPosLayer
     */
    TiledMapTileLayer getStartPos();

    /**
     *
     * @param x position
     * @param y position
     * @return if there is a start position at the position x, y.
     */
    boolean assertStartPosNotNull(int x, int y);

    /**
     *
     * @return the slow conveyor belt layer.
     */
    TiledMapTileLayer getConveyorSlow();

    /**
     *
     * @param x position
     * @param y position
     * @return if there is a Slow conveyor belt at the position x, y.
     */
    boolean assertConveyorSlowNotNull(int x, int y);

    /**
     *
     * @return the fast conveyor belt layer.
     */
    TiledMapTileLayer getConveyorFast();

    /**
     *
     * @param x position
     * @param y position
     * @return if there is a fast conveyor belt at the position x, y.
     */
    boolean assertConveyorFastNotNull(int x, int y);

    /**
     *
     * @return the wrench layer.
     */
    TiledMapTileLayer getWrench();

    /**
     *
     * @param x position
     * @param y position
     * @return if there is a wrench at position x, y.
     */
    boolean assertWrenchNotNull(int x, int y);

    /**
     *
     * @return the wrench hammer layer.
     */
    TiledMapTileLayer getWrenchHammer();

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is a wrench hammer at the position x, y.
     */
    boolean assertWrenchHammerNotNull(int x, int y);

    /**
     *
     * @return the laser layers
     */
    TiledMapTileLayer getLaser();

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is a laser at the position x, y.
     */
    boolean assertLaserNotNull(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @return a Laser Cell at position x, y.
     */
    TiledMapTileLayer.Cell getLaserCell(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @param cell type
     */
    void setLaserCell(int x, int y, TiledMapTileLayer.Cell cell);

    /**
     *
     * @param x position
     * @param y position
     * @return the ID for the laser at the position x, y in the laser Layer.
     */
    int getLaserID(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @return the ID for the laser cannon at the position x, y in the cannon Layer.
     */
    int getLaserCannonID(int x, int y);

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is a laser cannon at the position x, y.
     */
    boolean assertLaserCannonNotNull(int x, int y);

    /**
     *
     * @return the bug layer
     */
    TiledMapTileLayer getBug();

    /**
     *
     * @param x position
     * @param y position
     * @return true if there is a bug at the position x, y.
     */
    boolean assertBugNotNull(int x, int y);
}
