package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;

public class Robot implements IRobot {

    private Vector2 robotPosition;

    public Robot() {
        this.robotPosition = new Vector2(0, 0);
    }

    @Override
    public void setPosition(float x, float y) {
        this.robotPosition.x = x;
        this.robotPosition.y = y;
    }

    @Override
    public Vector2 getPosition() {
        return this.robotPosition;
    }

    @Override
    public int getPositionX() {
        return (int) this.robotPosition.x;
    }

    @Override
    public int getPositionY() {
        return (int) this.robotPosition.y;
    }

}