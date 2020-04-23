package roborally.utilities;

import com.badlogic.gdx.math.GridPoint2;

public class SettingsUtil {
    //region Game Settings
    public static int NUMBER_OF_PHASES = 5;
    public static int NUMBER_OF_ROBOTS = 8;
    public static int NUMBER_OF_FLAGS = 3;

    public static int ROBOT_MAX_HEALTH = 10;
    public static int ROBOT_MAX_REBOOTS = 4;
    public static int MAX_DAMAGE = 10;
    public static int REGISTER_SIZE = 5;

    public static int TIMER_DURATION = 30;
    //endregion

    public static GridPoint2 GRAVEYARD = new GridPoint2(-1,-1);

    /*private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static int WINDOW_WIDTH = gd.getDisplayMode().getWidth();
    public static int WINDOW_HEIGHT = gd.getDisplayMode().getHeight();*/
    public static int WINDOW_WIDTH = 1920;
    public static int WINDOW_HEIGHT = 1080;
    public static float UNIT_SCALE = 3 / 16f;
    public static float TILE_SCALE = UNIT_SCALE * 300;
    public static float MAP_WIDTH = TILE_SCALE * 16; // needs to be generic if we want maps with different sizes.
    public static float MAP_HEIGHT = TILE_SCALE * 12; // -||-
    public static float X_SHIFT = (WINDOW_WIDTH - MAP_WIDTH) / 2;
    public static float Y_SHIFT = (WINDOW_HEIGHT - MAP_HEIGHT) / 2;

}
