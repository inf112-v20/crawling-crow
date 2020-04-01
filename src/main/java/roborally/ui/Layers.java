package roborally.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;
import roborally.utilities.tiledtranslator.TiledTranslator;

import java.util.HashMap;

// Getters for various layers in the current TiledMap.
public class Layers implements ILayers {
    private TiledTranslator tiledTranslator;
    private HashMap<String, TiledMapTileLayer> oldLayers;

    private HashMap<LayerName, TiledMapTileLayer> layers;

    public Layers() {
        tiledTranslator = new TiledTranslator();
        oldLayers = new HashMap<>(AssetManagerUtil.oldGetLoadedLayers());

        layers = new HashMap<>(AssetManagerUtil.getLoadedLayers());
        // Check if any layers in LayerName is not there
    }


    public boolean isLoaded() {
        return !layers.isEmpty();
    }

    @Override
    public TiledMapTileLayer getLayer(LayerName layerName) {
        return layers.get(layerName);
    }


    @Deprecated
    public boolean contains(String key) {
        return oldLayers.containsKey(key);
    }

    public boolean assertPusherNotNull(GridPoint2 pos) {
        return oldLayers.get("Pusher").getCell(pos.x, pos.y) != null;
    }

    // Robots

    @Deprecated
    public TiledMapTileLayer getRobots() {
        return layers.get(LayerName.ROBOT);
    }

    public TiledMapTileLayer.Cell getRobotTexture(GridPoint2 pos) {
        return layers.get(LayerName.ROBOT).getCell(pos.x, pos.y);
    }

    public void setRobotTexture(GridPoint2 pos, TiledMapTileLayer.Cell cell) {
        layers.get(LayerName.ROBOT).setCell(pos.x, pos.y, cell);
    }

