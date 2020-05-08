package roborally.game.gameboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
	private final GameBoard gameBoard = new GameBoard("maps/Eight.tmx");

	@Test
	public void hasFlags() {
		assertTrue(gameBoard.findAllFlags().size() > 0);
	}

	@Test
	public void hasRepairSites() {
		assertTrue(gameBoard.findAllRepairSites().size() > 0);
	}

	@Test
	public void hasCogs() {
		assertTrue(gameBoard.findAllCogs().size() > 0);
	}

	@Test
	public void hasNormalConveyorBelts() {
		assertTrue(gameBoard.findAllNormalConveyorBelts().size() > 0);
	}

	@Test
	public void hasExpressConveyorBelts() {
		assertTrue(gameBoard.findAllExpressConveyorBelts().size() > 0);
	}

	@Test
	public void hasPushers() {
		assertTrue(gameBoard.hasPushers());
	}

}