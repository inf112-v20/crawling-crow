package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import roborally.tools.AssetManagerUtil;
import roborally.tools.Direction;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import roborally.ui.UI;

public class RobotUI implements IRobotUI {

    private TiledMapTileLayer.Cell robotWonCellTexture;
    private TiledMapTileLayer.Cell robotLostCellTexture;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotDefaultCellTexture;
    private ILayers layers;
    private int uiRobotPosX;
    private int uiRobotPosY;
    private int height;
    private int width;

    // Creates new UIRobot in position x and y.
    public RobotUI(int x, int y) {
        this.uiRobotPosX = x;
        this.uiRobotPosY = y;
        this.layers = new Layers();
        this.height = layers.getRobots().getHeight();
        this.width = layers.getRobots().getWidth();
    }

    public void setTextureRegion(int cellId) {
        this.robotTextureRegion = TextureRegion.split(AssetManagerUtil.getRobotTexture(cellId), UI.TILE_SIZE, UI.TILE_SIZE);
        layers.setRobotCell(this.uiRobotPosX, this.uiRobotPosY, getTexture());
    }

    @Override
    public void setWinTexture(int x, int y) {
        if (this.robotWonCellTexture == null) {
            this.robotWonCellTexture = new TiledMapTileLayer.Cell();
            this.robotWonCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        layers.setRobotCell(x, y, this.robotWonCellTexture);
    }

    @Override
    public void setLostTexture(int x, int y) {
        if (this.robotLostCellTexture == null) {
            this.robotLostCellTexture = new TiledMapTileLayer.Cell();
            this.robotLostCellTexture.setTile(new StaticTiledMapTile(this.robotTextureRegion[0][1]));
        }
        layers.setRobotCell(x, y, this.robotLostCellTexture);
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
    public boolean moveRobot(int x, int y, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if ((newX >= 0) && (newY >= 0) && (newX < width) && (newY < height)) {
            layers.setRobotCell(newX, newY, getTexture());
            layers.setRobotCell(x, y,null);
            return true;
        }
        return false;
    }

    @Override
    public void setDirection(int x, int y, Direction direction) {
        if (layers.assertRobotNotNull(x, y))
            layers.getRobotCell(x, y).setRotation(direction.getDirectionId());
    }
}
