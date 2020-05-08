package roborally.utilities.asset;

import com.badlogic.gdx.assets.AssetManager;

public interface ManageableAsset {

	/**
	 * Loads all the assets into the manager.
	 * @param manager the AssetManager.
	 */
	void loadAssets(AssetManager manager);

	/**
	 * Puts all the assets into a hashmap for creating access to them.
	 * @param manager the AssetManager.
	 */
	void putAssetsInMap(AssetManager manager);

}
