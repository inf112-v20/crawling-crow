package roborally.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    //Potential stuff we might call the layers when creating maps, feel free to add some! =)
    public static String[][] readLayerNames() {
        String[][] layernames = new String[getnumberOfLinesInLayerNames()-1][2];
        try {
            File fr = new File("assets/maps/layernames.txt");
            BufferedReader br = new BufferedReader(new FileReader(fr));
            String[] string;
            int i = 0;
            while (!(string = br.readLine().split(" "))[0].equals("end")) {
                layernames[i][0] = string[0]; layernames[i++][1] = string[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return layernames;
    }

    public static int getnumberOfLinesInLayerNames() {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/maps/layernames.txt"));
            while(br.readLine()!=null)
                count++;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

}
