package roborally.game.objects.laser;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import roborally.tools.AssetManagerUtil;
import roborally.tools.BooleanCalculator;
import roborally.ui.ILayers;
import roborally.ui.Layers;

import java.util.ArrayList;
import java.util.HashMap;
public class Laser {
    private HashMap<Integer, String> laserType;
    private int laserDegree;
    private ILayers layers;
    private GridPoint2 robotsOrigin;
    private ArrayList<GridPoint2> storedCoordsCoords;
    private TiledMapTileLayer.Cell storedLaserCell;
    private TiledMapTileLayer.Cell crossLaser;
    private TiledMapTileLayer.Cell horizontalLaser;
    private TiledMapTileLayer.Cell verticalLaser;
    private BooleanCalculator booleanCalculator;
    private int cannonId;
    private boolean removeLaser;

    /**
     * Constructs a laser.
     *
     * @param laserDegree Horizontal or Vertical laser.
     */
    public Laser(int laserDegree) {
        layers = new Layers();
        laserType = new HashMap<>();
        laserType.put(39, "HORIZONTAL");
        laserType.put(47, "VERTICAL");
        laserType.put(40, "BOTH");
        laserType.put(46, "LeftCannon");
        this.laserDegree = laserDegree;
        storedCoordsCoords = new ArrayList<>(); // Stores coordinates of laser-cells that are active.
        this.booleanCalculator = new BooleanCalculator();
        removeLaser = false;
    }

    public void clearLaser() {
        removeLaser();
        update();
        clearStoredCoordinates();
    }

    /**
     * Shoots a laser until it hits a wall or a robot. Stores the cells for clearing them after.
     *
     * @param robotsPos The position of the robot that is shooting the laser.
     * @param direction The direction the robot is looking.
     */
    public void fireLaser(GridPoint2 robotsPos, int direction) {
        if (!getCoords().isEmpty()) {
            clearLaser();
            return;
        }
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.5);
        storedCoordsCoords.clear();
        storedLaserCell = createLaser(direction);
        int[] dir = setDirection(direction);
        int i = robotsPos.x + dir[0];
        int j = robotsPos.y + dir[1];

