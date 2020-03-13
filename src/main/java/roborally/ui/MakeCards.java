package roborally.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class MakeCards {
    private int cardPick;
    private ArrayList<Image> images;
    private int[] order;
    private ArrayList<Integer> priorities;

    public MakeCards() {
        this.cardPick = 0;
        this.images = new ArrayList<>();
        this.order = new int[]{-1,-1,-1,-1,-1};
        this.priorities = new ArrayList<>();
    }

    public void makeBackup(int priority) {
        Image backup = new Image(AssetManagerUtil.getBackup());
        backup.setSize(75, 100);
        backup.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for(int i = 0; i < order.length; i++)
                    if(images.indexOf(backup)==order[i]) {
                        backup.setColor(Color.WHITE);
                        reArrange(i);
                        cardPick--;
                        return true;
                    }
                order[cardPick++] = images.indexOf(backup);
                backup.setColor(Color.GREEN);
                return true;
            }
        });
        this.images.add(backup);
        this.priorities.add(images.indexOf(backup), priority);
    }

    public void makeMove(int priority) {
        Image move = new Image(AssetManagerUtil.getMove());
        move.setSize(75, 100);
        move.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for(int i = 0; i < order.length; i++)
                    if(images.indexOf(move)==order[i]) {
                        move.setColor(Color.WHITE);
                        reArrange(i);
                        cardPick--;
                        return true;
                    }
                order[cardPick++] = images.indexOf(move);
                move.setColor(Color.GREEN);
                return true;
            }
        });
        this.images.add(move);
        this.priorities.add(images.indexOf(move), priority);
    }

    public void makeRotateRight(int priority) {
        Image RotateRight = new Image(AssetManagerUtil.getRotateR());
        RotateRight.setSize(75, 100);
        RotateRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for(int i = 0; i < order.length; i++)
                    if(images.indexOf(RotateRight)==order[i]) {
                        RotateRight.setColor(Color.WHITE);
                        reArrange(i);
                        cardPick--;
                        return true;
                    }
                order[cardPick++] = images.indexOf(RotateRight);
                RotateRight.setColor(Color.GREEN);
                return true;
            }
        });
        this.images.add(RotateRight);
        this.priorities.add(images.indexOf(RotateRight), priority);
    }

    public void makeRotateLeft(int priority) {
        Image RotateLeft = new Image(AssetManagerUtil.getRotateL());
        RotateLeft.setSize(75, 100);
        RotateLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for(int i = 0; i < order.length; i++)
                    if(images.indexOf(RotateLeft)==order[i]) {
                        RotateLeft.setColor(Color.WHITE);
                        reArrange(i);
                        cardPick--;
                        return true;
                    }

                order[cardPick++] = images.indexOf(RotateLeft);
                RotateLeft.setColor(Color.GREEN);
                return true;
            }
        });
        this.images.add(RotateLeft);
        this.priorities.add(images.indexOf(RotateLeft), priority);
    }

    public void reArrange(int i) {
        order[i] = -1;
        while(i < 4)
            order[i]=order[++i];
    }

    public ArrayList<Image> getImages() {
        return this.images;
    }

    public int[] getOrder() {
        return this.order;
    }

    public boolean fiveCards() {
        return this.cardPick == 5;
    }

    public void clearStuff() {
        this.order = new int[]{-1,-1,-1,-1,-1};
        this.cardPick = 0;
        this.images.clear();
        this.priorities.clear();
        this.images = new ArrayList<>();
        this.priorities = new ArrayList<>();
    }
    public ArrayList<Integer> getPrio() {
        return this.priorities;
    }
}
