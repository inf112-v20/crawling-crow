package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundAssets implements Assets {
	public static final AssetDescriptor<Sound> SHOOT_LASER
			= new AssetDescriptor<>("sounds/fireLaser.mp3", Sound.class);
	public static final AssetDescriptor<Sound> STEPIN_LASER
			= new AssetDescriptor<>("sounds/stepIntoLaser.wav", Sound.class);
	public static final AssetDescriptor<Sound> ROBOT_HIT
			= new AssetDescriptor<>("sounds/robotHit.mp3", Sound.class);
	public static final AssetDescriptor<Sound> STEP1
			= new AssetDescriptor<>("sounds/step1.mp3", Sound.class);
	public static final AssetDescriptor<Sound> STEP2
			= new AssetDescriptor<>("sounds/step2.mp3", Sound.class);
	public static final AssetDescriptor<Sound> STEP3
			= new AssetDescriptor<>("sounds/step3.mp3", Sound.class);
	public static final AssetDescriptor<Music> SOUNDTRACK
			= new AssetDescriptor<>("sounds/soundTrack.mp3", Music.class);

	@Override
	public void loadAssets(AssetManager manager) {
		manager.load(SHOOT_LASER);
		manager.load(STEPIN_LASER);
		manager.load(STEP1);
		manager.load(STEP2);
		manager.load(STEP3);
		manager.load(ROBOT_HIT);
		manager.load(SOUNDTRACK);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		// Gets the files directly from the class variables.
	}
}
