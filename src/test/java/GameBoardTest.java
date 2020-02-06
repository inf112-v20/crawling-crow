import Board.GameBoard;
import Board.IGameBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    private IGameBoard gameBoard;
    int width = 12;
    int height = 12;



    @Before
    public void setUp() throws Exception {
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
}