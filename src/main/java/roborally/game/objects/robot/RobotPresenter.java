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
    private IRobotView robotView;
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
        this.robotView = robotView;
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
        robotView.goToCheckPoint(this.getPosition(), robotState.getCheckPoint());
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
        this.robotView.setTextureRegion(i);
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
            if (this.robotView.moveRobot(x, y, dx, dy)) {
                this.setPos(newX, newY);
                System.out.println("New position: " + (newX) + " " + (newY));
                clearLaser();
                listener.listenLaser(newX, newY, getName(), laserRegister);
                if (layers.assertHoleNotNull(newX, newY)) {
                    this.setLostTexture();
                }
                this.robotView.setDirection(getPosition(), this.direction);
            }
        }
        else
            System.out.println("New position: " + x + " " + y);
        // update checkpoints etc.
        return true;
    }

    // TODO: Refactor to RobotModel. One method here to call one of the two in RobotModel (forward, backward).
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

    // TODO: Refactor to RobotModel
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

    // TODO: Refactor to RobotModel, same as move.
    @Override
    public void turnRight() {
        System.out.println("\nTurning right... ");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnRightFrom(this.direction);
        this.robotView.setDirection(getPosition(), this.direction);

        System.out.print("New direction: ");
        System.out.println(this.direction.toString());
        clearLaser(); // TODO: Move this, so it's called one time
    }

    // TODO: Refactor to RobotModel.
    @Override
    public void turnLeft() {
        System.out.println("\nTurning left...");
        System.out.print("Old direction: ");
        System.out.println(this.direction.toString());

        this.direction = Direction.turnLeftFrom(this.direction);
        this.robotView.setDirection(getPosition(), this.direction);

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
        this.robotView.setWinTexture(getPosition());
    }

    @Override
    public void setLostTexture() {
        this.robotView.setLostTexture(getPosition());
    }

    @Override
    public int getDirectionID(){
        return this.direction.getDirectionID();
    }

    // TODO: Refactor to RobotModel, make it correlate with GameBoard(GameModel).
    @Override
    public boolean hasVisitedAllFlags() {
        boolean visitedAll = true;
        for(boolean visitedFlag : visitedFlags){
            visitedAll = visitedAll && visitedFlag;
        }
        return visitedAll;
    }

    // TODO: Refactor to RobotModel.
    @Override
    public int getNextFlag() {
        for (int i = 0; i < visitedFlags.length; i++) {
            if (!visitedFlags[i]) {
                return i+1;
            }
        }
        return -1;
    }

    // TODO: Refactor to RobotModel
    @Override
    public void visitNextFlag() {
        this.setWinTexture();
        this.robotView.setDirection(getPosition(), this.direction);
        System.out.println("updated flag visited");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag-1] = true;
        if(nextFlag == visitedFlags.length)
            System.out.println("Congratulations you have collected all the flags, press 'W' to win the game.");
        else
            System.out.println("Next flag to visit: " + (nextFlag+1));
    }

    //TODO: Refactor to RobotModel
    @Override
    public void setNumberOfFlags(int nFlags) {
        this.visitedFlags = new boolean[nFlags];
    }
}
