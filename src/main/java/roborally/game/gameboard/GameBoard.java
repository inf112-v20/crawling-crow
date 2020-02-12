package roborally.game.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.RobotCore;
import roborally.ui.gameboard.Layers;

public class GameBoard implements IGameBoard {

    private Layers layers;
    private Vector2 pos;
    int x;
    int y;
    public GameBoard(Layers layers) {
        this.layers = layers;
        pos = new Vector2();
        int x = 0;
        int y = 0;
    }

    public void makePos(RobotCore robotCore) {
        this.pos = robotCore.getPosition();
        this.x = (int)pos.x;
        this.y = (int)pos.y;
    }

    @Override
    public boolean onFlag(RobotCore robotCore) {
        if(!layers.contains("Flag"))
            return false;
        makePos(robotCore);
        return layers.getFlag().getCell(x,y)!=null;
    }

    @Override
    public boolean onHole(RobotCore robotCore) {
        makePos(robotCore);
        if(layers.contains("bug"))
            if(layers.getBug().getCell(x,y)!=null)
                return true;
        if(!layers.contains("Hole"))
            return false;
        return layers.getHole().getCell(x,y)!=null;
    }

    @Override
    public boolean canMove(RobotCore robotCore) {
        return false;
    }
}
