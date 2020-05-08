package roborally.utilities.enums;

public enum LayerName {
    ROBOT("Robot"),
    HOLE("Hole"),
    BLACK_VOID("BlackVoid"),
    WALL("Walls"),
    FLOOR("Floor"),
    FLAG("Flag"),
    START_POSITIONS("StartPositions"),
    CONVEYOR("SlowConveyorBelt"),
    CONVEYOR_EXPRESS("FastConveyorBelt"),
    WRENCH("Wrench"),
    WRENCH_HAMMER("Hammer"),
    LASER("Laser"),
    CANNON("Cannon"),
    COG("RotatingGear"),
    BUG("Bug"),
    PUSHERS("Pushers");

    private final String layerString;

    LayerName(String layerString) {
        this.layerString = layerString;
    }

    public String getLayerString() {
        return layerString.toLowerCase();
    }
}
