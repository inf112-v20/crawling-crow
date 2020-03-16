package roborally;

import com.badlogic.gdx.Files;
import roborally.utilities.SettingsUtil;
import roborally.ui.gdx.UI;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally by CrawlingCrow";
        cfg.width = SettingsUtil.WINDOW_WIDTH;
        cfg.height = SettingsUtil.WINDOW_HEIGHT;
        //cfg.addIcon("icons/icon@4x.png", Files.FileType.Internal);
        //cfg.addIcon("icons/icon@2x.png", Files.FileType.Internal);
        //cfg.addIcon("icons/icon.png", Files.FileType.Internal);
        new LwjglApplication(new UI(), cfg);
    }
}