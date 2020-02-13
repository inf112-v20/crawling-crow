package roborally.game.objects;

import com.badlogic.gdx.math.Vector2;

public class Robot implements IRobot {
    private String name;

    private Vector2 robotPosition;

    public Robot(String name) {
        this.name = name;
        this.robotPosition = new Vector2(0, 0);
    }

    public String getName() {
        return this.name;
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