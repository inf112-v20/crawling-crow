package roborally.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.IGame;
import roborally.gameview.elements.ProgramCardsView;
import roborally.gameview.elements.UIElements;
import roborally.utilities.SettingsUtil;

public class AnimateEvent {
    private Events events;
    private ProgramCardsView programCardsView;
    private UIElements uiElements;
    private boolean cardPhase;

    public AnimateEvent(Events events, ProgramCardsView programCardsView, UIElements uiElements) {
        this.events = events;
        this.programCardsView = programCardsView;
        this.uiElements = uiElements;
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
        if (!game.getGameOptions().getMenu())
            drawUIElements(game, batch, stage);

        batch.end();
    }

    private void drawUIElements(IGame game, SpriteBatch batch, Stage stage) {
        for (Image reboot : uiElements.getReboots()) {
            reboot.draw(batch, 1);
        }

        for (Image damageToken : uiElements.getDamageTokens()) {
            damageToken.draw(batch, 1);
        }

        for (Image flag : uiElements.getFlags()) {
            flag.draw(batch, 1);
        }

        if (game.getUserRobot().getLogic().getHealth() <= 0) {
            uiElements.setMessageLabel("You died");
        }

        if (game.getUserRobot().isRobotInHole()) {
            uiElements.setMessageLabel("You went into a hole");
        }

        // TODO: Clean up this
        if (game.getUserRobot().getLogic().hasWon()) {
            uiElements.setMessageLabel(game.getUserRobot().getName() + " has won!");
        }

        uiElements.getMessageLabel().draw(batch, 1);

        if (uiElements.getMessageLabel().toString().contains("has won")) {
            uiElements.setQuitButton();
            uiElements.setRestartButton(game);
            stage.addActor(uiElements.getRestartButton());
            stage.addActor(uiElements.getQuitButton());
            uiElements.getRestartButton().draw(batch, 1);
            uiElements.getQuitButton().draw(batch, 1);
            Gdx.input.setInputProcessor(stage);
        }

        // TODO: Not sure if this is the correct way to do it, but it's temp
        if (cardPhase) {
            stage.addActor(uiElements.getPowerDownButton());
            uiElements.getPowerDownButton().draw(batch, 1);
        }
    }

    /**
     * Draws the cards until the user has chosen his or her cards.
     * @param game The game that is running.
     * @param batch The spriteBatch from UI.
     * @param stage The stage from UI.
     */
    private void drawCards(IGame game, SpriteBatch batch, Stage stage) {
        programCardsView.getCountdownLabel().draw(batch, stage.getHeight() / 2);
        programCardsView.getTimerLabel().draw(batch, stage.getHeight() / 2);
        programCardsView.updateTimer(Gdx.graphics.getDeltaTime(), game.getUserRobot());
        programCardsView.getDoneButton().draw(batch, stage.getWidth() / 2);
        for (Group group : programCardsView.getGroups()) {
            group.draw(batch, 1);
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
     *
     * @param stage The stage from UI.
     */
    public void initiateCards(Stage stage, ProgramCardsView programCardsView) {
        this.programCardsView = programCardsView;
        programCardsView.setStage(stage);
        programCardsView.setDoneButton();
        programCardsView.setCountdownLabel();
        programCardsView.setTimerLabel();

        stage.addActor(programCardsView.getCountdownLabel()); // FIXME: redundant
        stage.addActor(programCardsView.getTimerLabel()); // FIXME: redundant
        stage.addActor(programCardsView.getDoneButton());

        float cardsGroupPositionX = stage.getWidth() - programCardsView.getGroups().size() * programCardsView.getCardWidth();


        cardsGroupPositionX = cardsGroupPositionX / 2 - programCardsView.getCardWidth();
        for (Group group : programCardsView.getGroups()) {
            group.setX(cardsGroupPositionX += programCardsView.getCardWidth());
            stage.addActor(group);
        }
        // xShift to the right-side edge of the game board
        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float doneButtonPosX = xShift - programCardsView.getDoneButton().getWidth();
        programCardsView.getDoneButton().setX(doneButtonPosX);

        cardPhase = true;
        Gdx.input.setInputProcessor(stage);
    }

    public boolean getCardPhase() {
        return this.cardPhase;
    }
}