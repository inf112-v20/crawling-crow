package roborally.game.gameboard.objects.flag;

import com.badlogic.gdx.math.GridPoint2;

public class Flag implements IFlag {
    private final int id;
    private final GridPoint2 position;

    public Flag(int id, GridPoint2 position) {
        this.id = id;
        this.position = position;
    }

    @Override
    public GridPoint2 getPosition() {
        return position;
    }

    @Override
    public int getID() {
        return id;
    }
}
