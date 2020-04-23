package roborally.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class UIElements {
    private final static float REBOOT_IMAGE_UNIT_SCALE = 2.5f;
    private Image rebootActive;
    private Image rebootInActive;
    private ArrayList<Image> reboots;

    private Image damageTokenGreen;
    private Image damageTokenCardGreen;
    private Image damageTokenRed;
    private Image damageTokenCardRed;
    private ArrayList<Image> damageTokens;

    public UIElements() {
        this.reboots = new ArrayList<>();
        this.damageTokens = new ArrayList<>();
    }


    // TODO: Refactor
    public void setReboots(int availableReboots) {
        for (int i = 0; i < availableReboots; i++) {
            setRebootActive();
            this.reboots.add(getRebootActive());
        }

        /*if (availableReboots == 2) {
            setRebootInActive();
            this.reboots.add(getRebootInActive());
        } else if (availableReboots == 1) {
            setRebootInActive();
            this.reboots.add(getRebootInActive());
            setRebootInActive();
            this.reboots.add(getRebootInActive());
        } else if (availableReboots == 0) {
            for (int i = 0; i < 3; i++) {
                setRebootInActive();
                this.reboots.add(getRebootInActive());
            }
        }*/
    }

    public void setRebootActive() {
        this.rebootActive = new Image(AssetManagerUtil.getRebootActive());
        this.rebootActive.setPosition(0, 150);
        this.rebootActive.setSize(rebootActive.getPrefWidth() / REBOOT_IMAGE_UNIT_SCALE, rebootActive.getPrefHeight() / REBOOT_IMAGE_UNIT_SCALE);
    }

    public Image getRebootActive() {
        return rebootActive;
    }

    public void setRebootInActive() {
        this.rebootInActive = new Image(AssetManagerUtil.getRebootInactive());
        this.rebootInActive.setPosition(0, 150);
        this.rebootInActive.setSize(rebootInActive.getPrefWidth() / REBOOT_IMAGE_UNIT_SCALE, rebootInActive.getPrefHeight() / REBOOT_IMAGE_UNIT_SCALE);
    }

    public Image getRebootInActive() {
        return rebootInActive;
    }

    public ArrayList<Image> getReboots() {
        return reboots;
    }

    public void clearReboots() {
        this.reboots = new ArrayList<>();
    }

    public void updateReboots(Robot robot) {

        clearReboots();

        setReboots(robot.getLogic().getReboots() - 1);

        float mapWidth = SettingsUtil.MAP_WIDTH / 2f;
        float rebootsWidth = getReboots().size() * getRebootActive().getWidth();
        float rebootsListFixedPosX = (Gdx.graphics.getWidth() / 2f) - (rebootsWidth / 2f) - mapWidth;

        for (Image reboot : getReboots()) {
            reboot.setX(rebootsListFixedPosX += reboot.getWidth());
        }
    }

    public void setDamageTokens(int availableHealth) {
        for (int i = 0; i < availableHealth; i++) {
            setDamageTokenGreen();
            this.damageTokens.add(getDamageTokenGreen());
        }

        if (availableHealth < SettingsUtil.ROBOT_MAX_HEALTH) {
            for (int i = 0; i < (SettingsUtil.ROBOT_MAX_HEALTH - availableHealth); i++) {
                setDamageTokenRed();
                this.damageTokens.add(getDamageTokenRed());
            }
        }
    }

    private Image getDamageTokenGreen() {
        return damageTokenGreen;
    }

    private void setDamageTokenGreen() {
        this.damageTokenGreen = new Image(AssetManagerUtil.getDamageTokenGreen());
        this.damageTokenGreen.setPosition(0, 150);
        this.damageTokenGreen.setSize(damageTokenGreen.getPrefWidth() / REBOOT_IMAGE_UNIT_SCALE, damageTokenGreen.getPrefHeight() / REBOOT_IMAGE_UNIT_SCALE);
    }

    private Image getDamageTokenRed() {
        return damageTokenRed;
    }

    private void setDamageTokenRed() {
        this.damageTokenRed = new Image(AssetManagerUtil.getDamageTokenRed());
        this.damageTokenRed.setPosition(0, 150);
        this.damageTokenRed.setSize(damageTokenRed.getPrefWidth() / REBOOT_IMAGE_UNIT_SCALE, damageTokenRed.getPrefHeight() / REBOOT_IMAGE_UNIT_SCALE);
    }

    public ArrayList<Image> getDamageTokens() {
        return damageTokens;
    }

    public void clearDamageTokens() {
        this.damageTokens = new ArrayList<>();
    }

    public void updateDamageTokens(Robot robot) {
        clearDamageTokens();

        setDamageTokens(robot.getLogic().getHealth());

        float damageTokensWidth = getDamageTokens().size() * getDamageTokenGreen().getWidth();
        float damageTokenListFixedPosX = (Gdx.graphics.getWidth() / 2f) - (damageTokensWidth / 2f);

        for (Image damageToken : getDamageTokens()) {
            damageToken.setX(damageTokenListFixedPosX += getDamageTokenGreen().getWidth());
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
}
