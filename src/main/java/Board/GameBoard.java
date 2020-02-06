package Board;

import PlacableObjects.IPlaceableObject;
import Position.IPosition;

public class GameBoard implements IGameBoard {
    private int width;
    private int height;

    // NOTE: (0,0) is top left corner
    public GameBoard(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void place(IPlaceableObject obj, IPosition pos) {

    }
}
