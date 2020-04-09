package roborally.game.gameboard.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Queue;
import roborally.game.cards.ProgramCards;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.Grid;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
import java.util.HashMap;

public class AIControl {
	private IRobotLogic robotLogic;
	private Direction logicDirection;
	private Grid grid;
	private int[] order;
	private int pickNr;
	private HashMap<String, Queue<ProgramCards.Card>> cardTypes;
	private ArrayList<IFlag> flags;
	private IFlag flag;
	private double newDistanceToFlag;

	public AIControl(IGameBoard gameBoard) {
		this.flags = gameBoard.getFlags();
		this.grid = gameBoard.getGrid();
	}

	public void controlRobot(IRobotLogic robotLogic) {
		this.robotLogic = robotLogic;
		this.logicDirection = robotLogic.getDirection();
		this.pickNr = 0;
		for (IFlag flag : flags) {
			if (flag.getID() == robotLogic.getNextFlag())
				this.flag = flag;
		}
		cardTypes = new HashMap<>();
		cardTypes.put("move", new Queue<>());
		cardTypes.put("left", new Queue<>());
		cardTypes.put("right", new Queue<>());
		cardTypes.put("uTurn", new Queue<>());
		organizeCards();
	}

	public int[] getOrder() {
		return this.order;
	}

	private ArrayList<ProgramCards.Card> organizeCards() {
		ArrayList<ProgramCards.Card> temp = robotLogic.getCardsInHand();
		this.order = new int[Math.min(temp.size(), 5)];
		for (ProgramCards.Card card : temp) {
			if (card.getValue() > -2 && card.getValue() < 4)
				cardTypes.get("move").addLast(card);
			else if (card.getValue() == 90)
				cardTypes.get("right").addLast(card);
			else if (card.getValue() < -90)
				cardTypes.get("uTurn").addLast(card);
			else if (card.getValue() < -1)
				cardTypes.get("left").addLast(card);
		}
		chooseCards();
		return temp;
	}

	private void chooseCards() {
		GridPoint2 hypoPos = robotLogic.getPosition().cpy();
		double distToFlag = hypoPos.cpy().dst(flag.getPosition());
		double hypoDistToFlag = hypoPos.cpy().add(logicDirection.getStep()).dst(flag.getPosition());
		boolean rotated = false;
		boolean rotateEmpty = false;
		while (pickNr < order.length) {
			while (hypoDistToFlag > distToFlag && !rotateEmpty) {
				rotated = true;
				if (rotate())
					rotateEmpty = true;
				if (pickNr == order.length)
					break;
				hypoDistToFlag = hypoPos.cpy().add(logicDirection.getStep()).dst(flag.getPosition());
			}
			GridPoint2 newHypoPos = hypoPos.cpy();
			while (hypoDistToFlag < distToFlag && !cardTypes.get("move").isEmpty() && pickNr < order.length) {
				ProgramCards.Card move = cardTypes.get("move").removeFirst();
				for (int i = 0; i < move.getValue(); i++)
					distToFlag = hypoPos.add(logicDirection.getStep()).dst(flag.getPosition());
				hypoDistToFlag = hypoPos.cpy().add(logicDirection.getStep()).dst(flag.getPosition());
				order[pickNr++] = robotLogic.getCardsInHand().indexOf(move);
			}
			if ((rotated && hypoPos.equals(newHypoPos) && pickNr < order.length) ||
					((cardTypes.get("move").isEmpty() || rotateEmpty) && pickNr < order.length)) {
				if (rotate() && pickNr < order.length) {
					ProgramCards.Card move = cardTypes.get("move").removeFirst();
					for (int i = 0; i < move.getValue(); i++)
						distToFlag = hypoPos.add(logicDirection.getStep()).dst(flag.getPosition());
					order[pickNr++] = robotLogic.getCardsInHand().indexOf(move);
					hypoDistToFlag = hypoPos.cpy().add(logicDirection.getStep()).dst(flag.getPosition());
				}
			} else
				rotated = false;
		}
		newDistanceToFlag = distToFlag;
	}

	private boolean rotate() {
		ArrayList<ProgramCards.Card> cardsInHand = robotLogic.getCardsInHand();
		if (!cardTypes.get("left").isEmpty()) {
			logicDirection = Direction.turnLeftFrom(logicDirection);
			order[pickNr++] = cardsInHand.indexOf(cardTypes.get("left").removeFirst());
		} else if (!cardTypes.get("right").isEmpty()) {
			logicDirection = Direction.turnRightFrom(logicDirection);
			order[pickNr++] = cardsInHand.indexOf(cardTypes.get("right").removeFirst());
		} else if (!cardTypes.get("uTurn").isEmpty()) {
			logicDirection = Direction.turnLeftFrom(logicDirection);
			logicDirection = Direction.turnLeftFrom(logicDirection);
			order[pickNr++] = cardsInHand.indexOf(cardTypes.get("uTurn").removeFirst());
		} else
			return true;
		return false;
	}

	public double getNewDistanceToFlag() {
		return this.newDistanceToFlag;
	}

	public GridPoint2 getConveyorStep(GridPoint2 pos) {
		return null;
	}

}
