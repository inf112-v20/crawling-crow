package roborally.ui.gdx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.IGame;

import java.util.ArrayList;

public class Events {
    private boolean waitEvent;
    private int pauseCount;
    private float dt;
    private boolean robotFadeOrder;
    private ArrayList<Image> fadeableRobots;

    public Events() {
        this.waitEvent = false;
        this.dt = 0f;
        this.pauseCount = 1;
        this.robotFadeOrder = false;
        this.fadeableRobots = new ArrayList<>();
    }

    public void waitMoveEvent(float dt, IGame game) {
        this.dt += dt;
        if (this.dt >= 0.35) {
            game.playNextCard();
            this.dt = 0;
            this.pauseCount++;
        }
        if (pauseCount >= 5) {
            this.dt = 0f;
            this.pauseCount = 1;
            setPauseEvent(false);
        }
    }

    public boolean hasWaitEvent() {
        return waitEvent;
    }

    public void setPauseEvent(boolean value) {
        this.waitEvent = value;
    }

    public void fadeRobot(GridPoint2 pos, TextureRegion[][] texture) {
        Image image = new Image(texture[0][0]);
        image.setX(pos.x * 56.5f);
        image.setY(pos.y * 56.5f);
        image.setSize(55, 55);
        this.fadeableRobots.add(image);
        System.out.println(image.getX());
        System.out.println(image.getY());
    }

    public boolean getFadeRobot() {
        return this.robotFadeOrder;
    }

    public void setFadeRobot(boolean value) {
        this.robotFadeOrder = value;
    }

    public ArrayList<Image> getFadeableRobot() {
        return this.fadeableRobots;
    }

}
