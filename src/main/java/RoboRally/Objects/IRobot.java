package RoboRally.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public interface IRobot {
    TiledMapTileLayer.Cell getWonCell();
    TiledMapTileLayer.Cell getLostCell();
    TiledMapTileLayer.Cell getCell();
    void setPosition(Vector2 pos);
    Vector2 getPosition();
    TiledMapTileLayer getLayer();
    Texture getTexture();

    boolean moveRobot(int x, int y, int dx, int dy, TiledMapTileLayer.Cell cell);
}
