package roborally.game.objects.robot;

import com.badlogic.gdx.math.Vector2;

public interface IRobot {

    void setPosition(float x, float y);

    Vector2 getPosition();

    String getName();

    int getPositionX();

    int getPositionY();

    boolean hasVisitedAllFlags();
}
