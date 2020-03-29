package roborally.game.objects.gameboard;

import com.badlogic.gdx.math.GridPoint2;

public class RepairSite {
    private int id;
    private GridPoint2 position;

    RepairSite(GridPoint2 position) {
        //this.id = id;
        this.position = position;
    }

    public GridPoint2 getPosition() {
        return this.position;
    }

    public int getID() {
        return this.id;
    }
}
