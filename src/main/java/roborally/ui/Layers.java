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
        return !oldLayers.isEmpty();
    }


    @Deprecated
    public boolean contains(String key) {
        return oldLayers.containsKey(key);
    }

    public boolean assertPusherNotNull(GridPoint2 pos) {
        return oldLayers.get("Pusher").getCell(pos.x, pos.y) != null;
    }

    // Robots

    public TiledMapTileLayer getRobots() {
        return oldLayers.get("Robot");
    }

    public TiledMapTileLayer.Cell getRobotTexture(GridPoint2 pos) {
        return oldLayers.get("Robot").getCell(pos.x, pos.y);
    }

    public void setRobotTexture(GridPoint2 pos, TiledMapTileLayer.Cell cell) {
        oldLayers.get("Robot").setCell(pos.x, pos.y, cell);
    }

    public boolean assertRobotNotNull(GridPoint2 pos) {
        if (contains("Robot"))
            return oldLayers.get("Robot").getCell(pos.x, pos.y) != null;
        System.out.println("Robot layer does not exist");
        return false;
    }

    // Walls

    public TiledMapTileLayer getWall() {
        return oldLayers.get("Wall");
    }


    public TiledMapTileLayer.Cell getWallCell(GridPoint2 pos) {
        if (!contains("Wall"))
            return null;
        return oldLayers.get("Wall").getCell(pos.x, pos.y);
    }


    public boolean assertWallNotNull(GridPoint2 pos) {
        if (contains("Wall"))
            return oldLayers.get("Wall").getCell(pos.x, pos.y) != null;
        System.out.println("Wall layer does not exist");
        return false;
    }


    public int getWallID(GridPoint2 pos) {
        if (contains("Wall"))
            return oldLayers.get("Wall").getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("Wall layer does not exist");
        return -1;
    }

    // Floor

    public TiledMapTileLayer getFloor() {
        return oldLayers.get("Floor");
    }


    public boolean assertFloorNotNull(GridPoint2 pos) {
        if (contains("Floor"))
            return oldLayers.get("Floor").getCell(pos.x, pos.y) != null;
        System.out.println("Floor layer does not exist");
        return false;
    }


    public int getWidth() {
        return oldLayers.get("Floor").getWidth();
    }


    public int getHeight() {
        return oldLayers.get("Floor").getHeight();
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
        return oldLayers.get("Flag");
    }


    public boolean assertFlagNotNull(GridPoint2 pos) {
        if (contains("Flag"))
            return oldLayers.get("Flag").getCell(pos.x, pos.y) != null;
        System.out.println("Flag layer does not exist");
        return false;
    }


    public int getFlagID(GridPoint2 pos) {
        if (contains("Flag"))
            return oldLayers.get("Flag").getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("Flag layer does not exist");
        return -1;
    }

    // Start positions

    public TiledMapTileLayer getStartPos() {
        return oldLayers.get("startPositions");
    }


    public boolean assertStartPosNotNull(GridPoint2 pos) {
        if (contains("startPositions"))
            return oldLayers.get("startPositions").getCell(pos.x, pos.y) != null;
        System.out.println("startPositions does not exist");
        return false;
    }

    // Conveyor belts

    public TiledMapTileLayer getConveyorSlow() {
        return oldLayers.get("slowConveyorBelt");
    }


    public boolean assertConveyorSlowNotNull(GridPoint2 pos) {
        if (contains("slowConveyorBelt"))
            return oldLayers.get("slowConveyorBelt").getCell(pos.x, pos.y) != null;
        System.out.println("slowConveyorBelt does not exist");
        return false;
    }


    public TileName getConveyorSlowTileName(GridPoint2 pos) {
        return tiledTranslator.getTileName(oldLayers.get("slowConveyorBelt").getCell(pos.x, pos.y).getTile().getId());
    }


    public TiledMapTileLayer getConveyorFast() {
        return oldLayers.get("fastConveyorBelt");
    }


    public boolean assertConveyorFastNotNull(GridPoint2 pos) {
        if (contains("fastConveyorBelt"))
            return oldLayers.get("fastConveyorBelt").getCell(pos.x, pos.y) != null;
        System.out.println("fastConveyorBelt does not exist");
        return false;
    }


    public TileName getConveyorFastTileName(GridPoint2 pos) {
        return tiledTranslator.getTileName(oldLayers.get("fastConveyorBelt").getCell(pos.x, pos.y).getTile().getId());
    }

    // Wrenches

    public TiledMapTileLayer getWrench() {
        return oldLayers.get("Wrench");
    }


    public boolean assertWrenchNotNull(GridPoint2 pos) {
        if (contains("Wrench"))
            return oldLayers.get("Wrench").getCell(pos.x, pos.y) != null;
        System.out.println("Wrench layer does not exist");
        return false;
    }


    public TiledMapTileLayer getWrenchHammer() {
        return oldLayers.get("Hammer");
    }


    public boolean assertWrenchHammerNotNull(GridPoint2 pos) {
        if (contains("wrenchHammer"))
            return oldLayers.get("wrenchHammer").getCell(pos.x, pos.y) != null;
        System.out.println("wrenchHammer layer does not exist");
        return false;
    }

    // Lasers

    public TiledMapTileLayer getLaser() {
        return oldLayers.get("Laser");
    }


    public boolean assertLaserNotNull(GridPoint2 pos) {
        if (contains("Laser"))
            return oldLayers.get("Laser").getCell(pos.x, pos.y) != null;
        System.out.println("Laser layer does not exist");
        return false;
    }


    public TiledMapTileLayer.Cell getLaserCell(GridPoint2 pos) {
        if (!contains("Laser"))
            return null;
        return oldLayers.get("Laser").getCell(pos.x, pos.y);
    }


    public void setLaserCell(GridPoint2 pos, TiledMapTileLayer.Cell cell) {
        if (contains("Laser"))
            oldLayers.get("Laser").setCell(pos.x, pos.y, cell);
        else
            System.out.println("Laser layer does not exist");
    }


    public int getLaserID(GridPoint2 pos) {
        if (contains("Laser"))
            return oldLayers.get("Laser").getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("Laser layer does not exist");
        return -1;
    }


    public int getLaserCannonID(GridPoint2 pos) {
        if (contains("laserCannon"))
            return oldLayers.get("laserCannon").getCell(pos.x, pos.y).getTile().getId();
        else
            System.out.println("laserCannon layer does not exist");
        return -1;
    }


    public boolean assertLaserCannonNotNull(GridPoint2 pos) {
        if (contains("laserCannon"))
            return oldLayers.get("laserCannon").getCell(pos.x, pos.y) != null;
        System.out.println("laserCannon layer does not exist");
        return false;
    }

    // Gear

    public boolean assertGearNotNull(GridPoint2 pos) {
        return oldLayers.get("Gear").getCell(pos.x, pos.y) != null;
    }


    public TileName getGearTileName(GridPoint2 pos) {
        return tiledTranslator.getTileName(oldLayers.get("Gear").getCell(pos.x, pos.y).getTile().getId());
    }

    // Bug
    // Returns the bug layer.

    public TiledMapTileLayer getBug() {
        return oldLayers.get("bug");
    }


    public boolean assertBugNotNull(GridPoint2 pos) {
        return oldLayers.get("bug").getCell(pos.x, pos.y) != null;
    }
}
