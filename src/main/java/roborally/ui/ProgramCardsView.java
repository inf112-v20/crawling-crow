package roborally.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.jetbrains.annotations.NotNull;
import roborally.game.cards.IProgramCards;
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
	private final ArrayList<Label> topLabelList;
	private final int cardWidth;
	private final int cardHeight;
	private int cardPick;
	private ArrayList<Group> cardGroups;
	private int[] order;
	private Label doneLabel;

	public ProgramCardsView() {
		this.topLabelList = new ArrayList<>();
		this.cardPick = 0;
		this.cardGroups = new ArrayList<>();
		this.order = new int[SettingsUtil.REGISTER_SIZE];
		Arrays.fill(order, -1);
		this.cardWidth = 75;
		this.cardHeight = 116;
	}

	public void makeCard(IProgramCards.@NotNull Card card) {
		int priority = card.getPriority();
		String cardName = card.getCardType().toString();
		Image image = new Image(AssetManagerUtil.getCardTexture(cardName.toLowerCase()));
		makeCardGroup(priority, image);
	}

	/**
	 * Makes a new Image of the card that is being played. Adds top
	 * label for the order the card is played, and a label for the priority.
	 *
	 * @param priority The priority of this card.
	 * @param image    The image created for the card, with the related texture.
	 */
	private void makeCardGroup(int priority, @NotNull Image image) {
		image.setSize(getCardWidth(), getCardHeight());
		Group group = new Group();
		group.addActor(image);
		Label selectedOrderLabel = makeSelectedOrderLabel();
		Label priorityLabel = makePriorityLabel(priority);
		group.addActor(priorityLabel);
		group.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				for (int i = 0; i < order.length; i++)
					if (cardGroups.indexOf(group) == order[i]) {
						group.getChildren().get(1).setColor(Color.ORANGE);
						group.getChildren().get(0).setColor(Color.WHITE);
						selectedOrderLabel.setText("");
						reArrange(i);
						if (cardPick - 1 != -1)
							cardPick--;
						return true;
					}
				if (cardPick == SettingsUtil.REGISTER_SIZE) {
					doneLabel.setColor(Color.RED);
					return true;
				}
				topLabelList.add(cardPick, selectedOrderLabel);
				topLabelList.get(cardPick).setText(Integer.toString((cardPick)));
				group.addActor(topLabelList.get(cardPick));
				order[cardPick++] = cardGroups.indexOf(group);
				group.getChildren().get(1).setColor(Color.GREEN.add(Color.RED));
				group.getChildren().get(0).setColor(Color.GREEN.add(Color.RED));
				return true;
			}
		});
		this.cardGroups.add(group);
	}

	public Label makeSelectedOrderLabel() {
		Label.LabelStyle topLabelStyle = new Label.LabelStyle();
		topLabelStyle.font = new BitmapFont();
		Label topLabel = new Label("", topLabelStyle);
		topLabel.setY(100);
		topLabel.setX(28);
		topLabel.setColor(Color.GREEN);
		topLabel.setFontScale(2f);
		return topLabel;
	}

	public Label makePriorityLabel(int priority) {
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = new BitmapFont();
		Label label = new Label(Integer.toString(priority), labelStyle);
		label.setX(28);
		label.setY(10);
		label.setFontScale(0.78f);
		label.setColor(Color.ORANGE);
		return label;
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
		return this.cardGroups;
	}

	public int[] getOrder() {
		return this.order;
	}

	public boolean done() {
		return this.cardPick == -1;
	}


	// Maybe deprecated.
	public void clearStuff() {
		this.order = new int[]{-1, -1, -1, -1, -1};
		this.cardPick = 0;
		this.cardGroups.clear();
		this.cardGroups = new ArrayList<>();
	}

	public int getCardWidth() {
		return this.cardWidth;
	}

	public int getCardHeight() {
		return this.cardHeight;
	}

	public Label getDoneLabel() {
		return this.doneLabel;
	}

	public void makeDoneLabel() {
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = new BitmapFont();
		doneLabel = new Label("Done", labelStyle);
		doneLabel.setPosition(getCardWidth(), getCardHeight());
		doneLabel.setFontScale(2);
		doneLabel.setScale(2);
		doneLabel.setHeight(this.cardHeight);
		doneLabel.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				doneLabel.setColor(Color.GREEN);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				doneLabel.setColor(Color.WHITE);
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (cardPick < SettingsUtil.REGISTER_SIZE) { //Kan ta minimum av groups.size() og REGISTER_SIZE her.. men funker ikke med register klassen da peeknextcard prøver å hente en posisjon for høyt.
					return;
				}
				int[] newOrder = new int[cardPick];
				System.arraycopy(order, 0, newOrder, 0, cardPick);
				order = newOrder;
				cardPick = -1;
			}
		});
	}

}