package roborally.game.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.Robot;
import roborally.ui.gameboard.Layers;
import roborally.ui.robot.IUIRobot;

public class GameBoard implements IGameBoard {

    private Layers layers;

    public GameBoard(Layers layers) {
        this.layers = layers;
    }

    @Override
    public boolean onFlag(IUIRobot uiRobot) {
        if (!layers.contains("Flag"))
            return false;
        return layers.getFlag().getCell(uiRobot.getRobot().getPositionX(), uiRobot.getRobot().getPositionY()) != null;
    }

    @Override
    public boolean onHole(IUIRobot uiRobot) {
        if (layers.contains("bug"))
            if(layers.getBug().getCell(uiRobot.getRobot().getPositionX(), uiRobot.getRobot().getPositionY()) != null)
                return true;
        if (!layers.contains("Hole"))
            return false;
        return layers.getHole().getCell(uiRobot.getRobot().getPositionX(), uiRobot.getRobot().getPositionY()) != null;
    }

    @Override
    public boolean canMove(Robot robot, Vector2 pos) {
        return false;
    }
}
