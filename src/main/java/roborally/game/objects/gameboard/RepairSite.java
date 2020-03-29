package roborally.game.objects.gameboard;

import com.badlogic.gdx.math.GridPoint2;

public class RepairSite {
    private GridPoint2 position;

    RepairSite(GridPoint2 position) {
        this.position = position;
    }

    public GridPoint2 getPosition() {
        return this.position;
    }
}
