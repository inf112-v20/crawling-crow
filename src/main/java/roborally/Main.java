package roborally;

import roborally.game.SettingsUtil;
import roborally.ui.UI;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally by CrawlingCrow";
        cfg.width = SettingsUtil.WINDOW_WIDTH;
        cfg.height = SettingsUtil.WINDOW_HEIGHT;

        new LwjglApplication(new UI(), cfg);
    }
}