package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.enums.Direction;


public interface Programmable {

    String getName();

    GridPoint2 getPos();

    void setPos(GridPoint2 newPos);

    int[] move(int steps);

    Direction rotate(String direction, int factor);

    void backToCheckPoint();


}
