package roborally.gameview.layout;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.jetbrains.annotations.NotNull;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.HashMap;

// Getters for various layers in the current TiledMap.
public class Layers implements ILayers {
    private HashMap<LayerName, TiledMapTileLayer> layers;

    public Layers() {
        layers = new HashMap<>(AssetManagerUtil.getLoadedLayers());
    }

    @Override
    public int getWidth() {
        return layers.get(LayerName.FLOOR).getWidth();
    }

    @Override
    public int getHeight() {
        return layers.get(LayerName.FLOOR).getHeight();
    }

    @Override
    public TiledMapTileLayer getLayer(LayerName layerName) {
        return layers.get(layerName);
    }

    @Override
    public TileName getTileName(LayerName layerName, @NotNull GridPoint2 position) {
        return SettingsUtil.TILED_TRANSLATOR.getTileName(getLayer(layerName).getCell(position.x, position.y).getTile().getId());
    }

    @Override
    public int getLayerID(LayerName layerName, GridPoint2 position) {
        if (layerNotNull(layerName, position))
            return layers.get(layerName).getCell(position.x, position.y).getTile().getId();
        return -1;
    }

    @Override
    public boolean layerNotNull(LayerName layerName, GridPoint2 position) {
        if (layers.containsKey(layerName))
            return layers.get(layerName).getCell(position.x, position.y) != null;
        System.out.println("'" + layerName + "'-layer does not exist");
        return false;
    }

    @Override
    public void setLayerCell(LayerName layerName, GridPoint2 position, TiledMapTileLayer.Cell cell) {
        if (layers.containsKey(layerName)) {
            layers.get(layerName).setCell(position.x, position.y, cell);
        } else {
            System.out.println("'" + layerName + "'-layer does not exist");
        }
    }

    @Override
    public TiledMapTileLayer.Cell getRobotTexture(@NotNull GridPoint2 pos) {
        return layers.get(LayerName.ROBOT).getCell(pos.x, pos.y);
    }

    @Override
    public void setRobotTexture(@NotNull GridPoint2 pos, TiledMapTileLayer.Cell cell) {
        layers.get(LayerName.ROBOT).setCell(pos.x, pos.y, cell);
    }
}
