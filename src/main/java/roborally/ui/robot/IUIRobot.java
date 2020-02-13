package roborally.ui.robot;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.game.objects.IRobot;

public interface IUIRobot {
    void getWinTexture(int x, int y);

    void getLostTexture(int x, int y);


    public boolean moveRobot(int x, int y);

    TiledMapTileLayer.Cell getTexture();
}
