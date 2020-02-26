package roborally.game.objects.robot;

import com.badlogic.gdx.math.Vector2;
import roborally.tools.AssetsManager;
import roborally.tools.BooleanCalculator;
import roborally.ui.gameboard.Layers;

import java.util.HashMap;
import java.util.Stack;

import static com.badlogic.gdx.math.MathUtils.random;

public class AI extends Robot {
    private BooleanCalculator booleanCalculator;
    private HashMap<String, Boolean> ops;
    private Stack<Vector2> flagPositions;
    private Layers layers;

    public AI(int x, int y, int cellId) {
        super(x, y, cellId);
        setTextureRegion(1);
        this.booleanCalculator = new BooleanCalculator();
        this.flagPositions = AssetsManager.makeFlagPos();
        this.layers = new Layers();
        getCalc().determineFlagPos(this.flagPositions.pop());
    }

    @Override
    public BooleanCalculator getCalc() {
        return this.booleanCalculator;
    }

    public int runCore() {
        int x = (int) getPosition().x;
        int y = (int) getPosition().y;
        booleanCalculator.loadAICalc(x, y);
        ops = booleanCalculator.getOperations();
        int[] rndMove = {29, 32, 47, 51};
        int r = random.nextInt(4);

        if (ops.get("Down")) {
            if (getCalc().isBlocked(x, y + 1)) {
                return rndMove[r];
            }
            else if (layers.assertHoleNotNull(x,y+1))
                return 47;
            return 51;
        }
        if (ops.get("Up")) {
            if (getCalc().isBlocked(x, y - 1)) {
                return rndMove[r];
            }
            else if (layers.assertHoleNotNull(x, y - 1))
                return 51;
            return 47;
        }
        if (ops.get("Left")) {
            if (getCalc().isBlocked(x + 1, y)) {
                return rndMove[r];
            }
            else if (layers.assertHoleNotNull(x + 1 , y))
                return 29;
            return 32;
        }
        if (ops.get("Right")) {
            if (getCalc().isBlocked(x - 1 , y)) {
                return rndMove[r];
            }
            else if (layers.assertHoleNotNull(x + -1 , y))
                return 32;
        return 29;
        }
        return 0;
    }
    public void setFlagPos() {
        if (this.flagPositions.isEmpty())
            this.flagPositions = AssetsManager.makeFlagPos();
        else
            getCalc().determineFlagPos(this.flagPositions.pop());
    }

    public Vector2 getCurrFlagPos() {
        return getCalc().getCurrFlagPos();
    }

}
