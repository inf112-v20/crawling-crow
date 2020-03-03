package roborally.tools;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.laser.Lasers;
import roborally.game.objects.robot.IRobot;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import java.util.HashMap;

// Beep... Robots need to calculate.
public class BooleanCalculator {
    private HashMap<String, Boolean> operations;
    private ILayers layers;
    private int x;
    private int y;
    private int height;
    private int width;
    private Lasers lasers;

    public BooleanCalculator() {
        layers = new Layers();
        width = layers.getRobots().getWidth();
        height = layers.getRobots().getHeight();
        // Advanced calculations for AI, can take multiple conditions to figure out a good move.
        operations = new HashMap<>();
        lasers = new Lasers();
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
        if(checkForWall(x, y, dx, dy))
            return true;
            if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                if(checkForWall(x,y,dx,dy))
                    return true;
                if (layers.assertRobotNotNull(x+dx,y+dy))
                    recursiveRobot = robotNextToRobot(x + dx, y + dy, dx, dy);
                for (IRobot robot : AssetManagerUtil.getRobots())
                    if(robot.getPosition().x == x && robot.getPosition().y == y && !recursiveRobot) {
                        System.out.println("\nPushing robot...");
                        robot.move(dx, dy);
                    }
        }
            // Robot "deletion"
            else
                for(IRobot robot : AssetManagerUtil.getRobots())
                    if(robot.getPosition().x == x && robot.getPosition().y == y) {
                        layers.setRobotCell(x, y, null);
                        robot.setPos(-1,-1);
                    }
        return recursiveRobot;
    }

    public Lasers getLasers() {
        return this.lasers;
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

    /** Creates a new laser instance if there is a laser cell in the position the robot is moving to.
     *  Else it will see if the robot is currently in a laser instance.
     * @param x The x-coordinate the robot is moving to
     * @param y The y-coordinate the robot is moving to
     * @param name The name of the robot
     */

    public void checkForLasers(int x, int y, String name) {
        GridPoint2 pos = new GridPoint2(x, y);
        int id;
        if (layers.assertLaserNotNull(x, y)) {
            id = layers.getLaserID(x, y);
            lasers.createLaser(id, pos, name);
        }
        else
            lasers.checkIfRobotWasInLaser(name, pos);
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
        for (IRobot robot : AssetManagerUtil.getRobots()){
            GridPoint2 bumpingPos = new GridPoint2(x, y);
            if (robot != null) {
                GridPoint2 bumpedPos = new GridPoint2((int)robot.getPosition().x, (int)robot.getPosition().y);
                if(bumpedPos.equals(bumpingPos) && (x + dx >= width || x + dx < 0 || y + dy >= height || y + dy < 0)) {
                    // Robot "deletion".
                    robot.setPos(-1, -1);
                    layers.setRobotCell(x, y, null);
                }
                else if (bumpedPos.equals(bumpingPos)){
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

    // Check a specific position if it is blocked
    public boolean isBlocked(int x, int y) {
        return layers.assertRobotNotNull(x, y);
    }

    public boolean isOnHole(int x, int y) {
        return layers.assertHoleNotNull(x, y);
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