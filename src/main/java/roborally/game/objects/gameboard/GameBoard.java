package roborally.game.objects.gameboard;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.Flag;
import roborally.game.objects.IFlag;
import roborally.game.objects.robot.AI;
import roborally.ui.ILayers;
import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard implements IGameBoard {

    private ILayers layers;
    private HashMap<Integer, Integer> flagIdMap;

    public GameBoard(ILayers layers) {
        this.flagIdMap = new HashMap<>();
        this.flagIdMap.put(55, 1);
        this.flagIdMap.put(63, 2);
        this.flagIdMap.put(71, 3);
        this.layers = layers;
    }

    @Override
    public ArrayList<IFlag> findAllFlags() {
        ArrayList<IFlag> flags = new ArrayList<>();
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
}
