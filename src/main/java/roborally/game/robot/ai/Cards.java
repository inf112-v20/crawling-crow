package roborally.game.robot.ai;

import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static roborally.game.cards.IProgramCards.Card;

public class Cards {
	private final ArrayList<Card> cards;
	private final HashMap<String, Queue<Card>> cardTypes;

	public Cards(ArrayList<Card> cards) {
		this.cards = cards;
		cardTypes = new HashMap<>();
		cardTypes.put("move", new Queue<>());
		cardTypes.put("left", new Queue<>());
		cardTypes.put("right", new Queue<>());
		cardTypes.put("uTurn", new Queue<>());
		organizeCards();
	}

	private void organizeCards() {
		for (Card card : cards) {
			if (card.getValue() > -2 && card.getValue() < 4)
				cardTypes.get("move").addLast(card);
			else if (card.getValue() == 90)
				cardTypes.get("right").addLast(card);
			else if (card.getValue() < -90)
				cardTypes.get("uTurn").addLast(card);
			else if (card.getValue() < -1)
				cardTypes.get("left").addLast(card);
		}
	}

	public boolean hasCard(String cardType) {
		return !cardTypes.get(cardType).isEmpty();
	}

	public boolean leftNext() {
		boolean moreLeftThanRight = cardTypes.get("left").size > cardTypes.get("right").size;
		return hasCard("left") && moreLeftThanRight;
	}

	public Card getNextCard(String type) {
		return cardTypes.get(type).removeFirst();
	}

	public boolean isSizeGreaterThan1() {
		return cardTypes.get("move").size > 1;
	}

	public ArrayList<Card> getMoveCardList() {
		ArrayList<Card> tempCards = new ArrayList<>();
		while (!cardTypes.get("move").isEmpty())
			tempCards.add(getNextCard("move"));
		return tempCards;
	}

	public void refillMoveCards(Map<Card, Double> bestCard, ArrayList<Card> tempCards) {
		Map.Entry<Card, Double> min = Collections.min(bestCard.entrySet(),
				Map.Entry.comparingByValue());
		cardTypes.get("move").addFirst(min.getKey());
		tempCards.remove(min.getKey());
		for (Card card : tempCards)
			cardTypes.get("move").addLast(card);
	}

	public void putCardInMove(Card card) {
		cardTypes.get("move").addFirst(card);
	}

	public void removeCardFromMove() {
		cardTypes.get("move").removeFirst();
	}

	public int getNextMoveValue() {
		return cardTypes.get("move").first().getValue();
	}
}
