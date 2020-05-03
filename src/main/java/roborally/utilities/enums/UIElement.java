package roborally.utilities.enums;

import com.badlogic.gdx.graphics.Texture;

import static roborally.utilities.assets.AssetManagerUtil.*;

public enum UIElement {
    DAMAGE_TOKEN_GREEN(getViews().getDamageTokenGreen()),
    DAMAGE_TOKEN_RED(getViews().getDamageTokenRed()),
    DAMAGE_TOKEN_CARD_GREEN(getViews().getDamageTokenCardGreen()),
    DAMAGE_TOKEN_CARD_RED(getViews().getDamageTokenCardRed()),
    REBOOT_ACTIVE(getViews().getRebootActive()),
    REBOOT_INACTIVE(getViews().getRebootInactive()),
    DONE_BUTTON(getViews().getDoneButton()),
    DONE_BUTTON_PRESSED(getViews().getDoneButtonPressed()),
    DONE_BUTTON_HOVER(getViews().getDoneButtonHover()),
    POWERED_DOWN(getViews().getPowerDownButton()),
    POWERED_ON(getViews().getPoweredDown()),
    POWERING_DOWN(getViews().getPoweringDown()),
    FLAG_WHITE(getViews().getFlagWhite()),
    RESTART_BUTTON(getViews().getRestartButton()),
    RESTART_BUTTON_PRESSED(getViews().getRestartButtonPressed()),
    EXIT_BUTTON(getViews().getExitButton());
    private final Texture texture;

    UIElement(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
