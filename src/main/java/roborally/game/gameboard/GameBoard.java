package roborally.game.gameboard;

import com.badlogic.gdx.math.Vector2;
import roborally.game.objects.Robot;
import roborally.ui.robot.IUIRobot;

public class GameBoard implements IGameBoard {
    @Override
    public Robot onFlag() {
        return null;
    }

    @Override
    public Robot onHole() {
        return null;
    }

    @Override
    public Boolean canMove(Robot robot, Vector2 pos) {
        return null;
    }
}
