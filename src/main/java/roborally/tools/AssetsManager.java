package roborally.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.game.objects.AI;
import roborally.game.objects.RobotCore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class AssetsManager {
    private static TiledMap loadedMap;
    private static HashMap<String,TiledMapTileLayer> layers;
    private static Stack<String> robotNames;
    public static RobotCore[] robots;
    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    //Maps
    private static final AssetDescriptor<TiledMap> MAP_TEST
            = new AssetDescriptor<>("assets/maps/testMap001.tmx", TiledMap.class);
    //Robots
    private static final AssetDescriptor<Texture> uibOwl
            = new AssetDescriptor<>("assets/robots/player.png", Texture.class);
    private static final AssetDescriptor<Texture> aiOwl
            = new AssetDescriptor<>("assets/robots/AI.png", Texture.class);

    public static void load() {
        //Robots
        manager.load(uibOwl);
        manager.load(MAP_TEST);
        manager.load(aiOwl);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap () {
        loadedMap = manager.get(MAP_TEST);
        return manager.get(MAP_TEST);
    }

    // Only one robotTexture so far but can add more and return a list.
    public static Texture getRobotTexture (int i) {
        Texture[] robotTexture = new Texture[2];
        robotTexture[0] = manager.get(uibOwl);
        robotTexture[1] = manager.get(aiOwl);
        return robotTexture[i];
    }

    public static void dispose() {
        manager.clear();
    }

    public static TiledMap getLoadedMap() {
        return loadedMap;
    }

    public static HashMap<String,TiledMapTileLayer> getLoadedLayers() {
        if(layers==null)
            layers = createLayers(getLoadedMap());
        return layers;
    }

    // Default robots
    public static void makeRobots() {
        robots = new RobotCore[8];
        robots[0] = new RobotCore(3,0);
        robots[0].setTextureRegion(0);
        robots[1] = new RobotCore(0,1);
        robots[1].setTextureRegion(0);
        robots[2] = new AI(5, 2);
        robots[3] = new AI(8, 3);
        robots[4] = new AI(3,3);
    }

    public static RobotCore[] getRobots() {
        return robots;
    }

    // Default names for the robots
    public static void makeRobotNames() {
        robotNames.add("Red");
        robotNames.add("Blue");
        robotNames.add("Green");
        robotNames.add("Orange");
        robotNames.add("Pink");
        robotNames.add("Yellow");
        robotNames.add("Purple");
        robotNames.add("Teal");
    }
    public static String getRobotName() {
        if(robotNames==null) {
            robotNames = new Stack<>();
            makeRobotNames();
        }
        return robotNames.pop();
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
        for(MapLayer layer : tiledMap.getLayers()) {
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
