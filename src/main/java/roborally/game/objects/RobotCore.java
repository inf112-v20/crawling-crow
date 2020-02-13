package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;
import roborally.ui.robot.IUIRobot;
import roborally.ui.robot.UIRobot;

public class RobotCore {
    private IUIRobot uiRobot;
    private IRobot robot;
    private int degrees;

    // Constructor for testing the robot model.
    public RobotCore(Robot robot) {
        this.robot = robot;
    }

    // Creates new RobotCore wth a robot shell to update its movement and a ui\view to update its graphic interface.
    public RobotCore(int x, int y) {
        IRobot robot = new Robot();
        IUIRobot uiRobot = new UIRobot(x, y);
        this.robot = robot;
        this.uiRobot = uiRobot;
        robot.setPosition(x,y);
    }

    // To handle bumping into other robots.
    public void crashControl() {

    }

    // Returns the position for this robotCores robot.
    public Vector2 getPosition() {
        return robot.getPosition();
    }


    // Makes move inside the graphical interface for uiRobot,
    // checks if its still on the map and updates the robots position, returns true if so.
    public boolean move(int x, int y) {
        Vector2 pos = this.getPosition();
        x = x + (int)pos.x; y = y + (int)pos.y;
        boolean onMap = this.uiRobot.moveRobot(x, y);
        if(onMap)
            this.robot.setPosition(x, y);
        return onMap;
    }


    // Sets position for this robotCores robot.
    public void setPos(int x, int y) {
        this.robot.setPosition(x,y);
    }

    // Updates the current cell to a WinCell
    public void getWinCell() {
        this.uiRobot.getWinTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    // Updates the current cell to a LoseCell
    public void getLoseCell() {
        this.uiRobot.getLostTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    // Angle robot is facing. 0 = North, 90 = Right, 180 = South, 270 = West
    public int getDegrees() {
        return degrees;
    }

    // Rotates left or right.
    public void setDegrees(int rotate, char dir) {
        if (dir == 'R')
            degrees += rotate;
        else if (dir == 'L')
            degrees-= rotate;
        degrees = degrees%360;
    }
}
