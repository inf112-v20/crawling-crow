package roborally.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class MakeCards {
    private int cardPick;
    private ArrayList<Image> images;
    private int[] order;

    public MakeCards() {
        this.cardPick = 0;
        this.images = new ArrayList<>();
        order = new int[5];
    }

    public void makeBackup() {
        Image backup = new Image(AssetManagerUtil.getBackup());
        backup.setSize(75, 100);
        backup.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                order[cardPick++] = images.indexOf(backup);
                return true;
            }
        });
        this.images.add(backup);
    }

    public void makeMove() {
        Image move = new Image(AssetManagerUtil.getMove());
        move.setSize(75, 100);
        move.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                order[cardPick++] = images.indexOf(move);
                return true;
            }
        });
        this.images.add(move);
    }

    public void makeRotateRight() {
        Image RotateRight = new Image(AssetManagerUtil.getRotateR());
        RotateRight.setSize(75, 100);
        RotateRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                order[cardPick++] = images.indexOf(RotateRight);
                return true;
            }
        });
        this.images.add(RotateRight);
    }

    public void makeRotateLeft() {
        Image RotateLeft = new Image(AssetManagerUtil.getRotateL());
        RotateLeft.setSize(75, 100);
        RotateLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                order[cardPick++] = images.indexOf(RotateLeft);
                return true;
            }
        });
        this.images.add(RotateLeft);
    }

    public ArrayList<Image> getImages() {
        return this.images;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public int[] getOrder() {
        return this.order;
    }

    public boolean fiveCards() {
        return this.cardPick == 5;
    }

    public void clearStuff() {
        this.order = new int[5];
        this.cardPick = 0;
        this.images = new ArrayList<>();
    }
}
