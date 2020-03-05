package roborally.game.objects.robot;

public class AI extends Robot {

    public AI(int x, int y, int cellId) {
        super(x, y, cellId);
        setTextureRegion(1);
    }

    public int runCore() {
        // Calculates a good move.
        return 123456789;
    }
}