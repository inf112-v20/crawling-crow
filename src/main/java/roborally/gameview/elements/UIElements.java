package roborally.gameview.elements;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import roborally.game.robot.Robot;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

import static roborally.utilities.enums.UIElement.*;

public class UIElements {
    private final static float UI_ELEMENT_SCALE = 2.5f;
    private ArrayList<Image> reboots;
    private ArrayList<Image> damageTokens;

    private ImageButton powerDownButton;
    private boolean hasPowerDownBeenActivated;
    private boolean isPowerDownSetForNextRound;

    private Stage stage;

    public UIElements() {
        this.reboots = new ArrayList<>();
        this.damageTokens = new ArrayList<>();
        this.hasPowerDownBeenActivated = false;
    }

    private Image getAndSetUIElement(UIElement uiElement) {
        Image rebootType = new Image(uiElement.getTexture());
        rebootType.setPosition(0, 150);
        rebootType.setSize(rebootType.getPrefWidth() / UI_ELEMENT_SCALE, rebootType.getPrefHeight() / UI_ELEMENT_SCALE);
        return rebootType;
    }

    private void setReboots(int availableReboots) {
        for (int i = 0; i < availableReboots; i++) {
            this.reboots.add(getAndSetUIElement(REBOOT_ACTIVE));
        }

        if (availableReboots < (SettingsUtil.ROBOT_MAX_REBOOTS - 1)) {
            for (int i = 0; i < ((SettingsUtil.ROBOT_MAX_REBOOTS - 1) - availableReboots); i++) {
                this.reboots.add(getAndSetUIElement(REBOOT_INACTIVE));
            }
        }
    }

    public ArrayList<Image> getReboots() {
        return reboots;
    }

    private void clearReboots() {
        this.reboots = new ArrayList<>();
    }

    public void updateReboots(Robot robot) {
        clearReboots();

        setReboots(robot.getLogic().getReboots() - 1);

        float mapWidth = SettingsUtil.MAP_WIDTH / 2f;
        float rebootsWidth = getReboots().size() * (REBOOT_ACTIVE.getTexture().getWidth() / UI_ELEMENT_SCALE);
        float rebootsListFixedPosX = (stage.getWidth() / 2f) - (rebootsWidth / 2f) - mapWidth;

        for (Image reboot : getReboots()) {
            reboot.setX(rebootsListFixedPosX += reboot.getWidth());
        }
    }

    private void setDamageTokens(int availableHealth) {
        /*
            TODO: Should be displayed like this, not sure how yet.
            0       : red
            1 - 5   : card_green
            6 - 9   : green
         */
        for (int i = 0; i < availableHealth; i++) {
            this.damageTokens.add(getAndSetUIElement(DAMAGE_TOKEN_GREEN));
        }

        if (availableHealth < SettingsUtil.ROBOT_MAX_HEALTH) {
            for (int i = 0; i < (SettingsUtil.ROBOT_MAX_HEALTH - availableHealth); i++) {
                this.damageTokens.add(getAndSetUIElement(DAMAGE_TOKEN_RED));
            }
        }
    }

    public ArrayList<Image> getDamageTokens() {
        return damageTokens;
    }

    private void clearDamageTokens() {
        this.damageTokens = new ArrayList<>();
    }

    public void updateDamageTokens(Robot robot) {
        clearDamageTokens();
        // UNCOMMENT for debug
        //System.out.println(robot.getName() + "'s health is before updating damage tokens: " + robot.getLogic().getHealth());

        setDamageTokens(Math.max(robot.getLogic().getHealth(), 0));

        float damageTokensWidth = getDamageTokens().size() * (DAMAGE_TOKEN_GREEN.getTexture().getWidth() / UI_ELEMENT_SCALE);
        float damageTokenListFixedPosX = (stage.getWidth() / 2f) - (damageTokensWidth / 2f);

        int index = 0;
        for (Image damageToken : getDamageTokens()) {
            if (index == 0) {
                damageToken.setX(damageTokenListFixedPosX -= damageToken.getWidth());
            }
            damageToken.setX(damageTokenListFixedPosX += damageToken.getWidth());
            index++;
        }
    }

    /**
     * For debugging
     *
     * @param robot The user controlled robot
     */
    public void update(Robot robot) {
        updateReboots(robot);
        updateDamageTokens(robot);
    }

    public void setPowerDownButton(UIElement powerDownState) {
        powerDownButton = new ImageButton(new TextureRegionDrawable(powerDownState.getTexture()), new TextureRegionDrawable((POWERING_DOWN.getTexture())), new TextureRegionDrawable((POWERING_DOWN.getTexture())));

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float powerDownButtonFixedPosX = xShift - powerDownButton.getWidth();

        powerDownButton.setPosition(powerDownButtonFixedPosX, 130);

        powerDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Activated power down");
                powerDownButton.setChecked(true);
                isPowerDownSetForNextRound = true;
                hasPowerDownBeenActivated = true;
            }
        });
    }

    public ImageButton getPowerDownButton() {
        return powerDownButton;
    }

    // For checking if the button has been pressed
    public boolean hasPowerDownBeenActivated() {
        return hasPowerDownBeenActivated;
    }

    public void hasPowerDownBeenActivated(boolean state) {
        this.hasPowerDownBeenActivated = state;
    }

    public boolean isPowerDownSetForNextRound() {
        return isPowerDownSetForNextRound;
    }

    public void setPowerDownForNextRound(boolean state) {
        this.isPowerDownSetForNextRound = state;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
