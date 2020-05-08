package roborally.utilities;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.utilities.enums.LayerName;

import java.util.HashMap;

public class ReadAndWriteLayers {

    public HashMap<LayerName, TiledMapTileLayer> createLayers(TiledMap tiledMap) {
        HashMap<LayerName, TiledMapTileLayer> layers = new HashMap<>();
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            boolean layerImplemented = false;
            for (LayerName layerName : LayerName.values()) {
                if (mapLayer.getName().equalsIgnoreCase(layerName.getLayerString())) {
                    layers.put(layerName, (TiledMapTileLayer) mapLayer);
                    layerImplemented = true;
                }
            }
            if (!layerImplemented) {
                System.out.println("Layer: '" + mapLayer.getName() + "' has not yet been implemented in the game.");
                System.out.println("\t- Check the layer in Tiled Map Editor and the layer names in the map's .tmx-file.");
                System.out.println("\t- This layer will act as a hole.");
            }
        }
        return layers;
    }
}
