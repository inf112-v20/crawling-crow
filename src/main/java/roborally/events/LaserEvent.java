package roborally.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.Robot;
import roborally.utilities.assets.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.assets.SoundAssets;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
/** This class creates an event that moves
 * the texture of a laser in a given direction.
 * The speed of the animation of the texture
 * is determined by {@link #factor}, which is
 * set to 800 in normal mode.
 */

public class LaserEvent {
    private static float tileEdge = 10; // To make the graphic not go too far out on the edges.
    private GridPoint2 laserPoint;
    private boolean laserEvent;
    private Image laserImage;
    private int factor;
    private int id;
    private boolean hitRobot;
    private Robot robot;
    private final Stage stage;

    public LaserEvent(int factor, Stage stage) {
        this.factor = factor;
        this.stage = stage;
    }

    public void laserImage(int id) {
        Image laserImage = new Image(AssetManagerUtil.getTileSets().getTile(id).getTextureRegion());
        laserImage.setSize(SettingsUtil.TILE_SCALE, SettingsUtil.TILE_SCALE);
        this.laserImage = laserImage;
    }

    /** Creates a new event that constructs an image of the laser cell relative to the direction the robots is facing.
     *
     * @param origin The position the robot is standing on.
     * @param laserPoint The position the laser is heading to.
     */
    public void laserEvent(GridPoint2 origin, GridPoint2 laserPoint) {
        float laserX = SettingsUtil.TILE_SCALE * origin.x + getXShift();
        float laserY = SettingsUtil.TILE_SCALE * origin.y + getYShift();
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
        if(this.id == TileName.LASER_HORIZONTAL.getTileID())
            laserY = SettingsUtil.TILE_SCALE*origin.y + getYShift();
        this.laserImage.setX(laserX);
        this.laserImage.setY(laserY);
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
        if (this.laserImage.getWidth() < tileEdge) {
            hitRobot(robots);
        }
        // Refactor points into list of endpoints.
        boolean negative = this.laserImage.getX() < (this.laserPoint.x)*SettingsUtil.TILE_SCALE + tileEdge + getXShift();
        boolean positive = this.laserImage.getX() > (this.laserPoint.x)*SettingsUtil.TILE_SCALE - tileEdge + getXShift();
        float oldWidth = this.laserImage.getWidth();
        float oldX = this.laserImage.getX();
        if (positive && factor > 0) {
            this.laserImage.setX(this.laserImage.getX() + dt/3);
            this.laserImage.setWidth(oldWidth - (this.laserImage.getX() - oldX));
        }
        if (negative && factor < 0) {
            float x = (this.laserImage.getX() - dt / 3);
            this.laserImage.setWidth(oldWidth + this.laserImage.getX() - x);
        } else if (!(positive && factor > 0))
            this.laserImage.setX(this.laserImage.getX() + dt);
        this.laserImage.draw(batch, 1);
    }

    public void drawLaserEventVertically(SpriteBatch batch, ArrayList<Robot> robots, float dt) {
        if (this.laserImage.getHeight() < tileEdge) {
            hitRobot(robots);
        }
        // Refactor points into list of endpoints.
        boolean negative = this.laserImage.getY() < (this.laserPoint.y)*SettingsUtil.TILE_SCALE + tileEdge + getYShift();
        boolean positive = this.laserImage.getY() > (this.laserPoint.y)*SettingsUtil.TILE_SCALE - tileEdge + getYShift();
        float oldHeight = this.laserImage.getHeight();
        float oldY = this.laserImage.getY();
        if (positive && factor > 0) {
            this.laserImage.setY(this.laserImage.getY() + dt / 3);
            this.laserImage.setHeight(oldHeight - (this.laserImage.getY() - oldY));
        }
        if (negative && factor < 0) {
            float y = this.laserImage.getY() - dt / 3;
            this.laserImage.setHeight(oldHeight + this.laserImage.getY() - y);
        } else if (!(positive && factor > 0))
            this.laserImage.setY(this.laserImage.getY() + dt);
        this.laserImage.draw(batch, 1);
    }

    // The robot that is hit takes damage, sound is played and event is stopped.
    private void hitRobot(ArrayList<Robot> robots) {

        for (Robot robot : robots)
            if (robot.getPosition().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        if (hitRobot) {
            this.robot.takeDamage(1);
            Sound sound = AssetManagerUtil.ASSET_MANAGER.get(SoundAssets.ROBOT_HIT);
            sound.play(0.035f * AssetManagerUtil.volume);
            hitRobot = false;
        }
        this.laserEvent = false;
    }

    /** Returns true if this laser is currently active (it has not reached its laserPoint). */
    public boolean hasLaserEvent() {
        return this.laserEvent;
    }

    /** Returns the robot (if there is one) standing on the laserPoint. */
    public Robot getRobot() {
        return this.robot;
    }

    private float getXShift() {
        return (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
    }

    private float getYShift() {
        return (stage.getHeight() - SettingsUtil.MAP_HEIGHT) / 2f;
    }
}
