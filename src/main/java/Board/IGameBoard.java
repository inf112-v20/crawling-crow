package Board;

import PlacableObjects.IPlaceableObject;
import Position.IPosition;

public interface IGameBoard {
    int getWidth();

    int getHeight();

    void place(IPlaceableObject obj, IPosition pos);
}
