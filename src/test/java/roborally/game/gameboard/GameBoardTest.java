package roborally.game.gameboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
	private GameBoard gameBoard = new GameBoard("maps/riskyExchangeBeginnerWithStartAreaVertical.tmx");

	@Test
	public void hasFlags() {
		assertTrue(gameBoard.findAllFlags().size() > 0);
	}

	@Test
	public void hasRepairSites() {
		assertTrue(gameBoard.findAllRepairSites().size() > 0);
	}
}