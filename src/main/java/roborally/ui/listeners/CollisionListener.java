package roborally.ui.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.robot.IRobotPresenter;
import roborally.ui.ILayers;
import roborally.utilities.AssetManagerUtil;

public class CollisionListener {
    private ILayers layers;
    private WallListener wallListener;
    public CollisionListener(ILayers layers) {
        this.layers = layers;
        this.wallListener = new WallListener(this.layers);
    }

    /**
     * Called from {@link #checkIfBlocked} if there is multiple robots in a line in the direction the robot is moving.
     * Iterates through all the robots to see if the foremost robot in the direction is blocked, moves robots
     * accordingly.
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return True if there is a wall blocking the path.
     */
    public boolean robotNextToRobot(int x, int y, int dx, int dy) {
        int width = layers.getWidth();
        int height = layers.getHeight();
        boolean recursiveRobot = false;
        if(wallListener.checkForWall(x, y, dx, dy))
            return true;
        if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
            if(wallListener.checkForWall(x,y,dx,dy))
                return true;
            if (layers.assertRobotNotNull(x+dx,y+dy))
                recursiveRobot = robotNextToRobot(x + dx, y + dy, dx, dy);
            for (IRobotPresenter robot : AssetManagerUtil.getRobots())
                if(robot.getPosition().x == x && robot.getPosition().y == y && !recursiveRobot) {
                    System.out.println("\nPushing robot...");
                    robot.move(dx, dy);
                    System.out.println("Pushing robot complete");
                }
        }
        // RobotPresenter "deletion"
        else
            for(IRobotPresenter robot : AssetManagerUtil.getRobots())
                if(robot.getPosition().equals(new GridPoint2(x, y))) {
                    layers.setRobotCell(x, y, null);
                    robot.setPos(-1, -1);
                }
        return recursiveRobot;
    }

    /**
     * Called from {@link #checkIfBlocked} if it is possible for the moving robot to bump into a robot.
     * Moves the robots accordingly.
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     */
    public void findCollidingRobot(int x, int y, int dx, int dy) {
        int width = layers.getRobots().getWidth();
        int height = layers.getRobots().getHeight();
        for (IRobotPresenter robot : AssetManagerUtil.getRobots()){
            GridPoint2 bumpingPos = new GridPoint2(x, y);
            if (robot != null) {
                GridPoint2 bumpedPos = robot.getPosition();
                if(bumpedPos.equals(bumpingPos) && (x + dx >= width || x + dx < 0 || y + dy >= height || y + dy < 0)) {
                    // RobotPresenter "deletion".
                    robot.setPos(-1, -1);
                    layers.setRobotCell(x, y, null);
                }
                else if (bumpedPos.equals(bumpingPos)){
                    System.out.println("\nPushing... ");
                    robot.move(dx, dy);
                    System.out.println("Pushing complete... ");
                    if (layers.assertFlagNotNull(x + dx, y + dy))  //Checks if the robot got bumped into a flag.
                        robot.setWinTexture();
                    else if (layers.assertHoleNotNull(x + dx, y + dy)) //Checks if the robot got bumped into a hole.
                        robot.setLostTexture();
                }
            }
        }
    }

    /**
     * Checks if the robot is blocked by another robot, true if the robot is on the edge. If not, then bumping
     * with the method {@link #findCollidingRobot}
     * @param x the x position
     * @param y the y position
     * @param dx steps taken in x-direction
     * @param dy steps taken in y-direction
     * @return True if the robot or any of the robots in front of it is found in {@link #robotNextToRobot} is blocked.
     */
    public boolean checkIfBlocked(int x, int y, int dx, int dy) {
        if(wallListener.checkForWall(x, y, dx, dy))
            return true;
        int newX = x + dx;
        int newY = y + dy;
        // There is no RobotPresenter on the next position.
        if(!layers.assertRobotNotNull(newX, newY))
            return false;
        else {
            if(wallListener.checkForWall(newX, newY, dx, dy))
                return true;
            if(layers.assertRobotNotNull(newX + dx, newY + dy) && robotNextToRobot(newX, newY, dx, dy))
                return true;
        }
        findCollidingRobot(newX, newY, dx, dy);
        return false;
    }
}