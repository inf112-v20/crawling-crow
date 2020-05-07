package roborally.utilities.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.HashMap;

public class MapAsset implements ManageableAsset {
	private HashMap<Integer, TiledMap> mapAssets;
	private HashMap<Integer, Texture> backgroundAssets;

	private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP2
			= new AssetDescriptor<>("maps/fruityLoops.tmx", TiledMap.class);
	private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP
			= new AssetDescriptor<>("maps/Eight.tmx", TiledMap.class);
	private static final AssetDescriptor<TiledMap> LISES_MAP
			= new AssetDescriptor<>("maps/riskyExchangeBeginnerWithStartAreaVertical.tmx", TiledMap.class);

	private static final AssetDescriptor<Texture> SIGMUND_1_BACKGROUND
			= new AssetDescriptor<>("ui-elements/sigmund_background.png", Texture.class);
	private static final AssetDescriptor<Texture> SIGMUND_2_BACKGROUND
			= new AssetDescriptor<>("ui-elements/sigmund_background2.png", Texture.class);
	private static final AssetDescriptor<Texture> LISE_BACKGROUND
			= new AssetDescriptor<>("ui-elements/lise_background.png", Texture.class);

	public void loadAssets(AssetManager manager) {
		manager.load(SIGMUNDS_MAP2);
		manager.load(SIGMUNDS_MAP);
		manager.load(LISES_MAP);

		manager.load(LISE_BACKGROUND);
		manager.load(SIGMUND_1_BACKGROUND);
		manager.load(SIGMUND_2_BACKGROUND);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		mapAssets = new HashMap<>();
		backgroundAssets = new HashMap<>();
		mapAssets.put(0, manager.get(SIGMUNDS_MAP));
		mapAssets.put(1, manager.get(LISES_MAP));
		mapAssets.put(2, manager.get(SIGMUNDS_MAP2));

		backgroundAssets.put(0, manager.get(SIGMUND_1_BACKGROUND));
		backgroundAssets.put(1, manager.get(LISE_BACKGROUND));
		backgroundAssets.put(2, manager.get(SIGMUND_2_BACKGROUND));
	}

	public TiledMap getMap(int mapID) {
		return mapAssets.get(mapID);
	}

	public Texture getBackground(int backgroundID) { return backgroundAssets.get(backgroundID); }
}
