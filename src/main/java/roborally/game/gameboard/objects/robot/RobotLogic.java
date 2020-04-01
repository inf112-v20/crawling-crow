package roborally.game.gameboard.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Queue;
import roborally.game.cards.CardsInHand;
import roborally.game.cards.IProgramCards;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;

public class RobotLogic implements IRobotLogic {
    public boolean hasSelectedCards;
    private String name;
    private GridPoint2 robotPosition;
    private GridPoint2 archiveMarker;
    private int health = SettingsUtil.ROBOT_MAX_HEALTH;
    private int reboots = SettingsUtil.ROBOT_MAX_REBOOTS;
    private Direction direction;
    private CardsInHand cardsInHand;
    private Queue<IProgramCards.Card> nextCard;

    public RobotLogic(String name) {
        this.name = name;
        this.robotPosition = new GridPoint2();
        this.direction = Direction.North;
    }

    //region Robot stats
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void addHealth(int amount) {
        if (getHealth() < SettingsUtil.ROBOT_MAX_HEALTH) {
            this.health += amount;
            if (getHealth() > SettingsUtil.ROBOT_MAX_HEALTH) {
                this.health = SettingsUtil.ROBOT_MAX_HEALTH;
            }
        }
    }

    @Override
    public int getReboots() {
        return reboots;
    }

    @Override
    public String getStatus() {
        if (this.health < 5 && this.health > 0)
            return "Badly damaged";
        else if (this.health == 0) {
            this.health = -1;
            return "Destroyed";
        } else if (this.health > 5)
            return "Everything is OK!";
        else
            return getName() + " is gone";
    }
    //endregion

    //region Position
    @Override
    public GridPoint2 getPosition() {
        return this.robotPosition;
    }

    @Override
    public void setPosition(GridPoint2 newPosition) {
        this.robotPosition.set(newPosition);
    }

    //region Archive marker
    @Override
    public void backToArchiveMarker() {
        this.health = SettingsUtil.ROBOT_MAX_HEALTH;
        this.reboots -= 1;
        setPosition(this.archiveMarker);
        this.direction = Direction.North;
    }

    public GridPoint2 getArchiveMarker() {
        return this.archiveMarker;
    }

    public void setArchiveMarker(GridPoint2 pos) {
        System.out.println("- " + getName() + " has its Archive marker at " + getPosition());
        this.archiveMarker = pos;
    }
    //endregion
    //endregion

    //region Rotating and direction
    @Override
    public void rotate(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }
    //endregion

    @Override
    public boolean takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0 && this.reboots > 1)
            return true;
        else if (this.health <= 0)
            this.health = 0;
        return false;
    }

    //region Program cards in hand
    @Override
    public void setCardsInHand(CardsInHand newCardsInHand) {
        this.cardsInHand = newCardsInHand;
    }

    @Override
    public void arrangeCardsInHand(int[] newOrder) {
        this.cardsInHand.arrangeCards(newOrder);
        Queue<IProgramCards.Card> nextCard = new Queue<>();
        for (IProgramCards.Card card : cardsInHand.getCards())
            nextCard.addFirst(card);
        this.nextCard = nextCard;
    }

    @Override
    public IProgramCards.Card getNextCardInHand() {
        assert nextCard != null;
        if (!nextCard.isEmpty()) {
            return nextCard.removeLast();
        }
        return null;
    }

    @Override
    public IProgramCards.Card peekNextCardInHand() {
        if (nextCard == null || nextCard.isEmpty())
            return null;
        return nextCard.last();
    }

    @Override
    public void setHasSelectedCards(boolean value) {
        this.hasSelectedCards = value;
    }

    @Override
    public boolean isCardsSelected() {
        return this.hasSelectedCards;
    }

    @Override
    public IProgramCards drawCards(IProgramCards deckOfProgramCards) {
        ArrayList<IProgramCards.Card> cardsDrawn = new ArrayList<>();

        for (int i = 0; i < getNumberOfCardsToDraw(); i++) {
            cardsDrawn.add(deckOfProgramCards.getNextCard());
        }
        CardsInHand cardsInHand = new CardsInHand(cardsDrawn);
        this.setCardsInHand(cardsInHand);

        return deckOfProgramCards;
    }


    @Override
    public void autoArrangeCardsInHand() {
        int[] newOrder = new int[getNumberOfCardsToDraw()];

        for (int i = 0; i < Math.min(getNumberOfCardsToDraw(), 5); i++) {
            newOrder[i] = i;
        }
        this.arrangeCardsInHand(newOrder);
    }

    private int getNumberOfCardsToDraw() {
        int numberOfCardsToDraw = this.getHealth() - 1; // For damage tokens, see rulebook page 9
        return Math.max(0, numberOfCardsToDraw);
    }

    @Override
    public ArrayList<IProgramCards.Card> getCardsInHand() {
        return this.cardsInHand.getCards();
    }
    //endregion
}