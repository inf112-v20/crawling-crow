package roborally.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.utilities.AssetManagerUtil;
import java.util.HashMap;

// Getters for various layers in the current TiledMap. 
public class Layers implements ILayers {
    private HashMap<String,TiledMapTileLayer> layers;
    public Layers() {
        layers = new HashMap<>();
        layers = AssetManagerUtil.getLoadedLayers();
    }

    @Override
    public boolean isLoaded() {
        return !layers.isEmpty();
    }

    @Override
    public boolean contains(String key) {
        return layers.containsKey(key);
    }

    // Robots
    @Override
    public TiledMapTileLayer getRobots() {
        return layers.get("Robot");
    }

    @Override
    public TiledMapTileLayer.Cell getRobotCell(int x, int y) {
        if (!contains("Robot"))
            return null;
        return layers.get("Robot").getCell(x, y);
    }

    @Override
    public void setRobotCell(int x, int y, TiledMapTileLayer.Cell cell) {
        if (contains("Robot"))
            layers.get("Robot").setCell(x, y, cell);
        else
            System.out.println("Robot layer does not exist");
    }

    @Override
    public boolean assertRobotNotNull(int x, int y) {
        if (contains("Robot"))
            return layers.get("Robot").getCell(x, y) != null;
        System.out.println("Robot layer does not exist");
        return false;
    }

    // Walls
    @Override
    public TiledMapTileLayer getWall() {
        return layers.get("Wall"); }

    @Override
    public TiledMapTileLayer.Cell getWallCell(int x, int y) {
        if (!contains("Wall"))
            return null;
        return layers.get("Wall").getCell(x, y); }

    @Override
    public boolean assertWallNotNull(int x, int y) {
        if (contains("Wall"))
            return layers.get("Wall").getCell(x, y) != null;
        System.out.println("Wall layer does not exist");
        return false;
    }

    @Override
    public int getWallID(int x, int y) {
        if (contains("Wall"))
            return layers.get("Wall").getCell(x, y).getTile().getId();
        else
            System.out.println("Wall layer does not exist");
        return -1;
    }

    // Floor
    @Override
    public TiledMapTileLayer getFloor() {
        return layers.get("Floor");
    }

    @Override
    public boolean assertFloorNotNull(int x, int y) {
        if (contains("Floor"))
            return layers.get("Floor").getCell(x, y) != null;
        System.out.println("Floor layer does not exist");
        return false;
    }

    @Override
    public int getWidth() {
        return layers.get("Floor").getWidth();
    }

    @Override
    public int getHeight() {
        return layers.get("Floor").getHeight();
    }

    // Holes
    @Override
    public TiledMapTileLayer getHole() {
        return layers.get("Hole");
    }

    @Override
    public boolean assertHoleNotNull(int x, int y) {
        if (contains("Hole"))
            return layers.get("Hole").getCell(x, y) != null;
        System.out.println("Hole layer does not exist");
        return false;
    }

    // Flags
    @Override
    public TiledMapTileLayer getFlag() {
        return layers.get("Flag");
    }

    @Override
    public boolean assertFlagNotNull(int x, int y) {
        if (contains("Flag"))
            return layers.get("Flag").getCell(x, y) != null;
        System.out.println("Flag layer does not exist");
        return false;
    }

    @Override
    public int getFlagID(int x, int y) {
        if(contains("Flag"))
            return layers.get("Flag").getCell(x, y).getTile().getId();
        else
            System.out.println("Flag layer does not exist");
        return -1;
    }

    // Start positions
    @Override
    public TiledMapTileLayer getStartPos() {
        return layers.get("startPositions");
    }

    @Override
    public boolean assertStartPosNotNull(int x, int y) {
        if (contains("startPositions"))
            return layers.get("startPositions").getCell(x, y) != null;
        System.out.println("startPositions does not exist");
        return false;
    }

    // Conveyor belts
    @Override
    public TiledMapTileLayer getConveyorSlow() {
        return layers.get("slowConveyorBelt");
    }

    @Override
    public boolean assertConveyorSlowNotNull(int x, int y) {
        if (contains("slowConveyorBelt"))
            return layers.get("slowConveyorBelt").getCell(x, y) != null;
        System.out.println("slowConveyorBelt does not exist");
        return false;
    }

    @Override
    public TiledMapTileLayer getConveyorFast() {
        return layers.get("fastConveyorBelt");
    }

    @Override
    public boolean assertConveyorFastNotNull(int x, int y) {
        if (contains("fastConveyorBelt"))
            return layers.get("fastConveyorBelt").getCell(x, y) != null;
        System.out.println("fastConveyorBelt does not exist");
        return false;
    }

    // Wrenches
    @Override
    public TiledMapTileLayer getWrench() {
        return layers.get("Wrench");
    }

    @Override
    public boolean assertWrenchNotNull(int x, int y) {
        if (contains("Wrench"))
            return layers.get("Wrench").getCell(x, y) != null;
        System.out.println("Wrench layer does not exist");
        return false;
    }

    @Override
    public TiledMapTileLayer getWrenchHammer() {
        return layers.get("wrenchHammer");
    }

    @Override
    public boolean assertWrenchHammerNotNull(int x, int y) {
        if (contains("wrenchHammer"))
            return layers.get("wrenchHammer").getCell(x, y) != null;
        System.out.println("wrenchHammer layer does not exist");
        return false;
    }
    // Lasers
    @Override
    public TiledMapTileLayer getLaser() {
        return layers.get("Laser");
    }

    @Override
    public boolean assertLaserNotNull(int x, int y) {
        if (contains("Laser"))
            return layers.get("Laser").getCell(x, y) != null;
        System.out.println("Laser layer does not exist");
        return false;
    }

    @Override
    public TiledMapTileLayer.Cell getLaserCell(int x, int y) {
        if (!contains("Laser"))
            return null;
        return layers.get("Laser").getCell(x, y);
    }

    @Override
    public void setLaserCell(int x, int y, TiledMapTileLayer.Cell cell) {
        if (contains("Laser"))
            layers.get("Laser").setCell(x, y, cell);
        else
            System.out.println("Laser layer does not exist");
    }

    @Override
    public int getLaserID(int x, int y) {
        if(contains("Laser"))
            return layers.get("Laser").getCell(x, y).getTile().getId();
        else
            System.out.println("Laser layer does not exist");
        return -1;
    }

    @Override
    public int getLaserCannonID(int x, int y) {
        if(contains("laserCannon"))
            return layers.get("laserCannon").getCell(x, y).getTile().getId();
        else
            System.out.println("laserCannon layer does not exist");
        return -1;
    }

    @Override
    public boolean assertLaserCannonNotNull(int x, int y) {
        if (contains("laserCannon"))
            return layers.get("laserCannon").getCell(x, y) != null;
        System.out.println("laserCannon layer does not exist");
        return false;
    }

    // Bug
    // Returns the bug layer.
    @Override
    public TiledMapTileLayer getBug() {
        return layers.get("bug");
    }
    @Override
    public boolean assertBugNotNull(int x, int y) {
        return layers.get("bug").getCell(x, y) != null;
    }

}
