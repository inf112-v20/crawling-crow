package roborally.ui.gdx.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.objects.robot.Robot;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;

public class LaserEvent {
    public static final float unitScale = 300 * 3f / 16f;
    private GridPoint2 laserPoint;
    private boolean laserEvent;
    private Image laserImage;
    private int factor;
    private int id;
    private boolean hitRobot;
    private Robot robot;

    public LaserEvent(int factor) {
        this.factor = factor;
    }

    public void laserImage(int id) {
        Image laserImage = new Image(AssetManagerUtil.getTileSets().getTile(id).getTextureRegion());
        laserImage.setSize(unitScale, unitScale);
        laserImage.setColor(Color.MAGENTA);
        this.laserImage = laserImage;
    }

    public void laserEvent(GridPoint2 origin, GridPoint2 laserPoint) {
        this.laserPoint = laserPoint;
        if (laserPoint.y != origin.y) {
            this.id = TileName.LASER_VERTICAL.getTileID();
            this.laserImage(this.id);
            if (origin.y > laserPoint.y)
                this.factor = -this.factor;
        } else {
            this.id = TileName.LASER_HORIZONTAL.getTileID();
            this.laserImage(this.id);
            if (origin.x > laserPoint.x)
                this.factor = -this.factor;
        }
        this.laserImage.setX(unitScale * origin.x);
        this.laserImage.setY(unitScale * origin.y);
        this.laserEvent = true;
    }

    /**
     * Draws an image through the batch from UI.
     *
     * @param batch  the SpriteBatch from UI.
     * @param robots The list of robots that's playing the game.
     */
    public void drawLaserEvent(SpriteBatch batch, ArrayList<Robot> robots) {
        float dt = (Gdx.graphics.getDeltaTime() * factor);
        if (this.id == TileName.LASER_HORIZONTAL.getTileID())
            drawLaserEventHorizontally(batch, robots, dt);
        else
            drawLaserEventVertically(batch, robots, dt);
    }

    public void drawLaserEventHorizontally(SpriteBatch batch, ArrayList<Robot> robots, float dt) {
        for (Robot robot : robots)
            if (robot.getPosition().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        if (this.laserImage.getWidth() < 10) {
            hitRobot();
        }
        boolean negative = this.laserImage.getX() <= (this.laserPoint.x) * unitScale;
        boolean positive = this.laserImage.getX() >= (this.laserPoint.x) * unitScale;
        float oldWidth = this.laserImage.getWidth();
        float oldX = this.laserImage.getX();
        if (positive && factor > 0) {
            this.laserImage.setX(this.laserImage.getX() + dt);
            this.laserImage.setWidth(oldWidth - (this.laserImage.getX() - oldX));
        }
        if (negative && factor < 0) {
            this.laserImage.setX(this.laserImage.getX() - dt);
            this.laserImage.setWidth(oldWidth + (oldX - this.laserImage.getX()));
        } else if (!(positive && factor > 0))
            this.laserImage.setX(this.laserImage.getX() + (Gdx.graphics.getDeltaTime() * factor));
        this.laserImage.draw(batch, 1);
    }

    public void drawLaserEventVertically(SpriteBatch batch, ArrayList<Robot> robots, float dt) {
        for (Robot robot : robots)
            if (robot.getPosition().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        if (this.laserImage.getHeight() < 10) {
            hitRobot();
        }
        boolean negative = this.laserImage.getY() <= (this.laserPoint.y) * unitScale;
        boolean positive = this.laserImage.getY() >= (this.laserPoint.y) * unitScale;
        float oldHeight = this.laserImage.getHeight();
        float oldY = this.laserImage.getY();
        if (positive && factor > 0) {
            this.laserImage.setY(this.laserImage.getY() + dt);
            this.laserImage.setHeight(oldHeight - (this.laserImage.getY() - oldY));
        }
        if (negative && factor < 0) {
            this.laserImage.setY(this.laserImage.getY() - dt);
            this.laserImage.setHeight(oldHeight + (oldY - this.laserImage.getY()));
        } else if (!(positive && factor > 0))
            this.laserImage.setY(this.laserImage.getY() + (Gdx.graphics.getDeltaTime() * factor));
        this.laserImage.draw(batch, 1);
    }

    // The robot that is hit takes damage, sound is played and event is stopped.
    private void hitRobot() {
        if (hitRobot) {
            this.robot.getLogic().takeDamage(1);
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.ROBOT_HIT);
            sound.play(0.05f * AssetManagerUtil.volume);
            hitRobot = false;
        }
        this.laserEvent = false;
    }

    public boolean hasLaserEvent() {
        return this.laserEvent;
    }

    public void setLaserSpeed(int factor) {
        this.factor = factor;
    }
}
