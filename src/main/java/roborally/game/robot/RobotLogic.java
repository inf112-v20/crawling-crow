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

    public RobotLogic(String name) {
        this.name = name;
        this.position = new GridPoint2();
        this.direction = Direction.NORTH;
        this.register = new Register();
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
        // UNCOMMENT to debug
        // System.out.println("Took damage: " + damage + ". Robot: " + name);
        health -= damage;
        if (health < 0)
            health = 0;
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
        fillFirstCardsFromHandIntoRegister();
    }

    private void fillFirstCardsFromHandIntoRegister() {
        int cardsMissingInRegister = Math.min(getNumberOfCardsToDraw(), 5);
        Card[] cardToRegister = new Card[cardsMissingInRegister];

        // UNCOMMENT to debug if necessary
         System.out.println();
         System.out.println(name + " " + health);
         System.out.println("Locked cards: " + register.getNumberOfLockedCards());
         System.out.println("In hand: " + cardsInHand.getCards().size());
         System.out.println("To place: " + cardToRegister.length);

        for(int i = 0; i < cardsMissingInRegister; i++){
            cardToRegister[i] = cardsInHand.getCards().get(i);
        }
        register.add(cardToRegister);
    }

    private int getNumberOfCardsToDraw() {
        int numberOfCardsToDraw = getHealth() - 1; // For damage tokens, see rulebook page 9
        return Math.max(0, numberOfCardsToDraw);
    }

    private int getNumberOfCardsToLock(){
        if (getHealth() < 1){
            return 5; // Lock whole register if the robot has no health
        }
        int cardsToLock = getHealth() - 2*(getHealth()-3);
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
        System.out.println("\t\t- " + name + " has hp: " + health + ". Locking " + getNumberOfCardsToLock() + " cards..");
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

    @Override
    public boolean[] getVisitedFlags() {
        return visitedFlags;
    }
    //endregion

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
}