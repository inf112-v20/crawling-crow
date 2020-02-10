package roborally.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface IRobot {
    TiledMapTileLayer.Cell getWinCell();
    TiledMapTileLayer.Cell getLostCell();
    TiledMapTileLayer.Cell getCell();
    void setPosition(float x, float y);
    int getPositionX();
    int getPositionY();
    Texture getTexture();

    boolean moveRobot(int dx, int dy);
}
