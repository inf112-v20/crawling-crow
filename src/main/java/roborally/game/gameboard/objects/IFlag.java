package roborally.game.gameboard.objects;

import com.badlogic.gdx.math.GridPoint2;

public interface IFlag {

    /**
     * @return the x and y position of a flag
     */
    GridPoint2 getPosition();

    /**
     * @return the id from tilemap of a flag
     */
    int getID();
}
