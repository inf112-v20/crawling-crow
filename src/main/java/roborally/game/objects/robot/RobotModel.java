package roborally.game.objects.robot;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Queue;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.PlayCards;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;

public class RobotModel implements Programmable {
    private String name;
    private GridPoint2 robotPosition;
    private GridPoint2 checkPoint;
    private int health = 10;
    private int reboots = 3;
    private Direction direction;
    private PlayCards playCards;
    private Queue<IProgramCards.Card> nextCard;

    public RobotModel(String name) {
        this.name = name;
        this.robotPosition = new GridPoint2();
        this.direction = Direction.North;
    }

    public String getName() {
        return this.name;
    }

    public RobotModel getModel() {
        return this;
    }

    public GridPoint2 getPos() {
        return this.robotPosition;
    }

    public void setPos(GridPoint2 newPos) {
        this.robotPosition.set(newPos);
    }

    public int[] move(int steps) {
        System.out.println(this.getName());
        int[] moveValues = getMoveValues();
        System.out.println("\nMoving forward...");
        if (steps == -1) {
            moveValues[0] = -moveValues[0];
            moveValues[1] = -moveValues[1];
        }
        return moveValues;
    }

    public int[] getMoveValues() {
        int dy = 0;
        int dx = 0;
        Direction dir = this.direction;
        if (dir == Direction.North) {
            dy = 1;
        }
        if (dir == Direction.East) {
            dx = 1;
        }
        if (dir == Direction.West) {
            dx = -1;
        }
        if (dir == Direction.South) {
            dy = -1;
        }
        return new int[]{dx, dy};
    }

    public Direction rotate(String direction, int factor) {
        if ("L".equals(direction))
            this.direction = Direction.turnLeftFrom(this.direction);
        else if ("R".equals(direction))
            this.direction = Direction.turnRightFrom(this.direction);
        if (factor == 2) {
            if ("L".equals(direction))
                this.direction = Direction.turnLeftFrom(this.direction);
            else if ("R".equals(direction))
                this.direction = Direction.turnRightFrom(this.direction);
        }

        return this.direction;
    }

    public void backToCheckPoint() {
        setPos(this.checkPoint);
        this.direction = Direction.North;
    }

    public GridPoint2 getCheckPoint() {
        return this.checkPoint;
    }

    public void setCheckPoint(int x, int y) {
        this.checkPoint = new GridPoint2(x, y);
    }


    public int getHealth() {
        return health;
    }


    public int getReboots() {
        return reboots;
    }


    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0)
            this.health = 0;
    }

    public int getDirectionID() {
        return this.direction.getDirectionID();
    }

    public Direction getDirection() {
        return this.direction;
    }


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

    public void newCards(PlayCards playCards) {
        this.playCards = playCards;
    }

    public void arrangeCards(int[] order) {
        this.playCards.arrangeCards(order);
        Queue<IProgramCards.Card> nextCard = new Queue<>();
        for (IProgramCards.Card card : playCards.getCards())
            nextCard.addFirst(card);
        this.nextCard = nextCard;
    }

    public IProgramCards.Card getNextCard() {
        if (!nextCard.isEmpty())
            return nextCard.removeLast();
        return null;
    }

    public ArrayList<IProgramCards.Card> getCards() {
        return this.playCards.getCards();
    }

}