package roborally.ui.gameboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void sizeOfGameBoard() {
        assertEquals(GameBoard.TILE_SIZE, 300);
    }
}