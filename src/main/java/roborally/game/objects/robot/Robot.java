package roborally.game.objects.robot;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.laser.Laser;
import roborally.game.objects.laser.LaserRegister;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import roborally.ui.listeners.Listener;
import roborally.ui.robot.IRobotView;
import roborally.ui.robot.RobotView;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;

import java.util.HashMap;

public class Robot implements IRobot {
    private IRobotView robotView;
    private IRobotLogic robotLogic;
    private boolean[] visitedFlags;
    private Laser laser;
    private ILayers layers;
    private Listener listener;
    private LaserRegister laserRegister;
    private boolean reboot;

    private HashMap<IProgramCards.CardType, Runnable> cardTypeMethod;

    // Constructor for testing the robot model.
    public Robot(RobotLogic robotLogic) {
        this.robotLogic = robotLogic;
    }

    public Robot(GridPoint2 pos, int robotID, LaserRegister laserRegister) {
        RobotLogic robotLogic = new RobotLogic(AssetManagerUtil.getRobotName());
        IRobotView robotView = new RobotView(pos);
        this.robotLogic = robotLogic;
        this.robotView = robotView;
        this.setTextureRegion(robotID);
        this.robotLogic.setArchiveMarker(pos);
        this.layers = new Layers();
        this.laser = new Laser(0, layers);
        this.listener = new Listener(layers);
        this.laserRegister = laserRegister;
        setPosition(pos);
        checkForStationaryLaser(); // for spawning in the current lasers in fun mode.

        this.cardTypeMethod = new HashMap<>();
        setCardTypeMethod();
    }

    @Override
    public String getName() {
        return this.getLogic().getName();
    }

    @Override
    public IRobotLogic getLogic() {
        return this.robotLogic;
    }

    @Override
    public IRobotView getView() {
        return this.robotView;
    }

    //region Position
    @Override
    public GridPoint2 getPosition() {
        return this.getLogic().getPosition();
    }

    @Override
    public void setPosition(GridPoint2 newPosition) {
        this.getLogic().setPosition(newPosition);
    }

    @Override
    public void backToArchiveMarker() {
        if (reboot) {
            getView().goToArchiveMarker(this.getPosition(), getLogic().getArchiveMarker());
            this.getLogic().backToArchiveMarker();
            clearLaserRegister();
            reboot = false;
        }
    }
    //endregion

    //region Movement
    @Override
    public void move(int steps) {
        GridPoint2 oldPos = getPosition().cpy();
        GridPoint2 step = getLogic().getDirection().getStep();
        GridPoint2 move = new GridPoint2(step.x * (Math.abs(steps) / steps), step.y * (Math.abs(steps) / steps));
        // Perform all movement
        for (int i = 0; i < Math.abs(steps); i++)
            tryToMove(move);

        // Play movement sound
        playSoundWalking(oldPos);

        // Updates the graphic when moving through lasers.
        checkForStationaryLaser();
    }

    @Override
    public void tryToMove(GridPoint2 possiblePosition) {
        System.out.println("\n" + getName() + " trying to move:");
        GridPoint2 oldPos = getPosition();
        GridPoint2 newPos = oldPos.cpy().add(possiblePosition);

        System.out.println("\t- Old position: " + oldPos);

        // Check if the robot is not colliding with something
        if (!listener.listenCollision(oldPos, possiblePosition)) {

            // Checks that robot does not tries to move out of the map
            if (this.getView().canMoveRobot(oldPos, possiblePosition)) {
                // Robot moving outside map
                if(robotView.isRobotInGraveyard(newPos)) {
                    setPosition(SettingsUtil.GRAVEYARD);
                    this.takeDamage(SettingsUtil.MAX_DAMAGE);
                    return;
                }
                // Update pos
                this.setPosition(newPos);
                System.out.println("\t- New position: " + newPos);
                System.out.println("\t- Health: " + getLogic().getHealth());

                // Check if Robot is standing on a hole
                if (layers.assertHoleNotNull(newPos)) {
                    //robotWentInHole = true;
                    takeDamage(SettingsUtil.MAX_DAMAGE);
                    System.out.println("\t\t- Robot went into a hole");
                }
                getView().setDirection(newPos, getLogic().getDirection());

            }
        } else
            // Robot does not move
            System.out.println("\t\t- Robot cannot move this way: " + oldPos);
    }

