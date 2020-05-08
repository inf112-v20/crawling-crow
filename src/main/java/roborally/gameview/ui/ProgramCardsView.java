package roborally.gameview.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.jetbrains.annotations.NotNull;
import roborally.game.IGame;
import roborally.game.cards.IProgramCards;
import roborally.gameview.ui.elements.Timer;
import roborally.gameview.ui.elements.buttons.DoneButton;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<Group> programCardsGroup;
    private int[] order;
    private final ArrayList<Label> selectedOrderList;
    private float cardWidth;
    private float cardHeight;

    private final DoneButton doneButton;
    private final Timer timer;

    public ProgramCardsView(IGame game) {
        this.game = game;
        this.selectedOrderList = new ArrayList<>();
        this.cardPick = 0;
        this.programCardsGroup = new ArrayList<>();
        this.order = new int[SettingsUtil.REGISTER_SIZE];
        Arrays.fill(order, -1);

        this.doneButton = new DoneButton();
        this.timer = new Timer(SettingsUtil.TIMER_DURATION);
    }

    /**
     * Adds the Program Card into the group that makes the ProgramCardsView.
     *
     * @param card The card itself
     * @param active If the card is going to be interactable or not
     */
    public void addCard(IProgramCards.@NotNull Card card, boolean active) {
        this.cardWidth = AssetManagerUtil.getCards().getProgramCardWidth() / CARD_IMAGE_UNIT_SCALE;
        this.cardHeight = AssetManagerUtil.getCards().getProgramCardHeight() / CARD_IMAGE_UNIT_SCALE;

        Image cardImage = new Image(AssetManagerUtil.getCards().getCardTexture(card.getCardType()));
        cardImage.setSize(getCardWidth(), getCardHeight());
        if (active) {
            setCard(card.getPriority(), cardImage);
        } else {
            setCardInactive(card.getPriority(), cardImage);
        }
    }

    private void setCardInactive(int priority, @NotNull Image image) {
        Group fullCard = new Group();
        fullCard.addActor(image);
        Label priorityLabel = getPriorityLabel(priority);
        fullCard.addActor(priorityLabel);
        Actor card = fullCard.getChildren().get(0);
        card.setColor(Color.GRAY);
        this.programCardsGroup.add(fullCard);
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
        Group fullCard = new Group();
        fullCard.addActor(image);
        Label selectedOrderLabel = getSelectedOrderLabel();
        Label priorityLabel = getPriorityLabel(priority);
        fullCard.addActor(priorityLabel);
        Actor card = fullCard.getChildren().get(0);
        fullCard.addListener(new InputListener() {
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
                    if (programCardsGroup.indexOf(fullCard) == order[i]) {
                        card.setColor(Color.WHITE);
                        selectedOrderLabel.setText("");
                        reArrange(i);
                        if (cardPick -1 != -1) {
                            cardPick--;
                        }
                        return true;
                    }
                if (cardPick == SettingsUtil.REGISTER_SIZE) {
                    card.setColor(Color.RED);
                    if (SettingsUtil.DEBUG_MODE) System.out.println("You cannot select more than 5 cards");
                    return true;
                }
                selectedOrderLabel.setText(Integer.toString((cardPick) + 1));
                selectedOrderLabel.setX(getSelectedOrderLabel().getX() - (selectedOrderLabel.getPrefWidth() / 2f));
                fullCard.addActor(selectedOrderLabel);
                selectedOrderList.add(cardPick, selectedOrderLabel);
                order[cardPick++] = programCardsGroup.indexOf(fullCard);
                fullCard.getChildren().get(1).setColor(Color.YELLOW);
                fullCard.getChildren().get(0).setColor(Color.YELLOW);
                return true;
            }
        });
        this.programCardsGroup.add(fullCard);
    }

    public Label getSelectedOrderLabel() {
        Label.LabelStyle selectedOrderLabelStyle = new Label.LabelStyle();
        selectedOrderLabelStyle.font = new BitmapFont();
        Label selectedOrderLabel = new Label("", selectedOrderLabelStyle);
        selectedOrderLabel.setY(100);
        selectedOrderLabel.setX((cardWidth / 2f));
        selectedOrderLabel.setColor(Color.GREEN);
        selectedOrderLabel.setFontScale(2f);
        return selectedOrderLabel;
    }

    public Label getPriorityLabel(int priority) {
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

    public ArrayList<Group> getCards() {
        return programCardsGroup;
    }

	public int[] getOrder() {
		return order;
	}

	public void setOrder(int[] newOrder) {
        this.order = newOrder;
    }

	public boolean done() {
		return cardPick == -1;
	}

	public int getCardPick() {
        return cardPick;
    }

    public void setCardPick(int value) {
        this.cardPick = value;
    }

    public void clear() {
        this.order = new int[]{-1, -1, -1, -1, -1};
        this.cardPick = 0;
        this.programCardsGroup.clear();
        this.programCardsGroup = new ArrayList<>();
    }

    public float getCardWidth() {
        return cardWidth;
    }

    public float getCardHeight() {
        return cardHeight;
    }

    public void updateOrder(int idx) {
        order[cardPick++] = idx;
    }

    public DoneButton getDoneButton() {
        return doneButton;
    }

    public Timer getTimer() {
        return timer;
    }

    public IGame getGame() {
        return game;
    }
}