package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;

public interface IRobotLogic {

    /** Returns the nonGraphical position of the robot. */
    GridPoint2 getPosition();

    /**
     * Sets the nonGraphical position of the robot.
     *
     * @param pos x and y tuppel
     */
    void setPosition(GridPoint2 pos);

    /** Returns the name of the robot. */
    String getName();

    /** Sets the x,y position of the archive marker.
     * @param x position
     * @param y position
     */
    void setArchiveMarker(int x, int y);

    /** Returns the robots last known checkout place/reboot place. */
    GridPoint2 getArchiveMarker();

    /** Returns how much health the robot has left. */
    int getHealth();

    /** Returns the number of reboots the robot has left. */
    int getReboots();

    /**
     * Health is reduced by the amount of damage taken, if below or equal to zero then the robot is dead.
     */
    void takeDamage(int damage);

    /** Returns output of the shape relative to the health the robot is currently in. */
    String getStatus();
}
