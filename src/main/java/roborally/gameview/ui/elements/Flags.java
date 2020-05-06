package roborally.gameview.ui.elements;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.IRobot;
import roborally.gameview.ui.UIElements;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

import static roborally.utilities.enums.UIElement.FLAG_WHITE;

public class Flags implements IElement {
    private ArrayList<Image> flags;

    public void set(IRobot robot, Stage stage) {
        clear();
        int collectedFlags = 0;

        for (boolean flag : robot.getLogic().getVisitedFlags()) {
            if (flag) {
                collectedFlags++;
            }
        }

        update(collectedFlags, stage);
    }

    @Override
    public ArrayList<Image> get() {
        return flags;
    }

    @Override
    public void clear() {
        this.flags = new ArrayList<>();
    }

    private void update(int collectedFlags, Stage stage) {
        for (int i = 0; i < collectedFlags; i++) {
            this.flags.add(UIElements.get(FLAG_WHITE));
        }

        float flagsWidth = get().size() * (FLAG_WHITE.getTexture().getWidth() / SettingsUtil.UI_ELEMENT_SCALE);
        float flagsListFixedPosX = (stage.getWidth() / 3f) - (flagsWidth / 2f);

        int index = 0;
        for (Image flag : get()) {
            flag.setY((stage.getHeight()) - (SettingsUtil.MAP_HEIGHT / 4f) - (flag.getHeight() / 2f));
            if (index == 0) {
                flag.setX(flagsListFixedPosX -= flag.getWidth());
            }
            flag.setX(flagsListFixedPosX += flag.getWidth());
            index++;
        }
    }
}
