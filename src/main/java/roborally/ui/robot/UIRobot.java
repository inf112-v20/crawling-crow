package roborally.ui.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import roborally.game.objects.IRobot;
import roborally.tools.AssetManager;
import roborally.ui.gameboard.UIGameBoard;

public class UIRobot implements IUIRobot {

    private Texture robotTexture;
    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotCell;
    private IRobot robot;

    public UIRobot(IRobot robot) {
        this.robot = robot;
        this.robotTexture = AssetManager.getRobotTexture();
        robotTextureRegion = TextureRegion.split(robotTexture, UIGameBoard.TILE_SIZE, UIGameBoard.TILE_SIZE);
    }

    @Override
    public Texture getTexture() { return this.robotTexture;}

    @Override
    public TiledMapTileLayer.Cell getWinCell() {
        if (this.robotWonCell == null) {
            this.robotWonCell = new TiledMapTileLayer.Cell();
            this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        return this.robotWonCell;
    }

    @Override
    public TiledMapTileLayer.Cell getLostCell() {
        if (this.robotLostCell == null) {
            this.robotLostCell = new TiledMapTileLayer.Cell();
            this.robotLostCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][1]));
        }
        return this.robotLostCell;
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {
        if (this.robotCell == null) {
            this.robotCell = new TiledMapTileLayer.Cell();
            this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return this.robotCell;
    }
}