    public boolean assertRobotNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.ROBOT))
            return layers.get(LayerName.ROBOT).getCell(pos.x, pos.y) != null;
        System.out.println("Robot layer does not exist");
        return false;
    }

    // Walls

    public TiledMapTileLayer getWall() {
        return layers.get(LayerName.WALL);
    }


    public TiledMapTileLayer.Cell getWallCell(GridPoint2 pos) {
        if (layers.containsKey(LayerName.WALL))
            return oldLayers.get("Wall").getCell(pos.x, pos.y);
        return null;
    }


    public boolean wallNonNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.WALL))
            return layers.get(LayerName.WALL).getCell(pos.x, pos.y) != null;
        System.out.println("Wall layer does not exist");
        return false;
    }


    public int getWallID(GridPoint2 pos) {
        if (layers.containsKey(LayerName.WALL))
            return layers.get(LayerName.WALL).getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("Wall layer does not exist");
        return -1;
    }

    // Floor

    public TiledMapTileLayer getFloor() {
        return layers.get(LayerName.FLOOR);
    }


    public boolean assertFloorNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.FLOOR))
            return layers.get(LayerName.FLOOR).getCell(pos.x, pos.y) != null;
        System.out.println("Floor layer does not exist");
        return false;
    }


    public int getWidth() {
        return layers.get(LayerName.FLOOR).getWidth();
    }


    public int getHeight() {
        return layers.get(LayerName.FLOOR).getHeight();
    }

    // Holes

    public TiledMapTileLayer getHole() {
        //return oldLayers.get(LayerName.HOLE.getLayerString()); // TODO: Continue :)
        return layers.get(LayerName.HOLE);
    }


    public boolean assertHoleNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.HOLE))
            return layers.get(LayerName.HOLE).getCell(pos.x, pos.y) != null;
        System.out.println("Hole layer does not exist");
        return false;
    }

    // Flags

    public TiledMapTileLayer getFlag() {
        return layers.get(LayerName.FLAG);
    }


    public boolean assertFlagNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.FLAG))
            return layers.get(LayerName.FLAG).getCell(pos.x, pos.y) != null;
        System.out.println("Flag layer does not exist");
        return false;
    }


    public int getFlagID(GridPoint2 pos) {
        if (layers.containsKey(LayerName.FLAG))
            return layers.get(LayerName.FLAG).getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("Flag layer does not exist");
        return -1;
    }

    // Start positions

    public TiledMapTileLayer getStartPos() {
        return layers.get(LayerName.START_POSITIONS);
    }


    public boolean assertStartPosNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.START_POSITIONS))
            return layers.get(LayerName.START_POSITIONS).getCell(pos.x, pos.y) != null;
        System.out.println("startPositions does not exist");
        return false;
    }

    // Conveyor belts

    public TiledMapTileLayer getConveyorSlow() {
        return layers.get(LayerName.CONVEYOR);
    }


    public boolean assertConveyorSlowNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.CONVEYOR))
            return layers.get(LayerName.CONVEYOR).getCell(pos.x, pos.y) != null;
        System.out.println("slowConveyorBelt does not exist");
        return false;
    }


    public TileName getConveyorSlowTileName(GridPoint2 pos) {
        return tiledTranslator.getTileName(layers.get(LayerName.CONVEYOR).getCell(pos.x, pos.y).getTile().getId());
    }


    public TiledMapTileLayer getConveyorFast() {
        return layers.get(LayerName.CONVEYOR_EXPRESS);
    }


    public boolean assertConveyorFastNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.CONVEYOR_EXPRESS))
            return layers.get(LayerName.CONVEYOR_EXPRESS).getCell(pos.x, pos.y) != null;
        System.out.println("fastConveyorBelt does not exist");
        return false;
    }


    public TileName getConveyorFastTileName(GridPoint2 pos) {
        return tiledTranslator.getTileName(layers.get(LayerName.CONVEYOR_EXPRESS).getCell(pos.x, pos.y).getTile().getId());
    }

    // Wrenches

    public TiledMapTileLayer getWrench() {
        return layers.get(LayerName.WRENCH);
    }


    public boolean assertWrenchNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.WRENCH))
            return layers.get(LayerName.WRENCH).getCell(pos.x, pos.y) != null;
        System.out.println("Wrench layer does not exist");
        return false;
    }


    public TiledMapTileLayer getWrenchHammer() {
        return layers.get(LayerName.WRENCH_HAMMER);
    }


    public boolean assertWrenchHammerNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.WRENCH_HAMMER))
            return layers.get(LayerName.WRENCH_HAMMER).getCell(pos.x, pos.y) != null;
        System.out.println("wrenchHammer layer does not exist");
        return false;
    }

    // Lasers

    public TiledMapTileLayer getLaser() {
        return layers.get(LayerName.LASER);
    }


    public boolean assertLaserNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.LASER))
            return layers.get(LayerName.LASER).getCell(pos.x, pos.y) != null;
        System.out.println("Laser layer does not exist");
        return false;
    }


    public TiledMapTileLayer.Cell getLaserCell(GridPoint2 pos) {
        if (!layers.containsKey(LayerName.LASER))
            return null;
        return layers.get(LayerName.LASER).getCell(pos.x, pos.y);
    }


    public void setLaserCell(GridPoint2 pos, TiledMapTileLayer.Cell cell) {
        if (layers.containsKey(LayerName.LASER))
            layers.get(LayerName.LASER).setCell(pos.x, pos.y, cell);
        else
            System.out.println("Laser layer does not exist");
    }


    public int getLaserID(GridPoint2 pos) {
        if (layers.containsKey(LayerName.LASER))
            return layers.get(LayerName.LASER).getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("Laser layer does not exist");
        return -1;
    }


    public int getLaserCannonID(GridPoint2 pos) {
        if (layers.containsKey(LayerName.CANNON))
            return layers.get(LayerName.CANNON).getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("laserCannon layer does not exist");
        return -1;
    }


    public boolean assertLaserCannonNotNull(GridPoint2 pos) {
        if (layers.containsKey(LayerName.CANNON))
            return layers.get(LayerName.CANNON).getCell(pos.x, pos.y) != null;
        System.out.println("laserCannon layer does not exist");
        return false;
    }

    // Gear

    public boolean assertGearNotNull(GridPoint2 pos) {
        return layers.get(LayerName.COG).getCell(pos.x, pos.y) != null;
    }


    public TileName getGearTileName(GridPoint2 pos) {
        return tiledTranslator.getTileName(layers.get(LayerName.COG).getCell(pos.x, pos.y).getTile().getId());
    }

    // Bug
    // Returns the bug layer.

    public TiledMapTileLayer getBug() {
        return layers.get(LayerName.BUG);
    }


    public boolean assertBugNotNull(GridPoint2 pos) {
        return layers.get(LayerName.BUG).getCell(pos.x, pos.y) != null;
    }
}
