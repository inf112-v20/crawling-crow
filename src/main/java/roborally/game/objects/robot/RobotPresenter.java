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

public class RobotPresenter implements Programmable {
    private IRobotView robotView;
    private RobotModel robotModel;
    private boolean[] visitedFlags;
    private Laser laser;
    private ILayers layers;
    private Listener listener;
    private LaserRegister laserRegister;

    // Constructor for testing the robot model.
    public RobotPresenter(RobotModel robotModel) {
        this.robotModel = robotModel;
    }

    public RobotPresenter(int x, int y, int cellId) {
        RobotModel robotModel = new RobotModel(AssetManagerUtil.getRobotName());
        IRobotView robotView = new RobotView(x, y);
        this.robotModel = robotModel;
        this.robotView = robotView;
        setPos(new GridPoint2(x, y));
        this.setTextureRegion(cellId);
        laser = new Laser(0);
        this.robotModel.setCheckPoint(x, y);
        this.layers = new Layers();
        this.listener = new Listener(layers);
        this.laserRegister = new LaserRegister();

    }

    public TextureRegion[][] getTexture() {
        return this.robotView.getTextureRegion();
    }

    public String getName() {
        return this.robotModel.getName();
    }


    public GridPoint2 getPos() {
        return this.robotModel.getPos();
    }


    public void setPos(GridPoint2 newPos) {
        this.robotModel.setPos(newPos);
    }

    public Direction rotate(String way, int factor) {
        Direction direction = this.robotModel.rotate(way, factor);
        this.robotView.setDirection(getPos(), direction);
        return direction;
    }

    public RobotModel getModel() {
        return this.robotModel.getModel();
    }

    public void backToCheckPoint() {
        robotView.goToCheckPoint(this.getPos(), robotModel.getCheckPoint());
        this.robotModel.backToCheckPoint();
    }

    public void fireLaser() {
        laser.fireLaser(getPos(), this.robotModel.getDirectionID());
    }

    public Laser getLaser() {
        return this.laser;
    }

    public void setTextureRegion(int i) {
        this.robotView.setTextureRegion(i);
    }

    public int[] move(int steps) {
        int[] moveValues = robotModel.move(steps);
        for (int i = 0; i < Math.abs(steps); i++)
            moveRobot(moveValues[0], moveValues[1]);
        return this.robotModel.move(steps);
    }

    public void moveRobot(int dx, int dy) {
        GridPoint2 pos = this.getPos();
        GridPoint2 newPos = new GridPoint2(pos.x + dx, pos.y + dy);
        System.out.println("Old position: " + pos);
        // Checks for robots in its path before moving.
        if (!listener.listenCollision(pos.x, pos.y, dx, dy)) {
            if (this.robotView.moveRobot(pos.x, pos.y, dx, dy)) {
                this.setPos(newPos);
                System.out.println("New position: " + newPos);
                if (listener.listenLaser(newPos.x, newPos.y, getName(), laserRegister))
                    robotModel.takeDamage(1);
                if (layers.assertHoleNotNull(newPos.x, newPos.y)) {
                    robotModel.takeDamage(1);
                    this.setLostTexture();
                }
                this.robotView.setDirection(getPos(), robotModel.getDirection());
            }
        } else
            System.out.println("New position: " + pos);
    }

    public void setWinTexture() {
        this.robotView.setWinTexture(getPos());
    }


    public void setLostTexture() {
        this.robotView.setLostTexture(getPos());
    }

    public void playNextCard() {
        Random r = new Random();
        IProgramCards.Card card = getModel().getNextCard();
        if (card == null)
            return;
        if (card.getCardType() == IProgramCards.CardTypes.MOVE_1) {
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP1);
            sound.play(0.25f);
            move(1);
        }
        else if (card.getCardType() == IProgramCards.CardTypes.MOVE_2) {
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP2);
            sound.play(0.25f);
            move(2);
        }
        else if (card.getCardType() == IProgramCards.CardTypes.MOVE_3) {
            Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEP3);
            sound.play(0.25f);
            move(3);
        }
        else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_LEFT)
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
        this.robotView.setDirection(getPos(), robotModel.getDirection());
        System.out.println("updated flag visited");
        int nextFlag = getNextFlag();
        visitedFlags[nextFlag - 1] = true;
        if (nextFlag == visitedFlags.length)
            System.out.println("Congratulations you have collected all the flags, press 'W' to win the game.");
        else
            System.out.println("Next flag to visit: " + (nextFlag + 1));
    }

    //TODO: Refactor to RobotModel

    public void setNumberOfFlags(int nFlags) {
        this.visitedFlags = new boolean[nFlags];
    }
}
