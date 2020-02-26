package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import roborally.game.Settings;
import roborally.tools.AssetsManager;
import roborally.tools.BooleanCalculator;
import roborally.tools.Direction;
import roborally.ui.robot.IUIRobot;
import roborally.ui.robot.UIRobot;

public class Robot {
    private IUIRobot uiRobot;
    private IRobot robot;
    private int degrees;
    private BooleanCalculator booleanCalculator;
    private boolean[] visitedFlags;
    private Direction direction;

    // Constructor for testing the robot model.
    public Robot(RobotState robotState) {
        this.robot = robotState;
    }

    // Creates new RobotCore wth a robot shell to update its movement and a ui\view to update its graphic interface.
    public Robot(int x, int y) {
        IRobot robot = new RobotState(AssetsManager.getRobotName());
        IUIRobot uiRobot = new UIRobot(x, y);
        this.robot = robot;
        this.uiRobot = uiRobot;
        robot.setPosition(x,y);
        booleanCalculator = new BooleanCalculator();
        this.visitedFlags = new boolean[Settings.NUMBER_OF_FLAGS];
        this.direction = Direction.North;

    }

    // Returns the models name.
    public String getName() {
        return this.robot.getName();
    }

    // Returns a true\false calculator.
    public BooleanCalculator getCalc() {
        return this.booleanCalculator;
    }

    // Returns the position for this robotCores robot.
    public Vector2 getPosition() {
        return robot.getPosition();
    }

    // For setting different textureRegions for AI and Players
    public void setTextureRegion(int i) {
        this.uiRobot.setTextureRegion(i);
    }

    // Makes move inside the graphical interface for uiRobot.
    public boolean move(int dx, int dy) {
        Vector2 pos = this.getPosition();
        int x = (int)pos.x;
        int y = (int)pos.y;
        System.out.println("Old position: ");
        System.out.println(x + " " + y);

        // Checks for robots in its path before moving.
        if(!this.getCalc().checkIfBlocked(x, y, dx, dy)) {
            if (this.uiRobot.moveRobot(x, y, dx, dy)) {
                this.robot.setPosition(x + dx, y + dy);
                System.out.println("New position: ");
                System.out.println((x + dx) + " " + (y + dy));
            }
        }
        else
            System.out.println("New position: " + x + " " + y);
        // update checkpoints etc.
        return true;
    }

    public boolean moveForward() {
        System.out.println(this.getName());
        int dy = 0, dx = 0;
        System.out.println("Moving forward...");

        Direction dir = this.direction;
        if(dir == Direction.North){
            dy = 1;
        } if(dir == Direction.East){
            dx = 1;
        } if(dir == Direction.West){
            dx = -1;
        } if(dir == Direction.South){
            dy = -1;
        }

        return move(dx, dy);
    }

    public boolean moveBackward() {
        int dy = 0, dx = 0;
        System.out.println("Moving backwards...");

        Direction dir = this.direction;
        if(dir == Direction.North){
            dy = -1;
        } if(dir == Direction.East){
            dx = -1;
        } if(dir == Direction.West){
            dx = 1;
        } if(dir == Direction.South){
            dy = 1;
        }

        return move(dx, dy);
    }

    public void turnRight() {
        System.out.println("Turning right... ");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());
        this.direction = Direction.turnRight(this.direction);
        int x = (int)getPosition().x;
        int y = (int)getPosition().y;
        this.uiRobot.setDirection(x, y, -1);
        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
    }

    public void turnLeft() {
        System.out.println("Turning left...");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());
        this.direction = Direction.turnLeft(this.direction);
        int x = (int)getPosition().x;
        int y = (int)getPosition().y;
        this.uiRobot.setDirection(x, y, 1);
        System.out.print("New direction: ");
        System.out.println(this.direction.toString());

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

    public int getDegrees(){
        return this.direction.getDegrees();
    }

    public boolean hasVisitedAllFlags() {
        boolean visitedAll = true;
        for(boolean visitedFlag : visitedFlags){
            visitedAll = visitedAll && visitedFlag;
        }
        return visitedAll;
    }

    public int getNextFlag() {
        for (int i = 0; i < visitedFlags.length; i++) {
            if (!visitedFlags[i]) {
                return i;
            }
        }
        return -1;
    }
}
