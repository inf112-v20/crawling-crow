package roborally.game.gameboard.objects.laser;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.gameview.layout.ILayers;
import roborally.listeners.WallListener;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;

import static roborally.utilities.SettingsUtil.TILED_TRANSLATOR;

/** This class handles the logic involved in robots
 * shooting lasers {@link #fireLaser}.
 * Mainly it handles the static lasers projected
 * by cannons on the map, each time a robot goes
 * into a laser it is updated here through
 * {@link LaserRegister}.
 * */

public class Laser {
    private final int laserTileID;
    private int cannonTileID;
    private final boolean removeLaser;
    private final ILayers layers;
    private final WallListener wallListener;

    private GridPoint2 robotsOrigin;
    private final GridPoint2 cannonPos;
    private final ArrayList<GridPoint2> laserEndPositions;

    private TiledMapTileLayer.Cell storedLaserCell;
    private TiledMapTileLayer.Cell crossLaser;
    private TiledMapTileLayer.Cell horizontalLaser;
    private TiledMapTileLayer.Cell verticalLaser;

    /**
     * Constructs a new laser.
     *
     * @param laserTileID Horizontal or Vertical laser.
     * @param layers      The layers class.
     */
    public Laser(int laserTileID, ILayers layers) {
        this.layers = layers;
        this.laserTileID = laserTileID;
        this.laserEndPositions = new ArrayList<>();
        this.removeLaser = false;
        this.cannonPos = new GridPoint2();
        this.wallListener = new WallListener(layers);
    }

    public void clearLaser() {
        clearStoredCoordinates();
    }

    /**
     * Shoots a laser until it hits a wall or a robot. Stores the cells for clearing them after.
     * {@link #setDirection(int robotDirection)} to figure out the iterative values to shoot with.
     *
     * @param pos GridPoint2 position of the robot.
     * @param robotDirection The robotDirection the robot is looking.
     */
    public void fireLaser(GridPoint2 pos, int robotDirection) {
        clearLaser();
        this.laserEndPositions.clear();
        this.storedLaserCell = getLaser(robotDirection);
        GridPoint2 direction = setDirection(robotDirection);
        GridPoint2 newPos = pos.cpy().add(direction);

        if (this.wallListener.checkForWall(pos, direction))
            return;
        while (newPos.x >= 0 && newPos.x < layers.getWidth() && newPos.y >= 0 && newPos.y < this.layers.getHeight()) {
            this.laserEndPositions.add(newPos.cpy());
            if (this.wallListener.checkForWall(newPos, direction) || this.layers.layerNotNull(LayerName.ROBOT, newPos)) {
                break;
            }
            newPos.add(direction);
        }
    }

    /**
     * Determines which robotDirection the robot is firing it's laser.
     *
     * @param robotDirection The robotDirection the robot is facing
     * @return an array with values that determines which robotDirection the laser is being fired.
     */
    public GridPoint2 setDirection(int robotDirection) {
        int dx = 0;
        int dy = 0;
        if (robotDirection == 0)
            dy = 1;
        else if (robotDirection == 1)
            dx = -1;
        else if (robotDirection == 2)
            dy = -1;
        else if (robotDirection == 3)
            dx = 1;
        return new GridPoint2(dx, dy);
    }

    /**
     * Finds the laser cannon and adds the coordinates of its projected laser.
     *
     * @param robotsOrigin The position the robot is or was currently in.
     */
    public void findLaser(GridPoint2 robotsOrigin) {
        int cannonId = 0;
        this.robotsOrigin = robotsOrigin;
        TileName laserTileName = TILED_TRANSLATOR.getTileName(laserTileID);
        if (laserTileName == TileName.LASER_HORIZONTAL) {
            storedLaserCell = getLaser(1);
            cannonId = findHorizontal();
        } else if (laserTileName == TileName.LASER_VERTICAL) {
            storedLaserCell = getLaser(2);
            cannonId = findVertical();
        }
        this.cannonTileID = cannonId;
    }

    /**
     * Finds the cannon projecting a horizontal laser, and stores the cells of the laser.
     * Calls {@link #findCannon} with values where the laser cells end in a horizontal line.
     *
     * @return cannonId
     */
    public int findHorizontal() {
        int i = robotsOrigin.x + 1;
        int j = robotsOrigin.x - 1;
        int k = robotsOrigin.y;

        while (i < layers.getWidth() && layers.layerNotNull(LayerName.LASER, new GridPoint2(i, k))) i++;
        while (j >= 0 && layers.layerNotNull(LayerName.LASER, new GridPoint2(j, k))) j--;

        cannonTileID = findCannon(i, j, k);

        if (cannonTileID != 0) {
            int dx;
            TileName cannonTileName = TILED_TRANSLATOR.getTileName(cannonTileID);
            if (cannonTileName == TileName.WALL_CANNON_RIGHT)
                dx = -1;
            else {
                dx = 1;
                i = j;
            }
            this.cannonPos.set(i + dx, k);
            do {
                laserEndPositions.add(new GridPoint2(i += dx, k));
            } while (!wallListener.checkForWall(new GridPoint2(i, k), new GridPoint2(dx, 0)) && i >= 0 && i <= layers.getWidth());
        }
        return cannonTileID;
    }

