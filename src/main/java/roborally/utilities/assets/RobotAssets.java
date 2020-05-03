package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class RobotAssets implements Assets {
	HashMap<Integer, Texture> assets;

	private static final AssetDescriptor<Texture> ANGRY
			= new AssetDescriptor<>("robots/Angry.png", Texture.class);
	private static final AssetDescriptor<Texture> BLUE
			= new AssetDescriptor<>("robots/Blue.png", Texture.class);
	private static final AssetDescriptor<Texture> GREEN
			= new AssetDescriptor<>("robots/Green.png", Texture.class);
	private static final AssetDescriptor<Texture> ORANGE
			= new AssetDescriptor<>("robots/Orange.png", Texture.class);
	private static final AssetDescriptor<Texture> PINK
			= new AssetDescriptor<>("robots/Pink.png", Texture.class);
	private static final AssetDescriptor<Texture> PURPLE
			= new AssetDescriptor<>("robots/Purple.png", Texture.class);
	private static final AssetDescriptor<Texture> RED
			= new AssetDescriptor<>("robots/Red.png", Texture.class);
	private static final AssetDescriptor<Texture> YELLOW
			= new AssetDescriptor<>("robots/Yellow.png", Texture.class);

	@Override
	public void loadAssets(AssetManager manager) {
		manager.load(ANGRY);
		manager.load(BLUE);
		manager.load(GREEN);
		manager.load(ORANGE);
		manager.load(PINK);
		manager.load(PURPLE);
		manager.load(RED);
		manager.load(YELLOW);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		assets = new HashMap<>();
		assets.put(0, manager.get(ANGRY));
		assets.put(1, manager.get(BLUE));
		assets.put(2, manager.get(GREEN));
		assets.put(3, manager.get(ORANGE));
		assets.put(4, manager.get(PINK));
		assets.put(5, manager.get(PURPLE));
		assets.put(6, manager.get(RED));
		assets.put(7, manager.get(YELLOW));
	}

	public Texture getRobot(int i) {
		return assets.get(i);
	}
}
