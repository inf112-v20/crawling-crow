package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import roborally.game.objects.IRobot;
import roborally.game.objects.Robot;
import roborally.tools.AssetsManager;
import roborally.ui.gameboard.UIGameBoard;

public class UIRobot extends Robot implements IUIRobot {

    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotCell;
    private IRobot robot;

    public UIRobot() {
        this.robot = new Robot();
        robotTextureRegion = TextureRegion.split(AssetsManager.getRobotTexture(), UIGameBoard.TILE_SIZE, UIGameBoard.TILE_SIZE);
    }

    @Override
    public TiledMapTileLayer.Cell getWinTexture() {
        if (this.robotWonCell == null) {
            this.robotWonCell = new TiledMapTileLayer.Cell();
            this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        return this.robotWonCell;
    }

    @Override
    public TiledMapTileLayer.Cell getLostTexture() {
        if (this.robotLostCell == null) {
            this.robotLostCell = new TiledMapTileLayer.Cell();
            this.robotLostCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][1]));
        }
        return this.robotLostCell;
    }

    @Override
    public TiledMapTileLayer.Cell getTexture() {
        if (this.robotCell == null) {
            this.robotCell = new TiledMapTileLayer.Cell();
            this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return this.robotCell;
    }

    @Override
    public IRobot getRobot() {
        return this.robot;
    }
}
