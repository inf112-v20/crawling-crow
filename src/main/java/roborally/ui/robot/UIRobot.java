package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import roborally.tools.AssetsManager;
import roborally.tools.Direction;
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
    private int height, width;

    // Creates new UIRobot in position x and y.
    public UIRobot(int x, int y) {
        this.uiRobotPosX = x;
        this.uiRobotPosY = y;
        this.layers = new Layers();
        this.height = layers.getRobots().getHeight();
        this.width = layers.getRobots().getWidth();
    }

    public void setTextureRegion(int cellId) {
        this.robotTextureRegion = TextureRegion.split(AssetsManager.getRobotTexture(cellId), UI.TILE_SIZE, UI.TILE_SIZE);
        this.layers.getRobots().setCell(this.uiRobotPosX, this.uiRobotPosY, getTexture());
    }

    @Override
    public void setWinTexture(int x, int y) {
        if (this.robotWonCellTexture == null) {
            this.robotWonCellTexture = new TiledMapTileLayer.Cell();
            this.robotWonCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        this.layers.getRobots().setCell(x, y, this.robotWonCellTexture);
    }

    @Override
    public void setLostTexture(int x, int y) {
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
    public boolean moveRobot(int x, int y, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if ((newX >= 0) && (newY >= 0) && (newX < width) && (newY < height)) {
            this.layers.getRobots().setCell(newX, newY, getTexture());
            this.layers.getRobots().setCell(x, y,null);
            return true;
        }
        return false;
    }

    @Override
    public void setDirection(int x, int y, Direction direction) {
        System.out.println("ui position " + uiRobotPosX + " " + uiRobotPosY);
        layers.getRobots().getCell(x, y).setRotation(direction.getDirectionId());

    }
}
