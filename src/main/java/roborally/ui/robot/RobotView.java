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

    private TiledMapTileLayer.Cell robotVictoryCellTexture;
    private TiledMapTileLayer.Cell robotDamageTakenCellTexture;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotDefaultCellTexture;
    private ILayers layers;
    private GridPoint2 pos;
    private int height;
    private int width;
    private  boolean graveyard;

    public RobotView(GridPoint2 pos) {
        this.pos = pos;
        this.layers = new Layers();
        this.height = layers.getHeight();
        this.width = layers.getWidth();
    }

    @Override
    public void setVictoryTexture(GridPoint2 pos) {
        if (this.robotVictoryCellTexture == null) {
            this.robotVictoryCellTexture = new TiledMapTileLayer.Cell();
            this.robotVictoryCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][2]));
        }
        layers.setRobotTexture(pos, this.robotVictoryCellTexture);

    }

    @Override
    public void setDamageTakenTexture(GridPoint2 pos) {
        if (this.robotDamageTakenCellTexture == null) {
            this.robotDamageTakenCellTexture = new TiledMapTileLayer.Cell();
            this.robotDamageTakenCellTexture.setTile(new StaticTiledMapTile(this.robotTextureRegion[0][1]));
        }
        if(layers.assertRobotNotNull(pos)) {
            layers.setRobotTexture(pos, this.robotDamageTakenCellTexture);
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
    public boolean canMoveRobot(GridPoint2 oldPos, GridPoint2 step) {
        GridPoint2 newPos = oldPos.cpy().add(step);
        if(isRobotInGraveyard(oldPos))
            return false;
        else if(!isRobotInGraveyard(newPos))
            layers.setRobotTexture(newPos, getTexture());
        layers.setRobotTexture(oldPos, null);
        return true;
    }

    public boolean isRobotInGraveyard(GridPoint2 pos){
        graveyard = pos.x < 0 || pos.y < 0 || pos.y >= height || pos.x >= width;
        return graveyard;
    }

    @Override
    public void goToArchiveMarker(GridPoint2 pos, GridPoint2 archiveMarker) {
        if (!pos.equals(archiveMarker))
            layers.setRobotTexture(pos, null);
        if(!layers.assertRobotNotNull(archiveMarker)) { // Else starts the round virtual.
            layers.setRobotTexture(archiveMarker, getTexture());
            layers.getRobotTexture(archiveMarker).setRotation(0);
        }
    }

    @Override
    public void setDirection(GridPoint2 pos, Direction direction) {
        if (layers.assertRobotNotNull(pos))
            layers.getRobotTexture(pos).setRotation(direction.getDirectionID());
    }
}
