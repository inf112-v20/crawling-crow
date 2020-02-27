package roborally.game.objects.robot;

import com.badlogic.gdx.math.Vector2;
import roborally.tools.BooleanCalculator;

public interface IRobot {
    String getName();

    BooleanCalculator getCalc();

    Vector2 getPosition();

    void setTextureRegion(int i);

    boolean move(int dx, int dy);

    boolean moveForward();

    boolean moveBackward();

    void turnRight();

    void turnLeft();

    void setPos(int x, int y);

    void setWinTexture();

    void setLostTexture();

    int getDegrees();

    boolean hasVisitedAllFlags();

    int getNextFlag();

    void visitNextFlag();
}
