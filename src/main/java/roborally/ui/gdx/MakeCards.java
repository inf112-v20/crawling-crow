package roborally.ui.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class MakeCards {
    private int cardPick;
    private ArrayList<Group> groups;
    private int[] order;
    private ArrayList<Integer> priorities;

    public MakeCards() {
        this.cardPick = 0;
        this.groups = new ArrayList<>();
        this.order = new int[]{-1, -1, -1, -1, -1};
        this.priorities = new ArrayList<>();
    }

    public void makeBackup(int priority) {
        Image backup = new Image(AssetManagerUtil.getCardTexture("Backup"));
        makeSomething(priority, backup);
    }

    public void makeMove(int priority) {
        Image move = new Image(AssetManagerUtil.getCardTexture("Move"));
        makeSomething(priority, move);
    }

    public void makeRotateRight(int priority) {
        Image RotateR = new Image(AssetManagerUtil.getCardTexture("RotateRight"));
        makeSomething(priority, RotateR);
    }

    public void makeRotateLeft(int priority) {
        Image RotateL = new Image(AssetManagerUtil.getCardTexture("RotateLeft"));
        makeSomething(priority, RotateL);
    }

    private void makeSomething(int priority, Image image) {
        image.setSize(75, 100);
        Group group = new Group();
        group.addActor(image);
        Label topLabel = makeTopLabel();
        Label label = makeLabel(priority);
        group.addActor(label);
        group.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (int i = 0; i < order.length; i++)
                    if (groups.indexOf(group) == order[i]) {
                        group.getChildren().get(1).setColor(Color.ORANGE);
                        group.getChildren().get(0).setColor(Color.WHITE);
                        group.removeActor(topLabel);
                        reArrange(i);
                        cardPick--;
                        return true;
                    }

                topLabel.setText(Integer.toString(cardPick));
                group.addActor(topLabel);
                order[cardPick++] = groups.indexOf(group);
                group.getChildren().get(1).setColor(Color.GREEN);
                group.getChildren().get(0).setColor(Color.GREEN);
                return true;
            }
        });
        this.groups.add(group);
    }

    public Label makeTopLabel() {
        Label.LabelStyle topLabelStyle = new Label.LabelStyle();
        topLabelStyle.font = new BitmapFont();
        Label topLabel = new Label("0", topLabelStyle);
        topLabel.setY(100);
        topLabel.setX(25);
        topLabel.setColor(Color.GREEN);
        topLabel.setFontScale(2f);
        return topLabel;
    }

    public Label makeLabel(int priority) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        Label label = new Label(Integer.toString(priority), labelStyle);
        label.setX(25);
        label.setY(1);
        label.setFontScale(1.2f, 0.75f);
        label.setColor(Color.ORANGE);
        return label;
    }

    public void reArrange(int i) {
        order[i] = -1;
        while (i < 4)
            order[i] = order[++i];
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public int[] getOrder() {
        return this.order;
    }

    public boolean fiveCards() {
        return this.cardPick == 5;
    }

    public void clearStuff() {
        this.order = new int[]{-1, -1, -1, -1, -1};
        this.cardPick = 0;
        this.groups.clear();
        this.priorities.clear();
        this.groups = new ArrayList<>();
        this.priorities = new ArrayList<>();
    }
}
