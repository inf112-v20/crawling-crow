package roborally.game.objects.gameboard;

import com.badlogic.gdx.math.GridPoint2;

public class Flag implements IFlag {
    private int id;
    private GridPoint2 pos;

    public Flag(int id, GridPoint2 pos) {
        this.id = id;
        this.pos = pos;
    }

    @Override
    public GridPoint2 getPosition() {
        return pos;
    }

    @Override
    public int getID() {
        return id;
    }
}
