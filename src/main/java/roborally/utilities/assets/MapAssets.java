package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.HashMap;

public class MapAssets implements Assets {
	HashMap<Integer, TiledMap> assets2;

	private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP2
			= new AssetDescriptor<>("maps/fruityLoops.tmx", TiledMap.class);
	private static final AssetDescriptor<TiledMap> SIGMUNDS_MAP
			= new AssetDescriptor<>("maps/Eight.tmx", TiledMap.class);
	private static final AssetDescriptor<TiledMap> LISES_MAP
			= new AssetDescriptor<>("maps/riskyExchangeBeginnerWithStartAreaVertical.tmx", TiledMap.class);


	public void loadAssets(AssetManager manager) {
		manager.load(SIGMUNDS_MAP2);
		manager.load(SIGMUNDS_MAP);
		manager.load(LISES_MAP);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		assets2 = new HashMap<>();
		assets2.put(0, manager.get(SIGMUNDS_MAP2));
		assets2.put(1, manager.get(LISES_MAP));
		assets2.put(2, manager.get(SIGMUNDS_MAP));
	}

	public TiledMap getMap(int mapID) {
		return assets2.get(mapID);
	}
}
