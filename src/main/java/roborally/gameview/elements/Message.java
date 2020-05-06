package roborally.gameview.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import roborally.utilities.SettingsUtil;

public class Message {
    private Label messageLabel;

    public void set(String message, Stage stage) {
        Label.LabelStyle messageStyle = new Label.LabelStyle();
        messageStyle.fontColor = Color.YELLOW;
        messageStyle.font = new BitmapFont();
        this.messageLabel = new Label(message, messageStyle);
        this.messageLabel.setFontScale(3);
        float x = (stage.getWidth() / 2f) - (messageLabel.getPrefWidth() / 2f);
        float y = (((stage.getHeight() + SettingsUtil.MAP_HEIGHT) / 2f) + (messageLabel.getPrefHeight() / 2f));
        this.messageLabel.setPosition(x, y);
    }

    public Label get() {
        return messageLabel;
    }

    public void clear() {
        this.messageLabel.setText("");
    }
}
