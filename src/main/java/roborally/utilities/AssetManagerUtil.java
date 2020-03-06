package roborally.utilities;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.*;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.IRobotPresenter;
import roborally.game.objects.robot.RobotPresenter;

import java.util.*;

public class AssetManagerUtil {
    private static TiledMap loadedMap;
    private static HashMap<String,TiledMapTileLayer> layers;
    private static Stack<String> robotNames;
    public static AI[] airobots;
    public static IRobotPresenter[] robotcores;
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
        TiledMap[] tiledMaps = {manager.get(MAP_TEST), manager.get(MAP_TEST2),
                manager.get(MAP_LASER_TEST), manager.get(MAP_TEST_SINGLE_LASERS)};
        List<TiledMap> maps = Arrays.asList(tiledMaps);
        loadedMap = maps.get(map);
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
        if (loadedMap == null) {
            loadedMap = manager.get(MAP_TEST2);
        }
        return loadedMap;
    }

    public static HashMap<String,TiledMapTileLayer> getLoadedLayers() {
        if(layers==null) {
            ReadAndWriteLayers readAndWriteLayers = new ReadAndWriteLayers();
            layers = readAndWriteLayers.createLayers(getLoadedMap());
        }
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

    public static void setRobots(IRobotPresenter[] robots) {
        robotcores = robots;
    }

    public static IRobotPresenter[] getRobots() {
        return robotcores;
    }

    public static AI[] getAIRobots() {
        return airobots;
    }

    // Default Robots robots
    public static IRobotPresenter[] makeRobots() {
        robotcores = new RobotPresenter[8];
        robotcores[0] = new RobotPresenter(5, 0, 0);
        robotcores[1] = new RobotPresenter(6, 0, 1);
        robotcores[2] = new RobotPresenter(3, 1, 2);
        robotcores[3] = new RobotPresenter(8, 1, 3);
        robotcores[4] = new RobotPresenter(1, 2, 4);
        robotcores[5] = new RobotPresenter(5, 6, 5);
        robotcores[6] = new RobotPresenter(5, 4, 6);
        robotcores[7] = new RobotPresenter(5, 5, 7);

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
}
