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
	private GridPoint2 hypoPos;
	private double distToFlag;
	private double hypoDistToFlag;

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
		hypoPos = robotLogic.getPosition().cpy();
		distToFlag = hypoPos.cpy().dst(flag.getPosition());
		hypoDistToFlag = nextHypoDist();
		boolean rotated = false;
		boolean rotateEmpty = false;
		while (!fullOrder()) {
			while (!closerToFlag() && !rotateEmpty) {
				rotated = true;
				if (!rotate())
					rotateEmpty = true;
				if (fullOrder())
					break;
				hypoDistToFlag = nextHypoDist();
			}
			GridPoint2 newHypoPos = hypoPos.cpy();
			while (closerToFlag() && !movesIsEmpty() && !fullOrder())
				addMoveCard();
			boolean rotatedNotMoved = rotated && hypoPos.equals(newHypoPos) && !fullOrder();
			boolean moveOrRotateEmpty = (movesIsEmpty() || rotateEmpty) && !fullOrder();
			rotated = addNextCard(rotatedNotMoved, moveOrRotateEmpty);
		}
	}

	private void addMoveCard() {
		Card move = getNextMove();
		for (int i = 0; i < move.getValue(); i++)
			distToFlag = nextDist();
		hypoDistToFlag = nextHypoDist();
		updateOrder(move);
	}

	private boolean addNextCard(boolean rotatedNotMoved, boolean moveOrRotateEmpty) {
		if ((rotatedNotMoved || moveOrRotateEmpty) && (!rotate() && !fullOrder())) {
			addMoveCard();
			return true;
		}
		return false;
	}

	private boolean closerToFlag() {
		return hypoDistToFlag < distToFlag;
	}

	private Card getNextMove() {
		return cardTypes.get("move").removeFirst();
	}

	private boolean movesIsEmpty() {
		return cardTypes.get("move").isEmpty();
	}

	private void updateOrder(Card card) {
		order[pickNr++] = robotLogic.getCardsInHand().indexOf(card);
	}

	private double nextDist(){
		return hypoPos.add(logicDirection.getStep()).dst(flag.getPosition());
	}

	private double nextHypoDist() {
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
		return distToFlag;
	}

	public GridPoint2 getConveyorStep(GridPoint2 pos) {
		return null;
	}

}
