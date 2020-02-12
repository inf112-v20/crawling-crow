package roborally.game.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.Robot;

public interface IGameBoard {
    /**
     * Checks if Robot is on flag.
     *
     * @return the Robot on the flag/checkpoint.
     */
    public Robot onFlag();

    /**
     * Checks if Robot is on hole.
     *
     * @return the Robot on the hole.
     */
    public Robot onHole();

    /**
     * Check if Robot can move. (Might be a wall or another Robot).
     *
     * @return if the Robot can move.
     */
    public Boolean canMove(Robot robot, Vector2 pos);

    /**
     * Moves the Robot to the desired position.
     *
     * @param robot the Robot to be moved.
     * @param pos the position the robot is to be moved.
     */
    public void move(Robot robot, Vector2 pos);
}
