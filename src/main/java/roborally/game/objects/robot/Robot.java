package roborally.game.objects.robot;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.laser.Laser;
import roborally.tools.AssetManagerUtil;
import roborally.tools.BooleanCalculator;
import roborally.tools.Direction;
import roborally.ui.robot.IRobotUI;
import roborally.ui.robot.RobotUI;

public class Robot implements IRobot {
    private IRobotUI uiRobot;
    private IRobotState robotState;
    private BooleanCalculator booleanCalculator;
    private boolean[] visitedFlags;
    private Direction direction;
    private Laser laser;

    // Constructor for testing the robot model.
    public Robot(RobotState robotState) {
        this.robotState = robotState;
    }

    // Creates new RobotCore wth a robot shell to update its movement and a ui\view to update its graphic interface.
    public Robot(int x, int y, int cellId) {
        IRobotState robot = new RobotState(AssetManagerUtil.getRobotName());
        IRobotUI uiRobot = new RobotUI(x, y);
        this.robotState = robot;
        this.uiRobot = uiRobot;
        robot.setPosition(x,y);
        booleanCalculator = new BooleanCalculator();
        this.visitedFlags = new boolean[3]; //TODO : make sure number of flags are correct
        this.direction = Direction.North;
        this.setTextureRegion(cellId);
        laser = new Laser(39);
    }

    @Override
    public String getName() {
        return this.robotState.getName();
    }

    @Override
    public BooleanCalculator getCalc() {
        return this.booleanCalculator;
    }

    @Override
    public Vector2 getPosition() {
        return robotState.getPosition();
    }

    @Override
    public void fireLaser() {
        int x = (int)getPosition().x;
        int y = (int)getPosition().y;
        getCalc().getLasers().fireLaser(laser, new GridPoint2(x, y), getDegrees());
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.5);
    }

    @Override
    public void clearLaser() {
        if (this.laser.checkForLaserCells())
            getCalc().getLasers().clearLaser(this.laser);
    }

    @Override
    public void setTextureRegion(int i) {
        this.uiRobot.setTextureRegion(i);
    }

    @Override
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
                clearLaser();
                getCalc().checkForLasers(newX, newY, getName());
                if (getCalc().isOnHole(newX, newY)) {
                    this.setLostTexture();
                }
                this.uiRobot.setDirection(robotState.getPositionX(), robotState.getPositionY(), this.direction);
            }
        }
        else
            System.out.println("New position: " + x + " " + y);
        // update checkpoints etc.
        return true;
    }

    @Override
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

    @Override
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

    @Override
    public void turnRight() {
        System.out.println("Turning right... ");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnRightFrom(this.direction);
        this.uiRobot.setDirection(robotState.getPositionX(), robotState.getPositionY(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
        clearLaser(); // TODO: Move this, so it's called one time
    }

    @Override
    public void turnLeft() {
        System.out.println("Turning left...");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnLeftFrom(this.direction);
        this.uiRobot.setDirection(robotState.getPositionX(), robotState.getPositionY(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
        clearLaser(); // TODO: Move this, so it's called one time
    }

    @Override
    public void setPos(int x, int y) {
        this.robotState.setPosition(x, y);
    }

    @Override
    public void setWinTexture() {
        this.uiRobot.setWinTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    @Override
    public void setLostTexture() {
        this.uiRobot.setLostTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    @Override
    public int getDegrees(){
        return this.direction.getDirectionId();
    }

    @Override
    public boolean hasVisitedAllFlags() {
        boolean visitedAll = true;
        for(boolean visitedFlag : visitedFlags){
            visitedAll = visitedAll && visitedFlag;
        }
        return visitedAll;
    }

    @Override
    public int getNextFlag() {
        for (int i = 0; i < visitedFlags.length; i++) {
            if (!visitedFlags[i]) {
                return i+1;
            }
        }
        return -1;
    }

    @Override
    public void visitNextFlag() {
        this.setWinTexture();
        this.uiRobot.setDirection(robotState.getPositionX(), robotState.getPositionY(), this.direction);
        System.out.println("updated flag visited");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag-1] = true;
        System.out.println("Next flag to visit: " + (nextFlag+1));
    }

    @Override
    // TODO: Get number of flags from the size of the flags array in gameBoard.
    public void setNumberOfFlags(int nFlags) {
        this.visitedFlags = new boolean[nFlags];
    }
}
