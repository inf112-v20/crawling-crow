package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetManager;

import java.lang.reflect.Type;
import java.util.HashMap;

public interface Assets {
	HashMap<Object, Object> assets = new HashMap<>();

	void loadAssets(AssetManager manager);

	void putAssetsInMap(AssetManager manager);

}
