package roborally.gameview.robotview;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import roborally.gameview.layout.ILayers;
import roborally.gameview.layout.Layers;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;

public class RobotView implements IRobotView {

    private TiledMapTileLayer.Cell robotVictoryCellTexture;
    private TiledMapTileLayer.Cell robotDamageTakenCellTexture;
    private TextureRegion[][] robotTextureRegion;
    private TiledMapTileLayer.Cell robotDefaultCellTexture;
    private final ILayers layers;
    private final GridPoint2 pos;
    private final int height;
    private final int width;
    private  boolean virtualMode;
    private Direction virtualDirection;

    public RobotView(GridPoint2 pos) {
        this.pos = pos;
        this.layers = new Layers();
        this.height = layers.getHeight();
        this.width = layers.getWidth();
        this.virtualDirection = Direction.NORTH;
    }

    @Override
    public void setDefaultTexture(GridPoint2 pos) {
        if (this.robotDefaultCellTexture == null) {
            this.robotDefaultCellTexture = new TiledMapTileLayer.Cell();
            this.robotDefaultCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        layers.setRobotTexture(pos, this.robotDefaultCellTexture);
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
        if (layers.layerNotNull(LayerName.ROBOT, pos) && !virtualMode) {
            layers.setRobotTexture(pos, this.robotDamageTakenCellTexture);
        }
    }


    @Override
    public TiledMapTileLayer.Cell getTexture() {
        if (this.robotDefaultCellTexture == null) {
            this.robotDefaultCellTexture = new TiledMapTileLayer.Cell();
            this.robotDefaultCellTexture.setTile(new StaticTiledMapTile(robotTextureRegion[0][0]));
        }
        return robotDefaultCellTexture;
    }

    @Override
    public TextureRegion[][] getTextureRegion() {
        return robotTextureRegion;
    }

    @Override
    public void setTextureRegion(int robotID) {
        this.robotTextureRegion = TextureRegion.split(AssetManagerUtil.getRobotTexture(robotID), SettingsUtil.TILE_SIZE, SettingsUtil.TILE_SIZE);
        this.layers.setRobotTexture(this.pos, getTexture());
    }

    @Override
    public boolean canMoveRobot(GridPoint2 oldPos, GridPoint2 step) {
        GridPoint2 newPos = oldPos.cpy().add(step);
        if (isRobotInGraveyard(oldPos))
            return false;
        else if (!isRobotInGraveyard(newPos))
            layers.setRobotTexture(newPos, getTexture());
        if (!virtualMode)
            layers.setRobotTexture(oldPos, null);
        else { // Moves out of virtual mode
            virtualMode = false;
            this.setDirection(newPos, virtualDirection);
            this.virtualDirection = Direction.NORTH;
        }
        return true;
    }

    public boolean isRobotInGraveyard(GridPoint2 pos){
        return pos.x < 0 || pos.y < 0 || pos.y >= height || pos.x >= width;
    }

    @Override
    public void goToArchiveMarker(GridPoint2 pos, GridPoint2 archiveMarker) {
        if (!pos.equals(archiveMarker))
            layers.setRobotTexture(pos, null);
        if (!layers.layerNotNull(LayerName.ROBOT, archiveMarker)) {
            layers.setRobotTexture(archiveMarker, getTexture());
            layers.getRobotTexture(archiveMarker).setRotation(0);
        } else { // There's a robot on the archiveMarker already, this robot enters virtual mode.
            this.virtualMode = true;
        }
    }

    @Override
    public void setDirection(GridPoint2 pos, Direction direction) {
        if (layers.layerNotNull(LayerName.ROBOT, pos) && !virtualMode) {
            this.layers.getRobotTexture(pos).setRotation(direction.getID());
        } else if (virtualMode) { // Stores the new direction instead of updating it.
            this.virtualDirection = direction;
        }
    }
    @Override
    public boolean isVirtual() {
        return this.virtualMode;
    }
}
