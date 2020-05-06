package roborally.gameview.ui;

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
    private Label timerLabel;
    private float cardTimer;

    private Stage stage;
    private Label.LabelStyle timerLabelStyle;

    public ProgramCardsView(IGame game) {
        this.game = game;
        this.selectedOrderList = new ArrayList<>();
        this.cardPick = 0;
        this.groups = new ArrayList<>();
        this.order = new int[SettingsUtil.REGISTER_SIZE];
        Arrays.fill(order, -1);
        this.cardTimer = SettingsUtil.TIMER_DURATION;

        setTimerLabelStyle();
    }

    /**
     * Sets the Program Card into the group that makes the ProgramCardsView.
     *
     * @param card The card itself
     * @param active If the card is going to be interactable or not
     */
    public void setCard(IProgramCards.@NotNull Card card, boolean active) {
        this.cardWidth = getCards().getProgramCardWidth() / CARD_IMAGE_UNIT_SCALE;
        this.cardHeight = getCards().getProgramCardHeight() / CARD_IMAGE_UNIT_SCALE;

        Image cardImage = new Image(getCards().getCardTexture(card.getCardType()));
        if (active) {
            setCard(card.getPriority(), cardImage);
        } else {
            setCardInactive(card.getPriority(), cardImage);
        }
    }

    private void setCardInactive(int priority, @NotNull Image image) {
        image.setSize(image.getPrefWidth() / CARD_IMAGE_UNIT_SCALE, image.getPrefHeight() / CARD_IMAGE_UNIT_SCALE); // Using the card original pixel size
        Group group = new Group();
        group.addActor(image);
        Label priorityLabel = setPriorityLabel(priority);
        group.addActor(priorityLabel);
        Actor card = group.getChildren().get(0);
        card.setColor(Color.GRAY);
        this.groups.add(group);
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
        Actor card = group.getChildren().get(0);
        group.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!card.getColor().equals(Color.YELLOW) && !card.getColor().equals(Color.RED)) {
                    card.setColor(Color.GRAY);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (!card.getColor().equals(Color.YELLOW)) {
                    card.setColor(Color.WHITE);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (int i = 0; i < order.length; i++)
                    if (groups.indexOf(group) == order[i]) {
                        card.setColor(Color.WHITE);
                        selectedOrderLabel.setText("");
                        reArrange(i);
                        if (cardPick -1 != -1)
                            cardPick--;
                        return true;
                    }
                if (cardPick == SettingsUtil.REGISTER_SIZE) {
                    card.setColor(Color.RED);
                    if (SettingsUtil.DEBUG_MODE) System.out.println("You cannot select more than 5 cards");
                    return true;
                }
                selectedOrderList.add(cardPick, selectedOrderLabel);
                Label tempSelectedOrderLabel = selectedOrderList.get(cardPick);
                tempSelectedOrderLabel.setText(Integer.toString((cardPick)+1));
                tempSelectedOrderLabel.setX(tempSelectedOrderLabel.getX() - (tempSelectedOrderLabel.getPrefWidth() / 2f));
                group.addActor(tempSelectedOrderLabel);
                order[cardPick++] = groups.indexOf(group);
                group.getChildren().get(1).setColor(Color.YELLOW);
                group.getChildren().get(0).setColor(Color.YELLOW);
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
        selectedOrderLabel.setX((cardWidth / 2f));
        selectedOrderLabel.setColor(Color.GREEN);
        selectedOrderLabel.setFontScale(2f);
        return selectedOrderLabel;
    }

    public Label setPriorityLabel(int priority) {
        Label.LabelStyle PriorityLabelStyle = new Label.LabelStyle();
        PriorityLabelStyle.font = new BitmapFont();
        Label priorityLabel = new Label(Integer.toString(priority), PriorityLabelStyle);
        priorityLabel.setX((cardWidth / 2f) - (priorityLabel.getPrefWidth() / 2f));
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

    private void setTimerLabelStyle() {
        this.timerLabelStyle = new Label.LabelStyle();
        this.timerLabelStyle.fontColor = Color.YELLOW;
    }

    public void setTimerLabel() {
        this.timerLabelStyle.font = new BitmapFont();
        this.timerLabel = new Label(Float.toString(cardTimer), timerLabelStyle);
        this.timerLabel.setFontScale(3);
        float xShift = (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
        this.timerLabel.setPosition(xShift, cardHeight / 2f);
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public void updateTimer(float dt, Robot userRobot) {
        this.cardTimer -= dt;
        this.timerLabel.setText("0:" + (int) this.cardTimer);

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