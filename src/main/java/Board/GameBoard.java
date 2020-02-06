package Board;

import PlacableObjects.IPlaceableObject;
import PlacableObjects.PlaceableObject;
import Position.IPosition;

public class GameBoard implements IGameBoard {
    private int width;
    private int height;
    private IPlaceableObject robot;

    // NOTE: (0,0) is top left corner
    public GameBoard(int height, int width) {
        this.height = height;
        this.width = width;
        this.robot = new PlaceableObject();
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
    public void place(IPosition pos) {
        this.robot.setPos(pos);
    }

    @Override
    public IPlaceableObject getObj() {
        return this.robot;
    }
}
