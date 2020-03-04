package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;

public class RobotState implements IRobotState {
    private String name;
    private GridPoint2 robotPosition;

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
}