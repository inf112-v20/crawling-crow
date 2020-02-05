import Board.GameBoard;
import Board.IGameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    @Test
    public void boardSizeXAxis() {
        IGameBoard board = new GameBoard(10, 10);
        assertEquals(10, board.getX());
    }

    @Test
    public void boardSizeYAxis() {
        IGameBoard board = new GameBoard(10, 10);
        assertEquals(10, board.getY());
    }

    @Test
    public void boardCheckIfNewBoardHasNoRobotTokens() {

    }

    @Test
    public void boardCheckRobotTokenPlaceOntoEmptyCell() {

    }
}