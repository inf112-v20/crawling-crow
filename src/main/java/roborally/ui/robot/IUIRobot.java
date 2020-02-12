package roborally.ui.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface IUIRobot {
    Texture getTexture();

    TiledMapTileLayer.Cell getWinCell();

    TiledMapTileLayer.Cell getLostCell();

    TiledMapTileLayer.Cell getCell();
}
