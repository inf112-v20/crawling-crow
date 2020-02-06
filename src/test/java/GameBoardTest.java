import Board.GameBoard;
import Board.IGameBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    private IGameBoard gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new GameBoard(10, 10);
    }

    @Test
    public void getBoardWidth() {
        assertEquals(10, gameBoard.getWidth());
    }

    @Test
    public void getBoardHeight() {
        assertEquals(10, gameBoard.getHeight());
    }

    @Test
    public void boardCheckIfNewBoardHasNoRobotTokens() {

    }

    @Test
    public void boardCheckRobotTokenPlaceOntoEmptyCell() {

    }
}