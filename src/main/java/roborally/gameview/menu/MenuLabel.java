package roborally.gameview.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.asset.SoundAsset;

import java.util.Arrays;
import java.util.List;

public class MenuLabel {
	private Label playSongLabel;
	private Label previousMapLabel;
	private Label nextMapLabel;
	private Label gameSpeedLabel;
	private Label laserSpeedLabel;
	private Label volumeLabel;
	private List<Label> mainMenuLabels;
	private List<Label> mapMenuLabels;
	private Slider volumeSlider;
	private Skin skin;
	private int gSpeed;
	private int lSpeed;
	private Menu menu;
	private Music Song;

	public MenuLabel(Stage stage, Menu menu) {
		this.menu = menu;
		skin = new Skin(Gdx.files.internal("data/skin.json"));
		setLabels(stage);
		setOptionsListeners();
		setVolumeSlider();
		setOptionsListeners2();
		stage.addActor(gameSpeedLabel);
		stage.addActor(laserSpeedLabel);
		stage.addActor(volumeSlider);
		stage.addActor(playSongLabel);
		mainMenuLabels = Arrays.asList(gameSpeedLabel, laserSpeedLabel, volumeLabel, playSongLabel);
		mapMenuLabels = Arrays.asList(nextMapLabel, previousMapLabel);
	}

	private void setLabels(Stage stage) {
		gameSpeedLabel = new Label("Game Speed: fast", skin);
		laserSpeedLabel = new Label("Laser Speed: normal", skin);
		playSongLabel = new Label("Play a song: ", skin);
		volumeLabel = new Label("Volume ", skin);
		playSongLabel.setPosition(stage.getWidth() - 200, (stage.getHeight() / 1.5f) + 60);
		setVolumeSlider();
		volumeLabel.setPosition(playSongLabel.getX()
				, volumeSlider.getY() - (volumeLabel.getPrefHeight() * 2f));
		gSpeed = 0;
		lSpeed = 2;
		laserSpeedLabel.setPosition(playSongLabel.getX(),
				volumeLabel.getY() - (laserSpeedLabel.getPrefHeight() * 2f));
		gameSpeedLabel.setPosition(playSongLabel.getX(),
				laserSpeedLabel.getY() - (gameSpeedLabel.getPrefHeight() * 2f));
		previousMapLabel = new Label("<", skin);
		nextMapLabel = new Label(">", skin);
		previousMapLabel.setFontScale(2);
		nextMapLabel.setFontScale(2);
		float mapImagePosStartX = menu.getImageList().get("maps").get(0).getX();
		float mapImagePosEndX = menu.getImageList().get("maps").get(0).getWidth()
				+ menu.getImageList().get("maps").get(0).getX();
		previousMapLabel.setPosition(mapImagePosStartX - previousMapLabel.getPrefWidth() - 50
				, menu.centerVertical(previousMapLabel.getPrefHeight() - 50));
		nextMapLabel.setPosition(mapImagePosEndX + 50
				, menu.centerVertical(nextMapLabel.getPrefHeight() - 50));
		previousMapLabel.scaleBy(2);
		nextMapLabel.scaleBy(2);
		previousMapLabel.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (menu.getMapId() == 0)
					menu.setMapID(2);
				else
					menu.setMapID(menu.getMapId() - 1);
				return true;
			}
		});
		nextMapLabel.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (menu.getMapId() == 2)
					menu.setMapID(0);
				else
					menu.setMapID(menu.getMapId() + 1);
				return true;
			}
		});
	}
	private void setOptionsListeners() {
		gameSpeedLabel.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				gameSpeedLabel.setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				gameSpeedLabel.setColor(Color.WHITE);
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gSpeed == 0) {
					gameSpeedLabel.setText("Game Speed: fast");
					gSpeed++;
					menu.getEvents().setGameSpeed("fast");
				} else if (gSpeed == 1) {
					gameSpeedLabel.setText("Game Speed: fastest");
					gSpeed++;
					menu.getEvents().setGameSpeed("fastest");
				} else if (gSpeed == 2) {
					gameSpeedLabel.setText("Game Speed: normal");
					menu.getEvents().setGameSpeed("normal");
					gSpeed = 0;
				}
			}
		});
		laserSpeedLabel.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				laserSpeedLabel.setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				laserSpeedLabel.setColor(Color.WHITE);
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (lSpeed == 1) {
					laserSpeedLabel.setText("Laser Speed: normal");
					lSpeed++;
					menu.getEvents().setLaserSpeed("normal");
				} else if (lSpeed == 2) {
					laserSpeedLabel.setText("Laser Speed: fast");
					lSpeed = 0;
					menu.getEvents().setLaserSpeed("fast");
				} else if (lSpeed == 0) {
					laserSpeedLabel.setText("Laser Speed: slow");
					menu.getEvents().setLaserSpeed("slow");
					lSpeed++;
				}
			}
		});
	}
	private void setVolumeSlider() {
		Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
		Image birdImage = new Image(new Texture("assets/icons/Icon.png"));
		birdImage.setSize(15, 20);
		birdImage.scaleBy(100);
		Pixmap pixmap = new Pixmap(10, 8, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		sliderStyle.background = new Image(new Texture(pixmap)).getDrawable();
		sliderStyle.knob = birdImage.getDrawable();
		volumeSlider = new Slider(0f, 1f, 0.1f, false, sliderStyle);
		volumeSlider.setValue(0.6f);
		volumeSlider.setPosition(playSongLabel.getX(), playSongLabel.getY()
				- (volumeSlider.getHeight() * 2f));
	}

	private void setOptionsListeners2() {
		playSongLabel.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				playSongLabel.setColor(Color.GREEN);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				playSongLabel.setColor(Color.WHITE);
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				String text = playSongLabel.getText().toString();
				if ("Play a song: ".equals(text)) {
					Song = AssetManagerUtil.ASSET_MANAGER.get(SoundAsset.SOUNDTRACK);
					Song.setVolume(0.1f * SettingsUtil.VOLUME);
					Song.play();
					Song.setLooping(true);
					playSongLabel.setText("Click to stop playing.");
				} else {
					Song.stop();
					playSongLabel.setText("Play a song: ");
				}
			}
		});
		volumeSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent changeEvent, Actor actor) {
				SettingsUtil.VOLUME = volumeSlider.getValue();
				volumeLabel.setText("Volume " + Math.round(volumeSlider.getValue() * 100.0) / 100.0);
			}
		});
	}

	protected List<Label> getMainMenuLabels() {
		return this.mainMenuLabels;
	}

	protected List<Label> getMapMenuLabels() {
		return mapMenuLabels;
	}

	protected Slider getVolumeSlider() {
		return this.volumeSlider;
	}
}
