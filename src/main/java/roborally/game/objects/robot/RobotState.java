package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;

public class RobotState implements IRobotState {
    private String name;
    private GridPoint2 robotPosition;
    private GridPoint2 checkPoint;

    public RobotState(String name) {
        this.name = name;
        this.robotPosition = new GridPoint2();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void setPosition(GridPoint2 pos) {
        this.robotPosition = pos;
    }

    @Override
    public GridPoint2 getPosition() {
        return this.robotPosition;
    }

    @Override
    public int getPositionX() {
        return this.robotPosition.x;
    }

    @Override
    public int getPositionY() {
        return this.robotPosition.y;
    }

    @Override
    public GridPoint2 getCheckPoint() {
        return this.checkPoint;
    }

    @Override
    public void setCheckPoint(int x, int y) {
        this.checkPoint = new GridPoint2(x, y);
    }
}