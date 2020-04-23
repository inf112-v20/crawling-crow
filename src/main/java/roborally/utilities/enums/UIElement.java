package roborally.utilities.enums;

import com.badlogic.gdx.graphics.Texture;
import roborally.utilities.AssetManagerUtil;

public enum UIElement {
    DAMAGE_TOKEN_GREEN(AssetManagerUtil.getDamageTokenGreen()),
    DAMAGE_TOKEN_RED(AssetManagerUtil.getDamageTokenRed()),
    DAMAGE_TOKEN_CARD_GREEN(AssetManagerUtil.getDamageTokenCardGreen()),
    DAMAGE_TOKEN_CARD_RED(AssetManagerUtil.getDamageTokenCardRed()),
    REBOOT_ACTIVE(AssetManagerUtil.getRebootActive()),
    REBOOT_INACTIVE(AssetManagerUtil.getRebootInactive());

    private final Texture texture;

    UIElement(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
