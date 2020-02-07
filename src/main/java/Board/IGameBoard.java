package Board;

import PlacableObjects.IPlaceableObject;
import Position.IPosition;

public interface IGameBoard {
    int getWidth();

    int getHeight();

    void place(IPosition pos);

    IPlaceableObject getObj();
}
