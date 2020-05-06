package roborally.gameview.elements;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.IRobot;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

import static roborally.utilities.enums.UIElement.DAMAGE_TOKEN_GREEN;
import static roborally.utilities.enums.UIElement.DAMAGE_TOKEN_RED;

public class DamageTokens implements IElement {
    private ArrayList<Image> damageTokens;

    public void set(IRobot robot, Stage stage) {
        clear();

        update(Math.max(robot.getLogic().getHealth(), 0));

        float damageTokensWidth = get().size() * (DAMAGE_TOKEN_GREEN.getTexture().getWidth() / SettingsUtil.UI_ELEMENT_SCALE);
        float damageTokenListFixedPosX = (stage.getWidth() / 2f) - (damageTokensWidth / 2f);

        int index = 0;
        for (Image damageToken : get()) {
            if (index == 0) {
                damageToken.setX(damageTokenListFixedPosX -= damageToken.getWidth());
            }
            damageToken.setX(damageTokenListFixedPosX += damageToken.getWidth());
            index++;
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
        return damageTokens;
    }

    @Override
    public void clear() {
        this.damageTokens = new ArrayList<>();
    }

    private void update(int availableHealth) {
        /*
            TODO: Should be displayed like this, not sure how yet.
            0       : red
            1 - 5   : card_green
            6 - 9   : green
         */
        for (int i = 0; i < availableHealth; i++) {
            this.damageTokens.add(setAndGet(DAMAGE_TOKEN_GREEN));
        }

        if (availableHealth < SettingsUtil.ROBOT_MAX_HEALTH) {
            for (int i = 0; i < (SettingsUtil.ROBOT_MAX_HEALTH - availableHealth); i++) {
                this.damageTokens.add(setAndGet(DAMAGE_TOKEN_RED));
            }
        }
    }
}
