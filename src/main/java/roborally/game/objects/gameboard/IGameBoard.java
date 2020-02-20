package roborally.game.objects.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.robot.AI;

public interface IGameBoard {
    /**
     * Checks if Robot is on flag.
     *
     * @return the Robot on the flag/checkpoint.
     */
    boolean onFlag(int x, int y);

    /**
     * Checks if Robot is on hole.
     *
     * @return the Robot on the hole.
     */
    boolean onHole(int x, int y);

    /**
     * Check if Robot can move. (Might be a wall or another Robot).
     *
     * @return if the Robot can move.
     */
    boolean canMove();

    /**
     * Check if Robot can is onHole or onFlag, and other stuff in the future.
     *
     */
    void getCheckPoint(Vector2 pos, AI ai);

}
