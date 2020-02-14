package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.Layers;
import roborally.ui.gameboard.UIGameBoard;

public class UIRobot implements IUIRobot {

    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotCell;
    private Layers layers;

    // Creates new UIRobot in position i,j.
    public UIRobot(int i, int j) {
        this.layers = new Layers();
        this.robotTextureRegion = TextureRegion.split(AssetsManager.getRobotTexture(), UIGameBoard.TILE_SIZE, UIGameBoard.TILE_SIZE);
        this.layers.getRobots().setCell(i, j, getTexture());
    }

    // Creates new WinTexture at pos x,y.
    @Override
    public void getWinTexture(int x,int y) {
        if (this.robotWonCell == null) {
            this.robotWonCell = new TiledMapTileLayer.Cell();
            this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        this.layers.getRobots().setCell(x,y,this.robotWonCell);
    }

    // Creates new damageTaken/robotDestroyed texture at pos x,y.
    @Override
    public void getLostTexture(int x, int y) {
        if (this.robotLostCell == null) {
            this.robotLostCell = new TiledMapTileLayer.Cell();
            this.robotLostCell.setTile(new StaticTiledMapTile(this.robotTextureRegion[0][1]));
        }
        this.layers.getRobots().setCell(x, y, this.robotLostCell);
    }

    // Returns normal robot texture.
    @Override
    public TiledMapTileLayer.Cell getTexture() {
        if (this.robotCell == null) {
            this.robotCell = new TiledMapTileLayer.Cell();
            this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return this.robotCell;
    }

    // Tries to move robot, returns true if its made its move else false if its on the edge of the map.
    @Override
    public boolean moveRobot(int x, int y) {
        if ((x >= 0) && (y >= 0) && (x < this.layers.getRobots().getWidth()) && (y < this.layers.getRobots().getHeight())) {
            this.layers.getRobots().setCell(x, y, getTexture());
            return true;
        }
        return false;
    }
}
