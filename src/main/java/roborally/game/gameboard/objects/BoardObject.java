package roborally.game.gameboard.objects;

import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.enums.TileName;

public class BoardObject {
    private TileName type;
    private GridPoint2 position;

    public BoardObject(TileName type, GridPoint2 position) {
        this.position = position;
        this.type = type;
    }

    public GridPoint2 getPosition() {
        return this.position;
    }

    public TileName getType() {
        return type;
    }
}
