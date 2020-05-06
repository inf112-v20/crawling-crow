package roborally.gameview.elements;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.IRobot;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

import static roborally.utilities.enums.UIElement.REBOOT_ACTIVE;
import static roborally.utilities.enums.UIElement.REBOOT_INACTIVE;

public class Reboots implements IElement {
    private ArrayList<Image> rebootsList;

    public void set(IRobot robot, Stage stage) {
        clear();

        update(robot.getLogic().getReboots() - 1);

        float xShift = (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
        float rebootsListFixedPosX = xShift - get().get(0).getWidth();

        for (Image reboot : get()) {
            reboot.setX(rebootsListFixedPosX += reboot.getWidth());
        }
    }

    private Image setAndGet(UIElement element) {
        Image reboot = new Image(element.getTexture());
        reboot.setPosition(0, 150);
        reboot.setSize(reboot.getPrefWidth() / SettingsUtil.UI_ELEMENT_SCALE, reboot.getPrefHeight() / SettingsUtil.UI_ELEMENT_SCALE);
        return reboot;
    }

    @Override
    public ArrayList<Image> get() {
        return rebootsList;
    }

    @Override
    public void clear() {
        this.rebootsList = new ArrayList<>();
    }

    public void update(int availableReboots) {
        for (int i = 0; i < availableReboots; i++) {
            this.rebootsList.add(setAndGet(REBOOT_ACTIVE));
        }

        if (availableReboots < (SettingsUtil.ROBOT_MAX_REBOOTS - 1)) {
            for (int i = 0; i < ((SettingsUtil.ROBOT_MAX_REBOOTS - 1) - availableReboots); i++) {
                this.rebootsList.add(setAndGet(REBOOT_INACTIVE));
            }
        }
    }
}