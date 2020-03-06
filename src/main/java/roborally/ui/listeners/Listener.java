package roborally.ui.listeners;

import roborally.game.objects.laser.LaserRegister;
import roborally.ui.ILayers;

public class Listener {
    private WallListener wallListener;
    private CollisionListener collisionListener;
    private LaserListener laserListener;

    // Makes a new Listener to listen to the different listeners.
    public Listener(ILayers layers) {
        this.wallListener = new WallListener(layers);
        this.collisionListener = new CollisionListener(layers);
        this.laserListener = new LaserListener(layers);
    }

    public void listenWall(int x, int y, int dx, int dy) {
        wallListener.checkForWall(x, y, dx, dy);
    }

    public boolean listenCollision(int x, int y, int dx, int dy) {
        return collisionListener.checkIfBlocked(x, y, dx, dy);
    }

    public void listenLaser(int x, int y, String name, LaserRegister laserRegister) {
        this.laserListener.checkForLasers(x, y, name, laserRegister);
    }
}
