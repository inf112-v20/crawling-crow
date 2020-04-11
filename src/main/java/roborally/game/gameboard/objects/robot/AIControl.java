package roborally.game.gameboard.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Queue;
import org.jetbrains.annotations.NotNull;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.IFlag;
import roborally.utilities.Grid;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.*;

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

	public AIControl(@NotNull IGameBoard gameBoard) {
		this.flags = gameBoard.getFlags();
		this.grid = gameBoard.getGrid();
	}

	public void controlRobot(@NotNull IRobotLogic robotLogic) {
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
			System.out.print(card.getCard() + " ");
			if (card.getValue() > -2 && card.getValue() < 4)
				cardTypes.get("move").addLast(card);
			else if (card.getValue() == 90)
				cardTypes.get("right").addLast(card);
			else if (card.getValue() < -90)
				cardTypes.get("uTurn").addLast(card);
			else if (card.getValue() < -1)
				cardTypes.get("left").addLast(card);
		}
		System.out.println();
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
			boolean rotatedNotMoved = rotated && hypoPos.equals(newHypoPos);
			boolean moveOrRotateEmpty = (movesIsEmpty() || rotateEmpty);
			rotated = forcedCard(rotatedNotMoved, moveOrRotateEmpty);
		}
	}

	private boolean rotate() {
		if (!fullOrder()) {
			if (!cardTypes.get("left").isEmpty() && hypoRotate("left"))
				updateOrder(cardTypes.get("left").removeFirst());
			else if (!cardTypes.get("right").isEmpty() && hypoRotate("right"))
				updateOrder(cardTypes.get("right").removeFirst());
			else if (!cardTypes.get("uTurn").isEmpty()) {
				turn("left");
				turn("left");
				updateOrder(cardTypes.get("uTurn").removeFirst());
			}
			else if (!cardTypes.get("left").isEmpty() || !cardTypes.get("right").isEmpty()) {
				if (cardTypes.get("left").size > cardTypes.get("right").size) {
					turn("left");
					updateOrder(cardTypes.get("left").removeFirst());
				}
				else {
					turn("right");
					updateOrder(cardTypes.get("right").removeFirst());
				}
			} else
				return false;
		}
		return true;
	}

	private void addMoveCard() {
		Card move = getNextMove();
		for (int i = 0; i < Math.abs(move.getValue()); i++)
			distToFlag = nextDist();
		hypoPos.add(tilesAtPos(hypoPos));
		distToFlag = hypoPos.dst(flag.getPosition());
		hypoDistToFlag = nextHypoDist();
		updateOrder(move);
	}

	private void findBestMoveCard() {
		ArrayList<Card> temp = new ArrayList<>();
		Map<Card, Double> bestCard = new HashMap<>();
		while (!cardTypes.get("move").isEmpty())
			temp.add(getNextMove());
		for (Card card : temp) {
			cardTypes.get("move").addFirst(card);
			bestCard.put(card, nextHypoDist());
			cardTypes.get("move").removeFirst();
		}
		Map.Entry<Card, Double> min = Collections.min(bestCard.entrySet(),
				Map.Entry.comparingByValue());
		cardTypes.get("move").addFirst(min.getKey());
		hypoDistToFlag = min.getValue();
		temp.remove(min.getKey());
		for (Card card : temp)
			cardTypes.get("move").addLast(card);
	}

	private boolean forcedCard(boolean rotatedNotMoved, boolean moveOrRotateEmpty) {
		boolean forcedMove = (rotatedNotMoved || moveOrRotateEmpty) && !fullOrder();
		if (forcedMove && !rotate()) {
			addMoveCard();
			return true;
		}
		return false;
	}

	private boolean closerToFlag() {
		if (cardTypes.get("move").size > 1)
			findBestMoveCard();
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

	private double nextDist() {
		return hypoPos.add(logicDirection.getStep()).dst(flag.getPosition());
	}

	private double nextHypoDist() {
		int val;
		GridPoint2 pos = hypoPos.cpy();
		if (!cardTypes.get("move").isEmpty())
			val = cardTypes.get("move").get(0).getValue();
		else
			val = 1;
		if (val == -1)
			pos.sub(logicDirection.getStep());
		else {
			for (int i = 0; i < val; i++)
				pos.add(logicDirection.getStep());
		}
		GridPoint2 gp2 = tilesAtPos(pos);
		pos.add(gp2);
		return pos.dst(flag.getPosition());
	}

	private boolean fullOrder() {
		return this.order.length == pickNr;
	}

	private void turn(String dir) {
		if ("left".equals(dir))
			logicDirection = Direction.turnLeftFrom(logicDirection);
		else if ("right".equals(dir))
			logicDirection = Direction.turnRightFrom(logicDirection);
	}

	private boolean hypoRotate(String dir) {
		Direction temp = logicDirection;
		turn(dir);
		if (closerToFlag())
			return true;
		logicDirection = temp;
		return false;
	}

	public double getNewDistanceToFlag() {
		return distToFlag;
	}

	// Move to advanced class
	public GridPoint2 updatePos(@NotNull TileName tileName) {
		String[] splitted;
		String tileString = tileName.toString();
		if (tileString.contains("JOIN_"))
			tileString = tileString.substring(tileString.indexOf("JOIN_"));
		else if (tileString.contains("TO_"))
			tileString = tileString.substring(tileString.indexOf("TO_"));
		splitted = tileString.split("_");
		Direction dir = Direction.valueOf(splitted[1]);
		return dir.getStep();
	}

	// Move to advanced class
	public GridPoint2 tilesAtPos(GridPoint2 pos) {
		GridPoint2 gp2 = new GridPoint2();
		Iterator<TileName> tiles = grid.getTilesAtPosition(pos);
		if (tiles == null)
			return gp2;
		while (tiles.hasNext()) {
			TileName tileName = tiles.next();
			if (grid.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(tileName)) {
				gp2.add(updatePos(tileName));
				gp2.add(updatePos(tileName));
			}
			if (grid.getGridLayer(LayerName.CONVEYOR).containsValue(tileName))
				gp2.add(updatePos(tileName));
			if (grid.getGridLayer(LayerName.COG).containsValue(tileName)) {
				if (tileName.toString().contains("COUNTER"))
					turn("right");
				else if (tileName.toString().contains("CLOCKWISE"))
					turn("left");
			}
		}
		return gp2;
	}
}
