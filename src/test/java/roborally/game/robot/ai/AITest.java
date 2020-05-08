package roborally.game.robot.ai;

import org.junit.Before;
import org.junit.Test;
import roborally.game.cards.IProgramCards;
import roborally.game.cards.ProgramCards;
import roborally.game.gameboard.GameBoard;
import roborally.game.gameboard.objects.flag.IFlag;
import roborally.game.robot.RobotLogic;

import static org.junit.Assert.assertTrue;

public class AITest {
	private GameBoard gameBoard = new GameBoard("maps/Eight.tmx");
	private AI ai;
	private RobotLogic robotLogic;
	private IFlag flag;
	private IProgramCards programCards;

	@Before
	public void setUp() {
		robotLogic = new RobotLogic("TestAI");
		ai = new AI(gameBoard);
		programCards = new ProgramCards();
		flag = gameBoard.findAllFlags().get(0);
		robotLogic.setNumberOfFlags(gameBoard.findAllFlags().size());
	}

	@Test
	public void onAverageRobotIsCloserToNextFlagAfterAIControl() {
		int successfulTries = 0;
		for(int i = 0; i < 100; i++) {
			if(robotCloserToFlagAfterAIControl())
				successfulTries++;
		}
		assertTrue(successfulTries > 50);
	}

	public boolean robotCloserToFlagAfterAIControl() {
		double oldDistanceToFlag = robotLogic.getPosition().dst(flag.getPosition());
		robotLogic.drawCards(programCards);
		ai.controlRobot(robotLogic);
		double newDistanceToFlag = ai.getNewDistanceToFlag();
		return newDistanceToFlag < oldDistanceToFlag;
	}

}