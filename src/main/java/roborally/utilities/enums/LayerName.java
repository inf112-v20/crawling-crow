package roborally.utilities.enums;

public enum LayerName {
    HOLE("Hole"),
    WALL("Walls"),
    FLOOR("Floor"),
    FLAG("Flag"),
    START_POSITIONS("startPositions"),
    CONVEYOR("slowConveyorBelt"),
    CONVEYOR_EXPRESS("fastConveyorBelt"),
    WRENCH("Wrench"),
    WRENCH_HAMMER("wrenchHammer"),
    LASER("Laser"),
    CANNON("laserCannon"),
    COG("Gear"),
    BUG("bug");

    private final String layerString;

    LayerName(String layerString) {
        this.layerString = layerString;
    }

    public String getLayerString() {
        return this.layerString;
    }
}
