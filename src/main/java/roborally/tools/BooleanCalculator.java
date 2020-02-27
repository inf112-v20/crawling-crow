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
    private int x;
    private int y;
    private int height;
    private int width;
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
     * This method is run if there are more than 2 robots in a row, with a robot at one end making a move onto the whole
     * line of robots. What happens is all of the robots except the first 2 makes a single step in the same direction,
     * and then finally the 2 first robots will bump normally in the final part of checkIfBlocked.
     * Robot disappears the same way as stated in findCollidingRobot.
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return True if there is a wall blocking the path.
     */
    public boolean robotNextToRobot(int x, int y, int dx, int dy) {
        boolean recursiveRobot = false;
            if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                if(checkForWall(x,y,dx,dy))
                    return true;
                if (layers.assertRobotNotNull(x+dx,y+dy))
                    recursiveRobot = robotNextToRobot(x + dx, y + dy, dx, dy);
                for (Robot robot : AssetsManager.getRobots())
                    if(robot.getPosition().x == x && robot.getPosition().y == y && !recursiveRobot)
                        robot.move(dx,dy);
        }
            // Robot "deletion"
            else
                for(Robot robot : AssetsManager.getRobots())
                    if(robot.getPosition().x == x && robot.getPosition().y == y) {
                        layers.setRobotCell(x, y, null);
                        robot.setPos(-1,-1);
                    }
        return recursiveRobot;
    }

    /**
     * Checks if the robot is blocked by another robot, true if the robot is on the edge. If not, then bumping.
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return True if the robot or any robot on a straight line in its direction is facing a wall.
     */
    public boolean checkIfBlocked(int x, int y, int dx, int dy) {
        if(checkForWall(x, y, dx, dy))
            return true;
        int newX = x + dx;
        int newY = y + dy;
        // There is no Robot on the next position.
        if(!layers.assertRobotNotNull(newX, newY))
            return false;
        else {
            if(checkForWall(newX, newY, dx, dy))
                return true;
            if(layers.assertRobotNotNull(newX + dx, newY + dy) && robotNextToRobot(newX, newY, dx, dy))
                return true;

        }
        findCollidingRobot(newX, newY, dx, dy);
        return false;
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
            else if (dy < 0)
                wall = id == 29 || id == 8 || id == 32;
            else if (dx > 0)
                wall = id == 23 || id == 16 || id == 8;
            else if (dx < 0)
                wall = id == 30 || id == 32 || id == 24;
        }
        if(layers.assertWallNotNull(x + dx, y + dy) && !wall) {
            int id = layers.getWallID(x + dx, y + dy);
            if (dy > 0)
                return id == 8 || id == 29 || id == 32;
            else if (dy < 0)
                return id == 24 || id == 16 || id == 31;
            else if (dx > 0)
                return id == 30 || id == 24 || id == 32;
            else if (dx < 0)
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
    public boolean checkForLaser(int x, int y, String name) {
        if (name.equals(clumsyRobot) && !layers.assertLaserNotNull(x, y)) {
            GridPoint2 pos = new GridPoint2(x, y);
            for (GridPoint2 oldPos : restoreLaserCoordinates) {
                if (oldPos.equals(pos)) {
                    restoreLaserCoordinates.removeIndex(restoreLaserCoordinates.indexOf(oldPos,true));
                    layers.setLaserCell(x, y, restoreLaserCell);
                    if(restoreLaserCoordinates.isEmpty())
                        clumsyRobot = "";
                    return true;
                }
            }
            while(!restoreLaserCoordinates.isEmpty()) {
                pos = restoreLaserCoordinates.removeLast();
                layers.setLaserCell(pos.x, pos.y, restoreLaserCell);
            }
            clumsyRobot = "";
            restoreLaserCoordinates.clear();
            restoreLaserCell = null;
            return true;
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
        return true;
    }

    /**
     * Finds the given robot at the colliding position and moves it one step in the bumping direction then clears its old position.
     * If the robot of interest is on the edge of the map it will temporary get position -1,-1, and it's cell set to null.
     * This is until we find a better way to deal with damage\robots getting destroyed.
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     */
    public void findCollidingRobot(int x, int y, int dx, int dy) {
        for (Robot robot : AssetsManager.getRobots()){
            if (robot!=null) {
                if((int)robot.getPosition().x == x && (int)robot.getPosition().y == y) {
                    if (x + dx >= width || x + dx < 0 || y + dy >= height || y + dy < 0) {
                        // Robot "deletion".
                        robot.setPos(-1,-1);
                        layers.setRobotCell(x, y, null);
                    }
                    else {
                        System.out.println("The bumped robot: ");
                        robot.move(dx, dy);
                        System.out.println("The bumping robot: ");
                        if (layers.assertFlagNotNull(x + dx, y + dy))  //Checks if the robot got bumped into a flag.
                            robot.setWinTexture();
                        else if (layers.assertHoleNotNull(x + dx, y + dy)) //Checks if the robot got bumped into a hole.
                            robot.setLostTexture();
                    }
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

    private boolean isBelowFlagOnMap(int y){
        return y < this.y;
    }

    private boolean isAboveFlagOnMap(int y){
        return y > this.y;
    }

    private boolean isToTheRightOfFlagOnMap(int x){
        return x > this.x;
    }

    private boolean isToTheLeftOfFlagOnMap(int x){
        return x < this.x;
    }

    public HashMap<String, Boolean> getOperations() {
        return this.operations;
    }

    public void loadAICalc(int x, int y) {
    operations.put("Left", isToTheLeftOfFlagOnMap(x));
    operations.put("Right", isToTheRightOfFlagOnMap(x));
    operations.put("Up", isAboveFlagOnMap(y));
    operations.put("Down", isBelowFlagOnMap(y));
    }
}