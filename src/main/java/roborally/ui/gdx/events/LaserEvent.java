package roborally.ui.gdx.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.utilities.AssetManagerUtil;

public class LaserEvent {
    private GridPoint2 laserPoint;
    private boolean laserEvent;
    private Image laserImage;
    private int factor;
    private int id;

    public LaserEvent() {
    }

    public void laserImage(int id) {
        Image laserImage = new Image(AssetManagerUtil.getTileSets().getTile(id).getTextureRegion());
        laserImage.setSize((300 * 3f / 16f), (300 * 3f / 16f));
        this.laserImage = laserImage;
    }

    public void laserEvent(GridPoint2 origin, GridPoint2 laserPoint) {
        this.laserPoint = laserPoint;
        if (laserPoint.y != origin.y) {
            this.id = 47;
            this.laserImage(47);
            if (origin.y > laserPoint.y)
                this.factor = -300;
            else
                this.factor = 300;
        } else {
            this.id = 39;
            this.laserImage(39);
            if (origin.x > laserPoint.x)
                this.factor = -300;
            else
                this.factor = 300;
        }
        this.laserImage.setX((300 * 3f / 16f) * origin.x);
        this.laserImage.setY((300 * 3f / 16f) * origin.y);
        this.laserEvent = true;
    }

    public void drawLaserEvent(SpriteBatch batch) {
        if (this.id == 39)
            drawLaserEventHorizontally(batch);
        else
            drawLaserEventVertically(batch);
    }

    public void drawLaserEventHorizontally(SpriteBatch batch) {
        this.laserImage.setX(this.laserImage.getX() + (Gdx.graphics.getDeltaTime() * factor));
        batch.begin();
        this.laserImage.draw(batch, 1);
        batch.end();
        if (this.laserImage.getWidth() < 15)
            this.laserEvent = false;
        boolean whichWay;
        if (factor > 0)
            whichWay = this.laserImage.getX() >= (this.laserPoint.x - 1) * (300 * 3f / 16f);
        else
            whichWay = this.laserImage.getX() <= (this.laserPoint.x + 1.75) * (300 * 3f / 16f);
        if (whichWay) {
            float f;
            if (factor > 0)
                f = this.laserImage.getWidth() - this.laserImage.getWidth() / 1.1f;
            else
                f = this.laserImage.getWidth() / 1.1f - this.laserImage.getWidth();
            this.laserImage.setWidth(this.laserImage.getWidth() / 1.1f);
            this.laserImage.setX(this.laserImage.getX() + f);
        }
    }

    public void drawLaserEventVertically(SpriteBatch batch) {
        this.laserImage.setY(this.laserImage.getY() + (Gdx.graphics.getDeltaTime() * factor));
        batch.begin();
        this.laserImage.draw(batch, 1);
        batch.end();
        if (this.laserImage.getHeight() < 15)
            this.laserEvent = false;
        boolean whichway;
        if (factor > 0)
            whichway = this.laserImage.getY() >= (this.laserPoint.y - 1) * (300 * 3f / 16f);
        else
            whichway = this.laserImage.getY() <= (this.laserPoint.y + 1.75) * (300 * 3f / 16f);
        if (whichway) {
            float f;
            if (factor > 0)
                f = this.laserImage.getHeight() - this.laserImage.getHeight() / 1.1f;
            else
                f = this.laserImage.getHeight() / 1.1f - this.laserImage.getHeight();
            this.laserImage.setHeight(this.laserImage.getHeight() / 1.1f);
            this.laserImage.setY(this.laserImage.getY() + f);
        }
    }

    public boolean hasLaserEvent() {
        return this.laserEvent;
    }
}