    /**
     * Finds the cannon projecting a vertical laser, and stores laser cells.
     * Calls {@link #findCannon} with values where the laser cells end in a vertical line.
     *
     * @return cannonId
     */
    public int findVertical() {
        int i = robotsOrigin.y + 1;
        int j = robotsOrigin.y - 1;
        int k = robotsOrigin.x;

        while (i < layers.getHeight() && layers.layerNotNull(LayerName.LASER, new GridPoint2(k, i))) i++;
        while (j >= 0 && layers.layerNotNull(LayerName.LASER, new GridPoint2(k, j))) j--;
        cannonTileID = findCannon(i, j, k);
        if (cannonTileID != 0) {
            int dy;
            TileName cannonTileName = TILED_TRANSLATOR.getTileName(cannonTileID);
            if (cannonTileName == TileName.WALL_CANNON_BOTTOM)
                dy = 1;
            else {
                dy = -1;
                j = i;
            }
            this.cannonPos.set(k, j + dy);
            do {
                laserEndPositions.add(new GridPoint2(k, j += dy));
            } while (!wallListener.checkForWall(new GridPoint2(k, j), new GridPoint2(0, dy)) && j >= 0 && j <= layers.getHeight());
        }
        return cannonTileID;
    }

    /**
     * Updates the cannon when there is activity, and continues to do so until it is registered in Lasers
     * that no robot is no longer active in the laser. Calls {@link #identifyLaser} to handle cross-lasers.
     */
    public void update() {
        for (GridPoint2 pos : laserEndPositions) {
            if (identifyLaser(pos.x, pos.y, false)) {
                layers.setLayerCell(LayerName.LASER, pos, null);
            }
        }
        if (removeLaser)
            return;
        for (GridPoint2 pos : laserEndPositions) {
            if (identifyLaser(pos.x, pos.y, true))
                layers.setLayerCell(LayerName.LASER, pos, this.storedLaserCell);
            if (layers.layerNotNull(LayerName.ROBOT, pos))
                break;
        }
    }

    /**
     * Calculates where the laser is coming from.
     *
     * @param i The negative sides endpoint (down or left) which the laser might be coming from.
     * @param j The positive sides endpoint (up or right) which the laser might be coming from.
     * @param k The static x or y coordinate.
     * @return cannonId
     */
    private int findCannon(int i, int j, int k) {
        TileName laserTileName = TILED_TRANSLATOR.getTileName(laserTileID);
        if (laserTileName == TileName.LASER_VERTICAL) {
            if (layers.layerNotNull(LayerName.CANNON, new GridPoint2(k, i - 1)))
                return layers.getLayerID(LayerName.CANNON, new GridPoint2(k, i - 1));
            if (layers.layerNotNull(LayerName.CANNON, new GridPoint2(k, j + 1)))
                return layers.getLayerID(LayerName.CANNON, new GridPoint2(k, j + 1));
        } else {
            if (layers.layerNotNull(LayerName.CANNON, new GridPoint2(i - 1, k)))
                return layers.getLayerID(LayerName.CANNON, new GridPoint2(i - 1, k));
            if (layers.layerNotNull(LayerName.CANNON, new GridPoint2(j + 1, k)))
                return layers.getLayerID(LayerName.CANNON, new GridPoint2(j + 1, k));
        }
        return 0;
    }

    /**
     * Checks if the given laser contains the position queried.
     * @param pos GridPoint2 of the position queried.
     * @return true if the position is in the list of positions the laser consists of.
     */
    public boolean gotPos(GridPoint2 pos) {
        return laserEndPositions.contains(pos);
    }

    public void clearStoredCoordinates() {
        laserEndPositions.clear();
    }

    public ArrayList<GridPoint2> getCoords() {
        return laserEndPositions;
    }

    /**
     * Identifies when to different laser cells intersect, creates a cross laser.
     *
     * @param i the x-position of the cell
     * @param j the y-position of the cell
     * @param create true if it is putting lasers back, false if it is removing laser cells.
     * @return false if there is a cross-laser present, or if logic determines there to be one.
     */
    public boolean identifyLaser(int i, int j, boolean create) {
        if (layers.layerNotNull(LayerName.LASER, new GridPoint2(i, j))) {
            if (layers.getLayerID(LayerName.LASER, new GridPoint2(i, j)) == crossLaser.getTile().getId() && !create) {
                int storedLaserCellID = storedLaserCell.getTile().getId();

                TileName laserTileName = TILED_TRANSLATOR.getTileName(storedLaserCellID);
                if (laserTileName == TileName.LASER_VERTICAL)
                    layers.setLayerCell(LayerName.LASER, new GridPoint2(i, j), horizontalLaser);
                else
                    layers.setLayerCell(LayerName.LASER, new GridPoint2(i, j), verticalLaser);
                return false;
            } else if (layers.getLayerID(LayerName.LASER, new GridPoint2(i, j)) != storedLaserCell.getTile().getId() && create) {
                layers.setLayerCell(LayerName.LASER, new GridPoint2(i, j), crossLaser);
                return false;
            } else return layers.getLayerID(LayerName.LASER, new GridPoint2(i, j)) == storedLaserCell.getTile().getId() || create;
        }
        return true;
    }

    public GridPoint2 getPosition() {
        return cannonPos;
    }

    /**
     * Create laser tiles if they are not created.
     *
     * @param laserDirection the direction the laser is going
     * @return the laser cell.
     */
    public TiledMapTileLayer.Cell getLaser(int laserDirection) {
        if (storedLaserCell == null) {
            horizontalLaser = new TiledMapTileLayer.Cell();
            horizontalLaser.setTile(AssetManagerUtil.getTileSets().getTile(TileName.LASER_HORIZONTAL.getTileID()));

            verticalLaser = new TiledMapTileLayer.Cell();
            verticalLaser.setTile(AssetManagerUtil.getTileSets().getTile(TileName.LASER_VERTICAL.getTileID()));

            crossLaser = new TiledMapTileLayer.Cell();
            crossLaser.setTile(AssetManagerUtil.getTileSets().getTile(TileName.LASER_CROSS.getTileID()));
        }
        if (laserDirection == 0 || laserDirection == 2)
            return verticalLaser;
        else
            return horizontalLaser;
    }
}