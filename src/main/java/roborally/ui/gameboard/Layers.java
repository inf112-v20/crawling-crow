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
    public boolean isLoaded() {
        return !layers.isEmpty();
    }
    
    public boolean contains(String key) {
        return layers.containsKey(key);
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

    public TiledMapTileLayer getStartPos() {
        return layers.get("Startpositions");
    }

    public TiledMapTileLayer getConveyorSlow() {
        return layers.get("Slowconveyorbelt");
    }

    public TiledMapTileLayer getConveyorFast() {
        return layers.get("Fastconveyorbelt");
    }

    public TiledMapTileLayer getWrench() {
        return layers.get("Wrench");
    }

    public TiledMapTileLayer getWrenchHammer() {
        return layers.get("WrenchHammer");
    }

    public TiledMapTileLayer getBug() {
        return layers.get("bug");
    }
}
