package roborally.game.robot.ai;

import org.jetbrains.annotations.NotNull;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.flag.IFlag;
import roborally.game.robot.IRobotLogic;
import roborally.game.Grid;
import roborally.utilities.enums.Direction;

import java.util.*;

import static roborally.game.cards.IProgramCards.Card;

public class AI {
	private IRobotLogic robotLogic;
	private Grid grid;
	private int pickNr;
	private final ArrayList<IFlag> flags;
	private IFlag flag;
	private Pathway pathway;
	private Cards cards;
	private int[] order;
	private boolean rotated;

	public AI(@NotNull IGameBoard gameBoard) {
		this.flags = gameBoard.findAllFlags();
		this.grid = gameBoard.getGrid();
	}

	public void controlRobot(@NotNull IRobotLogic robotLogic) {
		rotated = false;
		this.robotLogic = robotLogic;
		for (IFlag flag : flags) {
			if (flag.getID() == robotLogic.getNextFlag())
				this.flag = flag;
		}
		this.pickNr = 0;
		this.order = new int[Math.min(5,robotLogic.getCardsInHand().size())];
		pathway = new Pathway(robotLogic.getPosition().cpy(), flag.getPosition(), robotLogic.getDirection(), grid);
		cards = new Cards(robotLogic.getCardsInHand());
		chooseCards();
	}

	public int[] getOrder() {
		return order;
	}

	private void chooseCards() {
		boolean rotateEmpty = false;

		while (!fullOrder()) {
			while (!closerToFlag() && !rotateEmpty) {
				if (!rotate())
					rotateEmpty = true;
				if (fullOrder())
					break;
				nextHypoDistToFlag();
			}
			while (closerToFlag() && cards.hasCard("move") && !fullOrder())
				addMoveCard();
			boolean moveOrRotateEmpty = (!cards.hasCard("move") || rotateEmpty);
			if (moveOrRotateEmpty && !fullOrder() && !rotate()) {
				addMoveCard();
			}
			rotated = false;
		}
	}

	// FIXME: Should not use strings
	private boolean rotate() {
		pathway.storeCurrentPosition();
		pathway.tilesAtPos();
		if (goodRotation("left"))
			updateOrder(cards.getNextCard("left"));
		else if (goodRotation("right"))
			updateOrder(cards.getNextCard("right"));
		else if (goodRotation("uTurn")) {
			pathway.turnLeft();
			pathway.turnLeft();
			updateOrder(cards.getNextCard("uTurn"));
		}
		else if (cards.leftNext() && (!rotated || !cards.hasCard("move"))) {
			pathway.turnLeft();
			updateOrder(cards.getNextCard("left"));
		} else if (cards.hasCard("right") && (!rotated || !cards.hasCard("move"))) {
			pathway.turnRight();
			updateOrder(cards.getNextCard("right"));
		}
		else {
			pathway.restoreCurrentPosition();
			return false;
		}
		rotated = true;
	return true;
	}

	private boolean goodRotation(String dir) {
		boolean goodRotation = cards.hasCard(dir);
		if (goodRotation && ("left".equals(dir) || "right".equals(dir)))
			goodRotation = rotationPointsToFlag(dir);
		return goodRotation;
	}

	private boolean rotationPointsToFlag(String dir) {
		Direction temp = pathway.getDirection();
		if("left".equals(dir))
			pathway.turnLeft();
		else if("right".equals(dir))
			pathway.turnRight();
		if (closerToFlag())
			return true;
		pathway.setDirection(temp);
		return false;
	}

	private boolean closerToFlag() {
		if(cards.isSizeGreaterThan1())
			findBestMoveCard();
		nextHypoDistToFlag();
		return pathway.closerToFlag();
	}

	private void addMoveCard() {
		Card move = cards.getNextCard("move");
		int val = move.getValue();
		pathway.updatePos(val);
		pathway.tilesAtPos();
		updateOrder(move);
	}

	private void findBestMoveCard() {
		ArrayList<Card> tempCards =  cards.getMoveCardList();
		Map<Card, Double> bestCard = new HashMap<>();
		for (Card card : tempCards) {
			cards.putCardInMove(card);
			bestCard.put(card, nextHypoDistToFlag());
			cards.removeCardFromMove();
		}
		cards.refillMoveCards(bestCard, tempCards);
	}

	private void updateOrder(Card card) {
		order[pickNr++] = robotLogic.getCardsInHand().indexOf(card);
	}

	private double nextHypoDistToFlag() {
		int steps;
		if (cards.hasCard("move"))
			steps = cards.getNextMoveValue();
		else
			steps = 1;
		return pathway.nextDstToFlag(steps);
	}

	private boolean fullOrder() {
		return order.length == pickNr;
	}

	public double getNewDistanceToFlag() {
		return pathway.distanceToFlag();
	}
}
