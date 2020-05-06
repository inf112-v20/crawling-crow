package roborally.gameview.ui.elements.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.jetbrains.annotations.NotNull;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import static roborally.utilities.enums.UIElement.POWERING_DOWN;

public class PowerDownButton implements IButton {
    private ImageButton powerDownButton;
    private boolean isActivated;
    private boolean isActivatedForNextRound;

    public void set(@NotNull UIElement powerDownState, Stage stage) {
        powerDownButton = new ImageButton(new TextureRegionDrawable(powerDownState.getTexture()), new TextureRegionDrawable((POWERING_DOWN.getTexture())), new TextureRegionDrawable((POWERING_DOWN.getTexture())));

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float powerDownButtonFixedPosX = xShift - powerDownButton.getWidth();

        powerDownButton.setPosition(powerDownButtonFixedPosX, 130);

        powerDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (SettingsUtil.DEBUG_MODE) System.out.println("Activated power down");
                powerDownButton.setChecked(true);
                isActivatedForNextRound = true;
                isActivated = true;
            }
        });
    }

    @Override
    public ImageButton get() {
        return powerDownButton;
    }

    @Override
    public void clear() {
        powerDownButton.clear();
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean state) {
        this.isActivated = state;
    }

    public boolean isForNextRound() {
        return isActivatedForNextRound;
    }

    public void setForNextRound(boolean state) {
        this.isActivatedForNextRound = state;
    }
}
