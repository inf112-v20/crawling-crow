package roborally.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;

public class UIElements {
    private final static float REBOOT_IMAGE_UNIT_SCALE = 2.5f;
    private Image rebootActive;
    private ArrayList<Image> reboots;
    private Image rebootInActive;

    public UIElements() {
        this.reboots = new ArrayList<>();
    }


    // TODO: Refactor
    public void setReboots(int availableReboots) {
        for (int i = 0; i < availableReboots; i++) {
            setRebootActive();
            this.reboots.add(getRebootActive());
        }

        if (availableReboots == 2) {
            setRebootInActive();
            this.reboots.add(getRebootInActive());
        } else if (availableReboots == 1) {
            setRebootInActive();
            this.reboots.add(getRebootInActive());
            setRebootInActive();
            this.reboots.add(getRebootInActive());
        } else if (availableReboots == 0) {
            for (int i = 0; i < 3; i++) {
                setRebootInActive();
                this.reboots.add(getRebootInActive());
            }
        }
    }

    public void setRebootActive() {
        this.rebootActive = new Image(AssetManagerUtil.getRebootActive());
        this.rebootActive.setPosition(0, 150);
        this.rebootActive.setSize(rebootActive.getPrefWidth() / REBOOT_IMAGE_UNIT_SCALE, rebootActive.getPrefHeight() / REBOOT_IMAGE_UNIT_SCALE);
    }

    public Image getRebootActive() {
        return rebootActive;
    }

    public void setRebootInActive() {
        this.rebootInActive = new Image(AssetManagerUtil.getRebootInactive());
        this.rebootInActive.setPosition(0, 150);
        this.rebootInActive.setSize(rebootInActive.getPrefWidth() / REBOOT_IMAGE_UNIT_SCALE, rebootInActive.getPrefHeight() / REBOOT_IMAGE_UNIT_SCALE);
    }

    public Image getRebootInActive() {
        return rebootInActive;
    }

    public ArrayList<Image> getReboots() {
        return reboots;
    }
}
