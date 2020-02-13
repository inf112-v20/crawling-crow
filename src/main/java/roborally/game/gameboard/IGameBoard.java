package roborally.game.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.RobotCore;

public interface IGameBoard {
    /**
     * Checks if Robot is on flag.
     *
     * @return the Robot on the flag/checkpoint.
     */
    public boolean onFlag(int x, int y);

    /**
     * Checks if Robot is on hole.
     *
     * @return the Robot on the hole.
     */
    public boolean onHole(int x, int y);

    /**
     * Check if Robot can move. (Might be a wall or another Robot).
     *
     * @return if the Robot can move.
     */
    public boolean canMove();

    /**
     * Check if Robot can is onHole or onFlag, and other stuff in the future.
     *
     */
    public void getCheckPoint(Vector2 pos, RobotCore robotCore);

}
