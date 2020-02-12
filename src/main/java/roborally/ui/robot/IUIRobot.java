package roborally.ui.robot;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.game.objects.IRobot;

public interface IUIRobot extends IRobot {
    TiledMapTileLayer.Cell getWinTexture();

    TiledMapTileLayer.Cell getLostTexture();

    TiledMapTileLayer.Cell getTexture();

    IRobot getRobot();
}
