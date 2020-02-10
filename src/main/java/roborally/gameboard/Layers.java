package roborally.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.tools.AssMan;

import java.util.HashMap;

// Glorified HashMap, can be created with ease.
public class Layers {
    HashMap<String,TiledMapTileLayer> layers;
    public Layers() {
        layers = new HashMap<>();
        layers = AssMan.getLoadedLayers();
    }
    public TiledMapTileLayer get(String key) {
        return layers.get(key);
    }
    
    public boolean contains(String key) {
        return layers.containsKey(key);
    }
    
    public TiledMapTileLayer getRobotCore() {
        return layers.get("Robot");
    }
}
