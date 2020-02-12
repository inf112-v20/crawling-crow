package roborally.ui.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.game.objects.IRobot;

public interface IUIRobot {
    Texture getTexture();

    TiledMapTileLayer.Cell getWinCell();

    TiledMapTileLayer.Cell getLostCell();

    TiledMapTileLayer.Cell getCell();

    IRobot getRobot();

    TiledMapTileLayer getLayer();
}
