package roborally.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.IGame;
import roborally.game.robot.Robot;
import roborally.gameview.ui.ProgramCardsView;
import roborally.gameview.ui.UIElements;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

import static roborally.utilities.SettingsUtil.STAGE_WIDTH;

public class AnimateEvent {
    private final Events events;
    private ProgramCardsView programCardsView;
    private ProgramCardsView registerCardsView;
    private final UIElements uiElements;
    private boolean cardPhase;
    private boolean playPhase;
    private final WinEvent winEvent;

    public AnimateEvent(Events events, ProgramCardsView programCardsView, UIElements uiElements) {
        this.events = events;
        this.programCardsView = programCardsView;
        this.uiElements = uiElements;
        this.winEvent = new WinEvent();
    }

    /**
     * Called from UI to draw events in order. If no events are active nothing gets drawn.
     * @param batch the spriteBatch from UI.
     * @param game the game that is running.
     * @param stage the stage from UI.
     */
    public void drawEvents(SpriteBatch batch, IGame game, Stage stage) {
        batch.begin();
        drawCardsInHandAndRegister(batch, game, stage);
        fadeRobots(batch);
        drawLasers(batch, game);
        drawUIElements(game, batch, stage);
        drawExplosions(batch);
        drawArchiveMarkers(batch, game);
        batch.end();
    }

    private void drawCardsInHandAndRegister(SpriteBatch batch, IGame game, Stage stage) {
        if (cardPhase) {
            drawCardsInHand(game, batch, stage);
            stage.act();
        } else if (playPhase)
            drawRegisterCards(batch, game);
    }

    private void fadeRobots(SpriteBatch batch) {
        if (events.getFadeRobot())
            events.fadeRobots(batch);
    }

    private void drawLasers(SpriteBatch batch, IGame game) {
        if (events.hasLaserEvent()) {
            for (LaserEvent laserEvent : events.getLaserEvents()) {
                laserEvent.drawLaserEvent(batch, game.getRobots());
            }
        }
    }

    private void drawExplosions(SpriteBatch batch) {
        if (events.hasExplosionEvent()) {
            for (List<Image> list : events.getExplosions()) {
                events.explode(Gdx.graphics.getDeltaTime(), (ArrayList<Image>) list);
                for(Image image : list)
                    image.draw(batch, 1);
            }
        }
    }

    private void drawArchiveMarkers(SpriteBatch batch, IGame game) {
        if (events.hasArchiveBorders() && !game.getGameOptions().inMenu()) {
            for(Image image : events.getArchiveBorders().values()) {
                image.draw(batch, 1);
            }
        }
    }


