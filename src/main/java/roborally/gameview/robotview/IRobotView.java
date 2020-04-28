package roborally.gameview.robotview;

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
    void setVictoryTexture(GridPoint2 pos);

    /**
     * Creates new damageTaken/robotDestroyed texture at position.
     *
     * @param pos the position
     */
    void setDamageTakenTexture(GridPoint2 pos);

    /**
     * @return The normal robot texture.
     */
    TiledMapTileLayer.Cell getTexture();

    /**
     * @return the texture region
     */
    TextureRegion[][] getTextureRegion();

    /**
     * Gets texture region from AssetsManager and sets the starting position with this texture.
     *
     * @param robotID The id of the position of the cell texture
     */
    void setTextureRegion(int robotID);

    /**
     * Checks if it's possible to move robot.
     *
     * @param oldPos where the robot comes from
     * @param step how the robots wants to move relative to itself
     * @return True if its made its move else false if its on the edge of the map.
     */
    boolean canMoveRobot(GridPoint2 oldPos, GridPoint2 step);

    /**
     * Checks if the robot is in positions outside of the maps range
     * or coordinates less than (0,0).
     *
     * @param pos position
     * @return true or false
     */
    boolean isRobotInGraveyard(GridPoint2 pos);

    /**
     * Goes back to the last registered checkpoint/archive marker
     * If a robot is already there, virtual mode is activated
     * and you will appear the next time you move.
     *
     * @param pos position
     * @param archiveMarker last checkpoint
     */
    void goToArchiveMarker(GridPoint2 pos, GridPoint2 archiveMarker);


    /**
     * @param pos       the Position
     * @param direction The direction the robot is now facing.
     */
    void setDirection(GridPoint2 pos, Direction direction);
}
