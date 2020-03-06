package roborally.ui.listeners;

import roborally.ui.ILayers;

public class Listener {
    private ILayers layers;
    private WallListener wallListener;
    private CollisionListener collisionListener;
    private LaserListener laserListener;
    public Listener(ILayers layers) {
        this.layers = layers;
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

    public void listenLaser(int x, int y, String name) {
        this.laserListener.checkForLasers(x, y, name);
    }
}
