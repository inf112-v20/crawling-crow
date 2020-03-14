package roborally.ui.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

// Learn more about menu badlogicgames.com -> InputAdapter
public class Menu {
    private TextureRegion[][] buttons;
    private ArrayList<Image> clickableButtons;
    private boolean resume;
    private boolean changeMap;
    private int mapId;
    private Texture menu;
    private ArrayList<Texture> maps;

    public Menu(Stage stage) {
        this.clickableButtons = new ArrayList<>();
        this.buttons = TextureRegion.split(AssetManagerUtil.getButtons(), 200, 50);
        this.resume = false;
        this.changeMap = false;
        this.mapId = 1;
        int y = 600;
        menu = AssetManagerUtil.getMenu();
        for (int i = 0; i < 3; i++) {
            clickableButtons.add(new Image(buttons[i][0]));
            clickableButtons.get(i).setY(y -= 50);
            clickableButtons.get(i).setX(675);
            stage.addActor(clickableButtons.get(i));
        }
        clickableButtons.get(0).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                resume = true;
                return true;
            }
        });
        clickableButtons.get(1).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                changeMap = true;
                return true;
            }
        });
        clickableButtons.get(2).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        maps = new ArrayList<>();
        maps.add(new Texture(Gdx.files.internal("assets/maps/models/map0.png")));
        maps.add(new Texture(Gdx.files.internal("assets/maps/models/map1.png")));
        maps.add(new Texture(Gdx.files.internal("assets/maps/models/map2.png")));
        maps.add(new Texture(Gdx.files.internal("assets/maps/models/map3.png")));

    }

    public ArrayList<Image> getButtons() {
        return this.clickableButtons;
    }

    public boolean isResume() {
        if (resume) {
            resume = false;
            return true;
        }
        return false;
    }

    public boolean isChangeMap() {
        if (changeMap) {
            changeMap = false;
            return true;
        }
        return false;
    }

    public int getMapId() {
        mapId++;
        if (mapId == 4)
            mapId = 0;
        return this.mapId;
    }

    public Texture getMenu() {
        return this.menu;
    }

    public Texture getMap() {
        return maps.get(mapId);
    }

    public void reloadStage(Stage stage) {
        for (Image image : getButtons())
            stage.addActor(image);
    }
}
