package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;

public interface IRobotModel {

    /**
     * @return the nongraphical position of the robot
     */
    GridPoint2 getPosition();

    /**
     * sets the nongraphical position of the robot
     *
     * @param pos x and y tuppel
     */
    void setPosition(GridPoint2 pos);

    /**
     * @return the name of the robot
     */
    String getName();

    /**
     * @param x position
     * @param y position
     *          sets the x,y position of the checkpoint/rebootplace
     */
    void setCheckPoint(int x, int y);

    /**
     * @return gets the robots checkoutplace/rebootplace
     */
    GridPoint2 getCheckPoint();

    /**
     * @return how much health the robot has left
     */
    int getHealth();

    /**
     * @return how many reboots the robot has left
     */
    int getReboots();

    /**
     * Health is reduced by the amount of damage taken, if below or equal to zero then the robot is dead.
     */
    void takeDamage(int damage);

    /** Returns the shape relative to the health the robot is currently in */
    String getStatus();
}
