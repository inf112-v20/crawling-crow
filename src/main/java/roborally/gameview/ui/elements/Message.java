package roborally.gameview.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import roborally.utilities.SettingsUtil;

import static roborally.utilities.SettingsUtil.STAGE_HEIGHT;
import static roborally.utilities.SettingsUtil.STAGE_WIDTH;

public class Message {
    private Label messageLabel;

    public void set(String message) {
        Label.LabelStyle messageStyle = new Label.LabelStyle();
        messageStyle.fontColor = Color.YELLOW;
        messageStyle.font = new BitmapFont();
        this.messageLabel = new Label(message, messageStyle);
        this.messageLabel.setFontScale(3);
        float x = (STAGE_WIDTH / 2f) - (messageLabel.getPrefWidth() / 2f);
        float y = (((STAGE_HEIGHT + SettingsUtil.MAP_HEIGHT) / 2f) + (messageLabel.getPrefHeight() / 2f));
        this.messageLabel.setPosition(x, y);
    }

    public Label get() {
        return messageLabel;
    }

    public void clear() {
        this.messageLabel.setText("");
    }
}
