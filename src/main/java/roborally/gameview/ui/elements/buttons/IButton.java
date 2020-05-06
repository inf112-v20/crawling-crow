package roborally.gameview.ui.elements.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public interface IButton {
    /**
     * @return A button with a Image
     */
    ImageButton get();

    /**
     * Clears button
     */
    void clear();
}
