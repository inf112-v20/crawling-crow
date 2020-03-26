package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Queue;
import roborally.game.objects.cards.CardsInHand;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;

public class RobotLogic implements Programmable {
    public boolean hasChosenCards;
    private String name;
    private GridPoint2 robotPosition;
    private GridPoint2 checkPoint;
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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public RobotLogic getLogic() {
        return this;
    }

    //region Position
    @Override
    public GridPoint2 getPosition() {
        return this.robotPosition;
    }

    @Override
    public void setPosition(GridPoint2 newPosition) {
        this.robotPosition.set(newPosition);
    }
    //endregion

    //region Movement
    @Override
    @Deprecated // TODO: Remove when safe
    public void move(int steps) {
        // ...
    }

    @Override
    public void rotate(Direction direction) {
        this.direction = direction;
    }

    //endregion

    //region Checkpoint
    @Override
    public void backToCheckPoint() {
        this.health = SettingsUtil.ROBOT_MAX_HEALTH;
        this.reboots -= 1;
        setPosition(this.checkPoint);
        this.direction = Direction.North;
    }

    public GridPoint2 getCheckPoint() {
        return this.checkPoint;
    }

    public void setCheckPoint(GridPoint2 pos) {
        this.checkPoint = pos;
    }
    //endregion

    public int getHealth() {
        return health;
    }

    public int getReboots() {
        return reboots;
    }

    public boolean takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0 && this.reboots > 1 )
            return true;
        else if (this.health <= 0)
            this.health = 0;
        return false;
    }

    //region Direction
    public Direction getDirection() {
        return this.direction;
    }

    public int getDirectionID() {
        return this.direction.getDirectionID();
    }
    //endregion


    public String getStatus() {
        if (this.health < 5 && this.health > 0)
            return "Badly damaged";
        else if (this.health == 0) {
            this.health = -1;
            return "Destroyed";
        } else if (this.health > 5)
            return "Everything ok!";
        else
            return "Robot is gone";
    }

    //region Cards
    public void newCards(CardsInHand cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void arrangeCards(int[] order) {
        this.cardsInHand.arrangeCards(order);
        Queue<IProgramCards.Card> nextCard = new Queue<>();
        for (IProgramCards.Card card : cardsInHand.getCards())
            nextCard.addFirst(card);
        this.nextCard = nextCard;
    }

    public IProgramCards.Card getNextCard() {
        assert nextCard != null;
        if (!nextCard.isEmpty()) {
            return nextCard.removeLast();
        }
        return null;
    }

    public ProgramCards.Card peekNextCard() {
        if(nextCard.isEmpty() || nextCard == null)
            return null;
        return nextCard.last();
    }

    public void setHasChosenCards(boolean value) {
        this.hasChosenCards = value;
    }
    public boolean hasChosenCards() {
        return this.hasChosenCards;
    }

    public ArrayList<IProgramCards.Card> getCards() {
        return this.cardsInHand.getCards();
    }
    //endregion
}