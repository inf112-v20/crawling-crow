package roborally.game.objects;

import com.badlogic.gdx.math.GridPoint2;

public class Flag implements IFlag{
    private int id;
    private GridPoint2 pos;

    public Flag(int id, GridPoint2 pos) {
        this.id = id;
        this.pos = pos;
    }

    public GridPoint2 getPos() {
        return pos;
    }

    public int getId() {
        return id;
    }
}
