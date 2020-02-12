package roborally.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import roborally.ui.gameboard.Layers;

public class RobotCore {
    private TiledMapTileLayer core;
    private Layers layers;
    private int degrees;

    public RobotCore() {
        this.layers = new Layers();
        this.core = layers.getRobotCore();

    }

    // To handle bumping into other robots.
    public void crashControl() {

    }

    // Angle robot is facing. 0 = North, 90 = Right, 180 = South, 270 = West
    public int getDegrees() {
        return degrees;
    }

    // Rotates left or right.
    public void setDegrees(int rotate, char dir) {
        if (dir == 'R')
            degrees += rotate;
        else if (dir == 'L')
            degrees-= rotate;
        degrees = degrees%360;
    }
}
