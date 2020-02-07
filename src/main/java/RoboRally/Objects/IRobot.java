package RoboRally.Objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public interface IRobot {
    TiledMapTileLayer.Cell getWonCell();
    TiledMapTileLayer.Cell getLostCell();
    TiledMapTileLayer.Cell getCell();
    void setPosition(Vector2 pos);
    Vector2 getPosition();
    TiledMapTileLayer getLayer();
}
