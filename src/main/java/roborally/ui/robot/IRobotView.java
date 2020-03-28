package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.enums.Direction;

public interface IRobotView {
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
     * @param oldPos where the robot comes from
     * @param step how the robots wants to move relative to itself
     * @return True if its made its move else false if its on the edge of the map.
     */
    boolean canMoveRobot(GridPoint2 oldPos, GridPoint2 step);

    /**
     * @param pos       the Position
     * @param direction The direction the robot is now facing.
     */
    void setDirection(GridPoint2 pos, Direction direction);

    void goToArchiveMarker(GridPoint2 pos, GridPoint2 archiveMarker);

    TextureRegion[][] getTextureRegion();

    /**
     * Gets texture region from AssetsManager and sets the starting position with this texture.
     *
     * @param robotID The id of the position of the cell texture
     */
    void setTextureRegion(int robotID);
}
