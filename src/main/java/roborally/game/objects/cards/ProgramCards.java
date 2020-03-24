package roborally.game.objects.cards;

import roborally.game.objects.robot.Robot;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProgramCards implements IProgramCards {
    private ArrayList<Card> deckOfCards;
    private HashMap<CardType, Runnable> cardTypeMethod;

    public ProgramCards() {
        deckOfCards = new ArrayList<>();
        putAllCardsIntoDeck();
        shuffleCards();
    }

    public ProgramCards(ArrayList<Robot> robots) {
        deckOfCards = new ArrayList<>();
        putAllCardsIntoDeck();
        shuffleCards();
        setCardTypeMethod(robots);
    }

    private void putAllCardsIntoDeck() {
        for (CardType type : CardType.values()) {
            for (int i = 0; i < type.getNumberOfCards(); i++) {
                this.deckOfCards.add(new Card(type));
            }
        }
    }

    @Override
    public void shuffleCards() {
        Collections.shuffle(deckOfCards);
    }

    @Override
    public ArrayList<Card> getDeck() {
        return this.deckOfCards;
    }

    //@Override
    public void setCardTypeMethod(ArrayList<Robot> robots) {
        for (Robot robot : robots) {
            cardTypeMethod.put(CardType.MOVE_1, () -> robot.move(1));
            cardTypeMethod.put(CardType.MOVE_2, () -> robot.move(2));
            cardTypeMethod.put(CardType.MOVE_3, () -> robot.move(3));
            cardTypeMethod.put(CardType.ROTATE_LEFT, () -> robot.rotate(Direction.turnLeftFrom(robot.getLogic().getDirection())));
            cardTypeMethod.put(CardType.ROTATE_RIGHT, () -> robot.rotate(Direction.turnRightFrom(robot.getLogic().getDirection())));
            cardTypeMethod.put(CardType.U_TURN, () -> robot.rotate(Direction.turnLeftFrom(robot.getLogic().getDirection()), 2));
        }
    }

    /*if (card.getCardType() == IProgramCards.CardType.MOVE_1)
    move(1);
        else if (card.getCardType() == IProgramCards.CardType.MOVE_2)
    move(2);
        else if (card.getCardType() == IProgramCards.CardType.MOVE_3)
    move(3);
        else if (card.getCardType() == IProgramCards.CardType.ROTATE_LEFT)
    rotate(Direction.turnLeftFrom(robotLogic.getDirection()));
        else if (card.getCardType() == IProgramCards.CardType.ROTATE_RIGHT)
    rotate(Direction.turnRightFrom(robotLogic.getDirection()));
        else if (card.getCardType() == IProgramCards.CardType.U_TURN) {
        rotate(Direction.turnLeftFrom(robotLogic.getDirection()));
        rotate(Direction.turnLeftFrom(robotLogic.getDirection()));
    } else if (card.getCardType() == IProgramCards.CardType.BACKUP)
    move(-1);*/
}