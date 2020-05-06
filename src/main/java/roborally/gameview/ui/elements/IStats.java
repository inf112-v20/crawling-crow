package roborally.gameview.ui.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public interface IStats {
    /**
     * @return List of images
     */
    ArrayList<Image> get();

    /**
     * Clears list of images
     */
    void clear();
}