    private void playSoundWalking(GridPoint2 oldPos) {
        Sound sound;
        if (getPosition().dst(oldPos) == 1) {
            sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP1);
            sound.play(0.25f * AssetManagerUtil.volume);
        } else if (getPosition().dst(oldPos) == 2) {
            sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP2);
            sound.play(0.25f * AssetManagerUtil.volume);
        } else if (getPosition().dst(oldPos) == 3) {
            sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP3);
            sound.play(0.25f * AssetManagerUtil.volume);
        }
    }

    @Override
    public void rotate(Direction newDirection) {
        this.getLogic().rotate(newDirection);
        this.getView().setDirection(getPosition(), newDirection);
    }
    //endregion

    @Override
    public void takeDamage(int dmg) {
        if (getLogic().takeDamage(dmg))
            reboot = true;
        else
            setDamageTakenTexture();
    }

    //region Lasers
    @Override
    public void fireLaser() {
        laser.fireLaser(getPosition(), this.getLogic().getDirection().getDirectionID());
    }

    @Override
    public Laser getLaser() {
        return this.laser;
    }

    @Override
    public boolean checkForStationaryLaser() {
        return (listener.listenLaser(getPosition(), getName(), laserRegister));
    }

    @Override
    public void clearLaserRegister() {
        laserRegister.updateLaser(getName(), getPosition());
    }
    //endregion

    //region Textures
    @Override
    public void setTextureRegion(int index) {
        this.getView().setTextureRegion(index);
    }

    @Override
    public TextureRegion[][] getTexture() {
        return this.getView().getTextureRegion();
    }

    @Override
    public void setVictoryTexture() {
        this.getView().setVictoryTexture(getPosition());
    }

    @Override
    public void setDamageTakenTexture() {
        this.getView().setDamageTakenTexture(getPosition());
        this.getView().setDirection(getPosition(), getLogic().getDirection());

    }
    //endregion

    @Override
    public void deleteRobot() {
        this.layers.setRobotTexture(getPosition(), null);
        this.setPosition(SettingsUtil.GRAVEYARD);
        clearLaserRegister();
    }

    //region Program cards
    @Override
    public void playNextCard() {
        //TODO: Add hashmap and move the hashmap to ProgramCards #117
        IProgramCards.Card card = getLogic().getNextCardInHand();
        if (card == null)
            return;

        for (IProgramCards.CardType cardType : IProgramCards.CardType.values()) {
            if (cardType.equals(card.getCardType())) {
                cardTypeMethod.get(cardType).run();
            }
        }

    }

    // FIXME: Temp helper-method for playNextCard()
    private void setCardTypeMethod() {
        this.cardTypeMethod.put(IProgramCards.CardType.MOVE_1, () -> move(1));
        this.cardTypeMethod.put(IProgramCards.CardType.MOVE_2, () -> move(2));
        this.cardTypeMethod.put(IProgramCards.CardType.MOVE_3, () -> move(3));
        this.cardTypeMethod.put(IProgramCards.CardType.ROTATE_LEFT, () -> rotate(Direction.turnLeftFrom(getLogic().getDirection())));
        this.cardTypeMethod.put(IProgramCards.CardType.ROTATE_RIGHT, () -> rotate(Direction.turnRightFrom(getLogic().getDirection())));
        this.cardTypeMethod.put(IProgramCards.CardType.U_TURN, () -> {
            rotate(Direction.turnLeftFrom(getLogic().getDirection()));
            rotate(Direction.turnLeftFrom(getLogic().getDirection()));
        });
        this.cardTypeMethod.put(IProgramCards.CardType.BACKUP, () -> move(-1));
    }

    @Override
    public int peekNextCardInHand() {
        if (getLogic().peekNextCardInHand() == null)
            return 0;
        return getLogic().peekNextCardInHand().getPriority();
    }
    //endregion

    public int getPositionX() {
        return getPosition().x;
    }
    public int getPositionY() {
        return getPosition().y;
    }

    // TODO: Refactor to RobotModel, make it correlate with GameBoard(GameModel).

    public boolean hasVisitedAllFlags() {
        boolean visitedAll = true;
        for (boolean visitedFlag : visitedFlags) {
            visitedAll = visitedAll && visitedFlag;
        }
        return visitedAll;
    }

    // TODO: Refactor to RobotModel.

    public int getNextFlag() {
        for (int i = 0; i < visitedFlags.length; i++) {
            if (!visitedFlags[i]) {
                return i + 1;
            }
        }
        return -1;
    }

    // TODO: Refactor to RobotModel

    public void visitNextFlag() {
        this.setVictoryTexture();
        this.getView().setDirection(getPosition(), getLogic().getDirection());
        System.out.println("- Updated next flag to visit");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag - 1] = true;
        if (nextFlag == visitedFlags.length)
            System.out.println("- Congratulations you have collected all the flags, press 'W' to end the game.");
        else
            System.out.println("- Next flag to visit: " + (nextFlag + 1));
    }

    public void setNumberOfFlags(int flags) {
        this.visitedFlags = new boolean[flags];
    }
}
