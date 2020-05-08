package roborally.utilities.enums;

import com.badlogic.gdx.graphics.Texture;

import static roborally.utilities.AssetManagerUtil.getUIElements;

public enum UIElement {
    DAMAGE_TOKEN_GREEN(getUIElements().getDamageTokenGreen()),
    DAMAGE_TOKEN_RED(getUIElements().getDamageTokenRed()),
    REBOOT_ACTIVE(getUIElements().getRebootActive()),
    REBOOT_INACTIVE(getUIElements().getRebootInactive()),
    DONE_BUTTON(getUIElements().getDoneButton()),
    DONE_BUTTON_PRESSED(getUIElements().getDoneButtonPressed()),
    DONE_BUTTON_HOVER(getUIElements().getDoneButtonHover()),
    POWERED_DOWN(getUIElements().getPowerDownButton()),
    POWERED_ON(getUIElements().getPoweredDown()),
    POWERING_DOWN(getUIElements().getPoweringDown()),
    FLAG_WHITE(getUIElements().getFlagWhite()),
    RESTART_BUTTON(getUIElements().getRestartButton()),
    RESTART_BUTTON_PRESSED(getUIElements().getRestartButtonPressed()),
    EXIT_BUTTON(getUIElements().getExitButton());
    private final Texture texture;

    UIElement(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
