package roborally.game.objects.robot;

import com.badlogic.gdx.math.Vector2;
import roborally.tools.AssetsManager;
import roborally.tools.BooleanCalculator;
import roborally.tools.Direction;
import roborally.ui.robot.IUIRobot;
import roborally.ui.robot.UIRobot;

public class Robot implements IRobot {
    private IUIRobot uiRobot;
    private IRobotState robotState;
    private BooleanCalculator booleanCalculator;
    private boolean[] visitedFlags;
    private Direction direction;

    // Constructor for testing the robot model.
    public Robot(RobotState robotState) {
        this.robotState = robotState;
    }

    // Creates new RobotCore wth a robot shell to update its movement and a ui\view to update its graphic interface.
    public Robot(int x, int y, int cellId) {
        IRobotState robot = new RobotState(AssetsManager.getRobotName());
        IUIRobot uiRobot = new UIRobot(x, y);
        this.robotState = robot;
        this.uiRobot = uiRobot;
        robot.setPosition(x,y);
        booleanCalculator = new BooleanCalculator();
        this.visitedFlags = new boolean[3]; //TODO : make sure number of flags are correct
        this.direction = Direction.North;
        this.setTextureRegion(cellId);
    }

    public String getName() {
        return this.robotState.getName();
    }

    public BooleanCalculator getCalc() {
        return this.booleanCalculator;
    }

    public Vector2 getPosition() {
        return robotState.getPosition();
    }

    public void setTextureRegion(int i) {
        this.uiRobot.setTextureRegion(i);
    }

    public boolean move(int dx, int dy) {
        Vector2 pos = this.getPosition();
        int x = (int)pos.x;
        int y = (int)pos.y;
        int newX = x + dx;
        int newY = y + dy;
        System.out.println("Old position: ");
        System.out.println(x + " " + y);

        // Checks for robots in its path before moving.
        if(!getCalc().checkIfBlocked(x, y, dx, dy)) {
            if (this.uiRobot.moveRobot(x, y, dx, dy)) {
                this.robotState.setPosition(newX, newY);
                System.out.println("New position: ");
                System.out.println((newX) + " " + (newY));
                getCalc().checkForLasers(newX, newY, getName());
            }
        }
        else
            System.out.println("New position: " + x + " " + y);
        // update checkpoints etc.
        return true;
    }

    public boolean moveForward() {
        System.out.println(this.getName());
        int dy = 0;
        int dx = 0;
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
        int dy = 0;
        int dx = 0;
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

        this.direction = Direction.turnRightFrom(this.direction);
        this.uiRobot.setDirection(robotState.getPositionX(), robotState.getPositionY(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
    }

    public void turnLeft() {
        System.out.println("Turning left...");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnLeftFrom(this.direction);
        this.uiRobot.setDirection(robotState.getPositionX(), robotState.getPositionY(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());

    }

    public void setPos(int x, int y) {
        this.robotState.setPosition(x, y);
    }

    public void setWinTexture() {
        this.uiRobot.setWinTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    public void setLostTexture() {
        this.uiRobot.setLostTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    public int getDegrees(){
        return this.direction.getDirectionId();
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
                return i+1;
            }
        }
        return -1;
    }

    public void visitNextFlag() {
        System.out.println("updated flag visited");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag-1] = true;
        System.out.println("Next flag to visit: " + (nextFlag+1));
    }
    // TODO: Get number of flags from the size of the flags array in gameBoard.
    public void setNumberOfFlags(int nFlags) {
        this.visitedFlags = new boolean[nFlags];
    }
}
