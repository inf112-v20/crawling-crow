package roborally.game.objects.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.Flag;
import roborally.game.objects.robot.AI;
import roborally.ui.gameboard.Layers;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard implements IGameBoard {

    private Layers layers;
    int x;
    int y;
    private HashMap<Integer, Integer> flagIdMap;

    public GameBoard(Layers layers) {
        this.flagIdMap = new HashMap<>();
        this.flagIdMap.put(55, 1);
        this.flagIdMap.put(63, 2);
        this.flagIdMap.put(71, 3);
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
        this.x = (int)pos.x;
        this.y = (int)pos.y;
        if (this.onFlag(x, y)) {
            ai.setWinTexture();
            if(ai.getCurrFlagPos().x == pos.x && ai.getCurrFlagPos().y == pos.y)
                ai.setFlagPos();
        }
        if (this.onHole(x, y))
            ai.setLostTexture();
    }

    @Override
    public ArrayList<Flag> findAllFlags() {
        ArrayList<Flag> flags = new ArrayList<>();
        TiledMapTileLayer flagLayer = layers.getFlag();

        for (int i = 0; i < flagLayer.getWidth(); i++) {
            for (int j = 0; j < flagLayer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = flagLayer.getCell(i, j);
                if (cell != null) {
                    int flagId = flagIdMap.get(cell.getTile().getId());
                    flags.add(new Flag (flagId, new GridPoint2(i,j)));
                }
            }
        }
        return flags;
    }

    @Override
    public boolean canMove() {
        return false;
    }
}
