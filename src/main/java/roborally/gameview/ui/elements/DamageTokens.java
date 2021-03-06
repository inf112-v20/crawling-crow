package roborally.gameview.ui.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.IRobot;
import roborally.gameview.ui.UIElements;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

import static roborally.utilities.SettingsUtil.STAGE_WIDTH;
import static roborally.utilities.enums.UIElement.DAMAGE_TOKEN_GREEN;
import static roborally.utilities.enums.UIElement.DAMAGE_TOKEN_RED;

public class DamageTokens implements IStats {
    private ArrayList<Image> damageTokens;

    public void update(IRobot robot) {
        clear();

        set(Math.max(robot.getLogic().getHealth(), 0));

        float damageTokensWidth = get().size() * (DAMAGE_TOKEN_GREEN.getTexture().getWidth() / SettingsUtil.UI_ELEMENT_SCALE);
        float damageTokenListFixedPosX = (STAGE_WIDTH / 2f) - (damageTokensWidth / 2f);

        int index = 0;
        for (Image damageToken : get()) {
            damageToken.setY(150);
            if (index == 0) {
                damageToken.setX(damageTokenListFixedPosX -= damageToken.getWidth());
            }
            damageToken.setX(damageTokenListFixedPosX += damageToken.getWidth());
            index++;
        }
    }

    @Override
    public ArrayList<Image> get() {
        return damageTokens;
    }

    @Override
    public void clear() {
        this.damageTokens = new ArrayList<>();
    }

    private void set(int availableHealth) {
        for (int i = 0; i < availableHealth; i++) {
            this.damageTokens.add(UIElements.get(DAMAGE_TOKEN_GREEN));
        }

        if (availableHealth < SettingsUtil.ROBOT_MAX_HEALTH) {
            for (int i = 0; i < (SettingsUtil.ROBOT_MAX_HEALTH - availableHealth); i++) {
                this.damageTokens.add(UIElements.get(DAMAGE_TOKEN_RED));
            }
        }
    }
}
