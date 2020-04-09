package roborally.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import roborally.game.cards.IProgramCards;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

/**
 * This class creates images of the program cards.
 * When initialized it adds listeners to the cards,
 * updates the order the user chooses to arrange
 * the cards and finally returns the chosen order
 * once the played clicks the done button.
 */
public class ProgramCardsView {
    private final static float CARD_IMAGE_UNIT_SCALE = 3.4f;
    private int cardPick;
    private ArrayList<Group> groups;
    private int[] order;
    private ArrayList<Label> topLabelList;
    private float cardWidth;
    private float cardHeight;
    private ImageButton doneButton;

    public ProgramCardsView() {
        this.topLabelList = new ArrayList<>();
        this.cardPick = 0;
        this.groups = new ArrayList<>();
        this.order = new int[]{-1, -1, -1, -1, -1};
    }

    public void setCard(IProgramCards.Card card) {
        this.cardWidth = AssetManagerUtil.getProgramCardWidth() / CARD_IMAGE_UNIT_SCALE;
        this.cardHeight = AssetManagerUtil.getProgramCardHeight() / CARD_IMAGE_UNIT_SCALE;

        Image cardImage = new Image(AssetManagerUtil.getCardTexture(card.getCardType()));
        setCard(card.getPriority(), cardImage);
    }

    /**
     * Makes a new Image of the card that is being played. Adds top
     * label for the order the card is played, and a label for the priority.
     *
     * @param priority The priority of this card.
     * @param image    The image created for the card, with the related texture.
     */
    private void setCard(int priority, Image image) {
        image.setSize(image.getPrefWidth() / CARD_IMAGE_UNIT_SCALE, image.getPrefHeight() / CARD_IMAGE_UNIT_SCALE); // Using the card original pixel size
        Group group = new Group();
        group.addActor(image);
        Label selectedOrderLabel = setSelectedOrderLabel();
        Label priorityLabel = setPriorityLabel(priority);
        group.addActor(priorityLabel);
        group.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (int i = 0; i < order.length; i++)
                    if (groups.indexOf(group) == order[i]) {
                        group.getChildren().get(1).setColor(Color.ORANGE);
                        group.getChildren().get(0).setColor(Color.WHITE);
                        selectedOrderLabel.setText("");
                        reArrange(i);
                        if(cardPick -1 != -1)
                            cardPick--;
                        return true;
                    }
                if (cardPick == 5) {
                    System.out.println("You cannot select more than 5 cards");
                    return true;
                }
                topLabelList.add(cardPick, selectedOrderLabel);
                topLabelList.get(cardPick).setText(Integer.toString((cardPick)));
                group.addActor(topLabelList.get(cardPick));
                order[cardPick++] = groups.indexOf(group);
                group.getChildren().get(1).setColor(Color.GREEN.add(Color.RED));
                group.getChildren().get(0).setColor(Color.GREEN.add(Color.RED));
                return true;
            }
        });
        this.groups.add(group);
    }

    public Label setSelectedOrderLabel() {
        Label.LabelStyle selectedOrderLabelStyle = new Label.LabelStyle();
        selectedOrderLabelStyle.font = new BitmapFont();
        Label selectedOrderLabel = new Label("", selectedOrderLabelStyle);
        selectedOrderLabel.setY(100);
        selectedOrderLabel.setX(28);
        selectedOrderLabel.setColor(Color.GREEN);
        selectedOrderLabel.setFontScale(2f);
        return selectedOrderLabel;
    }

    public Label setPriorityLabel(int priority) {
        Label.LabelStyle PriorityLabelStyle = new Label.LabelStyle();
        PriorityLabelStyle.font = new BitmapFont();
        Label priorityLabel = new Label(Integer.toString(priority), PriorityLabelStyle);
        priorityLabel.setX(28);
        priorityLabel.setY(10);
        priorityLabel.setFontScale(0.78f);
        priorityLabel.setColor(Color.ORANGE);
        return priorityLabel;
    }

    // Used to sort cards when deselecting a card.
    public void reArrange(int oldI) {
        int i = oldI;
        order[i] = -1;
        topLabelList.remove(i);
        while (i < 4) {
            order[i] = order[++i];
            if (order[i] != -1) {
                topLabelList.get(i - 1).setText(Integer.toString(i - 1));
                order[i] = -1;
            }
        }
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public int[] getOrder() {
        return this.order;
    }

    public boolean done() {
        return this.cardPick == -1;
    }


    // Maybe deprecated.
    public void clear() {
        this.order = new int[]{-1, -1, -1, -1, -1};
        this.cardPick = 0;
        this.groups.clear();
        this.groups = new ArrayList<>();
    }

    public float getCardWidth() {
        return this.cardWidth;
    }

    public float getCardHeight() {
        return this.cardHeight;
    }

    public void setDoneButton() {
        doneButton = new ImageButton(new TextureRegionDrawable(AssetManagerUtil.getDoneButton()), new TextureRegionDrawable(AssetManagerUtil.getDoneButtonPressed()));
        doneButton.setPosition(0, (getCardHeight() / 2f) - (doneButton.getPrefHeight() / 2f));

        doneButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int[] newOrder = new int[cardPick];
                System.arraycopy(order, 0, newOrder, 0, cardPick);
                order = newOrder;
                cardPick = -1;
            }
        });
    }

    public ImageButton getDoneButton() {
        return this.doneButton;
    }

}