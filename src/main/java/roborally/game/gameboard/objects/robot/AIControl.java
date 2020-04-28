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
		ArrayList<Card> robotsCards = robotLogic.getCardsInHand();
		this.order = new int[Math.min(robotsCards.size(), 5)];
		for (Card card : robotsCards) {
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
		hypoDistToFlag = nextHypoDistToFlag();
		boolean rotateEmpty = false;

		while (!fullOrder()) {
			while (!closerToFlag() && !rotateEmpty) {
				if (!rotate())
					rotateEmpty = true;
				if (fullOrder())
					break;
				hypoDistToFlag = nextHypoDistToFlag();
			}
			while (closerToFlag() && hasCard("move") && !fullOrder())
				addMoveCard();
			boolean moveOrRotateEmpty = (!hasCard("move") || rotateEmpty);
			if(moveOrRotateEmpty && !fullOrder())
				if(!rotate())
					addMoveCard();
		}
	}

	private boolean rotate() {
		GridPoint2 oldHypoPos = hypoPos.cpy();
		Direction oldDirection = logicDirection;
		hypoPos.add(tilesAtPos(hypoPos));
		distToFlag = hypoPos.dst(flag.getPosition());
		hypoDistToFlag = nextHypoDistToFlag();

		if (!fullOrder()) {
			if (goodRotation("left"))
				updateOrder(getNextCard("left"));
			else if (goodRotation("right"))
				updateOrder(getNextCard("right"));
			else if (goodRotation("uTurn")) {
				turn("left");
				turn("left");
				updateOrder(getNextCard("uTurn"));
			}
			else if (hasCard("left") || hasCard("right")) {
				if (cardTypes.get("left").size > cardTypes.get("right").size) {
					turn("left");
					updateOrder(getNextCard("left"));
				}
				else {
					turn("right");
					updateOrder(getNextCard("right"));
				}
			} else {
				hypoPos = oldHypoPos;
				logicDirection = oldDirection;
				return false;
			}
		}
		return true;
	}

	private boolean goodRotation(String dir) {
		boolean goodRotation = hasCard(dir);
		if (goodRotation && (dir.equals("left") || dir.equals("right")))
			goodRotation = rotationPointsToFlag(dir);
		return goodRotation;
	}

	private boolean rotationPointsToFlag(String dir) {
		Direction temp = logicDirection;
		turn(dir);
		if (closerToFlag())
			return true;
		logicDirection = temp;
		return false;
	}

	private boolean hasCard (String cardType) {
		return !cardTypes.get(cardType).isEmpty();
	}

	private void addMoveCard() {
		Card move = getNextCard("move");
		for (int i = 0; i < Math.abs(move.getValue()); i++)
			distToFlag = nextDistToFlag();
		hypoPos.add(tilesAtPos(hypoPos));
		closerToFlag();
		distToFlag = hypoPos.dst(flag.getPosition());
		hypoDistToFlag = nextHypoDistToFlag();
		updateOrder(move);
	}

	private boolean closerToFlag() {
		if (cardTypes.get("move").size > 1)
			findBestMoveCard();
		return hypoDistToFlag < distToFlag;
	}

	private void findBestMoveCard() {
		ArrayList<Card> tempCards = new ArrayList<>();
		Map<Card, Double> bestCard = new HashMap<>();
		while (!cardTypes.get("move").isEmpty())
			tempCards.add(getNextCard("move"));
		for (Card card : tempCards) {
			cardTypes.get("move").addFirst(card);
			bestCard.put(card, nextHypoDistToFlag());
			cardTypes.get("move").removeFirst();
		}
		Map.Entry<Card, Double> min = Collections.min(bestCard.entrySet(),
				Map.Entry.comparingByValue());
		cardTypes.get("move").addFirst(min.getKey());
		hypoDistToFlag = min.getValue();
		tempCards.remove(min.getKey());
		for (Card card : tempCards)
			cardTypes.get("move").addLast(card);
	}

	private Card getNextCard(String type) {
		return cardTypes.get(type).removeFirst();
	}

	private void updateOrder(Card card) {
		order[pickNr++] = robotLogic.getCardsInHand().indexOf(card);
	}

	private double nextDistToFlag() {
		return hypoPos.add(logicDirection.getStep()).dst(flag.getPosition());
	}

	private double nextHypoDistToFlag() {
		int steps;
		GridPoint2 hypotheticalPosition = hypoPos.cpy();
		if (!cardTypes.get("move").isEmpty())
			steps = cardTypes.get("move").get(0).getValue();
		else
			steps = 1;
		if (steps == -1)
			hypotheticalPosition.sub(logicDirection.getStep());
		else {
			for (int i = 0; i < steps; i++)
				hypotheticalPosition.add(logicDirection.getStep());
		}
		GridPoint2 stepsFromTiles = tilesAtPos(hypotheticalPosition);
		hypotheticalPosition.add(stepsFromTiles);
		return hypotheticalPosition.dst(flag.getPosition());
	}

	private boolean fullOrder() {
		return this.order.length == pickNr;
	}

	public double getNewDistanceToFlag() {
		return distToFlag;
	}

	// Move to advanced class
	public GridPoint2 tilesAtPos(GridPoint2 pos) {
		GridPoint2 stepsFromTiles = new GridPoint2();
		Iterator<TileName> tiles = grid.getTilesAtPosition(pos);
		if (tiles == null)
			return stepsFromTiles;
		while (tiles.hasNext()) {
			TileName tileName = tiles.next();
			if (grid.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(tileName)) {
				stepsFromTiles.add(updatePos(tileName));
				stepsFromTiles.add(updatePos(tileName));
			}
			if (grid.getGridLayer(LayerName.CONVEYOR).containsValue(tileName)) {
				stepsFromTiles.add(updatePos(tileName));
			}
			if(grid.getGridLayer(LayerName.COG).containsValue(tileName)) {
				if (tileName.toString().contains("COUNTER"))
					turn("right");
				else if (tileName.toString().contains("CLOCKWISE"))
					turn("left");
			}
		}
		return stepsFromTiles;
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

	private void turn(String dir) {
		if ("left".equals(dir))
			logicDirection = Direction.turnLeftFrom(logicDirection);
		else if ("right".equals(dir))
			logicDirection = Direction.turnRightFrom(logicDirection);
	}
}
