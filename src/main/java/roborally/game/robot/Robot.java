package roborally.game.robot;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.cards.IProgramCards;
import roborally.game.gameboard.objects.laser.Laser;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.gameview.layout.ILayers;
import roborally.gameview.layout.Layers;
import roborally.listeners.Listener;
import roborally.gameview.robotview.IRobotView;
import roborally.gameview.robotview.RobotView;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.assets.SoundAssets;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;

import java.util.HashMap;

public class Robot implements IRobot {
    private IRobotView robotView;
    private IRobotLogic robotLogic;
    private Laser laser;
    private ILayers layers;
    private Listener listener;
    private LaserRegister laserRegister;
    private boolean reboot;

    private boolean powerDownNextRound;
    private boolean isFalling;

    private HashMap<IProgramCards.CardType, Runnable> cardToAction;
    private boolean isRobotInHole;

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
        bindCardsToAction();
    }

    @Override
    public String getName() {
        return getLogic().getName();
    }

    @Override
    public IRobotLogic getLogic() {
        return robotLogic;
    }

    @Override
    public IRobotView getView() {
        return robotView;
    }

    //region Position
    @Override
    public GridPoint2 getPosition() {
        return getLogic().getPosition();
    }

    @Override
    public void setPosition(GridPoint2 newPosition) {
        getLogic().setPosition(newPosition);
    }

    @Override
    public void backToArchiveMarker() {
        if (reboot) {
            getView().goToArchiveMarker(getPosition(), getLogic().getArchiveMarker());
            getLogic().backToArchiveMarker();
            clearLaserRegister();
            reboot = false;
            isRobotInHole(false);
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
        if(isFalling())
            return;
        System.out.println("\n" + getName() + " trying to move:");
        GridPoint2 oldPos = getPosition();
        GridPoint2 newPos = oldPos.cpy().add(possiblePosition);

        System.out.println("\t- Old position: " + oldPos);

        // Check if the robot is not colliding with something
        if (!listener.listenCollision(oldPos, possiblePosition)) {

            // Checks that robot does not tries to move out of the map
            if (getView().canMoveRobot(oldPos, possiblePosition)) {
                // Robot moving outside map
                if(robotView.isRobotInGraveyard(newPos)) {
                    setPosition(SettingsUtil.GRAVEYARD);
                    takeDamage(SettingsUtil.MAX_DAMAGE);
                    return;
                }
                // Update pos
                setPosition(newPos);
                System.out.println("\t- New position: " + newPos);
                System.out.println("\t- Health: " + getLogic().getHealth());

                // Check if Robot is standing on a hole
                if (layers.layerNotNull(LayerName.HOLE, newPos)) {
                    isRobotInHole(false);
                    takeDamage(SettingsUtil.MAX_DAMAGE);
                    System.out.println("\t\t- Robot went into a hole");
                    setFalling(true);
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
            sound = AssetManagerUtil.ASSET_MANAGER.get(SoundAssets.STEP1);
            sound.play(0.25f * SettingsUtil.VOLUME);
        } else if (getPosition().dst(oldPos) == 2) {
            sound = AssetManagerUtil.ASSET_MANAGER.get(SoundAssets.STEP2);
            sound.play(0.25f * SettingsUtil.VOLUME);
        } else if (getPosition().dst(oldPos) == 3) {
            sound = AssetManagerUtil.ASSET_MANAGER.get(SoundAssets.STEP3);
            sound.play(0.25f * SettingsUtil.VOLUME);
        }
    }

    @Override
    public void rotate(Direction newDirection) {
        getLogic().rotate(newDirection);
        getView().setDirection(getPosition(), newDirection);
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
        if (!getLogic().getPowerDown()) {
            laser.fireLaser(getPosition(), getLogic().getDirection().getID());
        }
    }

    @Override
    public Laser getLaser() {
        return laser;
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
        getView().setTextureRegion(index);
    }

    @Override
    public TextureRegion[][] getTexture() {
        return getView().getTextureRegion();
    }

    @Override
    public void setVictoryTexture() {
        getView().setVictoryTexture(getPosition());
    }

    @Override
    public void setDamageTakenTexture() {
        getView().setDamageTakenTexture(getPosition());
        getView().setDirection(getPosition(), getLogic().getDirection());
    }
    //endregion

    @Override
    public void deleteRobot() {
        layers.setRobotTexture(getPosition(), null);
        setPosition(SettingsUtil.GRAVEYARD);
        clearLaserRegister();
        setFalling(false);
    }

    //region Program cards
    @Override
    public void playNextCard() {
        System.out.println(getName() + "'s power down status: " + getLogic().getPowerDown());
        IProgramCards.Card card = getLogic().getNextCardInRegister();
        if (card == null || getLogic().getPowerDown())
            return;

        for (IProgramCards.CardType cardType : IProgramCards.CardType.values()) {
            if (cardType.equals(card.getCardType())) {
                cardToAction.get(cardType).run();
            }
        }

    }

    private void bindCardsToAction() {
        cardToAction = new HashMap<>();
        cardToAction.put(IProgramCards.CardType.MOVE_1, () -> move(1));
        cardToAction.put(IProgramCards.CardType.MOVE_2, () -> move(2));
        cardToAction.put(IProgramCards.CardType.MOVE_3, () -> move(3));
        cardToAction.put(IProgramCards.CardType.ROTATE_LEFT, () -> rotate(Direction.turnLeftFrom(getLogic().getDirection())));
        cardToAction.put(IProgramCards.CardType.ROTATE_RIGHT, () -> rotate(Direction.turnRightFrom(getLogic().getDirection())));
        cardToAction.put(IProgramCards.CardType.U_TURN, () -> {
            rotate(Direction.turnLeftFrom(getLogic().getDirection()));
            rotate(Direction.turnLeftFrom(getLogic().getDirection()));
        });
        cardToAction.put(IProgramCards.CardType.BACKUP, () -> move(-1));
    }

    @Override
    public boolean isFalling() {
        return isFalling;
    }

    @Override
    public void setFalling(boolean falling) {
        this.isFalling = falling;
    }

    @Override
    public int peekNextCardInRegister() {
        if (getLogic().peekNextCardInRegister() == null)
            return 0;
        return getLogic().peekNextCardInRegister().getPriority();
    }
    //endregion

    public int getPositionX() {
        return getPosition().x;
    }

    public int getPositionY() {
        return getPosition().y;
    }

    public void visitNextFlag() {
        setVictoryTexture();
        getView().setDirection(getPosition(), getLogic().getDirection());
        getLogic().visitNextFlag();
    }

    @Override
    public boolean isRobotInHole() {
        return isRobotInHole;
    }

    @Override
    public void isRobotInHole(boolean state) {
        this.isRobotInHole = state;
    }
}