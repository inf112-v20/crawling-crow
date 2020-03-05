package roborally.utilities;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.laser.Cannon;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import java.util.HashMap;

// Beep... Robots need to calculate.
public class BooleanCalculator {
    private HashMap<String, Boolean> operations;
    public ILayers layers;
    private int x;
    private int y;
    private int height;
    private int width;
    private Cannon cannon;

    public BooleanCalculator() {
        layers = new Layers();
        width = layers.getRobots().getWidth();
        height = layers.getRobots().getHeight();
        // Advanced calculations for AI, can take multiple conditions to figure out a good move.
        operations = new HashMap<>();
        cannon = new Cannon();
    }

    public Cannon getCannon() {
        return this.cannon;
    }

    // Check a specific position if it is blocked
    public boolean isBlocked(int x, int y) {
        return layers.assertRobotNotNull(x, y);
    }

    public boolean isOnHole(int x, int y) {
        return layers.assertHoleNotNull(x, y);
    }

    // AI methods
    public void determineFlagPos(Vector2 flagPos) {
        this.x = (int) flagPos.x;
        this.y = (int) flagPos.y;
    }

    public Vector2 getCurrFlagPos() {
        return new Vector2(this.x,this.y);
    }

    private boolean isBelowFlagOnMap(int y){
        return y < this.y;
    }

    private boolean isAboveFlagOnMap(int y){
        return y > this.y;
    }

    private boolean isToTheRightOfFlagOnMap(int x){
        return x > this.x;
    }

    private boolean isToTheLeftOfFlagOnMap(int x){
        return x < this.x;
    }

    public HashMap<String, Boolean> getOperations() {
        return this.operations;
    }

    public void loadAICalc(int x, int y) {
        operations.put("Left", isToTheLeftOfFlagOnMap(x));
        operations.put("Right", isToTheRightOfFlagOnMap(x));
        operations.put("Up", isAboveFlagOnMap(y));
        operations.put("Down", isBelowFlagOnMap(y));
    }
}