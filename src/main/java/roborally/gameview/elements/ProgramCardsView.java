package roborally.gameview.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.jetbrains.annotations.NotNull;
import roborally.game.IGame;
import roborally.game.cards.IProgramCards;
import roborally.game.robot.Robot;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static roborally.utilities.AssetManagerUtil.*;
import static roborally.utilities.enums.UIElement.*;

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
                selectedOrderList.get(cardPick).setText(Integer.toString((cardPick)+1));
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
				selectedOrderList.get(i - 1).setText(Integer.toString(i));
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
        doneButton = new ImageButton(new TextureRegionDrawable(DONE_BUTTON.getTexture()), new TextureRegionDrawable(DONE_BUTTON_PRESSED.getTexture()), new TextureRegionDrawable(DONE_BUTTON_HOVER.getTexture()));
        doneButton.setPosition(0, (getCardHeight() / 2f) - (doneButton.getPrefHeight() / 2f));

        doneButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                doneButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                doneButton.setChecked(false);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                int numberOfLockedCard = game.getUserRobot().getLogic().getNumberOfLockedCards();
                int numberOfCardsToChoose = SettingsUtil.REGISTER_SIZE - numberOfLockedCard;
                if (cardPick != numberOfCardsToChoose ){
                    System.out.println("Must choose correct number of cards");
                    return;
                }

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
        this.countdownLabel.setFontScale(3);
        float xShift = (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
        this.countdownLabel.setPosition(xShift - 55, 85);
    }

    public void setTimerLabel() {
        this.countdownLabelStyle.font = new BitmapFont();
        this.timerLabel = new Label(Float.toString(cardTimer), countdownLabelStyle);
        this.timerLabel.setFontScale(3);
        float xShift = (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
        this.timerLabel.setPosition(xShift + 10, 40);
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
                    addIfCardIsNotChosen(cards, card);
                }
            }
            temporaryTimerOrder();
            System.out.println(Arrays.toString(order));
            cardPick = -1;
        }
    }

    private void addIfCardIsNotChosen(ArrayList<IProgramCards.Card> cards, IProgramCards.Card card) {
        int idx = cards.indexOf(card);
        boolean hasCard = false;
        for (int id : order) {
            if (id == idx) {
                hasCard = true;
                break;
            }
        }
        if (!hasCard) {
            order[cardPick++] = idx;
        }
    }


    // For å sørge for at den returnerer en liste med korrekt størrelse (og at det ikke er -1 som indexer).
    private void temporaryTimerOrder() {
        int abc = 0;
        while(abc < 5 && order[abc]!=-1)
            abc+=1;
        int[] newOrder = new int[abc];
        System.arraycopy(order, 0, newOrder, 0, abc);
        this.order = newOrder;
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