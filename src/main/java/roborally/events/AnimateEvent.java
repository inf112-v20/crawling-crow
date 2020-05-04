package roborally.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import java.util.List;

public class AnimateEvent {
    private Events events;
    private ProgramCardsView programCardsView;
    private ProgramCardsView registerCardsView;
    private UIElements uiElements;
    private boolean cardPhase;
    private boolean playPhase;
    private float abc;

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
            drawCardsInHand(game, batch, stage);
            stage.act();
        } else if (playPhase && !game.getGameOptions().inMenu()){
            drawRegisterCards(game, batch, stage);
        }
        if (events.getFadeRobot() && !game.getGameOptions().inMenu())
            events.fadeRobots(batch);
        if (events.hasLaserEvent() && !game.getGameOptions().inMenu())
            for (LaserEvent laserEvent : events.getLaserEvents())
                laserEvent.drawLaserEvent(batch, game.getRobots());
        if (!game.getGameOptions().inMenu())
            drawUIElements(game, batch, stage);
        if(events.hasExplosionEvent()) {
            for(List<Image> list : events.getExplosions()) {
                events.explode(Gdx.graphics.getDeltaTime(), (ArrayList<Image>) list);
                for(Image image : list)
                    image.draw(batch, 1);
            }
        }
        batch.end();
    }


    private void drawUIElements(IGame game, SpriteBatch batch, Stage stage) {
        drawUIElement(batch, uiElements.getReboots());
        drawUIElement(batch, uiElements.getDamageTokens());
        drawUIElement(batch, uiElements.getFlags());
        for(Group group : uiElements.getLeaderBoard())
            group.draw(batch, 1);

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
            checkRobotStatus(!robot.getLogic().isUserRobot() && robot.getLogic().hasWon(), "Sorry, you lost! " + robot.getName() + " won!");
            checkRobotStatus(robot.getLogic().isUserRobot() && robot.getLogic().hasWon(), "You have won!");
        }

        uiElements.getMessageLabel().draw(batch, 1);

        if (uiElements.getMessageLabel().toString().contains("won")) {
            uiElements.updateFlags(game.getUserRobot());
            uiElements.setExitButton(game, events);
            uiElements.setRestartButton(game);
            stage.addActor(uiElements.getRestartButton());
            stage.addActor(uiElements.getExitButton());
            uiElements.getRestartButton().draw(batch, 1);
            uiElements.getExitButton().draw(batch, 1);
            Gdx.input.setInputProcessor(stage);
            abc+=Gdx.graphics.getDeltaTime();
            if(abc > 0.3 && abc < 0.315) {
                events.createNewExplosionEvent(400+ 400 * abc, 600 + 200 * abc, Color.RED);
            }
            else if(abc > 0.6 && abc < 0.615) {
                events.createNewExplosionEvent(500 + 200 * abc, 400 + 300 * abc, Color.BLUE);
            }
            else if (abc > 0.9 && abc < 0.915) {
                events.createNewExplosionEvent(300 + 500 * abc, 700 + 100 * abc, Color.GREEN);
            }
            else if (abc > 1.2 && abc < 1.215) {
                events.createNewExplosionEvent(700 + 100 * abc, 500 + 200 * abc, Color.YELLOW);
            }
            else if (abc > 1.5)
                abc = 0;
            events.setWaitMoveEvent(false);
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
    private void drawCardsInHand(IGame game, SpriteBatch batch, Stage stage) {
        programCardsView.getTimerLabel().draw(batch, stage.getHeight() / 2);
        programCardsView.updateTimer(Gdx.graphics.getDeltaTime(), game.getUserRobot());
        programCardsView.getDoneButton().draw(batch, stage.getWidth() / 2);
        for (Group group : programCardsView.getGroups()) {
            group.draw(batch, 1);
        }

        if (programCardsView.done()) {
            cardPhase = false;
            stage.clear();

            game.orderTheUserRobotsCards(programCardsView.getOrder()); // TODO: Move to Game
            programCardsView.clear();
            events.setWaitMoveEvent(true);
        } else if (game.getUserRobot().getLogic().getPowerDown() || game.getUserRobot().getLogic().getNumberOfLockedCards() == SettingsUtil.REGISTER_SIZE) {
            cardPhase = false;
            stage.clear();
            programCardsView.clear();
            events.setWaitMoveEvent(true);
        }
    }

    private void drawRegisterCards(IGame game, SpriteBatch batch, Stage stage) {
        for (Group group : registerCardsView.getGroups()) {
            group.draw(batch, 1);
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
        programCardsView.setTimerLabel();

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

    /**
     * Initializes the cards in the register into fixed positions.
     * @param stage the stage from UI
     * @param registerView the ui reprentative of the register cards
     */
    public void initiateRegister(Stage stage, ProgramCardsView registerView) {
        this.registerCardsView = registerView;

        float cardsGroupPositionX = stage.getWidth() - registerCardsView.getGroups().size() * registerCardsView.getCardWidth();
        cardsGroupPositionX = cardsGroupPositionX / 2 - registerCardsView.getCardWidth();

        for (Group group : registerCardsView.getGroups()) {
            group.setX(cardsGroupPositionX += registerCardsView.getCardWidth());
        }

        playPhase = true;
    }

    public boolean getCardPhase() {
        return this.cardPhase;
    }
}
