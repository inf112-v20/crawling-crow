package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;

public interface IRobot {

    void setPosition(float x, float y);

    Vector2 getPosition();

    int getPositionX();

    int getPositionY();
}
