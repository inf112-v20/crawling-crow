package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.IRobot;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.Layers;
import roborally.ui.gameboard.UIGameBoard;

public class UIRobot implements IUIRobot {

    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotCell;
    private Layers layers;

    public UIRobot(int i, int j) {
        this.layers = new Layers();
        this.robotTextureRegion = TextureRegion.split(AssetsManager.getRobotTexture(), UIGameBoard.TILE_SIZE, UIGameBoard.TILE_SIZE);
        this.layers.getRobots().setCell(i, j, getTexture());
    }

    @Override
    public void getWinTexture(int x,int y) {
        if (this.robotWonCell == null) {
            this.robotWonCell = new TiledMapTileLayer.Cell();
            this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        this.layers.getRobots().setCell(x,y,this.robotWonCell);
    }

    @Override
    public void getLostTexture(int x, int y) {
        if (this.robotLostCell == null) {
            this.robotLostCell = new TiledMapTileLayer.Cell();
            this.robotLostCell.setTile(new StaticTiledMapTile(this.robotTextureRegion[0][1]));
        }
        this.layers.getRobots().setCell(x, y, this.robotLostCell);
    }

    @Override
    public TiledMapTileLayer.Cell getTexture() {
        if (this.robotCell == null) {
            this.robotCell = new TiledMapTileLayer.Cell();
            this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return this.robotCell;
    }

    public boolean moveRobot(int x, int y) {
        if ((x >= 0) && (y >= 0) && (x < this.layers.getRobots().getWidth()) && (y < this.layers.getRobots().getHeight())) {
            this.layers.getRobots().setCell(x, y, getTexture());
            return true;
        }
        return false;
    }
}
