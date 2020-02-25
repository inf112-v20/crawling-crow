package roborally.ui.robot;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.tools.Direction;

public interface IUIRobot {
    /**
     * Gets texture region from AssetsManager and sets the starting position with this texture.
     *
     * @param cellId The id of the position of the cell texture
     */
    void setTextureRegion(int cellId);

    /**
     * Creates new WinTexture at given position.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    void getWinTexture(int x, int y);

    /**
     * Creates new damageTaken/robotDestroyed texture at position.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    void getLostTexture(int x, int y);

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
    boolean moveRobot(int x, int y);

    void setDirection(Direction direction);
}
