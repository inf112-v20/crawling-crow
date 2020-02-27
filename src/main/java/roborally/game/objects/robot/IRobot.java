package roborally.game.objects.robot;

import com.badlogic.gdx.math.Vector2;
import roborally.tools.BooleanCalculator;

public interface IRobot {
    /**
     *
     * @return the models name.
     */
    String getName();

    /**
     *
     * @return a true\false calculator.
     */
    BooleanCalculator getCalc();

    /**
     *
     * @return the position for this robotCores robot.
     */
    Vector2 getPosition();

    /**
     *
     * @param i
     * For setting different textureRegions for AI and Players
     */
    void setTextureRegion(int i);

    /**
     *
     * @param dx
     * @param dy
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
    int getDegrees();

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
}
