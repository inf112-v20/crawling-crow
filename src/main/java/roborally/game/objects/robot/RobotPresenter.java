package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.laser.Laser;
import roborally.game.objects.laser.LaserRegister;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import roborally.ui.listeners.Listener;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.Direction;
import roborally.ui.robot.IRobotView;
import roborally.ui.robot.RobotView;

public class RobotPresenter implements IRobotPresenter {
    private IRobotView uiRobot;
    private IRobotModel robotState;
    private boolean[] visitedFlags;
    private Direction direction;
    private Laser laser;
    private ILayers layers;
    private Listener listener;
    private LaserRegister laserRegister;

    // Constructor for testing the robot model.
    public RobotPresenter(RobotModel robotState) {
        this.robotState = robotState;
    }

    // Creates new RobotCore wth a robot shell to update its movement and a ui\view to update its graphic interface.
    public RobotPresenter(int x, int y, int cellId) {
        IRobotModel robotModel = new RobotModel(AssetManagerUtil.getRobotName());
        IRobotView robotView = new RobotView(x, y);
        this.robotState = robotModel;
        this.uiRobot = robotView;
        setPos(x, y);
        this.direction = Direction.North;
        this.setTextureRegion(cellId);
        laser = new Laser(0);
        robotState.setCheckPoint(x, y);
        this.layers = new Layers();
        this.listener = new Listener(layers);
        this.laserRegister = new LaserRegister();

    }

    @Override
    public String getName() {
        return this.robotState.getName();
    }

    @Override
    public GridPoint2 getPosition() {
        return robotState.getPosition();
    }

    @Override
    public void backToCheckPoint() {
        uiRobot.goToCheckPoint(this.getPosition(), robotState.getCheckPoint());
        this.robotState.setPosition(robotState.getCheckPoint());
        this.direction = Direction.North;
    }

    @Override
    public void fireLaser() {
        laser.fireLaser(getPosition(), getDirectionID());
    }

    @Override
    public void clearLaser() { // TODO: Mabye more suitable elsewhere
        if (!this.laser.getCoords().isEmpty())
            laser.clearLaser();
    }

    @Override
    public Laser getLaser() {
        return this.laser;
    }

    @Override
    public void setTextureRegion(int i) {
        this.uiRobot.setTextureRegion(i);
    }

    @Override
    public boolean move(int dx, int dy) {
        GridPoint2 pos = this.getPosition();
        int x = pos.x;
        int y = pos.y;
        int newX = x + dx;
        int newY = y + dy;
        System.out.println("Old position: " + x + " " + y);

        // Checks for robots in its path before moving.
        if(!listener.listenCollision(x, y, dx, dy)) {
            if (this.uiRobot.moveRobot(x, y, dx, dy)) {
                this.setPos(newX, newY);
                System.out.println("New position: " + (newX) + " " + (newY));
                clearLaser();
                listener.listenLaser(newX, newY, getName(), laserRegister);
                if (layers.assertHoleNotNull(newX, newY)) {
                    this.setLostTexture();
                }
                this.uiRobot.setDirection(getPosition(), this.direction);
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
        System.out.println("\nMoving forward...");

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
        System.out.println("\nMoving backwards...");

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
        System.out.println("\nTurning right... ");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnRightFrom(this.direction);
        this.uiRobot.setDirection(getPosition(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
        clearLaser(); // TODO: Move this, so it's called one time
    }

    @Override
    public void turnLeft() {
        System.out.println("\nTurning left...");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnLeftFrom(this.direction);
        this.uiRobot.setDirection(getPosition(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
        clearLaser(); // TODO: Move this, so it's called one time
    }

    @Override
    public void setPos(int x, int y) {
        this.robotState.setPosition(new GridPoint2(x, y));
    }

    @Override
    public void setWinTexture() {
        this.uiRobot.setWinTexture(this.getPosition().x, this.getPosition().y);
    }

    @Override
    public void setLostTexture() {
        this.uiRobot.setLostTexture(this.getPosition().x, this.getPosition().y);
    }

    @Override
    public int getDirectionID(){
        return this.direction.getDirectionID();
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
        this.uiRobot.setDirection(getPosition(), this.direction);
        System.out.println("updated flag visited");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag-1] = true;
        if(nextFlag == visitedFlags.length)
            System.out.println("Congratulations you have collected all the flags, press 'W' to win the game.");
        else
            System.out.println("Next flag to visit: " + (nextFlag+1));
    }

    @Override
    public void setNumberOfFlags(int nFlags) {
        this.visitedFlags = new boolean[nFlags];
    }
}
