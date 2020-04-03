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
    private boolean[] visitedFlags;
    private String name;
    private GridPoint2 position;
    private GridPoint2 archiveMarker;
    private int health = SettingsUtil.ROBOT_MAX_HEALTH;
    private int reboots = SettingsUtil.ROBOT_MAX_REBOOTS;
    private Direction direction;
    private CardsInHand cardsInHand;
    private Queue<IProgramCards.Card> nextCard;

    public RobotLogic(String name) {
        this.name = name;
        this.position = new GridPoint2();
        this.direction = Direction.NORTH;
    }

    //region Robot stats
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void addHealth(int amount) {
        if (getHealth() < SettingsUtil.ROBOT_MAX_HEALTH) {
            health += amount;
            if (getHealth() > SettingsUtil.ROBOT_MAX_HEALTH) {
                health = SettingsUtil.ROBOT_MAX_HEALTH;
            }
        }
    }

    @Override
    public int getReboots() {
        return reboots;
    }

    @Override
    public String getStatus() {
        if (health < 5 && health > 0)
            return "Badly damaged";
        else if (health == 0) {
            health = -1;
            return "Destroyed";
        } else if (health > 5)
            return "Everything is OK!";
        else
            return getName() + " is gone";
    }
    //endregion

    //region Position
    @Override
    public GridPoint2 getPosition() {
        return position;
    }

    @Override
    public void setPosition(GridPoint2 position) {
        this.position.set(position);
    }

    //region Archive marker
    @Override
    public void backToArchiveMarker() {
        health = SettingsUtil.ROBOT_MAX_HEALTH;
        reboots -= 1;
        setPosition(archiveMarker);
        direction = Direction.NORTH;
    }

    public GridPoint2 getArchiveMarker() {
        return archiveMarker;
    }

    public void setArchiveMarker(GridPoint2 pos) {
        this.archiveMarker = pos;
        System.out.println("- " + getName() + " has its Archive marker at " + getArchiveMarker());
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
        return direction;
    }
    //endregion

    @Override
    public boolean takeDamage(int damage) {
        health -= damage;
        if (health <= 0 && reboots > 1)
            return true;
        else if (health <= 0)
            health = 0;
        return false;
    }

    //region Program cards in hand
    @Override
    public void setCardsInHand(CardsInHand cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    @Override
    public void arrangeCardsInHand(int[] newOrder) {
        cardsInHand.arrangeCards(newOrder);
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
    public void setHasSelectedCards(boolean bool) {
        this.hasSelectedCards = bool;
    }

    @Override
    public boolean isCardsSelected() {
        return hasSelectedCards;
    }

    @Override
    public IProgramCards drawCards(IProgramCards deckOfProgramCards) {
        ArrayList<IProgramCards.Card> cardsDrawn = new ArrayList<>();

        for (int i = 0; i < getNumberOfCardsToDraw(); i++) {
            cardsDrawn.add(deckOfProgramCards.getNextCard());
        }
        CardsInHand cardsInHand = new CardsInHand(cardsDrawn);
        setCardsInHand(cardsInHand);

        return deckOfProgramCards;
    }


    @Override
    public void autoArrangeCardsInHand() {
        int[] newOrder = new int[getNumberOfCardsToDraw()];

        for (int i = 0; i < Math.min(getNumberOfCardsToDraw(), 5); i++) {
            newOrder[i] = i;
        }
        arrangeCardsInHand(newOrder);
    }

    private int getNumberOfCardsToDraw() {
        int numberOfCardsToDraw = getHealth() - 1; // For damage tokens, see rulebook page 9
        return Math.max(0, numberOfCardsToDraw);
    }

    @Override
    public ArrayList<IProgramCards.Card> getCardsInHand() {
        return cardsInHand.getCards();
    }
    //endregion

    //region Flag
    @Override
    public void setNumberOfFlags(int flags) {
        this.visitedFlags = new boolean[flags];
    }

    @Override
    public int getNextFlag() {
        for (int i = 0; i < visitedFlags.length; i++) {
            if (!visitedFlags[i]) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public boolean hasVisitedAllFlags() {
        boolean visitedAll = true;
        for (boolean visitedFlag : visitedFlags) {
            visitedAll = visitedAll && visitedFlag;
        }
        return visitedAll;
    }

    @Override
    public void visitNextFlag() {
        System.out.println("- Updated next flag to visit");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag - 1] = true;
        if (nextFlag == visitedFlags.length)
            System.out.println("- Congratulations you have collected all the flags, press 'W' to end the game.");
        else
            System.out.println("- Next flag to visit: " + (nextFlag + 1));
    }
    //endregion
}