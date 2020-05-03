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

    private static final MapAssets mapAssets = new MapAssets();
    private static final MenuAssets menuAssets = new MenuAssets();
    private static final RobotAssets robotAssets = new RobotAssets();
    private static final CardAssets cardAssets = new CardAssets();
    private static final SoundAssets soundAssets = new SoundAssets();
    private static final UIAssets uiAssets = new UIAssets();

    // LeaderBoards
    private static final AssetDescriptor<Texture> ANGRY_LB = new AssetDescriptor<>("robots/leaderboard/angry-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> BLUE_LB = new AssetDescriptor<>("robots/leaderboard/blue-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> GREEN_LB = new AssetDescriptor<>("robots/leaderboard/green-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> ORANGE_LB = new AssetDescriptor<>("robots/leaderboard/orange-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> PINK_LB = new AssetDescriptor<>("robots/leaderboard/pink-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> PURPLE_LB = new AssetDescriptor<>("robots/leaderboard/purple-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> RED_LB = new AssetDescriptor<>("robots/leaderboard/red-leadingboard.png", Texture.class);
    private static final AssetDescriptor<Texture> YELLOW_LB = new AssetDescriptor<>("robots/leaderboard/yellow-leadingboard.png", Texture.class);


    public static int numberOfRobotCopies = 0;
    public static ArrayList<Robot> robots;
    private static TiledMap loadedMap;
    private static Stack<String> robotNames;

    public static void load() {
        mapAssets.loadAssets(ASSET_MANAGER);
        robotAssets.loadAssets(ASSET_MANAGER);
        soundAssets.loadAssets(ASSET_MANAGER);
        menuAssets.loadAssets(ASSET_MANAGER);
        cardAssets.loadAssets(ASSET_MANAGER);
        uiAssets.loadAssets(ASSET_MANAGER);

        // LeaderBoards
        ASSET_MANAGER.load(ANGRY_LB);
        ASSET_MANAGER.load(BLUE_LB);
        ASSET_MANAGER.load(GREEN_LB);
        ASSET_MANAGER.load(ORANGE_LB);
        ASSET_MANAGER.load(PINK_LB);
        ASSET_MANAGER.load(PURPLE_LB);
        ASSET_MANAGER.load(RED_LB);
        ASSET_MANAGER.load(YELLOW_LB);

        ASSET_MANAGER.finishLoading();
    }

    public static void loadAssetsToMap() {
        mapAssets.putAssetsInMap(ASSET_MANAGER);
        menuAssets.putAssetsInMap(ASSET_MANAGER);
        robotAssets.putAssetsInMap(ASSET_MANAGER);
        cardAssets.putAssetsInMap(ASSET_MANAGER);
        uiAssets.putAssetsInMap(ASSET_MANAGER);
    }

    public static Texture getLeaderBoardTexture(String name) {
        HashMap<String, Texture> leaderboardTexture = new HashMap<>();
        leaderboardTexture.put("Angry", ASSET_MANAGER.get(ANGRY_LB));
        leaderboardTexture.put("Blue", ASSET_MANAGER.get(BLUE_LB));
        leaderboardTexture.put("Green", ASSET_MANAGER.get(GREEN_LB));
        leaderboardTexture.put("Orange", ASSET_MANAGER.get(ORANGE_LB));
        leaderboardTexture.put("Pink", ASSET_MANAGER.get(PINK_LB));
        leaderboardTexture.put("Purple", ASSET_MANAGER.get(PURPLE_LB));
        leaderboardTexture.put("Red", ASSET_MANAGER.get(RED_LB));
        leaderboardTexture.put("Yellow", ASSET_MANAGER.get(YELLOW_LB));
        for(String key : leaderboardTexture.keySet())
            if(name.contains(key)) {
                return leaderboardTexture.get(key);
            }
        return null;
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

    public static Texture getBackground(int backgroundID) {
        return mapAssets.getBackground(backgroundID);
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