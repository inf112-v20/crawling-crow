package roborally.ui.gdx.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private ArrayList<Alpha> fadeableRobots;
    private int fadeCounter;
    private ArrayList<LaserEvent> laserEvent;

    public Events() {
        this.waitEvent = false;
        this.dt = 0f;
        this.pauseCount = 1;
        this.robotFadeOrder = false;
        this.fadeableRobots = new ArrayList<>();
        this.fadeCounter = 0;
        this.laserEvent = new ArrayList<>();

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
        image.setX(pos.x * (300 * 3f / 16f));
        image.setY(pos.y * (300 * 3f / 16f));
        image.setSize((300 * 3f / 16f), (300 * 3f / 16f));
        this.fadeableRobots.add(new Alpha(1f, image));
    }

    public boolean getFadeRobot() {
        return this.robotFadeOrder;
    }

    public void setFadeRobot(boolean value) {
        this.robotFadeOrder = value;
    }

    public void fadeRobots(SpriteBatch batch) {

        for (Alpha alpha : this.fadeableRobots) {
            alpha.getImage().draw(batch, alpha.update(Gdx.graphics.getDeltaTime()));
            if (alpha.dt <= 0)
                fadeCounter++;
        }

        if (fadeCounter == this.fadeableRobots.size()) {
            this.fadeableRobots.clear();
            setFadeRobot(false);
        }
        fadeCounter = 0;
    }


    public boolean hasLaserEvent() {
        return !this.laserEvent.isEmpty();
    }

    public ArrayList<LaserEvent> getLaserEvent() {
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 0; i < this.laserEvent.size(); i++) {
            if(!this.laserEvent.get(i).hasLaserEvent())
                temp.add(i);
        }
        for (int i : temp)
            this.laserEvent.remove(i);
        return this.laserEvent;
    }

    public void createNewLaserEvent(GridPoint2 origin, GridPoint2 pos) {
        this.laserEvent.add(new LaserEvent());
        this.laserEvent.get(this.laserEvent.size()-1).laserEvent(origin, pos);
    }

    private static class Alpha {
        private float dt;
        private Image image;

        private Alpha(float dt, Image image) {
            this.dt = dt;
            this.image = image;
        }

        private Image getImage() {
            return this.image;
        }

        private float update(float dt) {
            this.dt -= (0.5f * dt);
            return this.dt;
        }
    }

}
