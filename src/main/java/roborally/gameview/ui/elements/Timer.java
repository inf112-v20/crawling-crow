package roborally.gameview.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import roborally.game.cards.IProgramCards;
import roborally.game.robot.Robot;
import roborally.gameview.ui.ProgramCardsView;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

import static roborally.utilities.SettingsUtil.STAGE_WIDTH;

public class Timer {
    private Label timerLabel;
    private float cardTimer;

    public Timer(float cardTimer) {
        this.cardTimer = cardTimer;
    }

    public void set(ProgramCardsView programCardsView) {
        Label.LabelStyle timerLabelStyle = new Label.LabelStyle();
        timerLabelStyle.fontColor = Color.YELLOW;
        timerLabelStyle.font = new BitmapFont();
        this.timerLabel = new Label(Float.toString(cardTimer), timerLabelStyle);
        this.timerLabel.setFontScale(3);
        float xShift = (STAGE_WIDTH - SettingsUtil.MAP_WIDTH) / 2f;
        this.timerLabel.setPosition(xShift, programCardsView.getCardHeight() / 2f);
    }

    public Label get() {
        return timerLabel;
    }

    public void update(float deltaTime, Robot userRobot, ProgramCardsView programCardsView) {
        this.cardTimer -= deltaTime;
        this.timerLabel.setText("0:" + (int) this.cardTimer);

        if (cardTimer <= 10) {
            timerLabel.setColor(Color.RED);
        }

        if (cardTimer <= 1.0) {
            ArrayList<IProgramCards.Card> cards = userRobot.getLogic().getCardsInHand();
            int number = Math.min(5, cards.size());
            for (IProgramCards.Card card : cards) {
                if (programCardsView.getCardPick() < number) {
                    addIfCardIsNotChosen(cards, card, programCardsView);
                }
            }
            temporaryTimerOrder(programCardsView);
            programCardsView.setCardPick(-1);
        }
    }

    private void addIfCardIsNotChosen(ArrayList<IProgramCards.Card> cards, IProgramCards.Card card, ProgramCardsView programCardsView) {
        int idx = cards.indexOf(card);
        boolean hasCard = false;
        for (int id : programCardsView.getOrder()) {
            if (id == idx) {
                hasCard = true;
                break;
            }
        }
        if (!hasCard) {
            programCardsView.updateOrder(idx);
        }
    }

    /**
     * Resizes the ordered array to match the number of dealt cards
     *
     * @param cardsView the program (or register) cards view
     */
    private void temporaryTimerOrder(ProgramCardsView cardsView) {
        int temp = 0;
        while (temp < 5 && (cardsView.getOrder()[temp] != -1)) {
            temp += 1;
        }
        int[] newOrder = new int[temp];
        System.arraycopy(cardsView.getOrder(), 0, newOrder, 0, temp);
        cardsView.setOrder(newOrder);
    }
}
