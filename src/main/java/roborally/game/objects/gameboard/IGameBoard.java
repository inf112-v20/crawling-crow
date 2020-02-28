package roborally.game.objects.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.IFlag;
import roborally.game.objects.robot.AI;

import java.util.ArrayList;

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
     * TODO: Not sure what this is for, anymore... @buzzdead?
     * @param pos
     * @param ai
     */
    void setCheckpoint(Vector2 pos, AI ai);

    /**
     * @return All the flags on the game board
     */
    ArrayList<IFlag> findAllFlags();

    /**
     * Check if Robot can move. (Might be a wall or another Robot).
     * TODO: Is this one neccesary anymore?
     *
     * @return if the Robot can move.
     */
    boolean canMove();
}
