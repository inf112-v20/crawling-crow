package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.laser.Laser;

public interface IRobot {
    /**
     *
     * @return the models name.
     */
    String getName();

    /**
     *
     * @return the position for this robotCores robot.
     */
    GridPoint2 getPosition();

    /**
     * Shoots a laser, bound to key "F"
     */
    void fireLaser();

    /**
     * Clears the laser
     */
    void clearLaser();

    Laser getLaser();

    IRobotLogic getModel();


    /**
     * Sends the robot back to its checkpoint.
     */
    void backToCheckPoint();

    /**
     *
     * @param i
     * For setting different textureRegions for AI and Players
     */
    void setTextureRegion(int i);

    /**
     *
     * @param dx - change in x direction
     * @param dy - change in y direction
     * @return
     * Makes move inside the graphical interface for uiRobot.
     */
    boolean move(int dx, int dy);

    /**
     *
     * @return
     */
    boolean moveForward();

    /**
     *
     * @return
     */
    boolean moveBackward();

    /**
     *
     */
    void turnRight();

    /**
     *
     */
    void turnLeft();

    /**
     *
     * @param x
     * @param y
     * Sets position for this robotCores robot.
     */
    void setPos(int x, int y);

    /**
     * Updates the current cell to a WinCell
     */
    void setWinTexture();

    /**
     * Updates the current cell to a LoseCell
     */
    void setLostTexture();

    /**
     *
     * @return
     */
    int getDirectionID();

    /**
     *
     * @return
     */
    boolean hasVisitedAllFlags();

    /**
     *
     * @return
     */
    int getNextFlag();

    /**
     *
     */
    void visitNextFlag();

    /**
     *
     * @param nFlags
     */
    void setNumberOfFlags(int nFlags);
}
