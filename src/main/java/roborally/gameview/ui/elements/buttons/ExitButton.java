package roborally.gameview.ui.elements.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import roborally.events.Events;
import roborally.game.IGame;
import roborally.gameview.ui.UIElements;
import roborally.utilities.SettingsUtil;

import static roborally.utilities.enums.UIElement.EXIT_BUTTON;

public class ExitButton implements IButton {
    private ImageButton exitButton;

    public void set(IGame game, Events events, Stage stage, UIElements uiElements) {
        this.exitButton = new ImageButton(new TextureRegionDrawable(EXIT_BUTTON.getTexture()));
        float y = (((stage.getHeight() + SettingsUtil.MAP_HEIGHT) / 2f) + (exitButton.getHeight() / 2f));
        this.exitButton.setY(y);

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float quitButtonFixedX = xShift - exitButton.getWidth();
        this.exitButton.setX(quitButtonFixedX);

        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.endGame();
                events.setWonGame(true);
                uiElements.clearAll();
            }
        });
    }

    @Override
    public ImageButton get() {
        return exitButton;
    }

    public void clear() {
        exitButton.clear();
    }
}
