package roborally.utilities;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import roborally.game.cards.IProgramCards;
import roborally.game.robot.Robot;
import roborally.utilities.enums.LayerName;

import java.util.*;

public class AssetManagerUtil {
    public static final com.badlogic.gdx.assets.AssetManager ASSET_MANAGER = new com.badlogic.gdx.assets.AssetManager();
    //region Sound effects
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
    //endregion

    //region Maps TMX
    private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP2
            = new AssetDescriptor<>("maps/fruityLoops.tmx", TiledMap.class);
    private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP
            = new AssetDescriptor<>("maps/Eight.tmx", TiledMap.class);
    private static final AssetDescriptor<TiledMap> LISES_MAP
            = new AssetDescriptor<>("maps/riskyExchangeBeginnerWithStartAreaVertical.tmx", TiledMap.class);
    //endregion

    //region Menu
    private static final AssetDescriptor<Texture> MENU
            = new AssetDescriptor<>("menu/new-menu.png", Texture.class);
    private static final AssetDescriptor<Texture> BUTTONS
            = new AssetDescriptor<>("menu/buttons.png", Texture.class);
    private static final AssetDescriptor<Texture> MAP_BUTTON
            = new AssetDescriptor<>("menu/mapButton.png", Texture.class);
    //endregion

    //region Program cards
    private static final AssetDescriptor<Texture> BACKUP
            = new AssetDescriptor<>("cards/new/backup.png", Texture.class);
    private static final AssetDescriptor<Texture> ROTATE_LEFT
            = new AssetDescriptor<>("cards/new/rotate_left.png", Texture.class);
    private static final AssetDescriptor<Texture> ROTATE_RIGHT
            = new AssetDescriptor<>("cards/new/rotate_right.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_1
            = new AssetDescriptor<>("cards/new/move_1.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_2
            = new AssetDescriptor<>("cards/new/move_2.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_3
            = new AssetDescriptor<>("cards/new/move_3.png", Texture.class);
    private static final AssetDescriptor<Texture> U_TURN
            = new AssetDescriptor<>("cards/new/u_turn.png", Texture.class);

    //region Grayscale
    private static final AssetDescriptor<Texture> BACKUP_GRAY
            = new AssetDescriptor<>("cards/new/backup_gray.png", Texture.class);
    private static final AssetDescriptor<Texture> ROTATE_LEFT_GRAY
            = new AssetDescriptor<>("cards/new/rotate_left_gray.png", Texture.class);
    private static final AssetDescriptor<Texture> ROTATE_RIGHT_GRAY
            = new AssetDescriptor<>("cards/new/rotate_right_gray.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_1_GRAY
            = new AssetDescriptor<>("cards/new/move_1_gray.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_2_GRAY
            = new AssetDescriptor<>("cards/new/move_2_gray.png", Texture.class);
    private static final AssetDescriptor<Texture> MOVE_3_GRAY
            = new AssetDescriptor<>("cards/new/move_3_gray.png", Texture.class);
    private static final AssetDescriptor<Texture> U_TURN_GRAY
            = new AssetDescriptor<>("cards/new/u_turn_gray.png", Texture.class);
    //endregion
    //endregion

    //region Robots
    private static final AssetDescriptor<Texture> ANGRY
            = new AssetDescriptor<>("robots/Angry.png", Texture.class);
    private static final AssetDescriptor<Texture> BLUE
            = new AssetDescriptor<>("robots/Blue.png", Texture.class);
    private static final AssetDescriptor<Texture> GREEN
            = new AssetDescriptor<>("robots/Green.png", Texture.class);
    private static final AssetDescriptor<Texture> ORANGE
            = new AssetDescriptor<>("robots/Orange.png", Texture.class);
    private static final AssetDescriptor<Texture> PINK
            = new AssetDescriptor<>("robots/Pink.png", Texture.class);
    private static final AssetDescriptor<Texture> PURPLE
            = new AssetDescriptor<>("robots/Purple.png", Texture.class);
    private static final AssetDescriptor<Texture> RED
            = new AssetDescriptor<>("robots/Red.png", Texture.class);
    private static final AssetDescriptor<Texture> YELLOW
            = new AssetDescriptor<>("robots/Yellow.png", Texture.class);
    //endregion

    //region UI elements
    //region Buttons
    private static final AssetDescriptor<Texture> DONE_BUTTON = new AssetDescriptor<>("ui-elements/buttons/done-button.png", Texture.class);
    private static final AssetDescriptor<Texture> DONE_BUTTON_PRESSED = new AssetDescriptor<>("ui-elements/buttons/done-button-pressed.png", Texture.class);
    private static final AssetDescriptor<Texture> DONE_BUTTON_HOVER = new AssetDescriptor<>("ui-elements/buttons/done-button-hover.png", Texture.class);
    private static final AssetDescriptor<Texture> EXIT_BUTTON = new AssetDescriptor<>("ui-elements/buttons/exit-button.png", Texture.class);
    private static final AssetDescriptor<Texture> RESTART_BUTTON = new AssetDescriptor<>("ui-elements/buttons/restart-button.png", Texture.class);
    private static final AssetDescriptor<Texture> RESTART_BUTTON_PRESSED = new AssetDescriptor<>("ui-elements/buttons/restart-button-pressed.png", Texture.class);
    //endregion


    private static final AssetDescriptor<Texture> REBOOT_ACTIVE = new AssetDescriptor<>("ui-elements/reboots/reboot-active.png", Texture.class);
    private static final AssetDescriptor<Texture> REBOOT_INACTIVE = new AssetDescriptor<>("ui-elements/reboots/reboot-inactive.png", Texture.class);

    //region Damage tokens
    private static final AssetDescriptor<Texture> DAMAGE_TOKEN_GREEN = new AssetDescriptor<>("ui-elements/damagetokens/damage-token.png", Texture.class);
    private static final AssetDescriptor<Texture> DAMAGE_TOKEN_RED = new AssetDescriptor<>("ui-elements/damagetokens/damage-token-red.png", Texture.class);
    private static final AssetDescriptor<Texture> DAMAGE_TOKEN_CARD_GREEN = new AssetDescriptor<>("ui-elements/damagetokens/damage-token-card.png", Texture.class);
    private static final AssetDescriptor<Texture> DAMAGE_TOKEN_CARD_RED = new AssetDescriptor<>("ui-elements/damagetokens/damage-token-card-red.png", Texture.class);
    //endregion

    private static final AssetDescriptor<Texture> POWER_DOWN_BUTTON = new AssetDescriptor<>("ui-elements/buttons/power-down.png", Texture.class);
    private static final AssetDescriptor<Texture> POWERED_DOWN = new AssetDescriptor<>("ui-elements/buttons/powered-down.png", Texture.class);
    private static final AssetDescriptor<Texture> POWERING_DOWN = new AssetDescriptor<>("ui-elements/buttons/powering-down.png", Texture.class);

    private static final AssetDescriptor<Texture> FLAG_WHITE = new AssetDescriptor<>("ui-elements/flags/flag_white.png", Texture.class);
    //endregion

    public static float volume = 1;
    public static int numberOfRobotCopies = 0;
    public static ArrayList<Robot> robots;
    private static TiledMap loadedMap;
    private static Stack<String> robotNames;

    private static final HashMap<IProgramCards.CardType, Texture> cardTypeTextureHashMap = new HashMap<>();
    private static final HashMap<IProgramCards.CardType, Texture> cardTypeTextureGrayHashMap = new HashMap<>();

    public static void load() {
        //TODO: HashMap for loading these
        //region Maps
        ASSET_MANAGER.load(SIGMUNDS_MAP2);
        ASSET_MANAGER.load(SIGMUNDS_MAP);
        ASSET_MANAGER.load(LISES_MAP);
        //endregion

        //region UI elements
        //region Buttons
        ASSET_MANAGER.load(DONE_BUTTON);
        ASSET_MANAGER.load(DONE_BUTTON_PRESSED);
        ASSET_MANAGER.load(DONE_BUTTON_HOVER);
        ASSET_MANAGER.load(EXIT_BUTTON);
        ASSET_MANAGER.load(RESTART_BUTTON);
        ASSET_MANAGER.load(RESTART_BUTTON_PRESSED);
        //endregion
        ASSET_MANAGER.load(REBOOT_ACTIVE);
        ASSET_MANAGER.load(REBOOT_INACTIVE);
        ASSET_MANAGER.load(FLAG_WHITE);

        //region Damage tokens
        ASSET_MANAGER.load(DAMAGE_TOKEN_GREEN);
        ASSET_MANAGER.load(DAMAGE_TOKEN_RED);
        ASSET_MANAGER.load(DAMAGE_TOKEN_CARD_GREEN);
        ASSET_MANAGER.load(DAMAGE_TOKEN_CARD_RED);
        //endregion

        ASSET_MANAGER.load(POWER_DOWN_BUTTON);
        ASSET_MANAGER.load(POWERED_DOWN);
        ASSET_MANAGER.load(POWERING_DOWN);
        //endregion

        //region Robots
        ASSET_MANAGER.load(ANGRY);
        ASSET_MANAGER.load(BLUE);
        ASSET_MANAGER.load(GREEN);
        ASSET_MANAGER.load(ORANGE);
        ASSET_MANAGER.load(PINK);
        ASSET_MANAGER.load(PURPLE);
        ASSET_MANAGER.load(RED);
        ASSET_MANAGER.load(YELLOW);
        //endregion

        //region Sound effects
        ASSET_MANAGER.load(SHOOT_LASER);
        ASSET_MANAGER.load(STEPIN_LASER);
        ASSET_MANAGER.load(STEP1);
        ASSET_MANAGER.load(STEP2);
        ASSET_MANAGER.load(STEP3);
        ASSET_MANAGER.load(ROBOT_HIT);
        ASSET_MANAGER.load(SOUNDTRACK);
        //endregion

        //region Program cards
        ASSET_MANAGER.load(BACKUP);
        ASSET_MANAGER.load(ROTATE_RIGHT);
        ASSET_MANAGER.load(ROTATE_LEFT);
        ASSET_MANAGER.load(MOVE_1);
        ASSET_MANAGER.load(MOVE_2);
        ASSET_MANAGER.load(MOVE_3);
        ASSET_MANAGER.load(U_TURN);

        //region Grayscale
        ASSET_MANAGER.load(BACKUP_GRAY);
        ASSET_MANAGER.load(ROTATE_RIGHT_GRAY);
        ASSET_MANAGER.load(ROTATE_LEFT_GRAY);
        ASSET_MANAGER.load(MOVE_1_GRAY);
        ASSET_MANAGER.load(MOVE_2_GRAY);
        ASSET_MANAGER.load(MOVE_3_GRAY);
        ASSET_MANAGER.load(U_TURN_GRAY);
        //endregion
        //endregion

        //region Menu
        ASSET_MANAGER.load(MENU);
        ASSET_MANAGER.load(BUTTONS);
        ASSET_MANAGER.load(MAP_BUTTON);
        //endregion

        ASSET_MANAGER.finishLoading();

        bindCardToTexture();
        bindCardToTextureGray();
    }

    private static void bindCardToTexture() {
        cardTypeTextureHashMap.put(IProgramCards.CardType.ROTATE_RIGHT, ASSET_MANAGER.get(ROTATE_RIGHT));
        cardTypeTextureHashMap.put(IProgramCards.CardType.ROTATE_LEFT, ASSET_MANAGER.get(ROTATE_LEFT));
        cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_1, ASSET_MANAGER.get(MOVE_1));
        cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_2, ASSET_MANAGER.get(MOVE_2));
        cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_3, ASSET_MANAGER.get(MOVE_3));
        cardTypeTextureHashMap.put(IProgramCards.CardType.U_TURN, ASSET_MANAGER.get(U_TURN));
        cardTypeTextureHashMap.put(IProgramCards.CardType.BACKUP, ASSET_MANAGER.get(BACKUP));
    }

    private static void bindCardToTextureGray() {
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.ROTATE_RIGHT, ASSET_MANAGER.get(ROTATE_RIGHT_GRAY));
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.ROTATE_LEFT, ASSET_MANAGER.get(ROTATE_LEFT_GRAY));
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.MOVE_1, ASSET_MANAGER.get(MOVE_1_GRAY));
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.MOVE_2, ASSET_MANAGER.get(MOVE_2_GRAY));
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.MOVE_3, ASSET_MANAGER.get(MOVE_3_GRAY));
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.U_TURN, ASSET_MANAGER.get(U_TURN_GRAY));
        cardTypeTextureGrayHashMap.put(IProgramCards.CardType.BACKUP, ASSET_MANAGER.get(BACKUP_GRAY));
    }

    public static Texture getMenu() {
        return ASSET_MANAGER.get(MENU);
    }

    public static Texture getButtons() {
        return ASSET_MANAGER.get(BUTTONS);
    }

    public static Texture getMapButton() {
        return ASSET_MANAGER.get(MAP_BUTTON);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap(int map) {
        TiledMap[] tiledMaps = {ASSET_MANAGER.get(SIGMUNDS_MAP), ASSET_MANAGER.get(LISES_MAP), ASSET_MANAGER.get(SIGMUNDS_MAP2)};
        List<TiledMap> maps = Arrays.asList(tiledMaps);
        loadedMap = maps.get(map);
        return loadedMap;
    }

    public static Texture getCardTexture(IProgramCards.CardType card) {
        return cardTypeTextureHashMap.get(card);
    }
    public static Texture getCardTextureGray(IProgramCards.CardType card) {
        return cardTypeTextureGrayHashMap.get(card);
    }

    public static int getProgramCardWidth() {
        return ASSET_MANAGER.get(MOVE_1).getWidth();
    }

    public static int getProgramCardHeight() {
        return ASSET_MANAGER.get(MOVE_1).getHeight();
    }

    /**
     * Getter for robot texture-
     * @param i Integer defining which robot texture to get.
     * @return the robot texture at position i.
     */
    public static Texture getRobotTexture(int i) {
        Texture[] robotTexture = new Texture[8];
        robotTexture[0] = ASSET_MANAGER.get(ANGRY);
        robotTexture[1] = ASSET_MANAGER.get(BLUE);
        robotTexture[2] = ASSET_MANAGER.get(GREEN);
        robotTexture[3] = ASSET_MANAGER.get(ORANGE);
        robotTexture[4] = ASSET_MANAGER.get(PINK);
        robotTexture[5] = ASSET_MANAGER.get(PURPLE);
        robotTexture[6] = ASSET_MANAGER.get(RED);
        robotTexture[7] = ASSET_MANAGER.get(YELLOW);
        return robotTexture[i];
    }

    public static void dispose() {
        ASSET_MANAGER.clear();
    }

    /**
     * Returns the map that is loaded into the current game.
     */
    public static TiledMap getLoadedMap() {
        if (loadedMap == null) {
            loadedMap = ASSET_MANAGER.get(LISES_MAP);
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
        return ASSET_MANAGER.get(DONE_BUTTON);
    }

    public static Texture getDoneButtonPressed() {
        return ASSET_MANAGER.get(DONE_BUTTON_PRESSED);
    }

    public static Texture getDoneButtonHover() {
        return ASSET_MANAGER.get(DONE_BUTTON_HOVER);
    }

    public static Texture getRestartButton() {
        return ASSET_MANAGER.get(RESTART_BUTTON);
    }

    public static Texture getRestartButtonPressed() {
        return ASSET_MANAGER.get(RESTART_BUTTON_PRESSED);
    }

    public static Texture getExitButton() {
        return ASSET_MANAGER.get(EXIT_BUTTON);
    }

    public static Texture getRebootActive() {
        return ASSET_MANAGER.get(REBOOT_ACTIVE);
    }

    public static Texture getRebootInactive() {
        return ASSET_MANAGER.get(REBOOT_INACTIVE);
    }

    public static Texture getDamageTokenGreen() {
        return ASSET_MANAGER.get(DAMAGE_TOKEN_GREEN);
    }

    public static Texture getDamageTokenRed() {
        return ASSET_MANAGER.get(DAMAGE_TOKEN_RED);
    }

    public static Texture getDamageTokenCardGreen() {
        return ASSET_MANAGER.get(DAMAGE_TOKEN_CARD_GREEN);
    }

    public static Texture getDamageTokenCardRed() {
        return ASSET_MANAGER.get(DAMAGE_TOKEN_CARD_RED);
    }

    public static Texture getPowerDownButton() {
        return ASSET_MANAGER.get(POWER_DOWN_BUTTON);
    }

    public static Texture getPoweredDown() {
        return ASSET_MANAGER.get(POWERED_DOWN);
    }

    public static Texture getPoweringDown() {
        return ASSET_MANAGER.get(POWERING_DOWN);
    }

    public static Texture getFlagWhite() {
        return ASSET_MANAGER.get(FLAG_WHITE);
    }
}