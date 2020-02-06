package PlacableObjects;

import Position.IPosition;

public interface IPlaceableObject {

    int getPosX();
    int getPosY();
    void setPosX(int x);
    void setPosY(int y);
    void setPos(IPosition newPosition);

    IPosition getPos();

}
