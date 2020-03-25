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

public class Robot implements Programmable {
    private IRobotView robotView;
    private RobotLogic robotLogic;
    private boolean[] visitedFlags;
    private Laser laser;
    private ILayers layers;
    private Listener listener;
    private LaserRegister laserRegister;

    private HashMap<IProgramCards.CardType, Runnable> cardTypeMethod;

    // Constructor for testing the robot model.
    public Robot(RobotLogic robotLogic) {
        this.robotLogic = robotLogic;
    }

    public Robot(int x, int y, int robotID, LaserRegister laserRegister) {
        this.visitedFlags = new boolean[3];
        RobotLogic robotModel = new RobotLogic(AssetManagerUtil.getRobotName());
        IRobotView robotView = new RobotView(x, y);
        this.robotLogic = robotModel;
        this.robotView = robotView;
        setPosition(new GridPoint2(x, y));
        this.setTextureRegion(robotID);
        laser = new Laser(0);
        this.robotLogic.setCheckPoint(x, y);
        this.layers = new Layers();
        this.listener = new Listener(layers);
        listener.listenLaser(x, y, getName(), laserRegister);
        this.laserRegister = laserRegister;

        this.cardTypeMethod = new HashMap<>();
        setCardTypeMethod();
    }

    @Override
    public String getName() {
        return this.robotLogic.getName();
    }

    //region Position
    @Override
    public GridPoint2 getPosition() {
        return this.robotLogic.getPosition();
    }

    @Override
    public void setPosition(GridPoint2 newPosition) {
        this.robotLogic.setPosition(newPosition);
    }
    //endregion

    //region Movement
    @Override
    public void move(int steps) {
        GridPoint2 oldPos = getPosition().cpy();
        GridPoint2 step = getLogic().getDirection().getStep();

        // Perform all movement
        for (int i = 0; i < Math.abs(steps); i++)
            tryToMove(step);

        // Play movement sound
        playSoundWalking(oldPos);

        // Taking damage
        // TODO: This should be called from Game during the correct phase, not here.
        if (listener.listenLaser(getPosition().x, getPosition().y, getName(), laserRegister))
            robotLogic.takeDamage(1);
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
    public void rotate(Direction direction) {
        this.robotLogic.rotate(direction);
        this.robotView.setDirection(getPosition(), direction);
    }

    public void tryToMove(GridPoint2 step) {
        int dx = step.x;
        int dy = step.y;
        GridPoint2 oldPos = getPosition();
        GridPoint2 newPos = oldPos.cpy().add(step);

        System.out.println("Old position: " + oldPos);

        // Check if the robot is not colliding with something
        if(!listener.listenCollision(oldPos, step)){

            // Checks that robot does not tries to move out of the map
            if (this.robotView.moveRobot(oldPos, step)) {

                // Update pos
                this.setPosition(newPos);
                System.out.println("New position: " + newPos);

                // Check if you are standing in a hole
                if (layers.assertHoleNotNull(newPos.x, newPos.y)) {
                    robotLogic.takeDamage(10);
                    this.setLostTexture();
                }

                // Updates texture to reflect the robots direction
                // TODO: Move this to setLostTexture?? Not sure
                this.robotView.setDirection(getPosition(), robotLogic.getDirection());
            }
        } else
            // Robot does not move
            System.out.println("New position: " + oldPos);
    }
    //endregion

    public void backToCheckPoint() {
        robotView.goToCheckPoint(this.getPosition(), robotLogic.getCheckPoint());
        this.robotLogic.backToCheckPoint();
    }

    public RobotLogic getLogic() {
        return this.robotLogic.getLogic();
    }


    public void fireLaser() {
        laser.fireLaser(getPosition(), this.robotLogic.getDirectionID());
    }

    public Laser getLaser() {
        return this.laser;
    }

    //region Texture
    public void setTextureRegion(int i) {
        this.robotView.setTextureRegion(i);
    }

    public TextureRegion[][] getTexture() {
        return this.robotView.getTextureRegion();
    }

    public void setWinTexture() {
        this.robotView.setWinTexture(getPosition());
    }

    public void setLostTexture() {
        this.robotView.setLostTexture(getPosition());

    }

    public void deleteRobot() {
        this.layers.setRobotCell(getPosition(), null);
        this.setPosition(SettingsUtil.GRAVEYARD);
        clearRegister();
    }
    //endregion

    public void checkForLaser() {
        if (listener.listenLaser(getPosition().x, getPosition().y, getName(), laserRegister))
            robotLogic.takeDamage(1);
    }

    public void playNextCard() {
        //TODO: Add hashmap and move the hashmap to ProgramCards #117
        IProgramCards.Card card = getLogic().getNextCard();
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
        this.setWinTexture();
        this.robotView.setDirection(getPosition(), robotLogic.getDirection());
        System.out.println("updated flag visited");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag - 1] = true;
        if (nextFlag == visitedFlags.length)
            System.out.println("Congratulations you have collected all the flags, press 'W' to win the game.");
        else
            System.out.println("Next flag to visit: " + (nextFlag + 1));
    }

    public void clearRegister() {
        laserRegister.updateLaser(getName(), getPosition());
    }
}
