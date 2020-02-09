package roborally.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AssMan {
    public static final AssetManager manager = new AssetManager();

    //Maps
    private static final AssetDescriptor<TiledMap> MAP_TEST
            = new AssetDescriptor<>("assets/maps/testMap001.tmx", TiledMap.class);
    //Robots
    private static final AssetDescriptor<Texture> uibOwl
            = new AssetDescriptor<>("assets/robots/player.png", Texture.class);

    public static void load() {
        //Robots
        manager.load(uibOwl);
        manager.load(MAP_TEST);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap () {
        return manager.get(MAP_TEST);
    }

    // Only one robotTexture so far but can add more and return a list.
    public static Texture getRobotTexture () {
        return manager.get(uibOwl);
    }

    public static void dispose() {
        manager.clear();
    }

    //Potential stuff we might call the layers when creating maps, feel free to add some! =)
    public static String[][] readLayerNames() {
        String[][] layernames = new String[getnumberOfLinesInLayerNames()-1][2];
        String[] string;
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/maps/layernames.txt"));
            while (!(string = br.readLine().split(" "))[0].equals("end")) {
                layernames[i][0] = string[0]; layernames[i++][1] = string[1];
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return layernames;
    }

    //Gets the total number of lines in our layernames file.
    public static int getnumberOfLinesInLayerNames() {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/maps/layernames.txt"));
            while(br.readLine()!=null)
                count++;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    //Puts all the layers for the current map into a Map, accessible by standard names defined by us.
    public static HashMap<String,TiledMapTileLayer> createLayers(TiledMap tiledMap) {
        HashMap<String, TiledMapTileLayer> map = new HashMap<>();
        String[][] layernames = AssMan.readLayerNames();

        for(MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer key = (TiledMapTileLayer) layer;
            boolean layerImpl = false;
            String s = key.getName().toLowerCase();
            for (int i = 0; i < layernames.length; i++) {
                if (s.contains(layernames[i][0])) {
                    layerImpl = true;
                    map.put(layernames[i][1], key);
                    break;
                }
            }
            if (!layerImpl) {
                System.out.println("The layer: '" + s + "' has not yet been implemented in the game. \n" +
                        "check the layer in Tiled Map Editor and the list of names in map/layernames.txt\n" +
                        "this layer will act as a hole.");
                map.put("bug", key);
            }
        } return map;
    }

}
