package roborally.objects;


import roborally.gameboard.GameBoard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import roborally.tools.AssMan;

public class Robot implements IRobot {

    private TiledMapTileLayer.Cell robotCell;
    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;
    private Vector2 robotPosition;
    private Texture robotTexture;
    private TextureRegion[][] robotTextureRegion;

    public Robot() {
        AssMan.load();
        AssMan.manager.finishLoading();
        this.robotTexture = AssMan.getRobotTexture();
        robotTextureRegion = TextureRegion.split(robotTexture, GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.robotPosition = new Vector2(0,0);
    }

    @Override
    public Texture getTexture() { return this.robotTexture;}

    @Override
    public TiledMapTileLayer.Cell getWinCell() {
        if (this.robotWonCell==null) {
            this.robotWonCell = new TiledMapTileLayer.Cell();
            this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        return this.robotWonCell;
    }

    @Override
    public TiledMapTileLayer.Cell getLostCell() {
        if (this.robotLostCell==null) {
            this.robotLostCell = new TiledMapTileLayer.Cell();
            this.robotLostCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][1]));
        }
        return this.robotLostCell;
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {
        if (this.robotCell==null) {
            this.robotCell = new TiledMapTileLayer.Cell();
            this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return this.robotCell;
    }

    @Override
    public void setPosition(float x, float y) {
        this.robotPosition.x = x; this.robotPosition.y = y;
    }

    @Override
    public Vector2 getPosition() {
        return this.robotPosition;
    }

    @Override
    public boolean moveRobot(int dx, int dy, TiledMapTileLayer robotLayer, TiledMapTileLayer.Cell cell) {
        robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
        robotPosition.x+=dx; robotPosition.y+=dy;
        robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, cell);

        return robotPosition.x >= 0 && robotPosition.y >= 0 &&
                robotPosition.x < robotLayer.getWidth()
                && robotPosition.y < robotLayer.getHeight();
    }
}