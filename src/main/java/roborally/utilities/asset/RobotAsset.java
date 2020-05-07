package roborally.utilities.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class RobotAsset implements ManageableAsset {
	private HashMap<Integer, Texture> robotAssets;
	private HashMap<String, Texture> leaderboardTexture;

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

	// Leaderboards
	private static final AssetDescriptor<Texture> ANGRY_LB
			= new AssetDescriptor<>("robots/leaderboard/angry-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> BLUE_LB
			= new AssetDescriptor<>("robots/leaderboard/blue-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> GREEN_LB
			= new AssetDescriptor<>("robots/leaderboard/green-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> ORANGE_LB
			= new AssetDescriptor<>("robots/leaderboard/orange-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> PINK_LB
			= new AssetDescriptor<>("robots/leaderboard/pink-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> PURPLE_LB
			= new AssetDescriptor<>("robots/leaderboard/purple-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> RED_LB
			= new AssetDescriptor<>("robots/leaderboard/red-leadingboard.png", Texture.class);
	private static final AssetDescriptor<Texture> YELLOW_LB
			= new AssetDescriptor<>("robots/leaderboard/yellow-leadingboard.png", Texture.class);

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

		// Leaderboards
		manager.load(ANGRY_LB);
		manager.load(BLUE_LB);
		manager.load(GREEN_LB);
		manager.load(ORANGE_LB);
		manager.load(PINK_LB);
		manager.load(PURPLE_LB);
		manager.load(RED_LB);
		manager.load(YELLOW_LB);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		robotAssets = new HashMap<>();
		leaderboardTexture = new HashMap<>();
		robotAssets.put(0, manager.get(ANGRY));
		robotAssets.put(1, manager.get(BLUE));
		robotAssets.put(2, manager.get(GREEN));
		robotAssets.put(3, manager.get(ORANGE));
		robotAssets.put(4, manager.get(PINK));
		robotAssets.put(5, manager.get(PURPLE));
		robotAssets.put(6, manager.get(RED));
		robotAssets.put(7, manager.get(YELLOW));

		leaderboardTexture.put("Angry", manager.get(ANGRY_LB));
		leaderboardTexture.put("Blue", manager.get(BLUE_LB));
		leaderboardTexture.put("Green", manager.get(GREEN_LB));
		leaderboardTexture.put("Orange", manager.get(ORANGE_LB));
		leaderboardTexture.put("Pink", manager.get(PINK_LB));
		leaderboardTexture.put("Purple", manager.get(PURPLE_LB));
		leaderboardTexture.put("Red", manager.get(RED_LB));
		leaderboardTexture.put("Yellow", manager.get(YELLOW_LB));
	}

	public Texture getRobot(int i) {
		return robotAssets.get(i);
	}

	public Texture getLeaderBoardTexture(String name) {
		for(String key : leaderboardTexture.keySet())
			if(name.contains(key)) {
				return leaderboardTexture.get(key);
			}
		return null;
	}
}
