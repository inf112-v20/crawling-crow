package RoboRally.Objects;


import RoboRally.GameBoard.GameBoard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Robot implements IRobot {

    private TiledMapTileLayer.Cell robotCell;
    private TiledMapTileLayer.Cell robotWonCell;
    private TiledMapTileLayer.Cell robotLostCell;
    private Vector2 robotPosition;
    private TiledMapTileLayer robotLayer;
    private Texture robotTexture;

    public Robot(TiledMap tiledMap) {
        robotLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        this.robotTexture = new Texture("player.png");
        TextureRegion[][] robotTextureRegion = TextureRegion.split(robotTexture, GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.robotWonCell = new TiledMapTileLayer.Cell();
        this.robotLostCell = new TiledMapTileLayer.Cell();
        this.robotCell = new TiledMapTileLayer.Cell();
        this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        this.robotLostCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][1]));
        this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        this.robotPosition = new Vector2(0,0);
    }
    public Texture getTexture() {
        return this.robotTexture;
    }

    public TiledMapTileLayer.Cell getWonCell() {
        return this.robotWonCell;
    }

    public TiledMapTileLayer.Cell getLostCell() {
        return this.robotLostCell;
    }

    public TiledMapTileLayer.Cell getCell() {
        return this.robotCell;
    }

    public void setPosition(Vector2 pos) {
        this.robotPosition = pos;
    }

    public Vector2 getPosition() {
        return this.robotPosition;
    }

    public TiledMapTileLayer getLayer() {
        return this.robotLayer;
    }

    public boolean moveRobot(int x, int y, int dx, int dy, TiledMapTileLayer.Cell cell) {
        robotLayer.setCell((int) robotPosition.x, (int) robotPosition.y, null);
        robotPosition.x+=dx; robotPosition.y+=dy;
        robotLayer.setCell( (int)robotPosition.x, (int)robotPosition.y, cell);

        return robotPosition.x >= 0 && robotPosition.y >= 0 &&
                robotPosition.x < robotLayer.getWidth() &&
                robotPosition.y < robotLayer.getHeight();
    }
}