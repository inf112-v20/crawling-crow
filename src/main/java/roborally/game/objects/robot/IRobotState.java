package roborally.game.objects.robot;


import com.badlogic.gdx.math.GridPoint2;

public interface IRobotState {

    void setPosition(GridPoint2 pos);

    GridPoint2 getPosition();

    String getName();

    int getPositionX();

    int getPositionY();

    void setCheckPoint(int x, int y);

    GridPoint2 getCheckPoint();
}