    private void drawUIElements(IGame game, SpriteBatch batch, Stage stage) {
        drawUIElement(batch, uiElements.getReboots());
        drawUIElement(batch, uiElements.getDamageTokens());
        drawUIElement(batch, uiElements.getFlags().get());
        for (Group group : uiElements.getLeaderboard()) {
            group.draw(batch, 1);
        }

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
            stage.addActor(uiElements.getPowerDownButton().get());
            uiElements.getPowerDownButton().get().draw(batch, 1);
        }
    }

    private void updateMessageLabel(IGame game, SpriteBatch batch, Stage stage) {
        for (Robot robot : game.getRobots()) {
            checkRobotStatus(robot.getLogic().isDestroyed(), robot.getName() + " was destroyed!");
            checkRobotStatus(robot.isRobotInHole(), robot.getName() + " went into a hole!");
            checkRobotStatus(!robot.getLogic().isUserRobot() && robot.getLogic().hasWon(), "Sorry, you lost! " + robot.getName() + " won!");
            checkRobotStatus(robot.getLogic().isUserRobot() && robot.getLogic().hasWon(), "You have won!");
            checkRobotStatus(robot.getLogic().isUserRobot() && robot.getLogic().getReboots() == 0, "You lost");
        }

        uiElements.getMessage().get().draw(batch, 1);

        checkIfSomeoneWon(game, batch, stage);
    }

    private void checkIfSomeoneWon(IGame game, SpriteBatch batch, Stage stage) {
        if (uiElements.getMessage().get().toString().contains("won") || uiElements.getMessage().get().toString().contains("lost")) {
            uiElements.getFlags().update(game.getUserRobot());
            uiElements.getExitButton().set(game, events, uiElements);
            uiElements.getRestartButton().set(game, uiElements);
            stage.addActor(uiElements.getRestartButton().get());
            stage.addActor(uiElements.getExitButton().get());
            uiElements.getRestartButton().get().draw(batch, 1);
            uiElements.getExitButton().get().draw(batch, 1);
            Gdx.input.setInputProcessor(stage);
            winEvent.fireworks(Gdx.graphics.getDeltaTime(), events);
            events.setWaitMoveEvent(false);
        }
    }

    private void checkRobotStatus(boolean status, String message) {
        if (status) {
            uiElements.getMessage().set(message);
        }
    }

    /**
     * Draws the cards until the user has chosen his or her cards.
     * @param game The game that is running.
     * @param batch The spriteBatch from UI.
     * @param stage The stage from UI.
     */
    private void drawCardsInHand(IGame game, SpriteBatch batch, Stage stage) {
        programCardsView.getTimer().get().draw(batch, 1);
        programCardsView.getTimer().update(Gdx.graphics.getDeltaTime(), game.getUserRobot(), programCardsView);
        programCardsView.getDoneButton().get().draw(batch, 1);
        for (Group card : programCardsView.getCards()) {
            card.draw(batch, 1);
        }

        if (programCardsView.done()) {
            cardPhase = false;
            events.setCardPhase(false);
            stage.clear();

            game.orderTheUserRobotsCards(programCardsView.getOrder());
            programCardsView.clear();
            events.setWaitMoveEvent(true);
        }
    }

    private void drawRegisterCards(SpriteBatch batch, IGame game) {
        if (!game.getUserRobot().getLogic().getPowerDown()) {
            for (Group card : registerCardsView.getCards()) {
                card.draw(batch, 1);
            }
        }
    }

    /**
     * Initializes the cards into fixed positions. Makes a button to click to finish choosing cards.
     *
     * @param stage The stage from UI.
     */
    public void initiateCards(Stage stage, ProgramCardsView programCardsView) {
        this.programCardsView = programCardsView;
        programCardsView.getDoneButton().set(programCardsView);
        programCardsView.getTimer().set(programCardsView);

        float cardsGroupPositionX = STAGE_WIDTH - programCardsView.getCards().size() * programCardsView.getCardWidth();
        cardsGroupPositionX = cardsGroupPositionX / 2 - programCardsView.getCardWidth();

        for (Group card : programCardsView.getCards())
            card.setX(cardsGroupPositionX += programCardsView.getCardWidth());
        putProgramCardsViewInStage(stage, programCardsView);
        // xShift to the right-side edge of the game board
        float xShift = (STAGE_WIDTH + SettingsUtil.MAP_WIDTH) / 2f;
        float doneButtonPosX = xShift - programCardsView.getDoneButton().get().getWidth();
        programCardsView.getDoneButton().get().setX(doneButtonPosX);

        this.cardPhase = true;
        events.setCardPhase(true);
        Gdx.input.setInputProcessor(stage);
    }

    public void putProgramCardsViewInStage(Stage stage, ProgramCardsView programCardsView) {
        stage.addActor(programCardsView.getDoneButton().get());
        for (Group card : programCardsView.getCards())
            stage.addActor(card);
    }

    /**
     * Initializes the cards in the register into fixed positions.
     *
     * @param registerView the ui representative of the register cards
     */
    public void initiateRegister(ProgramCardsView registerView) {
        this.registerCardsView = registerView;

        float cardsGroupPositionX = STAGE_WIDTH - registerCardsView.getCards().size() * registerCardsView.getCardWidth();
        cardsGroupPositionX = cardsGroupPositionX / 2 - registerCardsView.getCardWidth();

        for (Group card : registerCardsView.getCards()) {
            card.setX(cardsGroupPositionX += registerCardsView.getCardWidth());
        }

        this.playPhase = true;
    }

    public boolean getCardPhase() {
        return cardPhase;
    }
}
