package roborally.game.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.AI;
import roborally.ui.gameboard.Layers;

public class GameBoard implements IGameBoard {

    private Layers layers;
    int x; int y;
    public GameBoard(Layers layers) {
        this.layers = layers;
    }

    @Override
    public boolean onFlag(int x, int y) {
        if(!layers.contains("Flag"))
            return false;
        return layers.getFlag().getCell(x, y)!=null;
    }

    @Override
    public boolean onHole(int x, int y) {
        if(layers.contains("bug"))
            if(layers.getBug().getCell(x, y)!=null)
                return true;
        if(!layers.contains("Hole"))
            return false;
        return layers.getHole().getCell(x, y)!=null;
    }

    public void getCheckPoint(Vector2 pos, AI ai) {
        this.x = (int)pos.x; this.y = (int)pos.y;
        if(this.onFlag(x, y)) {
            ai.getWinCell();
            ai.setFlagPos();
        }
        if(this.onHole(x, y))
            ai.getLoseCell();
    }

    @Override
    public boolean canMove() {
        return false;
    }
}
