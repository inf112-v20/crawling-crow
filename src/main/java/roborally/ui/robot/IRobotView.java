package roborally.ui.robot;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.enums.Direction;

public interface IRobotView {
    /**
     * Gets texture region from AssetsManager and sets the starting position with this texture.
     *
     * @param cellId The id of the position of the cell texture
     */
    void setTextureRegion(int cellId);

    /**
     * Creates new WinTexture at given position.
     *
     * @param pos the position
     */
    void setWinTexture(GridPoint2 pos);

    /**
     * Creates new damageTaken/robotDestroyed texture at position.
     *
     * @param pos the position
     */
    void setLostTexture(GridPoint2 pos);

    /**
     * @return The normal robot texture.
     */
    TiledMapTileLayer.Cell getTexture();

    /**
     * Checks if it's possible to move robot.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return True if its made its move else false if its on the edge of the map.
     */
    boolean moveRobot(int x, int y, int dx, int dy);

    /**
     * @param pos       the Position
     * @param direction The direction the robot is now facing.
     */
    void setDirection(GridPoint2 pos, Direction direction);

    void goToCheckPoint(GridPoint2 pos, GridPoint2 checkPoint);
}
