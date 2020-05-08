package roborally.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.gameview.layout.ILayers;
import roborally.utilities.enums.LayerName;

public class LaserListener {
    private final ILayers layers;

    public LaserListener(ILayers layers) {
        this.layers = layers;
    }

    /**
     * Checks the laser the robot has stepped in against the register to update, remove or add the laser.
     *
     * @param pos GridPoint2 position of the robot.
     * @param name          The name of the robot.
     * @param laserRegister the register.
     * @return true if it is a laser @pos.
     */
    public boolean checkForLasers(GridPoint2 pos, String name, LaserRegister laserRegister) {
        int id;
        if (layers.layerNotNull(LayerName.LASER, pos)) {
            id = layers.getLayerID(LayerName.LASER, pos);
            laserRegister.createLaser(id, pos, name);
            return true;
        } else
            laserRegister.updateLaser(name, pos);
        return false;
    }
}
