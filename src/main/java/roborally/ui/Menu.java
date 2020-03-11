package roborally.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import roborally.utilities.AssetManagerUtil;


// Learn more about menu badlogicgames.com -> InputAdapter
public class Menu extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private Texture menu;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private int i = 0;
    private boolean resume;
    private Vector2 mouseScreenPosition;
    private Vector2 mouseLocalPosition;
    private Actor actor;

    public Menu() {
        menu = AssetManagerUtil.getMenu();
        batch = new SpriteBatch();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        resume = false;
        if (screenX > 100 && screenY > 250 && screenX < 400 && screenY < 300) {
            if (i == 0) {
                System.out.println("Starting game");
                i = 1;
            } else
                System.out.println("Resuming game");
            resume = true;
        } else if (screenX > 100 && screenX < 400 && screenY > 450) {
            System.out.println("Exiting game");
            Gdx.app.exit();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.M && i == 1)
            resume = true;
        return true;
    }

    public void draw() {
        batch.begin();
        batch.draw(menu, 10, 10);
        batch.end();
    }

    public boolean resumeGame() {
        if (resume) {
            resume = false;
            return true;
        }
        return false;
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        draw();
        mouseScreenPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        if (mouseScreenPosition.x > 120 && mouseScreenPosition.x < 400
                && mouseScreenPosition.y > 250 && mouseScreenPosition.y < 300) {
            batch.setColor(555, 123, 450, 600);
            batch.begin();
            batch.draw(menu, 100, 10);
            batch.end();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
