package roborally.gameview.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.Robot;
import roborally.gameview.ui.elements.*;
import roborally.gameview.ui.elements.buttons.ExitButton;
import roborally.gameview.ui.elements.buttons.PowerDownButton;
import roborally.gameview.ui.elements.buttons.RestartButton;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

public class UIElements {
    private Stage stage;

    private Leaderboard leaderboard;
    private Reboots reboots;
    private DamageTokens damageTokens;
    private Flags flags;
    private PowerDownButton powerDownButton;
    private ExitButton exitButton;
    private RestartButton restartButton;
    private Message message;

    public UIElements() {
        this.reboots = new Reboots();
        this.damageTokens = new DamageTokens();
        this.flags = new Flags();
        this.powerDownButton = new PowerDownButton();
        this.exitButton = new ExitButton();
        this.restartButton = new RestartButton();
        this.message = new Message();
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

    public Message getMessage() {
        return message;
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
        getMessage().clear();
        getExitButton().clear();
        getRestartButton().clear();
    }

    public Stage getStage() {
        return stage;
    }

    public void update(Robot robot) {
        reboots.set(robot, stage);
        damageTokens.set(robot, stage);
        flags.set(robot, stage);
        leaderboard.updateLeaderboard();
    }
}
