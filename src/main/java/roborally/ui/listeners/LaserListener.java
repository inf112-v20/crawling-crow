package roborally.ui.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.laser.Cannon;
import roborally.ui.ILayers;

public class LaserListener {
    ILayers layers;

    public LaserListener(ILayers layers) {
        this.layers = layers;
    }

    /** Creates a new laser instance if there is a laser cell in the position the robot is moving to.
     *  Else it will see if the robot is currently in a laser instance.
     * @param x The x-coordinate the robot is moving to
     * @param y The y-coordinate the robot is moving to
     * @param name The name of the robot
     */
    public void checkForLasers(int x, int y, String name) {
        GridPoint2 pos = new GridPoint2(x, y);
        int id;
        Cannon cannon = new Cannon();
        if (layers.assertLaserNotNull(x, y)) {
            id = layers.getLaserID(x, y);
            cannon.createLaser(id, pos, name);
        }
        else
            cannon.updateLaser(name, pos);
    }
}
