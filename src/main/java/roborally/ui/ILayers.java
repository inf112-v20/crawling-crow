package roborally.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface ILayers {

    boolean isLoaded();

    boolean contains(String key);

    TiledMapTileLayer getRobots();

    TiledMapTileLayer.Cell getRobotCell(int x, int y);

    void setRobotCell(int x, int y, TiledMapTileLayer.Cell cell);

    boolean assertRobotNotNull(int x, int y);

    TiledMapTileLayer getWall();

    boolean assertWallNotNull(int x, int y);

    int getWallID(int x, int y);

    TiledMapTileLayer getFloor();

    boolean assertFloorNotNull(int x, int y);

    int getWidth();

    int getHeight();

    TiledMapTileLayer getHole();

    boolean assertHoleNotNull(int x, int y);

    TiledMapTileLayer getFlag();

    boolean assertFlagNotNull(int x, int y);

    int getFlagID(int x, int y);

    TiledMapTileLayer getStartPos();

    boolean assertStartPosNotNull(int x, int y);

    TiledMapTileLayer getConveyorSlow();

    boolean assertConveyorSlowNotNull(int x, int y);

    TiledMapTileLayer getConveyorFast();

    boolean assertConveyorFastNotNull(int x, int y);

    TiledMapTileLayer getWrench();

    boolean assertWrenchNotNull(int x, int y);

    TiledMapTileLayer getWrenchHammer();

    boolean assertWrenchHammerNotNull(int x, int y);

    TiledMapTileLayer getLaser();

    boolean assertLaserNotNull(int x, int y);

    TiledMapTileLayer.Cell getLaserCell(int x, int y);

    void setLaserCell(int x, int y, TiledMapTileLayer.Cell cell);

    int getLaserID(int x, int y);

    int getLaserCannonID(int x, int y);

    boolean assertLaserCannonNotNull(int x, int y);

    TiledMapTileLayer getBug();

    boolean assertBugNotNull(int x, int y);
}
