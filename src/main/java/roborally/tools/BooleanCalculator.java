package roborally.tools;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import roborally.game.objects.robot.Robot;
import roborally.ui.gameboard.Layers;
import java.util.HashMap;

// Beep... Robots need to calculate.
public class BooleanCalculator {
    HashMap<String, Boolean> operations;
    private Layers layers;
    private int x, y, height, width;
    private Queue<GridPoint2> restoreLaserCoordinates;
    private TiledMapTileLayer.Cell restoreLaserCell;
    private String clumsyRobot;

    public BooleanCalculator() {
        layers = new Layers();
        width = layers.getRobots().getWidth();
        height = layers.getRobots().getHeight();
        // Advanced calculations for AI, can take multiple conditions to figure out a good move.
        operations = new HashMap<>();
    }

    /**
     * Checks if there are 3 robots in a row. Here we look at the position next to the new x and y positons.
     * (One robot is already found, checks if there's is even one more next to it).
     *
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return For now it treats 2 robots as too heavy to push, but also checks if the robot in the way stands up to a wall.
     */
    public boolean robotNextToRobot(int x, int y, int dx, int dy) {
            if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                if(checkForWall(x,y,dx,dy))
                    return true;
                return layers.assertRobotNotNull(x + dx, y + dy);
        }
        return false;
    }

    /**
     * Checks if the robot is blocked by another robot, true if the robot is on the edge. If not, then bumping.
     *
     * FIXME: Could alternate this method to do something else if the robot is on the edge, be destroyed?
     *
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return True if it is in fact blocked, false if not.
     */
    public boolean checkIfBlocked(int x, int y, int dx, int dy) {
        if(checkForWall(x, y, dx, dy))
            return true;
        int newX = x + dx;
        int newY = y + dy;
        // There is no Robot on the next position.
        if(!layers.assertRobotNotNull(newX, newY))
            return false;
        if(robotNextToRobot(newX, newY, dx, dy))
            return true; // Returns blocked if moving into a robot with another one next to it, for now.
            if (newX + dx >= 0 && newY + dy >= 0 && newY + dy < height && newX + dx < width) {
                findCollidingRobot(newX, newY, dx, dy);
                return false;
            }
        return true; // Robot is on the edge, cant bump it anywhere.
    }
     /** A method that looks through the respective ID's from the tileset, for relevant walls for the robot as it
      * tries to move.
      * @param x the x position
      * @param y the y position
      * @param dx steps taken in x-direction
      * @param dy steps taken in y-direction
      * @return True if it finds a wall that corresponds to a wall in x or y direction that blocks the robot.
      */
    public boolean checkForWall(int x, int y, int dx, int dy) {
        boolean wall = false;
        if(layers.assertWallNotNull(x, y)) {
            int id = layers.getWallID(x, y);
            if (dy > 0)
                wall = id == 31 || id == 16 || id == 24;
            if (dy < 0)
                wall = id == 29 || id == 8 || id == 32;
            if (dx > 0)
                wall = id == 23 || id == 16 || id == 8;
            if (dx < 0)
                wall = id == 30 || id == 32 || id == 24;
        }
        if(layers.assertWallNotNull(x + dx, y + dy) && !wall) {
            int id = layers.getWallID(x + dx, y + dy);
            if (dy > 0)
                return id == 8 || id == 29 || id == 32;
            if (dy < 0)
                return id == 24 || id == 16 || id == 31;
            if (dx > 0)
                return id == 30 || id == 24 || id == 32;
            if (dx < 0)
                return id == 16 || id == 8 || id == 23;
        }
        return wall;
    }

    /** Checks first if the robot that's stepped on the laser is already in the laser, and either restores or removes
     * laser cells accordingly. If there's no robot active in the laser then all laser cells on the opposite side
     * of the direction the laser going is removed, and later restored when the same robot steps out of the laser.
     * laser id 39 = vertical laser, laser id 47 = horizontal laser.
     * cannonId 45 = downWards, 46 = leftGoing, 38 = rightGoing, 37 = upWards
     */
    public void checkForLaser(int x, int y, String name) {
        if (name.equals(clumsyRobot) && !layers.assertLaserNotNull(x, y)) {
            GridPoint2 pos = new GridPoint2(x, y);
            for (GridPoint2 oldPos : restoreLaserCoordinates) {
                if (oldPos.equals(pos)) {
                    restoreLaserCoordinates.removeIndex(restoreLaserCoordinates.indexOf(oldPos,true));
                    layers.setLaserCell(x, y, restoreLaserCell);
                    if(restoreLaserCoordinates.isEmpty())
                        clumsyRobot = "";
                }
            }
            while(!restoreLaserCoordinates.isEmpty()) {
                pos = restoreLaserCoordinates.removeLast();
                layers.setLaserCell(pos.x, pos.y, restoreLaserCell);
            }
            clumsyRobot = "";
            restoreLaserCoordinates.clear();
            restoreLaserCell = null;
        }
        int cannonId = 0;
        if (layers.assertLaserNotNull(x, y)) {
            if (layers.getLaserID(x, y) == 39) {
                int i = x + 1;
                while(i < width) {
                    if (layers.assertLaserNotNull(i,y))
                        i++;
                    else
                        break;
                }
                if (layers.assertLaserCannonNotNull(i-1, y))
                    cannonId = layers.getLaserCannonID(i-1, y);
                if (cannonId == 0 || cannonId == -1) {
                    i = x-1;
                    while(i >= 0) {
                        if (layers.assertLaserNotNull(i,y))
                            i--;
                        else
                            break;
                    }
                    if (layers.assertLaserCannonNotNull(i+1, y))
                        cannonId = layers.getLaserCannonID(i+1, y);
                }
            }
            System.out.println(cannonId);
            if(cannonId == 46) {
                int i = x;
                restoreLaserCell = layers.getLaserCell(i, y);
                if (!name.equals(clumsyRobot))
                    restoreLaserCoordinates = new Queue<>();
                while (layers.assertLaserNotNull(--i,y)) {
                    layers.setLaserCell(i, y, null);
                    restoreLaserCoordinates.addFirst(new GridPoint2(i,y));
                }
                clumsyRobot = name;
            }

        }
    }

    /**
     * Finds the given robot at the colliding position and moves it one step in the bumping direction then clears its old position.
     *
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     */
    public void findCollidingRobot(int x, int y, int dx, int dy) {
        for (Robot robot : AssetsManager.getRobots()){
            if (robot!=null) {
                if((int)robot.getPosition().x == x && (int)robot.getPosition().y == y) {
                    System.out.println("The bumped robot: ");
                    robot.move(dx, dy);
                    System.out.println("The bumping robot: ");
                    if(layers.assertFlagNotNull(x + dx,y + dy))  //Checks if the robot got bumped into a flag.
                        robot.setWinTexture();
                    else if(layers.assertHoleNotNull(x + dx,y + dy)) //Checks if the robot got bumped into a hole.
                        robot.setLostTexture();
                }
            }
        }
    }

    // Check a specific position if it is blocked
    public boolean isBlocked(int x, int y) {
        return layers.assertRobotNotNull(x, y);
    }

    // AI methods
    public void determineFlagPos(Vector2 flagPos) {
        this.x = (int) flagPos.x;
        this.y = (int) flagPos.y;
    }

    public Vector2 getCurrFlagPos() {
        return new Vector2(this.x,this.y);
    }

    private boolean isBelowFlagOnMap(int x, int y){
        return y < this.y;
    }

    private boolean isAboveFlagOnMap(int x, int y){
        return y > this.y;
    }

    private boolean isToTheRightOfFlagOnMap(int x, int y){
        return x > this.x;
    }

    private boolean isToTheLeftOfFlagOnMap(int x, int y){
        return x < this.x;
    }

    public HashMap<String, Boolean> getOperations() {
        return this.operations;
    }

    public void loadAICalc(int x, int y) {
    operations.put("Left", isToTheLeftOfFlagOnMap(x, y));
    operations.put("Right", isToTheRightOfFlagOnMap(x, y));
    operations.put("Up", isAboveFlagOnMap(x, y));
    operations.put("Down", isBelowFlagOnMap(x, y));
    }
}