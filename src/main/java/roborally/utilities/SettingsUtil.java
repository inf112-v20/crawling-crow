package roborally.utilities;

import com.badlogic.gdx.math.GridPoint2;

public class SettingsUtil {
    //region Game Settings
    public final static int NUMBER_OF_PHASES = 5;
    public static float VOLUME = 1;

    public final static int ROBOT_MAX_HEALTH = 10;
    public final static int ROBOT_MAX_REBOOTS = 4;
    public final static int MAX_DAMAGE = 10;
    public final static int REGISTER_SIZE = 5;

    public final static int TIMER_DURATION = 30;
    public final static boolean DEBUG_MODE = true;
    //endregion

    public final static GridPoint2 GRAVEYARD = new GridPoint2(-1,-1);

    public final static int WINDOW_WIDTH = 1920;
    public final static int WINDOW_HEIGHT = 1080;
    public final static float UNIT_SCALE = 3 / 16f;
    public final static int TILE_SIZE = 300;
    public final static float TILE_SCALE = UNIT_SCALE * TILE_SIZE;
    public static float MAP_WIDTH;
    public static float MAP_HEIGHT;
}
