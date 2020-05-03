package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class MenuAssets implements Assets {
	private HashMap<String, Texture> assets;

	private static final AssetDescriptor<Texture> MENU
			= new AssetDescriptor<>("menu/new-menu.png", Texture.class);
	private static final AssetDescriptor<Texture> BUTTONS
			= new AssetDescriptor<>("menu/buttons.png", Texture.class);
	private static final AssetDescriptor<Texture> MAP_BUTTON
			= new AssetDescriptor<>("menu/mapButton.png", Texture.class);

	@Override
	public void loadAssets(AssetManager manager) {
		manager.load(MENU);
		manager.load(BUTTONS);
		manager.load(MAP_BUTTON);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		assets = new HashMap<>();
		assets.put("main_menu", manager.get(MENU));
		assets.put("buttons", manager.get(BUTTONS));
		assets.put("map_button", manager.get(MAP_BUTTON));
	}

	public Texture getMainMenu() {
		return assets.get("main_menu");
	}

	public Texture getButtons() {
		return assets.get("buttons");
	}

	public Texture getMapButton() {
		return assets.get("map_button");
	}
}
