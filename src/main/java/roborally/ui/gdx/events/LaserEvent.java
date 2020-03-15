package roborally.ui.gdx.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.objects.robot.RobotPresenter;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.TileName;

public class LaserEvent {
    private GridPoint2 laserPoint;
    private boolean laserEvent;
    private Image laserImage;
    private int factor;
    private int id;
    private boolean hitRobot;
    private RobotPresenter robot;
    public static final float unitScale = 300 * 3f / 16f;

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
                this.factor = -400;
            else
                this.factor = 400;
        } else {
            this.id = TileName.LASER_HORIZONTAL.getTileID();
            this.laserImage(this.id);
            if (origin.x > laserPoint.x)
                this.factor = -400;
            else
                this.factor = 400;
        }
        this.laserImage.setX(unitScale * origin.x);
        this.laserImage.setY(unitScale * origin.y);
        this.laserEvent = true;
    }

    public void drawLaserEvent(SpriteBatch batch, RobotPresenter[] robots) {
        if (this.id == TileName.LASER_HORIZONTAL.getTileID())
            drawLaserEventHorizontally(batch, robots);
        else
            drawLaserEventVertically(batch, robots);
    }

    public void drawLaserEventHorizontally(SpriteBatch batch, RobotPresenter[] robots) {
        for (RobotPresenter robot : robots)
            if(robot.getPos().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        this.laserImage.setX(this.laserImage.getX() + (Gdx.graphics.getDeltaTime() * factor));
        this.laserImage.draw(batch, 1);
        if (this.laserImage.getWidth() < 20) {
            hitRobot();
        }
        boolean whichWay;
        if (factor > 0)
            whichWay = this.laserImage.getX() >= (this.laserPoint.x) * unitScale;
        else {
            whichWay = this.laserImage.getX() <= (this.laserPoint.x + 0.7) * unitScale;
        }
        if (whichWay) {
            this.laserImage.setWidth(this.laserImage.getWidth() / 1.2f);
        }
    }

    public void drawLaserEventVertically(SpriteBatch batch, RobotPresenter[] robots) {
        for (RobotPresenter robot : robots)
            if(robot.getPos().equals(laserPoint)) {
                hitRobot = true;
                this.robot = robot;
            }
        this.laserImage.setY(this.laserImage.getY() + (Gdx.graphics.getDeltaTime() * factor));
        this.laserImage.draw(batch, 1);
        if (this.laserImage.getHeight() < 20) {
            hitRobot();
        }
        boolean whichWay;
        if (factor > 0)
            whichWay = this.laserImage.getY() >= (this.laserPoint.y) * unitScale;
        else
            whichWay = this.laserImage.getY() <= (this.laserPoint.y + 0.7) * unitScale;
        if (whichWay) {
            this.laserImage.setHeight(this.laserImage.getHeight() / 1.2f);
        }
    }

    private void hitRobot() {
        if(hitRobot) {
            this.robot.getModel().takeDamage(1);
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.ROBOT_HIT);
            sound.play(0.1f);
            System.out.println(this.robot.getModel().getHealth());
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
