package roborally.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AssetsManager {
    public static TiledMap loadedMap;
    public static HashMap<String, TiledMapTileLayer> layers;
    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    // Maps
    private static final AssetDescriptor<TiledMap> MAP_TEST
            = new AssetDescriptor<>("assets/maps/testMap001.tmx", TiledMap.class);
    // Robots
    private static final AssetDescriptor<Texture> uibOwl
            = new AssetDescriptor<>("assets/robots/player.png", Texture.class);

    public static void load() {
        // Robots
        manager.load(uibOwl);
        manager.load(MAP_TEST);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap () {
        loadedMap = manager.get(MAP_TEST);
        return manager.get(MAP_TEST);
    }

    // Only one robotTexture so far but can add more and return a list.
    public static Texture getRobotTexture () {
        return manager.get(uibOwl);
    }

    public static void dispose() {
        manager.clear();
    }

    public static TiledMap getLoadedMap() {
        return loadedMap;
    }

    public static HashMap<String,TiledMapTileLayer> getLoadedLayers() {
        if (layers==null)
            layers = createLayers(getLoadedMap());
        return layers;
    }

    // Potential stuff we might call the layers when creating maps, feel free to add some! =)
    public static String[][] readLayerNames() {
        String[][] layerNames = new String[getNumberOfLinesInLayerNames()-1][2];
        String[] string;
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/maps/layerNames.txt"));
            while (!(string = br.readLine().split(" "))[0].equals("end")) {
                layerNames[i][0] = string[0]; layerNames[i++][1] = string[1];
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return layerNames;
    }

    // Gets the total number of lines in our layerNames file.
    public static int getNumberOfLinesInLayerNames() {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/maps/layerNames.txt"));
            while(br.readLine()!=null)
                count++;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Puts all the layers for the current map into a Map, accessible by standard names defined by us.
    public static HashMap<String,TiledMapTileLayer> createLayers(TiledMap tiledMap) {
        HashMap<String, TiledMapTileLayer> map = new HashMap<>();
        String[][] layerNames = readLayerNames();
        for (MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer key = (TiledMapTileLayer) layer;
            boolean layerImpl = false;
            String s = key.getName().toLowerCase();
            for (String[] layerName : layerNames)
                if (s.contains(layerName[0])) {
                    layerImpl = true;
                    map.put(layerName[1], key);
                    break;
                }
            if (!layerImpl) {
                System.out.println("The layer: '" + s + "' has not yet been implemented in the game. \n" +
                        "check the layer in Tiled Map Editor and the list of names in map/layernames.txt\n" +
                        "this layer will act as a hole.");
                map.put("bug", key);
            }
        }
        return map;
    }

}
