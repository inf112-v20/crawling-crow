package roborally.ui.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import roborally.ui.gdx.UI;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.Direction;

public class RobotView implements IRobotView {

    private TiledMapTileLayer.Cell robotWonCellTexture;
    private TiledMapTileLayer.Cell robotLostCellTexture;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotDefaultCellTexture;
    private ILayers layers;
    private GridPoint2 pos;
    private int height;
    private int width;

    public RobotView(GridPoint2 pos) {
        this.pos = pos;
        this.layers = new Layers();
        this.height = layers.getHeight();
        this.width = layers.getWidth();
    }

    @Override
    public void setWinTexture(GridPoint2 pos) {
        if (this.robotWonCellTexture == null) {
            this.robotWonCellTexture = new TiledMapTileLayer.Cell();
            this.robotWonCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        layers.setRobotTexture(pos, this.robotWonCellTexture);

    }

    @Override
    public void setLostTexture(GridPoint2 pos) {
        if (this.robotLostCellTexture == null) {
            this.robotLostCellTexture = new TiledMapTileLayer.Cell();
            this.robotLostCellTexture.setTile(new StaticTiledMapTile(this.robotTextureRegion[0][1]));
        }
        if(layers.assertRobotNotNull(pos)) {
            layers.setRobotTexture(pos, this.robotLostCellTexture);
        }
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
    public TextureRegion[][] getTextureRegion() {
        return this.robotTextureRegion;
    }

    @Override
    public void setTextureRegion(int robotID) {
        this.robotTextureRegion = TextureRegion.split(AssetManagerUtil.getRobotTexture(robotID), UI.TILE_SIZE, UI.TILE_SIZE);
        layers.setRobotTexture(this.pos, getTexture());
    }

    @Override
    public boolean moveRobot(GridPoint2 oldPos, GridPoint2 step) {
        GridPoint2 newPos = oldPos.cpy().add(step);
        if (isPositionOnMap(newPos)) {
            layers.setRobotTexture(newPos, getTexture());
            layers.setRobotTexture(oldPos, null);
            return true;
        }
        return false;
    }

    private boolean isPositionOnMap(GridPoint2 pos){
        return pos.x >= 0 && pos.y >= 0 && pos.y < height && pos.x < width;
    }

    @Override
    public void goToCheckPoint(GridPoint2 pos, GridPoint2 checkPoint) {
        if (!pos.equals(checkPoint)) {
            layers.setRobotTexture(pos, null);
            }
        if(!layers.assertRobotNotNull(checkPoint)) { // Else starts the round virtual.
            layers.setRobotTexture(checkPoint, getTexture());
            layers.getRobotTexture(checkPoint).setRotation(0);
        }
    }

    @Override
    public void setDirection(GridPoint2 pos, Direction direction) {
        if (layers.assertRobotNotNull(pos))
            layers.getRobotTexture(pos).setRotation(direction.getDirectionID());
    }
}
