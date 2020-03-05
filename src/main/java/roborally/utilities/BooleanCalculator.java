package roborally.utilities;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.laser.Cannon;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import java.util.HashMap;

// Beep... Robots need to calculate.

// TODO: @Thomas: Suspect all this code is deprecate now (05.04.20, 16:19). this have be carefully assessed.
// TODO: I do not have time safely go through it all in this session. Enjoy!

public class BooleanCalculator {
    private HashMap<String, Boolean> operations;
    public ILayers layers;
    private int aFlagPosX;
    private int aFlagPosY;

    public BooleanCalculator() {
        layers = new Layers();
        // Advanced calculations for AI, can take multiple conditions to figure out a good move.
        operations = new HashMap<>();
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
        this.aFlagPosX = (int) flagPos.x;
        this.aFlagPosY = (int) flagPos.y;
    }

    public Vector2 getCurrFlagPos() {
        return new Vector2(this.aFlagPosX,this.aFlagPosY);
    }

    private boolean isBelowFlagOnMap(int y){
        return y < this.aFlagPosY;
    }

    private boolean isAboveFlagOnMap(int y){
        return y > this.aFlagPosY;
    }

    private boolean isToTheRightOfFlagOnMap(int x){
        return x > this.aFlagPosX;
    }

    private boolean isToTheLeftOfFlagOnMap(int x){
        return x < this.aFlagPosX;
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