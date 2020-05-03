package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ViewAssets implements Assets{
	HashMap<String, Texture> assets;
	
	//region Buttons
	public static final AssetDescriptor<Texture> DONE_BUTTON = new AssetDescriptor<>("ui-elements/buttons/done-button.png", Texture.class);
	public static final AssetDescriptor<Texture> DONE_BUTTON_PRESSED = new AssetDescriptor<>("ui-elements/buttons/done-button-pressed.png", Texture.class);
	public static final AssetDescriptor<Texture> DONE_BUTTON_HOVER = new AssetDescriptor<>("ui-elements/buttons/done-button-hover.png", Texture.class);
	public static final AssetDescriptor<Texture> EXIT_BUTTON = new AssetDescriptor<>("ui-elements/buttons/exit-button.png", Texture.class);
	public static final AssetDescriptor<Texture> RESTART_BUTTON = new AssetDescriptor<>("ui-elements/buttons/restart-button.png", Texture.class);
	public static final AssetDescriptor<Texture> RESTART_BUTTON_PRESSED = new AssetDescriptor<>("ui-elements/buttons/restart-button-pressed.png", Texture.class);
	//endregion
	
	public static final AssetDescriptor<Texture> REBOOT_ACTIVE = new AssetDescriptor<>("ui-elements/reboots/reboot-active.png", Texture.class);
	public static final AssetDescriptor<Texture> REBOOT_INACTIVE = new AssetDescriptor<>("ui-elements/reboots/reboot-inactive.png", Texture.class);

	//region Damage tokens
	public static final AssetDescriptor<Texture> DAMAGE_TOKEN_GREEN = new AssetDescriptor<>("ui-elements/damagetokens/damage-token.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TOKEN_RED = new AssetDescriptor<>("ui-elements/damagetokens/damage-token-red.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TOKEN_CARD_GREEN = new AssetDescriptor<>("ui-elements/damagetokens/damage-token-card.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TOKEN_CARD_RED = new AssetDescriptor<>("ui-elements/damagetokens/damage-token-card-red.png", Texture.class);
	//endregion

	public static final AssetDescriptor<Texture> POWER_DOWN_BUTTON = new AssetDescriptor<>("ui-elements/buttons/power-down.png", Texture.class);
	public static final AssetDescriptor<Texture> POWERED_DOWN = new AssetDescriptor<>("ui-elements/buttons/powered-down.png", Texture.class);
	public static final AssetDescriptor<Texture> POWERING_DOWN = new AssetDescriptor<>("ui-elements/buttons/powering-down.png", Texture.class);

	public static final AssetDescriptor<Texture> FLAG_WHITE = new AssetDescriptor<>("ui-elements/flags/flag_white.png", Texture.class);

	@Override
	public void loadAssets(AssetManager manager) {
		manager.load(FLAG_WHITE);
		// Buttons
		manager.load(DONE_BUTTON);
		manager.load(DONE_BUTTON_PRESSED);
		manager.load(DONE_BUTTON_HOVER);
		manager.load(EXIT_BUTTON);
		manager.load(RESTART_BUTTON);
		manager.load(RESTART_BUTTON_PRESSED);
		manager.load(REBOOT_ACTIVE);
		manager.load(REBOOT_INACTIVE);
		manager.load(POWER_DOWN_BUTTON);
		manager.load(POWERED_DOWN);
		manager.load(POWERING_DOWN);
		
		// Damage tokens
		manager.load(DAMAGE_TOKEN_GREEN);
		manager.load(DAMAGE_TOKEN_RED);
		manager.load(DAMAGE_TOKEN_CARD_GREEN);
		manager.load(DAMAGE_TOKEN_CARD_RED);
		
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		assets = new HashMap<>();
		assets.put("done", manager.get(DONE_BUTTON));
		assets.put("done_pressed", manager.get(DONE_BUTTON_PRESSED));
		assets.put("done_hover", manager.get(DONE_BUTTON_HOVER));
		assets.put("restart", manager.get(RESTART_BUTTON));
		assets.put("restart_pressed", manager.get(RESTART_BUTTON_PRESSED));
		assets.put("exit", manager.get(EXIT_BUTTON));
		assets.put("reboot_active", manager.get(REBOOT_ACTIVE));
		assets.put("reboot_inacative", manager.get(REBOOT_INACTIVE));
		assets.put("damage_red", manager.get(DAMAGE_TOKEN_RED));
		assets.put("damage_green", manager.get(DAMAGE_TOKEN_GREEN));
		assets.put("damage_card_red", manager.get(DAMAGE_TOKEN_CARD_RED));
		assets.put("damage_card_green", manager.get(DAMAGE_TOKEN_CARD_GREEN));
		assets.put("power_down_button", manager.get(POWER_DOWN_BUTTON));
		assets.put("powered_down", manager.get(POWERED_DOWN));
		assets.put("powering_down", manager.get(POWERING_DOWN));
		assets.put("flag_white", manager.get(FLAG_WHITE));
	}
	public Texture getDoneButton() {
		return assets.get("done");
	}

	public Texture getDoneButtonPressed() {
		return assets.get("done_pressed");
	}

	public Texture getDoneButtonHover() {
		return assets.get("done_hover");
	}

	public Texture getRestartButton() {
		return assets.get("restart");
	}

	public Texture getRestartButtonPressed() {
		return assets.get("restart_pressed");
	}

	public Texture getExitButton() {
		return assets.get("exit");
	}

	public Texture getRebootActive() {
		return assets.get("reboot_active");
	}

	public Texture getRebootInactive() {
		return assets.get("reboot_inactive");
	}

	public Texture getDamageTokenGreen() {
		return assets.get("damage_green");
	}

	public Texture getDamageTokenRed() {
		return assets.get("damage_red");
	}

	public Texture getDamageTokenCardGreen() {
		return assets.get("damage_card_green");
	}

	public Texture getDamageTokenCardRed() {
		return assets.get("damage_card_red");
	}

	public Texture getPowerDownButton() {
		return assets.get("power_down_button");
	}

	public Texture getPoweredDown() {
		return assets.get("powered_down");
	}

	public Texture getPoweringDown() {
		return assets.get("powering_down");
	}

	public Texture getFlagWhite() {
		return assets.get("flag_white");
	}
}
