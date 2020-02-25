package roborally.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import roborally.game.Settings;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.game.objects.robot.Robot;
import roborally.game.objects.robot.RobotCore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class AssetsManager {
    private static TiledMap loadedMap;
    private static HashMap<String,TiledMapTileLayer> layers;
    private static Stack<String> robotNames;
    public static Stack<Vector2> flagPositions;
    public static AI[] airobots;
    public static IRobot[] robots;
    public static RobotCore[] robotcores;
    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    //Maps
    private static final AssetDescriptor<TiledMap> MAP_TEST
            = new AssetDescriptor<>("assets/maps/riskyExchangeBeginnerWithStartArea.tmx", TiledMap.class);
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
        //TODO: Add precondition to make sure map is loaded.
        return loadedMap;
    }

    public static HashMap<String,TiledMapTileLayer> getLoadedLayers() {
        if(layers==null)
            layers = createLayers(getLoadedMap());
        return layers;
    }

    // Default AI robots
    public static void makeAIRobots() {
        airobots = new AI[8];
        airobots[0] = new AI(3,0);
        airobots[1] = new AI(0,1);
        airobots[2] = new AI(3, 2);
        airobots[3] = new AI(8, 3);
        airobots[4] = new AI(3,3);
        airobots[5] = new AI(4,4);
        airobots[6] = new AI(4,5);
        airobots[7] = new AI(6,4);
    }

    public static AI[] getAIRobots() {
        return airobots;
    }

    // Default AI robots
    public static RobotCore[] makeRobotCore() {
        robotcores = new RobotCore[8];
        robotcores[0] = new RobotCore(3,0);
        robotcores[1] = new RobotCore(0,1);
        robotcores[2] = new RobotCore(3, 2);
        robotcores[3] = new RobotCore(8, 3);
        robotcores[4] = new RobotCore(3,3);
        robotcores[5] = new RobotCore(4,4);
        robotcores[6] = new RobotCore(4,5);
        robotcores[7] = new RobotCore(6,4);

        // Texture region is not automatically set in constructor (for now).
        for(RobotCore core : robotcores){
            core.setTextureRegion(1);
        }
        return robotcores;
    }

    // Default robots
    public static IRobot[] makeRobots() {
        robots = new IRobot[Settings.NUMBER_OF_ROBOTS];
        makeRobotNames();
        for(int i = 0; i < robots.length; i++){
            robots[i] = new Robot(robotNames.get(i));
        }
        return robots;
    }

    // Default names for the robots
    public static void makeRobotNames() {
        robotNames = new Stack<>();
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


    public static Stack<Vector2> makeFlagPos() {
        flagPositions = new Stack<>();
        flagPositions.push(new Vector2(9,1));
        flagPositions.push(new Vector2(2,1));
        flagPositions.push(new Vector2(2,10));
        flagPositions.push(new Vector2(9,10));
        return flagPositions;
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
