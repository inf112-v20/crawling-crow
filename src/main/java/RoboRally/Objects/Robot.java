package RoboRally.Objects;

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

    public Robot(TiledMap tiledMap) {
        robotLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        Texture robotTexture = new Texture("player.png");
        TextureRegion[][] robotTextureRegion = TextureRegion.split(robotTexture, 300, 300);
        this.robotWonCell = new TiledMapTileLayer.Cell();
        this.robotLostCell = new TiledMapTileLayer.Cell();
        this.robotCell = new TiledMapTileLayer.Cell();
        this.robotWonCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        this.robotLostCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][1]));
        this.robotCell.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        this.robotPosition = new Vector2(0,0);
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

    public Vector2 setPosition(Vector2 pos) {
        return this.robotPosition = pos;
    }

    public Vector2 getPosition() {
        return this.robotPosition;
    }

    public TiledMapTileLayer getLayer() {
        return this.robotLayer;
    }
}