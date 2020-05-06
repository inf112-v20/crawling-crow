package roborally.gameview.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import roborally.events.Events;
import roborally.game.IGame;
import roborally.game.robot.Robot;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

import static roborally.utilities.enums.UIElement.*;

public class UIElements {
    /*private ImageButton powerDownButton;
    private boolean hasPowerDownBeenActivated;
    private boolean isPowerDownSetForNextRound;*/

    private Label.LabelStyle messageStyle;
    private Label messageLabel;

    private Stage stage;
    private ImageButton restartButton;
    private ImageButton exitButton;

    private Leaderboard leaderboard;

    private Reboots reboots;
    private DamageTokens damageTokens;
    private Flags flags;

    private PowerDownButton powerDownButton;

    public UIElements() {
        this.reboots = new Reboots();
        this.damageTokens = new DamageTokens();
        this.flags = new Flags();

        this.powerDownButton = new PowerDownButton();

        //this.hasPowerDownBeenActivated = false;
        this.leaderboard = new Leaderboard();
    }

    public void createLeaderboard(ArrayList<Robot> robots) {
        leaderboard.addPlayers(robots);
        leaderboard.arrangeGroups();
    }

    public static Image get(UIElement element) {
        Image elementImage = new Image(element.getTexture());
        elementImage.setSize(elementImage.getPrefWidth() / SettingsUtil.UI_ELEMENT_SCALE, elementImage.getPrefHeight() / SettingsUtil.UI_ELEMENT_SCALE);
        return elementImage;
    }

    public ArrayList<Image> getReboots() {
        return reboots.get();
    }

    public ArrayList<Image> getDamageTokens() {
        return damageTokens.get();
    }

    public Flags getFlags() {
        return flags;
    }

    public PowerDownButton getPowerDownButton() {
        return powerDownButton;
    }

    /**
     * For debugging
     *
     * @param robot The user controlled robot
     */
    public void update(Robot robot) {
        reboots.set(robot, stage);
        damageTokens.set(robot, stage);
        flags.set(robot, stage);
        leaderboard.updateLeaderboard();
    }

    /*public void setPowerDownButton(@NotNull UIElement powerDownState) {
        powerDownButton = new ImageButton(new TextureRegionDrawable(powerDownState.getTexture()), new TextureRegionDrawable((POWERING_DOWN.getTexture())), new TextureRegionDrawable((POWERING_DOWN.getTexture())));

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float powerDownButtonFixedPosX = xShift - powerDownButton.getWidth();

        powerDownButton.setPosition(powerDownButtonFixedPosX, 130);

        powerDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (SettingsUtil.DEBUG_MODE) System.out.println("Activated power down");
                powerDownButton.setChecked(true);
                isPowerDownSetForNextRound = true;
                hasPowerDownBeenActivated = true;
            }
        });
    }

    public ImageButton getPowerDownButton() {
        return powerDownButton;
    }

    // For checking if the button has been pressed
    public boolean hasPowerDownBeenActivated() {
        return hasPowerDownBeenActivated;
    }

    public void hasPowerDownBeenActivated(boolean state) {
        this.hasPowerDownBeenActivated = state;
    }

    public boolean isPowerDownSetForNextRound() {
        return isPowerDownSetForNextRound;
    }

    public void setPowerDownForNextRound(boolean state) {
        this.isPowerDownSetForNextRound = state;
    }*/

    public void setMessageLabel(String message) {
        this.messageStyle = new Label.LabelStyle();
        this.messageStyle.fontColor = Color.YELLOW;
        this.messageStyle.font = new BitmapFont();
        this.messageLabel = new Label(message, messageStyle);
        this.messageLabel.setFontScale(3);
        float x = (stage.getWidth() / 2f) - (messageLabel.getPrefWidth() / 2f);
        float y = (((stage.getHeight() + SettingsUtil.MAP_HEIGHT) / 2f) + (messageLabel.getPrefHeight() / 2f));
        this.messageLabel.setPosition(x, y);
    }

    public Label getMessageLabel() {
        return messageLabel;
    }


    public void setRestartButton(IGame game) {
        this.restartButton = new ImageButton(new TextureRegionDrawable(RESTART_BUTTON.getTexture()), new TextureRegionDrawable((RESTART_BUTTON_PRESSED.getTexture())), new TextureRegionDrawable((RESTART_BUTTON_PRESSED.getTexture())));

        this.restartButton.setY(getExitButton().getY());


        this.restartButton.setX(getExitButton().getX() - getRestartButton().getWidth());

        this.restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (SettingsUtil.DEBUG_MODE) System.out.println("Clicked on restart button...");
                game.restartGame();
                clearAll();
            }
        });
    }

    public ImageButton getRestartButton() {
        return restartButton;
    }

    public void setExitButton(IGame game, Events events) {
        this.exitButton = new ImageButton(new TextureRegionDrawable(EXIT_BUTTON.getTexture()));
        float y = (((stage.getHeight() + SettingsUtil.MAP_HEIGHT) / 2f) + (exitButton.getHeight() / 2f));
        this.exitButton.setY(y);

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float quitButtonFixedX = xShift - exitButton.getWidth();
        this.exitButton.setX(quitButtonFixedX);

        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.endGame();
                events.setWonGame(true);
                clearAll();
            }
        });
    }

    public ArrayList<Group> getLeaderboard() {
        return leaderboard.getGroup();
    }

    public ImageButton getExitButton() {
        return exitButton;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void clearAll() {
        damageTokens.clear();
        reboots.clear();
        flags.clear();
        getMessageLabel().setText("");
        getExitButton().clear();
        getRestartButton().clear();
    }
}
