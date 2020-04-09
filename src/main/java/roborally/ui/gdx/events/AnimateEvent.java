package roborally.ui.gdx.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.IGame;
import roborally.ui.ProgramCardsView;

public class AnimateEvent {
    private Events events;
    private ProgramCardsView programCardsView;
    private boolean cardPhase;

    public AnimateEvent(Events events) {
        this.events = events;
    }

    /**
     * Called from UI to draw events in order. If no events are active nothing gets drawn.
     * @param batch the spriteBatch from UI.
     * @param game the game that is running.
     * @param stage the stage from UI.
     */
    public void drawEvents(SpriteBatch batch, IGame game, Stage stage) {
        batch.begin();
        if (cardPhase) {
            drawCards(game, batch, stage);
            stage.act();
        }
        if (events.getFadeRobot() && !game.getGameOptions().getMenu())
            events.fadeRobots(batch);
        if (events.hasLaserEvent() && !game.getGameOptions().getMenu())
            for (LaserEvent laserEvent : events.getLaserEvents())
                laserEvent.drawLaserEvent(batch, game.getRobots());
        batch.end();
    }

    /**
     * Draws the cards until the user has chosen his or her cards.
     * @param game The game that is running.
     * @param batch The spriteBatch from UI.
     * @param stage The stage from UI.
     */
    public void drawCards(IGame game, SpriteBatch batch, Stage stage) {
        programCardsView.getDoneButton().draw(batch, stage.getWidth() / 2);
        for (Group group : programCardsView.getGroups()) {
            group.draw(batch, 1);
        }

        for (Image reboot : programCardsView.getReboots()) {
            reboot.draw(batch, 1);
        }
        if (programCardsView.done()) {
            cardPhase = false;
            stage.clear();
            game.shuffleTheRobotsCards(programCardsView.getOrder()); // TODO: Move to Game
            programCardsView.clear();
            events.setWaitMoveEvent(true);
        }
    }

    /**
     * Initializes the cards into fixed positions. Makes a button to click to finish choosing cards.
     * @param programCardsView Class with card images and groups with listeners to choose cards with.
     * @param stage The stage from UI.
     */
    public void initiateCards(ProgramCardsView programCardsView, Stage stage) {
        this.programCardsView = programCardsView;
        programCardsView.setDoneButton();

        // FIXME: Temp, need to calculate to this number
        float rebootListPositionX = 510;
        programCardsView.setReboots(3);

        int index = 0;
        for (Image reboot : programCardsView.getReboots()) {
            if (index > 0) {
                reboot.setX(rebootListPositionX += reboot.getWidth() * 1.5);
            } else {
                reboot.setX(rebootListPositionX);
            }
            index++;
        }

        stage.addActor(programCardsView.getDoneButton());

        float cardsGroupPositionX = stage.getWidth() - programCardsView.getGroups().size() * programCardsView.getCardWidth();

        float cardRowWidth = programCardsView.getGroups().size() * programCardsView.getCardWidth();

        float donePositionX = (stage.getWidth() - cardRowWidth) + programCardsView.getDoneButton().getWidth();

        programCardsView.getDoneButton().setX(donePositionX);

        System.out.println("Card row width: " + cardRowWidth);
        System.out.println("Done button position: " + programCardsView.getDoneButton().getX() + "," + programCardsView.getDoneButton().getY());

        cardsGroupPositionX = cardsGroupPositionX / 2 - programCardsView.getCardWidth();
        for (Group group : programCardsView.getGroups()) {
            group.setX(cardsGroupPositionX += programCardsView.getCardWidth());
            stage.addActor(group);
        }
        cardPhase = true;
        Gdx.input.setInputProcessor(stage);
    }

    public boolean getCardPhase() {
        return this.cardPhase;
    }
}
