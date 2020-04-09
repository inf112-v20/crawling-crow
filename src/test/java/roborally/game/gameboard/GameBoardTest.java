package roborally.game.gameboard;

import org.junit.Test;
import roborally.game.cards.IProgramCards;
import roborally.game.cards.ProgramCards;
import roborally.game.gameboard.objects.robot.AIControl;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.game.gameboard.objects.robot.RobotLogic;

import java.util.Arrays;

import static org.junit.Assert.*;

public class GameBoardTest {
	private GameBoard gameBoard = new GameBoard("maps/newmap.tmx");
	AIControl aiControl = new AIControl(gameBoard);
	RobotLogic robotLogic = new RobotLogic("test");

	@Test
	public void verifyRobotsHypotheticalPositionIsCloserToFlagAfterRoundWithAIControl() {
		robotLogic.setNumberOfFlags(gameBoard.getFlags().size());
		double oldDistanceToFlag = robotLogic.getPosition().dst(gameBoard.getFlags().get(0).getPosition());
		IProgramCards deckOfProgramCards = new ProgramCards();
		robotLogic.drawCards(deckOfProgramCards);
		aiControl.controlRobot(robotLogic);
		assertTrue(oldDistanceToFlag > aiControl.getNewDistanceToFlag());
	}

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