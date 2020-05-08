package roborally.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.gameview.layout.ILayers;

public class Listener {
    private final CollisionListener collisionListener;
    private final LaserListener laserListener;

    // Makes a new Listener to listen to the different listeners.
    public Listener(ILayers layers) {
        this.collisionListener = new CollisionListener(layers);
        this.laserListener = new LaserListener(layers);
    }

    public boolean listenCollision(GridPoint2 pos, GridPoint2 move) {
        return collisionListener.checkIfBlocked(pos, move);
    }

    public boolean listenLaser(GridPoint2 pos, String name, LaserRegister laserRegister) {
        return laserListener.checkForLasers(pos, name, laserRegister);
    }
}
