package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.Direction;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import roborally.ui.UI;

public class RobotView implements IRobotView {

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
    public RobotView(int x, int y) {
        this.uiRobotPosX = x;
        this.uiRobotPosY = y;
        this.layers = new Layers();
        this.height = layers.getHeight();
        this.width = layers.getWidth();
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
    public void goToCheckPoint(GridPoint2 pos, GridPoint2 checkPoint) {
        layers.setRobotCell(checkPoint.x, checkPoint.y, getTexture());
        layers.getRobotCell(checkPoint.x, checkPoint.y).setRotation(0);
        if(!pos.equals(checkPoint))
            layers.setRobotCell(pos.x, pos.y, null);
    }

    @Override
    public void setDirection(GridPoint2 pos, Direction direction) {
        if (layers.assertRobotNotNull(pos.x, pos.y))
            layers.getRobotCell(pos.x, pos.y).setRotation(direction.getDirectionID());
    }
}
