package roborally.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import roborally.ui.gameboard.Layers;
import roborally.ui.robot.UIRobot;

public class RobotCore {
    private UIRobot uiRobot;
    private Robot robot;
    private int degrees;
    private Layers layers;

    public RobotCore(Robot robot) {
        this.robot = robot;
    }

    public RobotCore(Robot robot, UIRobot uiRobot) {
        this.robot = robot;
        this.uiRobot = uiRobot;
        layers = new Layers();
    }

    // To handle bumping into other robots.
    public void crashControl() {

    }

    public Vector2 getPosition() {
        return robot.getPosition();
    }

    public boolean move(int x, int y) {
        Vector2 pos = this.getPosition();
        x = x + (int)pos.x; y = y + (int)pos.y;
        boolean onMap = uiRobot.moveRobot(x, y);
        if(onMap)
            robot.setPosition(x, y);
        return onMap;
    }

    public void setPos(int x, int y) {
        robot.setPosition(x,y);
    }

    public TiledMapTileLayer.Cell getTexture() {
        return uiRobot.getTexture();
    }

    public void getWinCell() {
        uiRobot.getWinTexture((int)this.getPosition().x, (int)this.getPosition().y);
    }

    public void getLoseCell() {
        uiRobot.getLostTexture((int)this.getPosition().x, (int)this.getPosition().y);
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
