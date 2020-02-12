package roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface IRobot {
    void setPosition(float x, float y);
    int getPositionX();
    int getPositionY();
}
