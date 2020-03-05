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

    /** A method that looks through the respective ID's from the tileset, for relevant walls for the robot as it
     * tries to move.
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return True if it finds a wall that corresponds to a wall in x or y direction that blocks the robot.
     */
    default boolean checkForWall(int x, int y, int dx, int dy) {
        boolean wall = false;
        if(assertWallNotNull(x, y)) {
            int id = getWallID(x, y);
            if (dy > 0)
                wall = id == 31 || id == 16 || id == 24;
            else if (dy < 0)
                wall = id == 29 || id == 8 || id == 32;
            else if (dx > 0)
                wall = id == 23 || id == 16 || id == 8;
            else if (dx < 0)
                wall = id == 30 || id == 32 || id == 24;
        }
        if(assertWallNotNull(x + dx, y + dy) && !wall) {
            int id = getWallID(x + dx, y + dy);
            if (dy > 0)
                return id == 8 || id == 29 || id == 32;
            else if (dy < 0)
                return id == 24 || id == 16 || id == 31;
            else if (dx > 0)
                return id == 30 || id == 24 || id == 32;
            else if (dx < 0)
                return id == 16 || id == 8 || id == 23;
        }
        return wall;
    }
}
