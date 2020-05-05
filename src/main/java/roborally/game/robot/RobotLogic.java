package roborally.game.robot;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.cards.CardsInHand;
import roborally.game.cards.IProgramCards;
import roborally.game.cards.IProgramCards.Card;
import roborally.game.cards.Register;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;

import java.util.ArrayList;

public class RobotLogic implements IRobotLogic {
    public boolean hasSelectedCards;
    private boolean powerDown;
    private boolean powerDownNextRound;
    private boolean[] visitedFlags;
    private String name;
    private GridPoint2 position;
    private GridPoint2 archiveMarker;
    private int health = SettingsUtil.ROBOT_MAX_HEALTH;
    private int reboots = SettingsUtil.ROBOT_MAX_REBOOTS;
    private Direction direction;
    private CardsInHand cardsInHand;
    private Register register;

    private boolean isUserRobot;
    private boolean hasWon;
    private boolean isDestroyed;

    public RobotLogic(String name) {
        this.name = name;
        this.position = new GridPoint2();
        this.direction = Direction.NORTH;
        this.register = new Register();
        this.powerDown = false;
    }

    //region Robot stats
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
            this.health = -1;
            this.isDestroyed = true;
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
        this.health = SettingsUtil.ROBOT_MAX_HEALTH;
        this.reboots -= 1;
        this.isDestroyed = false;
        setPosition(archiveMarker);
        this.direction = Direction.NORTH;
    }

    public GridPoint2 getArchiveMarker() {
        return archiveMarker;
    }

    public void setArchiveMarker(GridPoint2 pos) {
        this.archiveMarker = pos;
        if (SettingsUtil.DEBUG_MODE) System.out.println("- " + getName() + " has its Archive marker at " + getArchiveMarker());
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
        if (SettingsUtil.DEBUG_MODE) System.out.println("Took damage: " + damage + ". Robot: " + name);
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            isDestroyed = true;
        }
        return reboots > 1 && health == 0;
    }

    //region Program cards in hand
    @Override
    public void setCardsInHand(CardsInHand cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    @Override
    public void arrangeCardsInHand(int[] newOrder) {
        cardsInHand.arrangeCards(newOrder);
        fillFirstCardsFromHandIntoRegister();
    }

    @Override
    public IProgramCards.Card getNextCardInRegister() {
        return register.getNextCard();
    }

    @Override
    public IProgramCards.Card peekNextCardInRegister() {
        return register.peekNextCard();
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
        if(SettingsUtil.DEBUG_MODE){
            System.out.println(getName() + "'s power down status: " + getPowerDown());
        }
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

    private void fillFirstCardsFromHandIntoRegister() {
        int cardsMissingInRegister = Math.min(getNumberOfCardsToDraw(), 5);
        Card[] cardToRegister = new Card[cardsMissingInRegister];

        if (SettingsUtil.DEBUG_MODE) {
            System.out.println(name + "'s health: " + health);
            System.out.println("Locked cards: " + register.getNumberOfLockedCards());
            System.out.println("In hand: " + cardsInHand.getCards().size());
            System.out.println("To place: " + cardToRegister.length);
            System.out.println();
        }

        for(int i = 0; i < cardsMissingInRegister; i++){
            cardToRegister[i] = cardsInHand.getCards().get(i);
        }
        register.add(cardToRegister);
    }

    private int getNumberOfCardsToDraw() {
        if (getPowerDown()) {
            return 0;
        } else {
            int numberOfCardsToDraw = getHealth() - 1; // For damage tokens, see rulebook page 9
            return Math.max(0, numberOfCardsToDraw);
        }
    }

    private int getNumberOfCardsToLock(){
        if (getHealth() < 1 || getPowerDown()) {
            return 5; // Lock whole register if the robot has no health
        }
        int cardsToLock = getHealth() - 2 * (getHealth() - 3);
        return Math.max(0, cardsToLock);
    }

    @Override
    public ArrayList<IProgramCards.Card> getCardsInHand() {
        return cardsInHand.getCards();
    }

    @Override
    public void putChosenCardsIntoRegister() {
        fillFirstCardsFromHandIntoRegister();
    }

    @Override
    public int getNumberOfLockedCards() {
        return register.getNumberOfLockedCards();
    }

    @Override
    public void cleanRegister(){
        if (SettingsUtil.DEBUG_MODE) {
            System.out.println("\t\t- " + name + " has hp: " + health + ". Locking " + getNumberOfCardsToLock() + " cards..");
        }
        register.cleanRegister(getNumberOfCardsToLock());
        if(register.getNumberOfLockedCards() != getNumberOfCardsToLock()){
            throw new IllegalStateException("Unexpected number of cards in register after cleanup." +
                    " Expected to lock: " + getNumberOfCardsToLock() +
                    " Actually locked: " + register.getNumberOfLockedCards());
        }
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
    public int getNumberOfVisitedFlags() {
        int i = 0;
        for(boolean bool : visitedFlags) {
            if(!bool)
                break;
            i++;
        }
        return i;
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
        if (SettingsUtil.DEBUG_MODE) System.out.println("- Updated next flag to visit");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag - 1] = true;
        if (SettingsUtil.DEBUG_MODE) {
            if (nextFlag == visitedFlags.length)
                System.out.println("- Congratulations you have collected all the flags!");
            else
                System.out.println("- Next flag to visit: " + (nextFlag + 1));
        }
    }

    @Override
    public boolean[] getVisitedFlags() {
        return visitedFlags;
    }
    //endregion

    @Override
    public boolean getPowerDown() {
        return powerDown;
    }

    @Override
    public void setPowerDown(boolean powerDown) {
        this.powerDown = powerDown;
        if (powerDown) {
            addHealth(SettingsUtil.ROBOT_MAX_HEALTH);
        }
    }

    @Override
    public void setPowerDownNextRound(boolean powerDownNextRound) {
        if (SettingsUtil.DEBUG_MODE) System.out.println(getName() + " is set power down to: " + powerDownNextRound + " next round");
        this.powerDownNextRound = powerDownNextRound;
    }

    @Override
    public boolean getPowerDownNextRound() {
        return powerDownNextRound;
    }

    @Override
    public void setHasWon() {
        this.hasWon = true;
    }

    @Override
    public boolean hasWon() {
        return hasWon;
    }

    @Override
    public boolean isUserRobot() {
        return isUserRobot;
    }

    @Override
    public void setUserRobot() {
        this.isUserRobot = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public Register getRegister() {
        return register;
    }
}