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
import roborally.utilities.enums.Direction;

import java.util.Random;

public class Robot implements Programmable {
    private IRobotView robotView;
    private RobotLogic robotLogic;
    private boolean[] visitedFlags;
    private Laser laser;
    private ILayers layers;
    private Listener listener;
    private LaserRegister laserRegister;

    // Constructor for testing the robot model.
    public Robot(RobotLogic robotLogic) {
        this.robotLogic = robotLogic;
    }

    public Robot(int x, int y, int robotID) {
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
        this.laserRegister = new LaserRegister();
        listener.listenLaser(x, y, getName(), laserRegister);
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
    public int[] move(int steps) {
        int[] moveValues = robotLogic.move(steps);
        for (int i = 0; i < Math.abs(steps); i++)
            moveRobot(moveValues[0], moveValues[1]);
        return this.robotLogic.move(steps);
    }

    @Override
    public Direction rotate(String leftOrRight, int factor) {
        Direction direction = this.robotLogic.rotate(leftOrRight, factor);
        this.robotView.setDirection(getPosition(), direction);
        return direction;
    }

    public void moveRobot(int dx, int dy) {
        GridPoint2 pos = this.getPosition();
        GridPoint2 newPos = new GridPoint2(pos.x + dx, pos.y + dy);
        System.out.println("Old position: " + pos);
        // Checks for robots in its path before moving.
        if (!listener.listenCollision(pos.x, pos.y, dx, dy)) {
            if (this.robotView.moveRobot(pos.x, pos.y, dx, dy)) {
                clearRegister();
                this.setPosition(newPos);
                System.out.println("New position: " + newPos);
                if (listener.listenLaser(newPos.x, newPos.y, getName(), laserRegister))
                    robotLogic.takeDamage(1);
                if (layers.assertHoleNotNull(newPos.x, newPos.y)) {
                    robotLogic.takeDamage(10);
                    this.setLostTexture();
                }
                this.robotView.setDirection(getPosition(), robotLogic.getDirection());
            }
        } else
            System.out.println("New position: " + pos);
    }
    //endregion

    public void backToCheckPoint() {
        robotView.goToCheckPoint(this.getPosition(), robotLogic.getCheckPoint());
        this.robotLogic.backToCheckPoint();
    }

    public RobotLogic getModel() {
        return this.robotLogic.getModel();
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
    //endregion

    public void playNextCard() {
        Random r = new Random();
        IProgramCards.Card card = getModel().getNextCard();
        if (card == null)
            return;
        if (card.getCardType() == IProgramCards.CardTypes.MOVE_1) {
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP1);
            sound.play(0.25f);
            move(1);
        } else if (card.getCardType() == IProgramCards.CardTypes.MOVE_2) {
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP2);
            sound.play(0.25f);
            move(2);
        } else if (card.getCardType() == IProgramCards.CardTypes.MOVE_3) {
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP3);
            sound.play(0.25f);
            move(3);
        } else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_LEFT)
            rotate("L", 1);
        else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_RIGHT)
            rotate("R", 1);
        else if (card.getCardType() == IProgramCards.CardTypes.U_TURN) {
            if (r.nextInt(2) == 1)
                rotate("L", 2);
            else
                rotate("R", 2);
        } else if (card.getCardType() == IProgramCards.CardTypes.BACKUP)
            move(-1);
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
