package roborally.game.objects;


import roborally.ui.gameboard.GameBoard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import roborally.ui.gameboard.Layers;
import roborally.tools.AssMan;

public class Robot implements IRobot {

    private Vector2 robotPosition;

    public Robot() {
        this.robotPosition = new Vector2(0,0);
    }

    @Override
    public void setPosition(float x, float y) {
        this.robotPosition.x = x;
        this.robotPosition.y = y;
    }

    @Override
    public int getPositionX() {
        return (int)this.robotPosition.x;
    }

    @Override
    public int getPositionY() {
        return (int)this.robotPosition.y;
    }

    // TODO: Implement robot move, not sure how yet
    /*
    @Override
    public boolean moveRobot(int dx, int dy) {
        layers.getRobot().setCell(this.getPositionX(), this.getPositionY(), null);
        this.setPosition(this.getPositionX()+dx,this.getPositionY()+dy);
        layers.getRobot().setCell(this.getPositionX(), this.getPositionY(), getCell());
        return this.getPositionX() >= 0 && this.getPositionY() >= 0
                && this.getPositionX() < layers.getRobot().getWidth()
                && this.getPositionY() < layers.getRobot().getHeight();
    }
    */

}