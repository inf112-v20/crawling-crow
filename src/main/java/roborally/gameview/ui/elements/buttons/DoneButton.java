package roborally.gameview.ui.elements.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import roborally.gameview.ui.ProgramCardsView;
import roborally.utilities.SettingsUtil;

import static roborally.utilities.enums.UIElement.*;

public class DoneButton implements IButton {
    private ImageButton doneButton;

    public void set(ProgramCardsView programCardsView) {
        doneButton = new ImageButton(new TextureRegionDrawable(DONE_BUTTON.getTexture()), new TextureRegionDrawable(DONE_BUTTON_PRESSED.getTexture()), new TextureRegionDrawable(DONE_BUTTON_HOVER.getTexture()));
        doneButton.setPosition(0, (programCardsView.getCardHeight() / 2f) - (doneButton.getPrefHeight() / 2f));

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
                int numberOfLockedCard = programCardsView.getGame().getUserRobot().getLogic().getNumberOfLockedCards();
                int numberOfCardsToChoose = SettingsUtil.REGISTER_SIZE - numberOfLockedCard;
                if (programCardsView.getCardPick() != numberOfCardsToChoose) {
                    System.out.println("Must choose correct number of cards");
                    return;
                }

                int[] newOrder = new int[programCardsView.getCardPick()];
                System.arraycopy(programCardsView.getOrder(), 0, newOrder, 0, programCardsView.getCardPick());
                programCardsView.setOrder(newOrder);
                programCardsView.setCardPick(-1);
            }
        });
    }

    @Override
    public ImageButton get() {
        return doneButton;
    }
}
