import Board.GameBoard;
import Board.IGameBoard;
import PlacableObjects.IPlaceableObject;
import Position.IPosition;
import Position.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    private IGameBoard gameBoard;
    int width = 12;
    int height = 12;



    @Before
    public void setUp() {
        gameBoard = new GameBoard(height, width);
    }

    @Test
    public void getBoardWidth() {
        assertEquals(width, gameBoard.getWidth());
    }

    @Test
    public void getBoardHeight() {
        assertEquals(height, gameBoard.getHeight());
    }

    @Test
    public void placePlaceableObjectOnBoard() {
        IPlaceableObject obj = gameBoard.getObj();
        IPosition oldPos = obj.getPos();
        IPosition newPos = new Position(1,1);
        if(oldPos.equals(newPos)){
            throw new IllegalArgumentException("The positions must be different for the test to work properly");
        }

        gameBoard.place(newPos);

        assertEquals(newPos, gameBoard.getObj().getPos());
    }
}