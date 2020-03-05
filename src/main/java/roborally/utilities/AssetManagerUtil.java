package roborally.utilities;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobot;
import roborally.game.objects.robot.Robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class AssetManagerUtil {
    private static TiledMap loadedMap;
    private static HashMap<String,TiledMapTileLayer> layers;
    private static Stack<String> robotNames;
    public static Stack<Vector2> flagPositions;
    public static AI[] airobots;
    public static IRobot[] robotcores;
    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    // Sounds
    public static final AssetDescriptor<Sound> SHOOT_LASER
            = new AssetDescriptor<>("assets/sounds/fireLaser.wav", Sound.class);
    public static final AssetDescriptor<Sound> STEPIN_LASER
            = new AssetDescriptor<>("assets/sounds/stepIntoLaser.wav", Sound.class);

    //Maps
    private static final AssetDescriptor<TiledMap> MAP_LASER_TEST
            = new AssetDescriptor<>("assets/maps/testMap001.tmx", TiledMap.class);
    private static final AssetDescriptor<TiledMap> MAP_TEST
            = new AssetDescriptor<>("assets/maps/riskyExchangeBeginnerWithStartArea.tmx", TiledMap.class);
    private static final AssetDescriptor<TiledMap> MAP_TEST_SINGLE_LASERS
            = new AssetDescriptor<>("assets/maps/testMapSingleLasers.tmx", TiledMap.class);

    //Maps
    private static final AssetDescriptor<TiledMap> MAP_TEST2
            = new AssetDescriptor<>("assets/maps/riskyExchangeBeginnerWithStartAreaVertical.tmx", TiledMap.class);
    //Robots
    private static final AssetDescriptor<Texture> ANGRY
            = new AssetDescriptor<>("assets/robots/new/Angry.png", Texture.class);
    private static final AssetDescriptor<Texture> BLUE
            = new AssetDescriptor<>("assets/robots/new/Blue.png", Texture.class);
    private static final AssetDescriptor<Texture> GREEN
            = new AssetDescriptor<>("assets/robots/new/Green.png", Texture.class);
    private static final AssetDescriptor<Texture> ORANGE
            = new AssetDescriptor<>("assets/robots/new/Orange.png", Texture.class);
    private static final AssetDescriptor<Texture> PINK
            = new AssetDescriptor<>("assets/robots/new/Pink.png", Texture.class);
    private static final AssetDescriptor<Texture> PURPLE
            = new AssetDescriptor<>("assets/robots/new/Purple.png", Texture.class);
    private static final AssetDescriptor<Texture> RED
            = new AssetDescriptor<>("assets/robots/new/Red.png", Texture.class);
    private static final AssetDescriptor<Texture> YELLOW
            = new AssetDescriptor<>("assets/robots/new/Yellow.png", Texture.class);

    public static void load() {
        //Robots
        manager.load(MAP_TEST_SINGLE_LASERS);
        manager.load(MAP_TEST);
        manager.load(MAP_TEST2);
        manager.load(MAP_LASER_TEST);
        manager.load(ANGRY);
        manager.load(BLUE);
        manager.load(GREEN);
        manager.load(ORANGE);
        manager.load(PINK);
        manager.load(PURPLE);
        manager.load(RED);
        manager.load(YELLOW);
        manager.load(SHOOT_LASER);
        manager.load(STEPIN_LASER);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap (int map) {
        if(map == 1)
            loadedMap = manager.get(MAP_TEST);
        if(map == 2)
            loadedMap = manager.get(MAP_TEST2);
        if(map == 3)
            loadedMap = manager.get(MAP_LASER_TEST);
        if(map == 4)
            loadedMap = manager.get(MAP_TEST_SINGLE_LASERS);

        return loadedMap;
    }

    // Only one robotTexture so far but can add more and return a list.
    public static Texture getRobotTexture (int i) {
        Texture[] robotTexture = new Texture[8];
        robotTexture[0] = manager.get(ANGRY);
        robotTexture[1] = manager.get(BLUE);
        robotTexture[2] = manager.get(GREEN);
        robotTexture[3] = manager.get(ORANGE);
        robotTexture[4] = manager.get(PINK);
        robotTexture[5] = manager.get(PURPLE);
        robotTexture[6] = manager.get(RED);
        robotTexture[7] = manager.get(YELLOW);
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

    public static TiledMapTileSets getTileSets() {
        return loadedMap.getTileSets();
    }

    // Default AI robots
    public static void makeAIRobots() {
        airobots = new AI[8];
        airobots[0] = new AI(3,0, 0);
        airobots[1] = new AI(0,1, 1);
        airobots[2] = new AI(3,2, 2);
        airobots[3] = new AI(8,3, 3);
        airobots[4] = new AI(3,3, 4);
        airobots[5] = new AI(4,4, 5);
        airobots[6] = new AI(4,5, 6);
        airobots[7] = new AI(6,4, 7);
    }

    public static void setRobots(IRobot[] robots) {
        robotcores = robots;
    }
    public static IRobot[] getRobots() {
        return robotcores;
    }
    public static AI[] getAIRobots() {
        return airobots;
    }

    // Default Robots robots
    public static IRobot[] makeRobots() {
        robotcores = new Robot[8];
        robotcores[0] = new Robot(5, 0, 0);
        robotcores[1] = new Robot(6, 0, 1);
        robotcores[2] = new Robot(3, 1, 2);
        robotcores[3] = new Robot(8, 1, 3);
        robotcores[4] = new Robot(1, 2, 4);
        robotcores[5] = new Robot(5, 6, 5);
        robotcores[6] = new Robot(5, 4, 6);
        robotcores[7] = new Robot(5, 5, 7);

        // Texture region is not automatically set in constructor (for now).
        return robotcores;
    }


    // Default names for the robots
    public static void makeRobotNames() {
        robotNames = new Stack<>();
        robotNames.add("Yellow");
        robotNames.add("Red");
        robotNames.add("Purple");
        robotNames.add("Pink");
        robotNames.add("Orange");
        robotNames.add("Green");
        robotNames.add("Blue");
        robotNames.add("Angry");
    }

    public static String getRobotName() {
        if(robotNames==null || robotNames.isEmpty()) {
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
