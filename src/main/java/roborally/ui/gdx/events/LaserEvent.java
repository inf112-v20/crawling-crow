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
        if (this.id == TileName.LASER_HORIZONTAL.getTileID())
            drawLaserEventHorizontally(batch, robots);
        else
            drawLaserEventVertically(batch, robots);
    }

    public void drawLaserEventHorizontally(SpriteBatch batch, ArrayList<Robot> robots) {
        for (Robot robot : robots)
            if (robot.getPosition().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        this.laserImage.setX(this.laserImage.getX() + (Gdx.graphics.getDeltaTime() * factor));
        this.laserImage.draw(batch, 1);
        if (this.laserImage.getWidth() < 5) {
            hitRobot();
        }
        boolean whichWay;
        if (factor > 0)
            whichWay = this.laserImage.getX() >= (this.laserPoint.x) * unitScale;
        else {
            whichWay = this.laserImage.getX() <= (this.laserPoint.x + 0.2 / Math.abs((200f/factor))) * unitScale;
        }
        if (whichWay) {
            this.laserImage.setX(this.laserImage.getX() - (Gdx.graphics.getDeltaTime() * factor / 1.4f));
            this.laserImage.setWidth(this.laserImage.getWidth() - this.laserImage.getWidth() / 4.5f);
        }
    }

    public void drawLaserEventVertically(SpriteBatch batch, ArrayList<Robot> robots) {
        for (Robot robot : robots)
            if (robot.getPosition().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        this.laserImage.setY(this.laserImage.getY() + (Gdx.graphics.getDeltaTime() * factor));
        this.laserImage.draw(batch, 1);
        if (this.laserImage.getHeight() < 5) {
            hitRobot();
        }
        boolean whichWay;
        if (factor > 0)
            whichWay = this.laserImage.getY() >= (this.laserPoint.y) * unitScale;
        else
            whichWay = this.laserImage.getY() <= (this.laserPoint.y + 0.2 / Math.abs((200f/factor))) * unitScale;
        if (whichWay) {
            System.out.println(this.laserImage.getHeight());
            this.laserImage.setY(this.laserImage.getY() - (Gdx.graphics.getDeltaTime() * factor / 1.5f));
            this.laserImage.setHeight(this.laserImage.getHeight() - this.laserImage.getHeight() / 4.5f);
        }
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
