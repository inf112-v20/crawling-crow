package roborally.ui.gdx;

import roborally.game.IGame;

public class Events {
    private boolean waitEvent;
    private int pauseCount;
    private float dt;
    public Events() {
        this.waitEvent = false;
        this.dt = 0f;
        this.pauseCount = 1;
    }

    public void waitMoveEvent(float dt, IGame game) {
        this.dt += dt;
        if(this.dt >= 0.5) {
            game.playNextCard();
            this.dt = 0;
            this.pauseCount++;
        }
        if(pauseCount>=5) {
            this.dt = 0f;
            this.pauseCount = 1;
            setPauseEvent(false);
        }
    }

    public boolean hasWaitEvent() {
        return waitEvent;
    }

    public void setPauseEvent(boolean value) {
        this.waitEvent = value;
    }

}
