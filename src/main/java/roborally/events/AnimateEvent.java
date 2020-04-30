package roborally.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.IGame;
import roborally.game.robot.Robot;
import roborally.gameview.elements.ProgramCardsView;
import roborally.gameview.elements.UIElements;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

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
        drawUIElement(batch, uiElements.getReboots());
        drawUIElement(batch, uiElements.getDamageTokens());
        drawUIElement(batch, uiElements.getFlags());

        updateMessageLabel(game, batch, stage);

        drawPowerDownButton(batch, stage);
    }

    private void drawUIElement(SpriteBatch batch, ArrayList<Image> uiElementsList) {
        for (Image element : uiElementsList) {
            element.draw(batch, 1);
        }
    }

    private void drawPowerDownButton(SpriteBatch batch, Stage stage) {
        if (cardPhase) {
            stage.addActor(uiElements.getPowerDownButton());
            uiElements.getPowerDownButton().draw(batch, 1);
        }
    }

    private void updateMessageLabel(IGame game, SpriteBatch batch, Stage stage) {
        for (Robot robot : game.getRobots()) {
            checkRobotStatus(robot.getLogic().isDestroyed(), robot.getName() + " was destroyed!");
            checkRobotStatus(robot.isRobotInHole(), robot.getName() + " went into a hole!");
            checkRobotStatus(!robot.getLogic().isUserRobot() && robot.getLogic().hasWon(), "Sorry, you lost! " + robot.getName() + " wins!");
            checkRobotStatus(robot.getLogic().isUserRobot() && robot.getLogic().hasWon(), "You have won!");
        }

        uiElements.getMessageLabel().draw(batch, 1);

        if (uiElements.getMessageLabel().toString().contains("won")) {
            uiElements.setExitButton();
            uiElements.setRestartButton(game);
            stage.addActor(uiElements.getRestartButton());
            stage.addActor(uiElements.getExitButton());
            uiElements.getRestartButton().draw(batch, 1);
            uiElements.getExitButton().draw(batch, 1);
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void checkRobotStatus(boolean status, String message) {
        if (status) {
            uiElements.setMessageLabel(message);
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