        // Makes sure there's not a wall blocking the laser.
        if (booleanCalculator.checkForWall(robotsPos.x, robotsPos.y, dir[0], dir[1]))
            return;
        while (i >= 0 && i < layers.getWidth() && j >= 0 && j < layers.getHeight()) {
            // Makes sure it doesnt stack laser on top of other laser cells.
            if (!layers.assertLaserNotNull(i, j) || layers.assertRobotNotNull(i, j)) {
                layers.setLaserCell(i, j, storedLaserCell);
                storedCoordsCoords.add(new GridPoint2(i, j));
                if (booleanCalculator.checkForWall(i, j, dir[0], dir[1]) || layers.assertRobotNotNull(i, j)) {
                    break;
                }
            }
            //Creates a crossing laser-cell as a combination of a vertical and horizontal laser.
            else if (storedLaserCell.getTile().getId() != layers.getLaserID(i, j)) {
                layers.setLaserCell(i, j, crossLaser);
                storedCoordsCoords.add(new GridPoint2(i, j));
            }
            i = i + dir[0];
            j = j + dir[1];
        }
    }

    /**
     * @param direction The direction the robot is facing
     * @return an array with values that determines which direction the laser is being fired.
     */
    public int[] setDirection(int direction) {
        int dx = 0;
        int dy = 0;
        if (direction == 0)
            dy = 1;
        else if (direction == 1)
            dx = -1;
        else if (direction == 2)
            dy = -1;
        else if (direction == 3)
            dx = 1;
        return new int[]{dx, dy};
    }

    /**
     * Finds the laser cannon and adds the laser cells opposite and direct of the robot.
     *
     * @param robotsOrigin The position the robot is or was currently in.
     */
    public int findLaser(GridPoint2 robotsOrigin) {
        int cannonId = 0;
        this.robotsOrigin = robotsOrigin;
        storedLaserCell = createLaser(1);
        if (laserType.get(laserDegree).equals("HORIZONTAL")) {
            cannonId = findHorizontal();
        } else if (laserType.get(laserDegree).equals("VERTICAL"))
            cannonId = findVertical();
        storedCoordsCoords.add(robotsOrigin);
        this.cannonId = cannonId;
        return cannonId;
    }

    /**
     * Finds the cannon projecting a horizontal laser, and stores laser cells to the left and right of the robot.
     */
    public int findHorizontal() {
        int i = robotsOrigin.x + 1;
        int j = robotsOrigin.x - 1;
        int k = robotsOrigin.y;
        while (i < layers.getWidth() && layers.assertLaserNotNull(i, k)) i++;
        while (j >= 0 && layers.assertLaserNotNull(j, k)) j--;
        cannonId = findCannon(i, j, k);
        if (cannonId != 0) {
            do {
                storedCoordsCoords.add(new GridPoint2(--i, k));
            } while (!booleanCalculator.checkForWall(i, k, -1, 0));
        }
        return cannonId;
    }

    /**
     * Finds the cannon projecting a vertical laser, and stores laser cells to the left and right of the robot.
     */
    public int findVertical() {
        int i = robotsOrigin.y + 1;
        int j = robotsOrigin.y - 1;
        int k = robotsOrigin.x;
        while (i < layers.getHeight() && layers.assertLaserNotNull(k, i)) i++;
        while (j >= 0 && layers.assertLaserNotNull(k, j)) j--;
        return findCannon(i, j, k);
    }

    public void update() {
        for (GridPoint2 pos : storedCoordsCoords) {
            if(!identifyLaser(pos.x, pos.y))
                layers.setLaserCell(pos.x, pos.y, null);
        }
        if (removeLaser)
            return;
        for (GridPoint2 pos : storedCoordsCoords) {
            layers.setLaserCell(pos.x, pos.y, this.storedLaserCell);
            if (layers.assertRobotNotNull(pos.x, pos.y))
                break;

        }
    }

    /**
     * Calculates where the laser is coming from, and removes laser cells accordingly.
     *
     * @param i The negative sides endpoint (down or left) which the laser might be coming from.
     * @param j The positive sides endpoint (up or right) which the laser might be coming from.
     * @param k The static x or y coordinate.
     */

    private int findCannon(int i, int j, int k) {
        if (laserType.get(this.laserDegree).equals("VERTICAL")) {
            if (layers.assertLaserCannonNotNull(k, i - 1))
                return layers.getLaserCannonID(k, i - 1);
            if (layers.assertLaserCannonNotNull(k, j + 1))
                return layers.getLaserCannonID(k, j + 1);
        } else {
            if (layers.assertLaserCannonNotNull(i - 1, k))
                return layers.getLaserCannonID(i - 1, k);
            if (layers.assertLaserCannonNotNull(j + 1, k))
                return layers.getLaserCannonID(j + 1, k);
        }
        return 0;
    }

    // Checks if the robot is in a laser instance.
    public boolean gotPos(GridPoint2 pos) {
        return storedCoordsCoords.contains(pos);
    }

    // Clears all stored coordinates.
    public void clearStoredCoordinates() {
        storedCoordsCoords.clear();
    }

    public void removeLaser() {
        this.removeLaser = true;
    }

    public ArrayList<GridPoint2> getCoords() {
        return this.storedCoordsCoords;
    }

    public boolean identifyLaser(int i, int j) {
        if (layers.assertLaserNotNull(i, j)) {
            if (layers.getLaserID(i, j) == 40) {
                if (laserType.get(storedLaserCell.getTile().getId()).equals("VERTICAL"))
                    layers.setLaserCell(i, j, horizontalLaser);
                else
                    layers.setLaserCell(i, j, verticalLaser);
                return true;
            }
        }
        return false;
    }
    // Creates a laser for a robot to shoot.
    public TiledMapTileLayer.Cell createLaser(int direction) {

        horizontalLaser = new TiledMapTileLayer.Cell();
        horizontalLaser.setTile(AssetManagerUtil.getTileSets().getTile(39));

        verticalLaser = new TiledMapTileLayer.Cell();
        verticalLaser.setTile(AssetManagerUtil.getTileSets().getTile(47));

        crossLaser = new TiledMapTileLayer.Cell();
        crossLaser.setTile(AssetManagerUtil.getTileSets().getTile(40));
        if (direction == 0 || direction == 2)
            return verticalLaser;
        else
            return horizontalLaser;
    }
}