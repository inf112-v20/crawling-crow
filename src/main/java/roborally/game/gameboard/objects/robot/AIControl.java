package roborally.game.gameboard.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Queue;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.Grid;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;
import java.util.HashMap;

import static roborally.game.cards.IProgramCards.Card;

public class AIControl {
	private IRobotLogic robotLogic;
	private Direction logicDirection;
	private Grid grid;
	private int[] order;
	private int pickNr;
	private HashMap<String, Queue<Card>> cardTypes;
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

	private void organizeCards() {
		ArrayList<Card> temp = robotLogic.getCardsInHand();
		this.order = new int[Math.min(temp.size(), 5)];
		for (Card card : temp) {
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
	}

	private void chooseCards() {
		GridPoint2 hypoPos = robotLogic.getPosition().cpy();
		double distToFlag = hypoPos.cpy().dst(flag.getPosition());
		double hypoDistToFlag = hypoPos.cpy().add(logicDirection.getStep()).dst(flag.getPosition());
		boolean rotated = false;
		boolean rotateEmpty = false;
		while (!fullOrder()) {
			while (hypoDistToFlag > distToFlag && !rotateEmpty) {
				rotated = true;
				if (!rotate())
					rotateEmpty = true;
				if (fullOrder())
					break;
				hypoDistToFlag = nextHypoStep(hypoPos);
			}
			GridPoint2 newHypoPos = hypoPos.cpy();
			while (hypoDistToFlag < distToFlag && !cardTypes.get("move").isEmpty() && !fullOrder()) {
				Card move = cardTypes.get("move").removeFirst();
				for (int i = 0; i < move.getValue(); i++)
					distToFlag = nextStep(hypoPos);
				hypoDistToFlag = nextHypoStep(hypoPos);
				updateOrder(move);
			}
			if ((rotated && hypoPos.equals(newHypoPos) && !fullOrder()) ||
					((cardTypes.get("move").isEmpty() || rotateEmpty) && !fullOrder())) {
				if (!rotate() && !fullOrder()) {
					Card move = cardTypes.get("move").removeFirst();
					for (int i = 0; i < move.getValue(); i++)
						distToFlag = nextStep(hypoPos);
					hypoDistToFlag = nextHypoStep(hypoPos);
					updateOrder(move);
				}
			} else
				rotated = false;
		}
		newDistanceToFlag = distToFlag;
	}

	private void updateOrder(Card card) {
		order[pickNr++] = robotLogic.getCardsInHand().indexOf(card);
	}

	private double nextStep(GridPoint2 hypoPos){
		return hypoPos.add(logicDirection.getStep()).dst(flag.getPosition());
	}

	private double nextHypoStep(GridPoint2 hypoPos) {
		return hypoPos.cpy().add(logicDirection.getStep()).dst(flag.getPosition());
	}

	private boolean fullOrder() {
		return this.order.length == pickNr;
	}

	private boolean rotate() {
		if (!cardTypes.get("left").isEmpty()) {
			logicDirection = Direction.turnLeftFrom(logicDirection);
			updateOrder(cardTypes.get("left").removeFirst());
		} else if (!cardTypes.get("right").isEmpty()) {
			logicDirection = Direction.turnRightFrom(logicDirection);
			updateOrder(cardTypes.get("right").removeFirst());
		} else if (!cardTypes.get("uTurn").isEmpty()) {
			logicDirection = Direction.turnLeftFrom(logicDirection);
			logicDirection = Direction.turnLeftFrom(logicDirection);
			updateOrder(cardTypes.get("uTurn").removeFirst());
		} else
			return false;
		return true;
	}

	public double getNewDistanceToFlag() {
		return this.newDistanceToFlag;
	}

	public GridPoint2 getConveyorStep(GridPoint2 pos) {
		return null;
	}

}
