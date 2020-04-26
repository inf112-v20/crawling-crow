package roborally.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.jetbrains.annotations.NotNull;
import roborally.game.IGame;
import roborally.game.cards.IProgramCards;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static roborally.utilities.AssetManagerUtil.*;
import static roborally.utilities.enums.UIElement.DONE_BUTTON;
import static roborally.utilities.enums.UIElement.DONE_BUTTON_PRESSED;

/**
 * This class creates images of the program cards.
 * When initialized it adds listeners to the cards,
 * updates the order the user chooses to arrange
 * the cards and finally returns the chosen order
 * once the played clicks the done button.
 */
public class ProgramCardsView {
    private final static float CARD_IMAGE_UNIT_SCALE = 3.4f;
    private final IGame game;
    private int cardPick;
    private ArrayList<Group> groups;
    private int[] order;
    private ArrayList<Label> selectedOrderList;
    private float cardWidth;
    private float cardHeight;
    private ImageButton doneButton;
    private Label countdownLabel;
    private Label timerLabel;
    private float cardTimer;

    private Stage stage;
    private Label.LabelStyle countdownLabelStyle;

    public ProgramCardsView(IGame game) {
        this.game = game;
        this.selectedOrderList = new ArrayList<>();
        this.cardPick = 0;
        this.groups = new ArrayList<>();
        this.order = new int[SettingsUtil.REGISTER_SIZE];
        Arrays.fill(order, -1);
        this.cardTimer = SettingsUtil.TIMER_DURATION;

        setCountdownLabelStyle();
    }

    public void setCard(IProgramCards.@NotNull Card card) {
        this.cardWidth = getProgramCardWidth() / CARD_IMAGE_UNIT_SCALE;
        this.cardHeight = getProgramCardHeight() / CARD_IMAGE_UNIT_SCALE;

        Image cardImage = new Image(getCardTexture(card.getCardType()));
        setCard(card.getPriority(), cardImage);
    }

    /**
     * Makes a new Image of the card that is being played. Adds top
     * label for the order the card is played, and a label for the priority.
     *
     * @param priority The priority of this card.
     * @param image    The image created for the card, with the related texture.
     */
    private void setCard(int priority, @NotNull Image image) {
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
                        group.getChildren().get(0).setColor(Color.WHITE);
                        selectedOrderLabel.setText("");
                        reArrange(i);
                        if (cardPick -1 != -1)
                            cardPick--;
                        return true;
                    }
                if (cardPick == SettingsUtil.REGISTER_SIZE) {
                    System.out.println("You cannot select more than 5 cards");
                    return true;
                }
                selectedOrderList.add(cardPick, selectedOrderLabel);
                selectedOrderList.get(cardPick).setText(Integer.toString((cardPick)));
                group.addActor(selectedOrderList.get(cardPick));
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
        priorityLabel.setColor(Color.YELLOW);
        return priorityLabel;
    }

	// Used to sort cards when deselecting a card.
	public void reArrange(int oldI) {
		int i = oldI;
		order[i] = -1;
		selectedOrderList.remove(i);
		while (i < 4) {
			order[i] = order[++i];
			if (order[i] != -1) {
				selectedOrderList.get(i - 1).setText(Integer.toString(i - 1));
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
        doneButton = new ImageButton(new TextureRegionDrawable(DONE_BUTTON.getTexture()), new TextureRegionDrawable(DONE_BUTTON_PRESSED.getTexture()));
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
        return doneButton;
    }

    private void setCountdownLabelStyle() {
        this.countdownLabelStyle = new Label.LabelStyle();
        this.countdownLabelStyle.fontColor = Color.YELLOW;
    }

    public void setCountdownLabel() {
        this.countdownLabelStyle.font = new BitmapFont();
        this.countdownLabel = new Label("Time left", countdownLabelStyle);
        this.countdownLabel.setFontScale(5);
        this.countdownLabel.setPosition(getCenterPositionHorizontal(getCountdownLabel().getPrefWidth()), getCenterPositionVertical());
    }

    public void setTimerLabel() {
        this.countdownLabelStyle.font = new BitmapFont();
        this.timerLabel = new Label(Float.toString(cardTimer), countdownLabelStyle);
        this.timerLabel.setFontScale(5);
        this.timerLabel.setPosition(getCenterPositionHorizontal(getTimerLabel().getPrefWidth()), getCenterPositionVertical(getCountdownLabel().getPrefHeight()) - 10);
    }

    public Label getCountdownLabel() {
        return countdownLabel;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public void updateTimer(float dt, Robot userRobot) {
        this.cardTimer -= dt;
        this.timerLabel.setText((int) this.cardTimer);

        if (cardTimer <= 10) {
            timerLabel.setColor(Color.RED);
        }

        if (cardTimer <= 1.0) {
            ArrayList<IProgramCards.Card> cards = userRobot.getLogic().getCardsInHand();
            int number = Math.min(5, cards.size());
            for (IProgramCards.Card card : cards) {
                if (cardPick < number) {
                    order[cardPick++] = cards.indexOf(card);
                }
            }
            cardPick = -1;
        }
    }

    private float getCenterPositionHorizontal() {
        return stage.getWidth() / 2f;
    }

    private float getCenterPositionHorizontal(float elementWidth) {
        return (stage.getWidth() / 2f) - (elementWidth / 2f);
    }

    private float getCenterPositionVertical() {
        return stage.getHeight() / 2f;
    }

    private float getCenterPositionVertical(float elementHeight) {
        return (stage.getHeight() / 2f) - (elementHeight / 2f);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}