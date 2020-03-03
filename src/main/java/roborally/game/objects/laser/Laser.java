package roborally.game.objects.laser;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.robot.IRobot;
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
    private ArrayList<GridPoint2> storedCoordsOpposite;
    private ArrayList<GridPoint2> storedCoordsDirect;
    private TiledMapTileLayer.Cell storedLaserCell;
    private TiledMapTileLayer.Cell storedLaserCellHorizontal;
    private TiledMapTileLayer.Cell storedLaserCellVertical;
    private TiledMapTileLayer.Cell storedLaserCellOnVertical;
    private BooleanCalculator booleanCalculator;

    /**
     * Constructs a laser.
     * @param laserDegree Horizontal or Vertical laser.
     */
    public Laser(int laserDegree) {
        layers = new Layers();
        laserType = new HashMap<>();
        laserType.put(39, "HORIZONTAL");
        laserType.put(47, "VERTICAL");
        laserType.put(123, "BOTH");
        this.laserDegree = laserDegree;
        storedCoordsOpposite = new ArrayList<>(); // Stores coordinates of laser-cells that are removed.
        storedCoordsDirect = new ArrayList<>(); // Stores coordinates of laser-cells that are active.
        this.booleanCalculator = new BooleanCalculator();
    }

    // Creates a laser for a robot to shoot.
    public TiledMapTileLayer.Cell createLaser(int direction) {

        storedLaserCellHorizontal = new TiledMapTileLayer.Cell();
        TextureRegion tr = new TextureRegion(AssetManagerUtil.getLaserTexture());
        storedLaserCellHorizontal.setTile(new StaticTiledMapTile(tr));
        storedLaserCellHorizontal.getTile().setId(39);

        storedLaserCellVertical = new TiledMapTileLayer.Cell();
        TextureRegion tr1 = new TextureRegion(AssetManagerUtil.getLaserVerticalTexture());
        storedLaserCellVertical.setTile(new StaticTiledMapTile(tr1));
        storedLaserCellVertical.getTile().setId(47);

        storedLaserCellOnVertical = new TiledMapTileLayer.Cell();
        TextureRegion tr2 = new TextureRegion(AssetManagerUtil.getLaserOnVerticalTexture());
        storedLaserCellOnVertical.setTile(new StaticTiledMapTile(tr2));
        storedLaserCellOnVertical.getTile().setId(123);
        if (direction == 0 || direction == 2)
            return storedLaserCellVertical;
        else
            return storedLaserCellHorizontal;
    }

    /**
     * Shoots a laser until it hits a wall or a robot. Stores the cells for clearing them after.
     * @param robotsPos The position of the robot that is shooting the laser.
     * @param direction The direction the robot is looking.
     */
    public void fireLaser(GridPoint2 robotsPos, int direction) {
        storedCoordsOpposite.clear();
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
                storedCoordsOpposite.add(new GridPoint2(i, j));
                if (booleanCalculator.checkForWall(i, j, dir[0], dir[1]) || layers.assertRobotNotNull(i, j)) {
                    break;
                }
            }
            //Creates a crossing laser-cell as a combination of a vertical and horizontal laser.
            else if(storedLaserCell.getTile().getId() != layers.getLaserID(i, j)){
                layers.setLaserCell(i, j, storedLaserCellOnVertical);
                storedCoordsOpposite.add(new GridPoint2(i, j));
            }
            i = i + dir[0];
            j = j + dir[1];
        }
    }

    /**
     *
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

    // Restores the entire laser that was removed.
    public void restoreLaser() {
        restoreLaserOpposite();
    }

    private void restoreLaserOpposite() {
        for (GridPoint2 coords : storedCoordsOpposite)
            layers.setLaserCell(coords.x, coords.y, storedLaserCell);
    }

    // Removes the opposite part of the direction the laser is going in relation to the robots position.
    public void remove() {
        removeLaser(storedCoordsOpposite);
    }

    // Default: removes laser cells projected in a direction that is currently being blocked by a robot.
    private void removeLaser(ArrayList<GridPoint2> storedCoords) {
        for (GridPoint2 coords : storedCoords) {
            if(!layers.assertLaserNotNull(coords.x, coords.y))
                continue;
            if (layers.getLaserID(coords.x, coords.y)==123)
                if(storedLaserCell.equals(storedLaserCellVertical))
                    layers.setLaserCell(coords.x, coords.y, storedLaserCellHorizontal);
                else
                    layers.setLaserCell(coords.x, coords.y, storedLaserCellVertical);
            else
                layers.setLaserCell(coords.x, coords.y, null);
        }
    }

    /**
     * Finds the laser cannon and adds the laser cells opposite and direct of the robot.
     *
     * @param robotsOrigin The position the robot is or was currently in.
     */
    public void findLaser(GridPoint2 robotsOrigin) {
        this.robotsOrigin = robotsOrigin;
        storedLaserCell = layers.getLaserCell(robotsOrigin.x, robotsOrigin.y);
        if (laserType.get(laserDegree).equals("HORIZONTAL")) {
            findHorizontal();
        }
        else if(laserType.get(laserDegree).equals("VERTICAL"))
            findVertical();
        else {
            laserDegree = 47;
            findVertical();
            laserDegree = 39;
            findHorizontal();
        }
        storedCoordsDirect.add(robotsOrigin);
    }

    /**
     * Finds the cannon projecting a horizontal laser, and stores laser cells to the left and right of the robot.
     */
    public void findHorizontal() {
        int i = robotsOrigin.x + 1;
        int j = robotsOrigin.x - 1;
        int k = robotsOrigin.y;
        while (i < layers.getWidth()) {
            if (layers.assertLaserNotNull(i, k))
                storedCoordsDirect.add(new GridPoint2(i++, k));
            else {
                break;
            }
        }
        while (j >= 0) {
            if (layers.assertLaserNotNull(j, k))
                storedCoordsOpposite.add(new GridPoint2(j--, k));
            else {
                break;
            }
        }
        findAndRemove(i, j, k);
    }

    /**
     * Finds the cannon projecting a vertical laser, and stores laser cells to the left and right of the robot.
     */
    public void findVertical() {
        int i = robotsOrigin.y + 1;
        int j = robotsOrigin.y - 1;
        int k = robotsOrigin.x;
        while (i < layers.getHeight()) {
            if (layers.assertLaserNotNull(k, i))
                storedCoordsDirect.add(new GridPoint2(k, i++));
            else {
                break;
            }
        }
        while (j >= 0) {
            if (layers.assertLaserNotNull(k, j))
                storedCoordsOpposite.add(new GridPoint2(k, j--));
            else {
                break;
            }
        }
        findAndRemove(i, j, k);
    }

    /**
     * Calculates where the laser is coming from, and removes laser cells accordingly.
     * @param i The negative sides endpoint (down or left) which the laser might be coming from.
     * @param j The positive sides endpoint (up or right) which the laser might be coming from.
     * @param k The static x or y coordinate.
     */
    private void findAndRemove(int i, int j, int k) {
        boolean cannon1;
        boolean cannon2;
        boolean robot;
        if (laserType.get(this.laserDegree).equals("VERTICAL")) {
            cannon1 = layers.assertLaserCannonNotNull(k, i - 1);
            robot = layers.assertRobotNotNull(k, i) && containsOrigin(k, i);
            cannon2 = layers.assertLaserCannonNotNull(k, j + 1);
        } else {
            cannon1 = layers.assertLaserCannonNotNull(i - 1, k);
            robot = layers.assertRobotNotNull(i, k) && containsOrigin(i, k);
            cannon2 = layers.assertLaserCannonNotNull(j + 1, k);
        }
        if (cannon2) {
            ArrayList<GridPoint2> temp = new ArrayList<>(storedCoordsDirect);
            storedCoordsDirect = storedCoordsOpposite;
            storedCoordsOpposite = temp;
        }
        // A robot is blocking the path, not a laser cannon.
        else if (!cannon1) {
            // The robot is the one projecting the laser.
            if (robot) {
                removeLaser(storedCoordsOpposite);
            } else
                removeLaser(storedCoordsDirect);
            clearStoredCoordinates();
        }
    }

    // Checks if the robot found is the one projecting the laser.
    public boolean containsOrigin(int i, int j) {
        for (IRobot robot : AssetManagerUtil.getRobots()) {
            if (robot.getLaser().getOpposite().contains(robotsOrigin) && robot.getPosition().x == i && robot.getPosition().y == j) {
                return true;
            }
        }
        return false;
    }

    /** Sees if the new position is going into or out of the lasers path.
     * @param pos The position the robot moved to.
     * Either adds a laser cell to the new position or removes one from the previous position.
     */
    public void moveInLaser(GridPoint2 pos) {
        if (storedCoordsOpposite.contains(pos)) {
            storedCoordsDirect.add(pos);
            storedCoordsOpposite.remove(pos);
            layers.setLaserCell(pos.x, pos.y, storedLaserCell);
        }
        else if (storedCoordsDirect.contains(pos)) {
            storedCoordsOpposite.add(robotsOrigin);
            storedCoordsDirect.remove(robotsOrigin);
            layers.setLaserCell(robotsOrigin.x, robotsOrigin.y, null);
        }
        this.robotsOrigin = pos;
    }

    // Checks if the robot is in a laser instance.
    public boolean checkIfPosIsInLaserPath(GridPoint2 pos) {
        return (storedCoordsOpposite.contains(pos) || storedCoordsDirect.contains(pos));
    }

    // Clears all stored coordinates.
    public void clearStoredCoordinates() {
        storedCoordsOpposite.clear();
        storedCoordsDirect.clear();
    }
    // Returns the list of opposite coordinates.
    public ArrayList<GridPoint2> getOpposite() {
        return this.storedCoordsOpposite;
    }
}