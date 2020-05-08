package roborally.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import roborally.game.robot.Robot;
import roborally.utilities.asset.CardAsset;
import roborally.utilities.asset.MapAsset;
import roborally.utilities.asset.MenuAsset;
import roborally.utilities.asset.RobotAsset;
import roborally.utilities.asset.SoundAsset;
import roborally.utilities.asset.UIAsset;
import roborally.utilities.asset.ManageableAsset;
import roborally.utilities.enums.LayerName;

import java.util.*;

public class AssetManagerUtil {

    public static final com.badlogic.gdx.assets.AssetManager ASSET_MANAGER
            = new com.badlogic.gdx.assets.AssetManager();

    private static final MapAsset mapAsset = new MapAsset();
    private static final MenuAsset menuAsset = new MenuAsset();
    private static final RobotAsset robotAsset = new RobotAsset();
    private static final CardAsset cardAsset = new CardAsset();
    private static final SoundAsset soundAsset = new SoundAsset();
    private static final UIAsset uiAsset = new UIAsset();
    private static final List<ManageableAsset> manageableAssets = Arrays.asList(
            mapAsset, menuAsset, robotAsset, cardAsset, soundAsset, uiAsset);

    public static ArrayList<Robot> robots;
    private static TiledMap loadedMap;
    private static Stack<String> robotNames;

    public static void load() {
        for(ManageableAsset manageableAsset : manageableAssets)
            manageableAsset.loadAssets(ASSET_MANAGER);
        ASSET_MANAGER.finishLoading();
    }

    public static void loadAssetsToMap() {
        for(ManageableAsset manageableAsset : manageableAssets)
            manageableAsset.putAssetsInMap(ASSET_MANAGER);
    }

    public static Texture getLeaderBoardTexture(String name) {
        return robotAsset.getLeaderBoardTexture(name);
    }

    public static void dispose() {
        ASSET_MANAGER.clear();
    }

    /**
     * Returns the map that is loaded into the current game.
     */
    public static TiledMap getLoadedMap() {
        if (loadedMap == null) {
            loadedMap = mapAsset.getMap(0);
        }
        return loadedMap;
    }

    public static TiledMap getMap(int mapID) {
        loadedMap = mapAsset.getMap(mapID);
        return loadedMap;
    }

    public static Texture getBackground(int backgroundID) {
        return mapAsset.getBackground(backgroundID);
    }

    public static HashMap<LayerName, TiledMapTileLayer> getLoadedLayers() {
        ReadAndWriteLayers readAndWriteLayers = new ReadAndWriteLayers();

        return readAndWriteLayers.createLayers(getLoadedMap());
    }

    public static TiledMapTileSets getTileSets() {
        return loadedMap.getTileSets();
    }

    public static MenuAsset getMenu() {
        return menuAsset;
    }

    public static CardAsset getCards() {
        return cardAsset;
    }

    public static UIAsset getUIElements() {
        return uiAsset;
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
            robotNames.add("Yellow");
            robotNames.add("Red");
            robotNames.add("Purple");
            robotNames.add("Pink");
            robotNames.add("Orange");
            robotNames.add("Green");
            robotNames.add("Blue");
            robotNames.add("Angry");
    }

    /**
     * Getter for robot texture.
     * @param i Integer defining which robot texture to get.
     * @return the robot texture at position i.
     */
    public static Texture getRobotTexture(int i) {
        return robotAsset.getRobot(i);
    }

    public static String getRobotName() {
        if (robotNames == null || robotNames.isEmpty()) {
            robotNames = new Stack<>();
            makeRobotNames();
        }
        return robotNames.pop();
    }
}