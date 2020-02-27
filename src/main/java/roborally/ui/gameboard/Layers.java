package roborally.ui.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.tools.AssetsManager;

import java.util.HashMap;

// Getters for various layers in the current TiledMap. 
public class Layers {
    private HashMap<String,TiledMapTileLayer> layers;
    public Layers() {
        layers = new HashMap<>();
        layers = AssetsManager.getLoadedLayers();
    }

    // Checks if the layers has been loaded.
    public boolean isLoaded() {
        return !layers.isEmpty();
    }

    // Checks if a given layer is within the layers of the loaded map.
    public boolean contains(String key) {
        return layers.containsKey(key);
    }

    // Robots
    // Returns the robotLayer.
    public TiledMapTileLayer getRobots() {
        return layers.get("Robot");
    }

    // Returns a robotCell at position x, y.
    public TiledMapTileLayer.Cell getRobotCell(int x, int y) {
        if (!contains("Robot"))
            return null;
        return layers.get("Robot").getCell(x, y);
    }

    // Set a cell in the robotLayer to a new cell value.
    public void setRobotCell(int x, int y, TiledMapTileLayer.Cell cell) {
        if (contains("Robot"))
            layers.get("Robot").setCell(x, y, cell);
        else
            System.out.println("Robot layer does not exist");
    }

    // Checks if there is a robot at the position x, y in the robotLayer
    public boolean assertRobotNotNull(int x, int y) {
        if (contains("Robot"))
            return layers.get("Robot").getCell(x, y) != null;
        System.out.println("Robot layer does not exist");
        return false;
    }

    // Walls
    // Returns the wallLayer
    public TiledMapTileLayer getWall() {
        return layers.get("Wall"); }

    // Returns a Wall Cell at position x, y.
    public TiledMapTileLayer.Cell getWallCell(int x, int y) {
        if (!contains("Wall"))
            return null;
        return layers.get("Wall").getCell(x, y); }

    // Checks if there is a wall at the position x, y.
    public boolean assertWallNotNull(int x, int y) {
        if (contains("Wall"))
            return layers.get("Wall").getCell(x, y) != null;
        System.out.println("Wall layer does not exist");
        return false;
    }

    // Returns the ID for the wall at the position x, y in the wallLayer.
    public int getWallID(int x, int y) {
        if (contains("Wall"))
            return layers.get("Wall").getCell(x, y).getTile().getId();
        else
            System.out.println("Wall layer does not exist");
        return -1;
    }

    // Floor

    public TiledMapTileLayer getFloor() {
        return layers.get("Floor");
    }

    // Checks if there is floor at the position x, y.
    public boolean assertFloorNotNull(int x, int y) {
        if (contains("Floor"))
            return layers.get("Floor").getCell(x, y) != null;
        System.out.println("Floor layer does not exist");
        return false;
    }

    // Holes
    // Returns the holeLayer.
    public TiledMapTileLayer getHole() {
        return layers.get("Hole");
    }

    // Checks if there is a hole at the position x, y.
    public boolean assertHoleNotNull(int x, int y) {
        if (contains("Hole"))
            return layers.get("Hole").getCell(x, y) != null;
        System.out.println("Hole layer does not exist");
        return false;
    }

    // Flags
    // Returns the flagLayer.
    public TiledMapTileLayer getFlag() {
        return layers.get("Flag");
    }

    // Checks if there is a flag at position x, y.
    public boolean assertFlagNotNull(int x, int y) {
        if (contains("Flag"))
            return layers.get("Flag").getCell(x, y) != null;
        System.out.println("Flag layer does not exist");
        return false;
    }

    // Returns the ID for the wall at the location x, y in the flagLayer.
    public int getFlagID(int x, int y) {
        if(contains("Flag"))
            return layers.get("Flag").getCell(x, y).getTile().getId();
        else
            System.out.println("Flag layer does not exist");
        return -1;
    }

    // Start positions
    // Returns the startPosLayer
    public TiledMapTileLayer getStartPos() {
        return layers.get("startPositions");
    }

    // Checks if there is a start position at the position x, y.
    public boolean assertStartPosNotNull(int x, int y) {
        if (contains("startPositions"))
            return layers.get("startPositions").getCell(x, y) != null;
        System.out.println("startPositions does not exist");
        return false;
    }

    // Conveyor belts
    // Returns the slow conveyor belt layer
    public TiledMapTileLayer getConveyorSlow() {
        return layers.get("slowConveyorBelt");
    }

    // Checks if there is a Slow conveyor belt at the position x, y.
    public boolean assertConveyorSlowNotNull(int x, int y) {
        if (contains("slowConveyorBelt"))
            return layers.get("slowConveyorBelt").getCell(x, y) != null;
        System.out.println("slowConveyorBelt does not exist");
        return false;
    }

    // Returns the fast conveyor belt layer.
    public TiledMapTileLayer getConveyorFast() {
        return layers.get("fastConveyorBelt");
    }

    // Checks if there is a fast conveyor belt at the position x, y.
    public boolean assertConveyorFastNotNull(int x, int y) {
        if (contains("fastConveyorBelt"))
            return layers.get("fastConveyorBelt").getCell(x, y) != null;
        System.out.println("fastConveyorBelt does not exist");
        return false;
    }

    // Wrenches
    // Returns the wrench layer.
    public TiledMapTileLayer getWrench() {
        return layers.get("Wrench");
    }

    // Checks if there is a wrench at position x, y.
    public boolean assertWrenchNotNull(int x, int y) {
        if (contains("Wrench"))
            return layers.get("Wrench").getCell(x, y) != null;
        System.out.println("Wrench layer does not exist");
        return false;
    }

    // Returns the wrench hammer layer.
    public TiledMapTileLayer getWrenchHammer() {
        return layers.get("wrenchHammer");
    }

    // checks if there is a wrench hammer at the position x, y.
    public boolean assertWrenchHammerNotNull(int x, int y) {
        if (contains("wrenchHammer"))
            return layers.get("wrenchHammer").getCell(x, y) != null;
        System.out.println("wrenchHammer layer does not exist");
        return false;
    }
    // Lasers
    // Return the laser layers
    public TiledMapTileLayer getLaser() {
        return layers.get("Laser");
    }

    public boolean assertLaserNotNull(int x, int y) {
        if (contains("Laser"))
            return layers.get("Laser").getCell(x, y) != null;
        System.out.println("Laser layer does not exist");
        return false;
    }

    public TiledMapTileLayer.Cell getLaserCell(int x, int y) {
        if (!contains("Laser"))
            return null;
        return layers.get("Laser").getCell(x, y);
    }

    public void setLaserCell(int x, int y, TiledMapTileLayer.Cell cell) {
        if (contains("Laser"))
            layers.get("Laser").setCell(x, y, cell);
        else
            System.out.println("Laser layer does not exist");
    }

    public int getLaserID(int x, int y) {
        if(contains("Laser"))
            return layers.get("Laser").getCell(x, y).getTile().getId();
        else
            System.out.println("Laser layer does not exist");
        return -1;
    }

    public int getLaserCannonID(int x, int y) {
        if(contains("laserCannon"))
            return layers.get("laserCannon").getCell(x, y).getTile().getId();
        else
            System.out.println("laserCannon layer does not exist");
        return -1;
    }

    public boolean assertLaserCannonNotNull(int x, int y) {
        if (contains("laserCannon"))
            return layers.get("laserCannon").getCell(x, y) != null;
        System.out.println("laserCannon layer does not exist");
        return false;
    }

    // Bug
    // Returns the bug layer.
    public TiledMapTileLayer getBug() {
        return layers.get("bug");
    }
}
