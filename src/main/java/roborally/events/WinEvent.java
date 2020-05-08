package roborally.events;

import com.badlogic.gdx.graphics.Color;

public class WinEvent {
	private float explosionTimer;

	public WinEvent () {
		this.explosionTimer = 0;
	}

	public void fireworks(float dt, Events events) {
		explosionTimer += dt;
		if(explosionTimer > 0.3 && explosionTimer < 0.315) {
			events.createNewExplosionEvent(400+ 400 * explosionTimer, 600 + 200 * explosionTimer, Color.RED);
		}
		else if(explosionTimer > 0.6 && explosionTimer < 0.615) {
			events.createNewExplosionEvent(500 + 200 * explosionTimer, 400 + 300 * explosionTimer, Color.BLUE);
		}
		else if (explosionTimer > 0.9 && explosionTimer < 0.915) {
			events.createNewExplosionEvent(300 + 500 * explosionTimer, 700 + 100 * explosionTimer, Color.GREEN);
		}
		else if (explosionTimer > 1.2 && explosionTimer < 1.215) {
			events.createNewExplosionEvent(700 + 100 * explosionTimer, 500 + 200 * explosionTimer, Color.YELLOW);
		}
		else if (explosionTimer > 1.5 && explosionTimer < 1.515) {
			events.createNewExplosionEvent(700 + 100 * explosionTimer, 500 + 200 * explosionTimer, Color.ORANGE);
		}
		else if(explosionTimer > 1.8 && explosionTimer < 1.815) {
			events.createNewExplosionEvent(1200 + 100 * explosionTimer, 450 + 200 * explosionTimer, Color.PINK);
		}
		else if (explosionTimer > 2.1 && explosionTimer < 2.115) {
			events.createNewExplosionEvent(1500 + 100 * explosionTimer, 500 + 200 * explosionTimer, Color.PURPLE);
		}
		else if (explosionTimer > 2.4 && explosionTimer < 2.415) {
			events.createNewExplosionEvent(1300 + 100 * explosionTimer, 500 + 200 * explosionTimer, Color.FIREBRICK);
		}
		else if(explosionTimer > 2.65)
			explosionTimer = 0;
	}
}
