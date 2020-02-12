package roborally.ui.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.tools.AssetsManager;

import java.util.HashMap;

// Glorified HashMap, can be created with ease.
public class Layers {
    private HashMap<String,TiledMapTileLayer> layers;
    public Layers() {
        layers = new HashMap<>();
        layers = AssetsManager.getLoadedLayers();
    }
    public boolean isLoaded() {
        return !layers.isEmpty();
    }
    
    public boolean contains(String key) {
        return layers.containsKey(key);
    }

    // Robotlayers temporary.
    public TiledMapTileLayer getRobotCore() {
        return layers.get("Robot");
    }

    public TiledMapTileLayer getRobots() {
        return layers.get("Robot");
    }

    public TiledMapTileLayer getWall() { return layers.get("Wall"); }

    public TiledMapTileLayer getFloor() {
        return layers.get("Floor");
    }

    public TiledMapTileLayer getHole() {
        return layers.get("Hole");
    }

    public TiledMapTileLayer getFlag() {
        return layers.get("Flag");
    }

    public TiledMapTileLayer getBug() {
        return layers.get("bug");
    }
}
