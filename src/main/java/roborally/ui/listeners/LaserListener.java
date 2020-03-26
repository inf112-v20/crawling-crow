package roborally.ui.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.laser.LaserRegister;
import roborally.ui.ILayers;
public class LaserListener {
    private ILayers layers;

    public LaserListener(ILayers layers) {
        this.layers = layers;
    }

    /**
     * Checks the laser the robot has stepped in against the register to update, remove or add the laser.
     *
     * @param name          The name of the robot
     * @param laserRegister the register.
     */
    public boolean checkForLasers(GridPoint2 pos, String name, LaserRegister laserRegister) {
        int id;
        if (layers.assertLaserNotNull(pos)) {
            id = layers.getLaserID(pos);
            laserRegister.createLaser(id, pos, name);
            return true;
        } else
            laserRegister.updateLaser(name, pos);
        return false;
    }
}
