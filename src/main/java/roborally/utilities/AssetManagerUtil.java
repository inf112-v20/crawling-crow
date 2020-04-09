package roborally.utilities;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.utilities.enums.LayerName;

import java.util.*;

public class AssetManagerUtil {
    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();
    // Sounds
    public static final AssetDescriptor<Sound> SHOOT_LASER
            = new AssetDescriptor<>("sounds/fireLaser.mp3", Sound.class);
    public static final AssetDescriptor<Sound> STEPIN_LASER
            = new AssetDescriptor<>("sounds/stepIntoLaser.wav", Sound.class);
    public static final AssetDescriptor<Sound> ROBOT_HIT
            = new AssetDescriptor<>("sounds/robotHit.mp3", Sound.class);
    public static final AssetDescriptor<Sound> STEP1
            = new AssetDescriptor<>("sounds/step1.mp3", Sound.class);
    public static final AssetDescriptor<Sound> STEP2
            = new AssetDescriptor<>("sounds/step2.mp3", Sound.class);
    public static final AssetDescriptor<Sound> STEP3
            = new AssetDescriptor<>("sounds/step3.mp3", Sound.class);
    public static final AssetDescriptor<Music> SOUNDTRACK
            = new AssetDescriptor<>("sounds/soundTrack.mp3", Music.class);
    //Maps
    private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP
            = new AssetDescriptor<>("maps/newmap.tmx", TiledMap.class);
    private static final AssetDescriptor<TiledMap> LISES_MAP
            = new AssetDescriptor<>("maps/riskyExchangeBeginnerWithStartAreaVertical.tmx", TiledMap.class);
    //Other
    private static final AssetDescriptor<Texture> MENU
            = new AssetDescriptor<>("menu/new-menu.png", Texture.class);
    private static final AssetDescriptor<Texture> BUTTONS
            = new AssetDescriptor<>("menu/buttons.png", Texture.class);
    private static final AssetDescriptor<Texture> MAP_BUTTON
            = new AssetDescriptor<>("menu/mapButton.png", Texture.class);
    private static final AssetDescriptor<Texture> BACKUP
            = new AssetDescriptor<>("cards/new/backup.png", Texture.class);
    private static final AssetDescriptor<Texture> ROTATELEFT
            = new AssetDescriptor<>("cards/new/rotatel.png", Texture.class);
    private static final AssetDescriptor<Texture> ROTATERIGHT
            = new AssetDescriptor<>("cards/new/rotater.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_1
            = new AssetDescriptor<>("cards/new/move1.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_2
            = new AssetDescriptor<>("cards/new/move2.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_3
            = new AssetDescriptor<>("cards/new/move3.png", Texture.class);
    private static final AssetDescriptor<Texture> U_TURN
            = new AssetDescriptor<>("cards/new/u-turn.png", Texture.class);
    //Robots
    private static final AssetDescriptor<Texture> ANGRY
            = new AssetDescriptor<>("robots/new/Angry.png", Texture.class);
    private static final AssetDescriptor<Texture> BLUE
            = new AssetDescriptor<>("robots/new/Blue.png", Texture.class);
    private static final AssetDescriptor<Texture> GREEN
            = new AssetDescriptor<>("robots/new/Green.png", Texture.class);
    private static final AssetDescriptor<Texture> ORANGE
            = new AssetDescriptor<>("robots/new/Orange.png", Texture.class);
    private static final AssetDescriptor<Texture> PINK
            = new AssetDescriptor<>("robots/new/Pink.png", Texture.class);
    private static final AssetDescriptor<Texture> PURPLE
            = new AssetDescriptor<>("robots/new/Purple.png", Texture.class);
    private static final AssetDescriptor<Texture> RED
            = new AssetDescriptor<>("robots/new/Red.png", Texture.class);
    private static final AssetDescriptor<Texture> YELLOW
            = new AssetDescriptor<>("robots/new/Yellow.png", Texture.class);

    private static final AssetDescriptor<Texture> DONE_BUTTON = new AssetDescriptor<>("ui-elements/done-button.png", Texture.class);
    private static final AssetDescriptor<Texture> DONE_BUTTON_PRESSED = new AssetDescriptor<>("ui-elements/done-button-pressed.png", Texture.class);

    public static float volume = 1;
    public static int numberOfRobotCopies = 0;
    public static ArrayList<Robot> robots;
    private static TiledMap loadedMap;
    private static Stack<String> robotNames;

    public static void load() {
        //Maps
        manager.load(SIGMUNDS_MAP);
        manager.load(LISES_MAP);

        manager.load(DONE_BUTTON);
        manager.load(DONE_BUTTON_PRESSED);

        //Robots
        manager.load(ANGRY);
        manager.load(BACKUP);
        manager.load(BLUE);
        manager.load(GREEN);
        manager.load(ORANGE);
        manager.load(PINK);
        manager.load(PURPLE);
        manager.load(RED);
        manager.load(YELLOW);

        //Sounds
        manager.load(SHOOT_LASER);
        manager.load(STEPIN_LASER);
        manager.load(STEP1);
        manager.load(STEP2);
        manager.load(STEP3);
        manager.load(ROBOT_HIT);
        manager.load(SOUNDTRACK);

        //Cards
        manager.load(ROTATERIGHT);
        manager.load(ROTATELEFT);
        manager.load(MOVE_1);
        manager.load(MOVE_2);
        manager.load(MOVE_3);
        manager.load(U_TURN);
        //Menu
        manager.load(MENU);
        manager.load(BUTTONS);
        manager.load(MAP_BUTTON);


    }

    public static Texture getMenu() {
        return manager.get(MENU);
    }

    public static Texture getButtons() {
        return manager.get(BUTTONS);
    }

    public static Texture getMapButton() {
        return manager.get(MAP_BUTTON);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap(int map) {
        TiledMap[] tiledMaps = {manager.get(SIGMUNDS_MAP), manager.get(LISES_MAP)};
        List<TiledMap> maps = Arrays.asList(tiledMaps);
        loadedMap = maps.get(map);
        return loadedMap;
    }

    public static Texture getCardTexture(String card) {
        HashMap<String, Texture> map = new HashMap<>();
        map.put("RotateRight", manager.get(ROTATERIGHT));
        map.put("RotateLeft", manager.get(ROTATELEFT));
        map.put("Move1", manager.get(MOVE_1));
        map.put("Move2", manager.get(MOVE_2));
        map.put("Move3", manager.get(MOVE_3));
        map.put("Uturn", manager.get(U_TURN));
        map.put("Backup", manager.get(BACKUP));
        return map.get(card);
    }

    public static int getCardWidth() {
        return manager.get(ROTATERIGHT).getWidth();
    }

    /**
     * Getter for robot texture-
     * @param i Integer defining which robot texture to get.
     * @return the robot texture at position i.
     */
    public static Texture getRobotTexture(int i) {
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

    /**
     * Returns the map that is loaded into the current game.
     */
    public static TiledMap getLoadedMap() {
        if (loadedMap == null) {
            loadedMap = manager.get(LISES_MAP);
        }
        return loadedMap;
    }

    public static HashMap<LayerName, TiledMapTileLayer> getLoadedLayers() {
        ReadAndWriteLayers readAndWriteLayers = new ReadAndWriteLayers();

        return readAndWriteLayers.createLayers(getLoadedMap());
    }

    public static TiledMapTileSets getTileSets() {
        return loadedMap.getTileSets();
    }

    public static ArrayList<Robot> getRobots() {
        return robots;
    }

    /**
     * Sets the robots array to be something different for other game modes.
     *
     * @param robots ArrayList of the robots that the current state of the game consists of.
     */
    public static void setRobots(ArrayList<Robot> robots) {
        AssetManagerUtil.robots = robots;
    }

    // Default names for the robots
    public static void makeRobotNames() {
        robotNames = new Stack<>();
        if (numberOfRobotCopies != 0) {
            robotNames.add("Yellow no. " + numberOfRobotCopies);
            robotNames.add("Red no. " + numberOfRobotCopies);
            robotNames.add("Purple no. " + numberOfRobotCopies);
            robotNames.add("Pink no. " + numberOfRobotCopies);
            robotNames.add("Orange no. " + numberOfRobotCopies);
            robotNames.add("Green no. " + numberOfRobotCopies);
            robotNames.add("Blue no. " + numberOfRobotCopies);
            robotNames.add("Angry no. " + numberOfRobotCopies);
        } else {
            robotNames.add("Yellow");
            robotNames.add("Red");
            robotNames.add("Purple");
            robotNames.add("Pink");
            robotNames.add("Orange");
            robotNames.add("Green");
            robotNames.add("Blue");
            robotNames.add("Angry");
        }
    }

    public static String getRobotName() {
        if (robotNames == null || robotNames.isEmpty()) {
            robotNames = new Stack<>();
            makeRobotNames();
            numberOfRobotCopies++;
        }
        return robotNames.pop();
    }

    public static Texture getDoneButton() {
        return manager.get(DONE_BUTTON);
    }

    public static Texture getDoneButtonPressed() {
        return manager.get(DONE_BUTTON_PRESSED);
    }
}