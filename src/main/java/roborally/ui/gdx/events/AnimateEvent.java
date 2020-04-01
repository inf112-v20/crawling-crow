package roborally.ui.gdx.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import roborally.game.IGame;
import roborally.ui.ProgramCardsView;

public class AnimateEvent {
    private Events events;
    private ProgramCardsView programCardsView;
    private boolean cardPhase;

    public AnimateEvent(Events events) {
        this.events = events;
    }

    public void drawEvents(SpriteBatch batch, IGame game, Stage stage) {
        batch.begin();
        if (cardPhase) { // Enter has been clicked, and the cards have been initialized.
            drawCards(game, batch, stage);
            stage.act();
        }
        // Fades robots if the game is not currently paused and if there is robots to be faded.
        if (events.getFadeRobot() && !game.getGameOptions().getMenu())
            events.fadeRobots(batch);
        // Draws laser if the game is not currently paused and there has been fired lasers.
        if (events.hasLaserEvent() && !game.getGameOptions().getMenu())
            for (LaserEvent laserEvent : events.getLaserEvents())
                laserEvent.drawLaserEvent(batch, game.getRobots());
        batch.end();
    }

    public void drawCards(IGame game, SpriteBatch batch, Stage stage) {
        // Draws cards while cardPhase is true.

        programCardsView.getDoneLabel().draw(batch, stage.getWidth() / 2);
        for (Group group : programCardsView.getGroups()) {
            group.draw(batch, 1);
        }
        if (programCardsView.done()) { // The user(s) has chosen the cards he or she wants to play, the event starts.
            cardPhase = false; // TODO: Move to Game
            stage.clear();
            game.shuffleTheRobotsCards(programCardsView.getOrder()); // TODO: Move to Game
            programCardsView.clearStuff();
            events.setWaitMoveEvent(true); //Starts the event
        }
    }

    // Called by user with key input, initializes the cards into fixed positions relative to the number of cards.
    public void initiateCards(ProgramCardsView programCardsView, Stage stage) {
        this.programCardsView = programCardsView;
        programCardsView.makeDoneLabel();
        stage.addActor(programCardsView.getDoneLabel());
        float i = stage.getWidth() - programCardsView.getGroups().size() * programCardsView.getCardWidth();
        programCardsView.getDoneLabel().setX(stage.getWidth() / 2);
        i = i / 2 - programCardsView.getCardWidth();
        for (Group group : programCardsView.getGroups()) {
            group.setX(i += programCardsView.getCardWidth());
            stage.addActor(group);
        }
        cardPhase = true;
        Gdx.input.setInputProcessor(stage);
    }
}
