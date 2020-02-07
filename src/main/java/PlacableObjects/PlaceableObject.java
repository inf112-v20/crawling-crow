package PlacableObjects;

import Position.IPosition;
import Position.Position;

public class PlaceableObject implements IPlaceableObject {
    private IPosition pos;

    public PlaceableObject() {
        pos = new Position(0,0);
    }

    public int getPosX() { return this.pos.getX(); }

    public int getPosY() { return this.pos.getY(); }

    @Override
    public IPosition getPos() {
        return this.pos;
    }

    public void setPosX(int x) { this.pos.setX(x); }

    public void setPosY(int y) { this.pos.setY(y); }

    @Override
    public void setPos(IPosition newPosition) {
        this.pos = newPosition;
    }
}
