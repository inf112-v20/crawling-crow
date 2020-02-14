package roborally.tools;

import roborally.game.objects.RobotCore;
import roborally.ui.gameboard.Layers;
import java.util.HashMap;

// Beep... Robots need to calculate.
public class BooleanCalculator {
    private Layers layers;
    private int height;
    private int width;

    public BooleanCalculator() {
        layers = new Layers();
        width = layers.getRobots().getWidth();
        height = layers.getRobots().getHeight();
        // Advanced calculations for AI, can take multiple conditions to figure out a good move.
        HashMap<String, Boolean> operations = new HashMap<>();
    }

    // Checks if there is 3 robots in a row. Here we look at the pos next to the new x and y pos.
    // (One robot is already found, checks if there's is even one more next to it).
    public boolean robotNextToRobot(int x, int y, int dx, int dy) {
            if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                return layers.getRobots().getCell(x + dx, y + dy) != null;
        }
        return true;
    }
    /*@x = the new x pos, @ y = the new y pos, @dx , @dy = steps taking in direction y and x
     Checks if the robot is blocked by another robot, true if the robot is on the edge. If not then bumping.
     Could alternate this method to do something else if the robot is on the edge, be destroyed?
     returns true if it is in fact blocked, false if not. Might consider adding a check for wall here also.*/
    public boolean checkIfBlocked(int x, int y, int dx, int dy) {
        if(robotNextToRobot(x, y, dx, dy))
            return true; // Returns blocked if moving into a robot with another one next to it, for now.
            if (x + dx >= 0 && y + dy >= 0 && y + dy < height && x + dx < width) {
                findCollidingRobot(x, y, dx, dy);
                return false;
            }
        return true; // Robot is on the edge, cant bump it anywhere.
    }

     /*Finds the given robot at the colliding position and moves it one step in the bumping direction
     then clears its old position. @x = new x, @y = new y, @dx, @dy = steps take in direction x and y.*/
    public void findCollidingRobot(int x, int y, int dx, int dy) {
        for (RobotCore robot : AssetsManager.getRobots()){
            if (robot!=null) {
                if((int)robot.getPosition().x == x && (int)robot.getPosition().y == y) {
                    robot.move(dx, dy);
                    if(this.isOnFlag(x+dx,y + dy))  //Checks if the robot got bumped into a flag.
                        robot.getWinCell();
                    else if(this.isOnHole(x + dx,y + dy)) //Checks if the robot got bumped into a hole.
                        robot.getLoseCell();
                    layers.getRobots().setCell(x, y,null);
                }
            }
        }
    }

    // checks if there is a flag on x,y.
    public boolean isOnFlag(int x, int y) {
        return layers.getFlag().getCell(x, y) != null;
    }

    // checks if there is a hole on x,y.
    public boolean isOnHole(int x, int y) {
        return layers.getHole().getCell(x, y) != null;
    }

}
