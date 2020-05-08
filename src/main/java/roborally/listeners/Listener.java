package roborally.listeners;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.gameview.layout.ILayers;

public class Listener {
    private final WallListener wallListener;
    private final CollisionListener collisionListener;
    private final LaserListener laserListener;

    // Makes a new Listener to listen to the different listeners.
    public Listener(ILayers layers) {
        this.wallListener = new WallListener(layers);
        this.collisionListener = new CollisionListener(layers);
        this.laserListener = new LaserListener(layers);
    }

    public void listenMove(GridPoint2 pos, int dx, int dy, String name, LaserRegister laserRegister) {
        // Adding a move listener to listen to them all in one swoop.
    }

    public void listenWall(GridPoint2 pos, GridPoint2 move) {
        wallListener.checkForWall(pos, move);
    }

    public boolean listenCollision(GridPoint2 pos, GridPoint2 move) {
        return collisionListener.checkIfBlocked(pos, move);
    }

    public boolean listenLaser(GridPoint2 pos, String name, LaserRegister laserRegister) {
        return laserListener.checkForLasers(pos, name, laserRegister);
    }
}
