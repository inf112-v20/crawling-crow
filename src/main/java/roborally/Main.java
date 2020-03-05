package roborally;

import com.badlogic.gdx.Files;
import roborally.game.SettingsUtil;
import roborally.ui.UI;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally by CrawlingCrow";
        cfg.width = SettingsUtil.WINDOW_WIDTH;
        cfg.height = SettingsUtil.WINDOW_HEIGHT;
        cfg.addIcon("icons/icon@4x.png", Files.FileType.Internal);
        cfg.addIcon("icons/icon@2x.png", Files.FileType.Internal);
        cfg.addIcon("icons/icon.png", Files.FileType.Internal);
        Scanner in = new Scanner(System.in);
        System.out.println("1: MAP_TEST  2: MAP_TEST2  3: MAP_LASER_TEST 4: MAP_TEST_SINGLE_LASERS");
        int mapID = in.nextInt();
        new LwjglApplication(new UI(mapID), cfg);
    }
}