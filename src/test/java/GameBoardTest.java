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
    public void boardSizeXAxis() {
        assertEquals(10, gameBoard.getX());
    }

    @Test
    public void boardSizeYAxis() {
        assertEquals(10, gameBoard.getY());
    }

    @Test
    public void boardCheckIfNewBoardHasNoRobotTokens() {

    }

    @Test
    public void boardCheckRobotTokenPlaceOntoEmptyCell() {

    }
}