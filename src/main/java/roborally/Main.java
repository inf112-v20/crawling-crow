package roborally;

import roborally.ui.UI;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally by CrawlingCrow";
        cfg.width = 600;
        //height is 600 without starting area, when the size of the board is just 12x12
        cfg.height = 800;

        new LwjglApplication(new UI(), cfg);
    }
}