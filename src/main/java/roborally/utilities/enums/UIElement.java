package roborally.utilities.enums;

import com.badlogic.gdx.graphics.Texture;

import static roborally.utilities.AssetManagerUtil.*;

public enum UIElement {
    DAMAGE_TOKEN_GREEN(getDamageTokenGreen()),
    DAMAGE_TOKEN_RED(getDamageTokenRed()),
    DAMAGE_TOKEN_CARD_GREEN(getDamageTokenCardGreen()),
    DAMAGE_TOKEN_CARD_RED(getDamageTokenCardRed()),
    REBOOT_ACTIVE(getRebootActive()),
    REBOOT_INACTIVE(getRebootInactive()),
    DONE_BUTTON(getDoneButton()),
    DONE_BUTTON_PRESSED(getDoneButtonPressed()),
    POWERED_DOWN(getPowerDownButton()),
    POWERED_ON(getPoweredDown()),
    POWERING_DOWN(getPoweringDown());

    private final Texture texture;

    UIElement(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
