package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.Layers;
import roborally.ui.gameboard.UI;

public class UIRobot implements IUIRobot {

    private TiledMapTileLayer.Cell robotWonCellTexture;
    private TiledMapTileLayer.Cell robotLostCellTexture;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotDefaultCellTexture;
    private Layers layers;
    private int uiRobotPosX;
    private int uiRobotPosY;

    // Creates new UIRobot in position x and y.
    public UIRobot(int x, int y) {
        this.uiRobotPosX = x;
        this.uiRobotPosY = y;
        this.layers = new Layers();
    }

    public void setTextureRegion(int cellId) {
        this.robotTextureRegion = TextureRegion.split(AssetsManager.getRobotTexture(cellId), UI.TILE_SIZE, UI.TILE_SIZE);
        this.layers.getRobots().setCell(this.uiRobotPosX, this.uiRobotPosY, getTexture());
    }

    @Override
    public void getWinTexture(int x,int y) {
        if (this.robotWonCellTexture == null) {
            this.robotWonCellTexture = new TiledMapTileLayer.Cell();
            this.robotWonCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        this.layers.getRobots().setCell(x,y,this.robotWonCellTexture);
    }

    @Override
    public void getLostTexture(int x, int y) {
        if (this.robotLostCellTexture == null) {
            this.robotLostCellTexture = new TiledMapTileLayer.Cell();
            this.robotLostCellTexture.setTile(new StaticTiledMapTile(this.robotTextureRegion[0][1]));
        }
        this.layers.getRobots().setCell(x, y, this.robotLostCellTexture);
    }

    @Override
    public TiledMapTileLayer.Cell getTexture() {
        if (this.robotDefaultCellTexture == null) {
            this.robotDefaultCellTexture = new TiledMapTileLayer.Cell();
            this.robotDefaultCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return this.robotDefaultCellTexture;
    }

    @Override
    public boolean moveRobot(int x, int y) {
        if ((x >= 0) && (y >= 0) && (x < this.layers.getRobots().getWidth()) && (y < this.layers.getRobots().getHeight())) {
            this.layers.getRobots().setCell(x, y, getTexture());
            return true;
        }
        return false;
    }
}
