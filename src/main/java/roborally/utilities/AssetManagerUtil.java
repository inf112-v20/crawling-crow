package roborally.utilities;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import roborally.game.robot.Robot;
import roborally.utilities.assets.*;
import roborally.utilities.enums.LayerName;

import java.util.*;

public class AssetManagerUtil {

    public static final com.badlogic.gdx.assets.AssetManager ASSET_MANAGER = new com.badlogic.gdx.assets.AssetManager();

    //region Background
    private static final AssetDescriptor<Texture> SIGMUND_1_BACKGROUND
            = new AssetDescriptor<>("ui-elements/sigmund_background.png", Texture.class);
    private static final AssetDescriptor<Texture> SIGMUND_2_BACKGROUND
            = new AssetDescriptor<>("ui-elements/sigmund_background2.png", Texture.class);
    private static final AssetDescriptor<Texture> LISE_BACKGROUND
            = new AssetDescriptor<>("ui-elements/lise_background.png", Texture.class);
    //endregion

    private static final MapAssets mapAssets = new MapAssets();
    private static final MenuAssets menuAssets = new MenuAssets();
    private static final RobotAssets robotAssets = new RobotAssets();
    private static final CardAssets cardAssets = new CardAssets();
    private static final SoundAssets soundAssets = new SoundAssets();
    private static final UIAssets uiAssets = new UIAssets();

    public static float volume = 1;
    public static int numberOfRobotCopies = 0;
    public static ArrayList<Robot> robots;
    private static TiledMap loadedMap;
    private static Stack<String> robotNames;

    private static List<Texture> backgroundList = new LinkedList<>();

    public static void load() {
        mapAssets.loadAssets(ASSET_MANAGER);
        robotAssets.loadAssets(ASSET_MANAGER);
        soundAssets.loadAssets(ASSET_MANAGER);
        menuAssets.loadAssets(ASSET_MANAGER);
        cardAssets.loadAssets(ASSET_MANAGER);
        uiAssets.loadAssets(ASSET_MANAGER);

        ASSET_MANAGER.finishLoading();
    }

    public static void loadAssetsToMap() {
        mapAssets.putAssetsInMap(ASSET_MANAGER);
        menuAssets.putAssetsInMap(ASSET_MANAGER);
        robotAssets.putAssetsInMap(ASSET_MANAGER);
        cardAssets.putAssetsInMap(ASSET_MANAGER);
        uiAssets.putAssetsInMap(ASSET_MANAGER);
    }

    private static void setBackgroundList() {
        backgroundList = Arrays.asList(
                ASSET_MANAGER.get(SIGMUND_1_BACKGROUND),
                ASSET_MANAGER.get(LISE_BACKGROUND),
                ASSET_MANAGER.get(SIGMUND_2_BACKGROUND)
        );
    }

    public static void dispose() {
        ASSET_MANAGER.clear();
    }

    /**
     * Returns the map that is loaded into the current game.
     */
    public static TiledMap getLoadedMap() {
        if (loadedMap == null) {
            loadedMap = mapAssets.getMap(0);
        }
        return loadedMap;
    }

    public static TiledMap getMap(int mapID) {
        loadedMap = mapAssets.getMap(mapID);
        return loadedMap;
    }

    public static HashMap<LayerName, TiledMapTileLayer> getLoadedLayers() {
        ReadAndWriteLayers readAndWriteLayers = new ReadAndWriteLayers();

        return readAndWriteLayers.createLayers(getLoadedMap());
    }

    public static TiledMapTileSets getTileSets() {
        return loadedMap.getTileSets();
    }

    public static MenuAssets getMenu() {
        return menuAssets;
    }

    public static CardAssets getCards() { return cardAssets; }

    public static UIAssets getUIElements() { return uiAssets; }

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

    /**
     * Getter for robot texture.
     * @param i Integer defining which robot texture to get.
     * @return the robot texture at position i.
     */
    public static Texture getRobotTexture(int i) {
        return robotAssets.getRobot(i);
    }

    public static String getRobotName() {
        if (robotNames == null || robotNames.isEmpty()) {
            robotNames = new Stack<>();
            makeRobotNames();
            numberOfRobotCopies++;
        }
        return robotNames.pop();
    }
}