package roborally.gameview.ui.elements.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import roborally.game.IGame;
import roborally.gameview.ui.UIElements;
import roborally.utilities.SettingsUtil;

import static roborally.utilities.enums.UIElement.RESTART_BUTTON;
import static roborally.utilities.enums.UIElement.RESTART_BUTTON_PRESSED;

public class RestartButton implements IButton {
    private ImageButton restartButton;

    public void set(IGame game, UIElements uiElements) {
        this.restartButton = new ImageButton(new TextureRegionDrawable(RESTART_BUTTON.getTexture()), new TextureRegionDrawable((RESTART_BUTTON_PRESSED.getTexture())), new TextureRegionDrawable((RESTART_BUTTON_PRESSED.getTexture())));

        this.restartButton.setY(uiElements.getExitButton().get().getY());


        this.restartButton.setX(uiElements.getExitButton().get().getX() - uiElements.getExitButton().get().getWidth());

        this.restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (SettingsUtil.DEBUG_MODE) System.out.println("Clicked on restart button...");
                game.restartGame();
                uiElements.clearAll();
            }
        });
    }

    @Override
    public ImageButton get() {
        return restartButton;
    }

    public void clear() {
        restartButton.clear();
    }
}
