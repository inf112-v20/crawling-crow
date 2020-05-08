package roborally;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import roborally.gameview.GameView;
import roborally.utilities.SettingsUtil;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally by CrawlingCrow";

        cfg.width = SettingsUtil.WINDOW_WIDTH;
        cfg.height = SettingsUtil.WINDOW_HEIGHT;
        cfg.addIcon("icons/icon@4x.png", Files.FileType.Internal);
        cfg.addIcon("icons/icon@2x.png", Files.FileType.Internal);
        cfg.addIcon("icons/icon.png", Files.FileType.Internal);
        new LwjglApplication(new GameView(), cfg);
    }
}