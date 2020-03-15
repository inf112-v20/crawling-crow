package roborally.ui.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import roborally.game.objects.cards.IProgramCards;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class MakeCards {
    private int cardPick;
    private ArrayList<Group> groups;
    private int[] order;

    public MakeCards() {
        this.cardPick = 0;
        this.groups = new ArrayList<>();
        this.order = new int[]{-1, -1, -1, -1, -1};
    }

    public void makeCard(IProgramCards.Card card) {
        if (card.getCardType() == IProgramCards.CardTypes.MOVE_1)
            this.makeMove1(card.getPriority());
        else if (card.getCardType() == IProgramCards.CardTypes.MOVE_2)
            this.makeMove2(card.getPriority());
        else if (card.getCardType() == IProgramCards.CardTypes.MOVE_3)
            this.makeMove3(card.getPriority());
        else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_LEFT)
            this.makeRotateLeft(card.getPriority());
        else if (card.getCardType() == IProgramCards.CardTypes.ROTATE_RIGHT)
            this.makeRotateRight(card.getPriority());
        else if (card.getCardType() == IProgramCards.CardTypes.U_TURN)
            this.makeUTurn(card.getPriority());
        else if (card.getCardType() == IProgramCards.CardTypes.BACKUP)
            this.makeBackup(card.getPriority());
    }

    public void makeUTurn(int priority) {
        Image uTurn = new Image(AssetManagerUtil.getCardTexture("Uturn"));
        makeSomething(priority, uTurn);
    }

    public void makeBackup(int priority) {
        Image backup = new Image(AssetManagerUtil.getCardTexture("Backup"));
        makeSomething(priority, backup);
    }

    public void makeMove1(int priority) {
        Image move = new Image(AssetManagerUtil.getCardTexture("Move1"));
        makeSomething(priority, move);
    }

    public void makeMove2(int priority) {
        Image move = new Image(AssetManagerUtil.getCardTexture("Move2"));
        makeSomething(priority, move);
    }

    public void makeMove3(int priority) {
        Image move = new Image(AssetManagerUtil.getCardTexture("Move3"));
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

    /**
     * Makes a new Image of the card that is being played. Adds top
     * label for the order the card is played, and a label for the priority.
     *
     * @param priority The priority of this card.
     * @param image    The image created for the card, with the related texture.
     */
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
                group.getChildren().get(1).setColor(Color.GREEN.add(Color.RED));
                group.getChildren().get(0).setColor(Color.GREEN.add(Color.RED));
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
        topLabel.setX(28);
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

    public void reArrange(int oldI) {
        int i = oldI;
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
        this.groups = new ArrayList<>();
    }
}
