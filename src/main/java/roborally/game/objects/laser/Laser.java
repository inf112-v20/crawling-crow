package roborally.game.objects.laser;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import roborally.tools.BooleanCalculator;
import roborally.ui.gameboard.Layers;

import java.util.ArrayList;
import java.util.HashMap;
public class Laser {
    private HashMap<Integer, String> laserType;
    private int laserDegree;
    private Layers layers;
    private GridPoint2 robotsOrigin;
    private ArrayList<GridPoint2> storedCoordsOpposite;
    private ArrayList<GridPoint2> storedCoordsDirect;
    private int width;
    private int height;
    private TiledMapTileLayer.Cell storedLaserCell;
    private BooleanCalculator booleanCalculator;

    /** Constructs a laser.
     * @param laserDegree Horizontal or Vertical laser.
     */
    public Laser(int laserDegree) {
        layers = new Layers();
        laserType = new HashMap<>();
        laserType.put(39, "HORIZONTAL");
        laserType.put(47, "VERTICAL");
        this.laserDegree = laserDegree;
        this.width = layers.getWidth();
        this.height = layers.getHeight();
        storedCoordsOpposite = new ArrayList<>(); // Stores coordinates of laser-cells that are removed.
        storedCoordsDirect = new ArrayList<>(); // Stores coordinates of laser-cells that are active.
        this.booleanCalculator = new BooleanCalculator();
    }

    // Creates a laser for a robot to shoot.
    public void createLaser(GridPoint2 pos) {
        storedLaserCell = layers.getLaserCell(11,13);
    }

    /** Shoots a laser in the given direction until it hits a wall. Stores the cells for clearing them after.
     * @param gp2 The position of the robot that is shooting the laser.
     * @param direction The direction the robot is looking.
     */
    public void fireLaser(GridPoint2 gp2, int direction) {
        storedCoordsOpposite.clear();
        int i = gp2.x;
        int j = gp2.y;
        if(direction == 1) {
            if (booleanCalculator.checkForWall(i, j, -1, 0))
                return;
            while (i > 0) {
                layers.setLaserCell(--i, j, storedLaserCell);
                storedCoordsOpposite.add(new GridPoint2(i, j));
                if (booleanCalculator.checkForWall(i, j, -1, 0) || layers.assertRobotNotNull(i, j))
                    break;
            }
        }

        else if(direction == 3) {
            if (booleanCalculator.checkForWall(i, j, 1, 0))
                return;
            while(i < width-1) {
                layers.setLaserCell(++i, j, storedLaserCell);
                storedCoordsOpposite.add(new GridPoint2(i, j));
                if (booleanCalculator.checkForWall(i, j, 1, 0) || layers.assertRobotNotNull(i, j))
                    break;
            }
        }
    }


    // Restores the entire laser that was removed.
    public void restoreLaser() {
        restoreLaserOpposite();
    }

    private void restoreLaserDirect() {
    }

    private void restoreLaserOpposite() {
        for (GridPoint2 coords : storedCoordsOpposite)
            layers.setLaserCell(coords.x, coords.y, storedLaserCell);
    }


    // Removes the opposite part of the direction the laser is going in relation to the robots position.
    public void removeLaser() {
        removeLaserOpposite();
    }

    private void removeLaserDirect() {
    }

    private void removeLaserOpposite() {
        for (GridPoint2 coords : storedCoordsOpposite)
            layers.setLaserCell(coords.x, coords.y, null);
    }

    /** Finds the laser cannon and adds the laser cells opposite and direct of the robot.
     * @param robotsOrigin The position the robot is or was currently in.
     *
     */
    public void findLaser(GridPoint2 robotsOrigin) {
        this.robotsOrigin = robotsOrigin;
        storedLaserCell = layers.getLaserCell(robotsOrigin.x, robotsOrigin.y);
        int i = robotsOrigin.x + 1;
        int j = robotsOrigin.x - 1;
        int k = robotsOrigin.y;
        if (laserType.get(laserDegree).equals("HORIZONTAL")) {
            findHorizontal(i, j, k);
        }
    }

    /** Finds the cannon projecting a horizontal laser, and stores laser cells to the left and right of the robot.
     * @param i The x-coordinate to the  cell to the right of the robot
     * @param j The x-coordinate to the cell to the left of the robot
     * @param k The y-coordinate of the robot
     */
    public int findHorizontal(int i, int j, int k) {
        int cannonId = 0;
        while (i < width) {
            if (layers.assertLaserNotNull(i, k))
                storedCoordsDirect.add(new GridPoint2(i++, k));
            else {
                break;
            }
        }
        if (layers.assertLaserCannonNotNull(i-1, k))
            cannonId = layers.getLaserCannonID(i-1, k);

        while (j >= 0) {
            if (layers.assertLaserNotNull(j, k))
                storedCoordsOpposite.add(new GridPoint2(j--, k));
            else {
                break;
            }
        }
        if (layers.assertLaserCannonNotNull(j+1, k)) {
            cannonId = layers.getLaserCannonID(j+1, k);
            ArrayList<GridPoint2> temp = new ArrayList<>();
            temp.addAll(storedCoordsDirect);
            storedCoordsDirect = storedCoordsOpposite;
            storedCoordsOpposite = temp;
        }
        storedCoordsDirect.add(robotsOrigin);
        return cannonId;
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
        } else if (storedCoordsDirect.contains(pos)) {
            storedCoordsOpposite.add(robotsOrigin);
            storedCoordsDirect.remove(robotsOrigin);
            layers.setLaserCell(robotsOrigin.x, robotsOrigin.y, null);
        }
        System.out.println(storedCoordsDirect);
        System.out.println(storedCoordsOpposite);
        this.robotsOrigin = pos;
    }

    // Checks if the robot is in a laser instance.
    public boolean checkIfPosIsInLaserPath(GridPoint2 pos) {
        return (storedCoordsOpposite.contains(pos) || storedCoordsDirect.contains(pos));
    }
    public boolean checkForLaserCells() {
        return (!storedCoordsOpposite.isEmpty());
    }
}