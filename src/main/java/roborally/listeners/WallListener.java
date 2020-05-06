package roborally.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.gameview.layout.ILayers;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;
import roborally.utilities.tiledtranslator.TiledTranslator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WallListener {
    private HashMap<String, List<TileName>> mapOfWallNames;
    //private TiledTranslator tiledTranslator;
    private ILayers layers;

    public WallListener(ILayers layers) {
        this.mapOfWallNames = makeWallMap();
        //this.tiledTranslator = new TiledTranslator();
        this.layers = layers;
    }

    /**
     * A method that looks through the respective ID's from the tileset, for relevant walls for the robot as it
     * tries to move.
     * @param move GridPoint2 with steps in x and y direction.
     * @param pos GridPoint2 position of the robot.
     * @return True if it finds a wall that corresponds to a wall in x or y direction that blocks the robot.
     */
    public boolean checkForWall(GridPoint2 pos, GridPoint2 move) {
        boolean wall = false;
        TileName wallName;
        if (layers.layerNotNull(LayerName.WALL, pos)) {
            wallName = SettingsUtil.TILED_TRANSLATOR.getTileName(layers.getLayerID(LayerName.WALL, pos));
            if (move.y > 0)
                wall = mapOfWallNames.get("North").contains(wallName);
            else if (move.y < 0)
                wall = mapOfWallNames.get("South").contains(wallName);
            else if (move.x > 0)
                wall = mapOfWallNames.get("East").contains(wallName);
            else if (move.x < 0)
                wall = mapOfWallNames.get("West").contains(wallName);
        }
        GridPoint2 nextPos = pos.cpy().add(move);
        if (layers.layerNotNull(LayerName.WALL, nextPos) && !wall) {
            wallName = SettingsUtil.TILED_TRANSLATOR.getTileName(layers.getLayerID(LayerName.WALL, nextPos));
            if (move.y > 0)
                return mapOfWallNames.get("South").contains(wallName);
            else if (move.y < 0)
                return mapOfWallNames.get("North").contains(wallName);
            else if (move.x > 0)
                return mapOfWallNames.get("West").contains(wallName);
            else if (move.x < 0)
                return mapOfWallNames.get("East").contains(wallName);
        }
        return wall;
    }

    public HashMap<String, List<TileName>> makeWallMap() {
        HashMap<String, List<TileName>> mapOfWallNames = new HashMap<>();
        TileName[] tnNorth = {TileName.WALL_NORTH, TileName.WALL_CORNER_NORTH_WEST, TileName.WALL_CORNER_NORTH_EAST};
        TileName[] tnSouth = {TileName.WALL_SOUTH, TileName.WALL_CORNER_SOUTH_WEST, TileName.WALL_CORNER_SOUTH_EAST};
        TileName[] tnWest = {TileName.WALL_WEST, TileName.WALL_CORNER_SOUTH_WEST, TileName.WALL_CORNER_NORTH_WEST};
        TileName[] tnEast = {TileName.WALL_EAST, TileName.WALL_CORNER_NORTH_EAST, TileName.WALL_CORNER_SOUTH_EAST};
        mapOfWallNames.put("North", (Arrays.asList(tnNorth)));
        mapOfWallNames.put("South", (Arrays.asList(tnSouth)));
        mapOfWallNames.put("East", (Arrays.asList(tnEast)));
        mapOfWallNames.put("West", (Arrays.asList(tnWest)));
        return mapOfWallNames;
    }
}
