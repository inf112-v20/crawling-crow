package roborally.gameview.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import roborally.game.robot.Robot;
import roborally.gameview.elements.buttons.ExitButton;
import roborally.gameview.elements.buttons.PowerDownButton;
import roborally.gameview.elements.buttons.RestartButton;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

public class UIElements {
    private Label.LabelStyle messageStyle;
    private Label messageLabel;

    private Stage stage;
    //private ImageButton restartButton;

    private Leaderboard leaderboard;

    private Reboots reboots;
    private DamageTokens damageTokens;
    private Flags flags;

    private PowerDownButton powerDownButton;
    private ExitButton exitButton;
    private RestartButton restartButton;

    public UIElements() {
        this.reboots = new Reboots();
        this.damageTokens = new DamageTokens();
        this.flags = new Flags();
        this.powerDownButton = new PowerDownButton();
        this.exitButton = new ExitButton();
        this.restartButton = new RestartButton();

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

    public ExitButton getExitButton() {
        return exitButton;
    }

    public RestartButton getRestartButton() {
        return restartButton;
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

    public ArrayList<Group> getLeaderboard() {
        return leaderboard.getGroup();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void clearAll() {
        getDamageTokens().clear();
        getReboots().clear();
        getFlags().clear();
        getMessageLabel().setText("");
        getExitButton().clear();
        getRestartButton().clear();
    }
}
