package roborally.gameview.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class Sliders {
	public static float dt = 0.00000000000001f;
	private ArrayList<Slider> sliders;
	private Slider.SliderStyle sliderStyle1;
	private float[] decoIterate;
	private int[] decoScale;
	private float[] decoWidth;
	private float superit;
	private float superit2;
	private boolean flip1;
	private boolean flip2;
	private boolean decoH1;
	private boolean decoH2;
	private boolean flip3;
	private boolean decoH3;

	public Sliders() {
		this.decoIterate = new float[3];
		this.decoScale = new int[3];
		this.decoWidth = new float[3];
		this.superit = 0;
		this.sliders = new ArrayList<>();
		this.decoScale[0] = 2;
		this.decoScale[1] = 2;
		this.decoScale[2] = 2;
	}
	public void abc () {
		Pixmap colur = new Pixmap(100, 10, Pixmap.Format.RGBA8888);
		Pixmap colur2 = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		Pixmap colur3 = new Pixmap(2, 2, Pixmap.Format.RGB888);
		Pixmap colur4 = new Pixmap(10, 1, Pixmap.Format.RGBA8888);
		colur3.setColor(Color.CHARTREUSE);
		colur3.fill();
		colur4.setColor(Color.ORANGE.set(0, 0, 0, 0));
		colur4.fill();
		Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
		sliderStyle1 = new Slider.SliderStyle();
		colur.setColor(Color.BLACK);
		colur.fill();
		colur2.setColor(Color.WHITE);
		colur2.fill();
		sliderStyle.background = new Image(new Texture(colur)).getDrawable();
		sliderStyle.knob = new Image(new Texture(colur2)).getDrawable();
		sliderStyle1.background = new Image(new Texture(colur4)).getDrawable();
		sliderStyle1.knob = new Image(new Texture(colur3)).getDrawable();
		Slider decor1 = new Slider(0, 10, 0.01f, false, sliderStyle1);
		decor1.setSize(170, 0.2f);
		decor1.setPosition(SettingsUtil.WINDOW_WIDTH / 2f, SettingsUtil.WINDOW_HEIGHT / 2f - 175);
		sliders.add(decor1);
		decoWidth[0] = 140;
		Image birdImage = new Image(new Texture("assets/icons/Icon.png"));
		birdImage.setSize(4, 4);
		sliderStyle1.knob = birdImage.getDrawable();
		Slider decor2 = new Slider(0, 10, 0.01f, false, sliderStyle1);
		decor2.setSize(225, 0.2f);
		decor2.setPosition(SettingsUtil.WINDOW_WIDTH / 2f - 10, SettingsUtil.WINDOW_HEIGHT / 2f - 35);
		sliders.add(decor2);
		Slider decor3 = new Slider(0, 10, 0.01f, false, sliderStyle1);
		decor3.setSize(180, 0.2f);
		decor3.setPosition(SettingsUtil.WINDOW_WIDTH / 2f + 10, SettingsUtil.WINDOW_HEIGHT / 2f - 110);
		sliders.add(decor3);
		decoWidth[1] = decor2.getWidth();
		decoWidth[2] = decor3.getWidth();
	}

	public void drawSliders(SpriteBatch batch) {
		for (int i = 0; i < sliders.size(); i++) {
			sliders.get(i).draw(batch, 1);
			superit2 -= Gdx.graphics.getDeltaTime() * 10;
			if (superit < 1) {
				superit += Gdx.graphics.getDeltaTime() * 10;
				sliders.get(i).setValue(decoIterate[i] += Gdx.graphics.getDeltaTime() * decoScale[i] * 2);
			} else if (superit2 < -10) {
				superit = superit2;
				superit2 = 0;
			}
			if (superit >= 1) {
				sliders.get(i).setValue(decoIterate[i] += Gdx.graphics.getDeltaTime() * decoScale[i] / 2);
				superit -= Gdx.graphics.getDeltaTime() * 10;
			}
			checkForFlips(i);
		}
	}

	private void checkForFlips(int i) {
		if (decoH1 && i == 0 || decoH2 && i == 1 || decoH3 && i == 2) {
			if (!flip1 && i == 0 || !flip2 && i == 1 || !flip3 && i == 2) {
				if (decoIterate[i] < sliders.get(i).getMinValue() + dt) {
					flipH(i);
				}
			}
			if (flip1 && decoIterate[i] > sliders.get(i).getMaxValue() - dt && i == 0 || flip2 && decoIterate[i] > sliders.get(i).getMaxValue() - dt && i == 1
					|| flip3 && decoIterate[i] > sliders.get(i).getMaxValue() - dt && i == 2) {
				flipH2(i);
			}
		} else {
			if (!flip1 && i == 0 || !flip2 && i == 1 || !flip3 && i == 2) {
				if (decoIterate[i] > sliders.get(i).getMaxValue() - dt) {
					flipV(i);
				}
			}
			if (flip1 && decoIterate[i] < sliders.get(i).getMinValue() + dt && i == 0 || flip2 && decoIterate[i] < sliders.get(i).getMinValue() + dt && i == 1 || flip3 && decoIterate[i] < sliders.get(i).getMinValue() + dt && i == 2) {
				flipV2(i);
			}
		}
	}
	public void flipV(int i) {
		Slider slider = new Slider(0, 5, 0.1f, true, sliderStyle1);
		slider.setSize(10, 30);
		slider.setPosition(sliders.get(i).getX() + sliders.get(i).getWidth() - slider.getWidth(), sliders.get(i).getY() - slider.getHeight());
		slider.setValue(5 - dt);
		decoIterate[i] = 5 - dt;
		decoScale[i] = -decoScale[i];
		sliders.set(i, slider);
		if (i == 0)
			decoH1 = true;
		if (i == 1)
			decoH2 = true;
		if (i == 2)
			decoH3 = true;
	}

	public void flipH(int i) {
		Slider slider = new Slider(0, 10, 0.01f, false, sliderStyle1);
		slider.setSize(decoWidth[i], 0.2f);
		slider.setPosition(sliders.get(i).getX() - slider.getWidth() + sliders.get(i).getWidth(), sliders.get(i).getY());
		slider.setValue(10 - dt);
		decoIterate[i] = 10 - dt;
		sliders.set(i, slider);
		if (i == 0) {
			decoH1 = false;
			flip1 = true;
		}
		if (i == 1) {
			decoH2 = false;
			flip2 = true;
		}
		if (i == 2) {
			decoH3 = false;
			flip3 = true;
		}
	}

	private void flipV2(int i) {
		Slider slider = new Slider(0, 5, 0.1f, true, sliderStyle1);
		slider.setSize(10, 30);
		slider.setPosition(sliders.get(i).getX(), sliders.get(i).getY());
		slider.setValue(0 + dt);
		decoIterate[i] = 0 + dt;
		decoScale[i] = Math.abs(decoScale[i]);
		sliders.set(i, slider);
		if (i == 0)
			decoH1 = true;
		if (i == 1)
			decoH2 = true;
		if (i == 2)
			decoH3 = true;
	}

	private void flipH2(int i) {
		Slider slider = new Slider(0, 10, 0.01f, false, sliderStyle1);
		slider.setSize(decoWidth[i], 0.2f);
		slider.setPosition(sliders.get(i).getX(), sliders.get(i).getY() + sliders.get(i).getHeight());
		slider.setValue(0 + dt);
		sliders.set(i, slider);
		decoIterate[i] = 0 + dt;
		decoScale[i] = Math.abs(decoScale[i]);
		if (i == 0) {
			decoH1 = false;
			flip1 = false;
		}
		if (i == 1) {
			decoH2 = false;
			flip2 = false;
		}
		if (i == 2) {
			decoH3 = false;
			flip3 = false;
		}

	}
}
