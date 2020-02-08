package roborally.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class AssMan {
    public static final AssetManager manager = new AssetManager();

    //Maps
    private static final AssetDescriptor<TiledMap> MAP_TEST
            = new AssetDescriptor<>("assets/maps/testMap001.tmx", TiledMap.class);
    //Robots
    private static final AssetDescriptor<Texture> uibOwl
            = new AssetDescriptor<>("assets/robots/player.png", Texture.class);

    public static void load() {
        //Robots
        manager.load(uibOwl);
        manager.load(MAP_TEST);
    }

    // Only one map so far, but can add more and return a list.
    public static TiledMap getMap () {
        return manager.get(MAP_TEST);
    }

    // Only one robotTexture so far but can add more and return a list.
    public static Texture getRobotTexture () {
        return manager.get(uibOwl);
    }

    public static void dispose() {
        manager.clear();
    }

}
