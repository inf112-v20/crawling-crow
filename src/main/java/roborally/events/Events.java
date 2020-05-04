package roborally.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.IGame;
import roborally.game.robot.Robot;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** This class handles events in the game.
 * An event is computation stretched over time,
 * to make the visualization of the game possible
 * to follow as a human player. Takes use of time
 * spent between frames in render to scale the events.
 * */
public class Events {
    private boolean waitEvent;
    private boolean wonGame;
    private float dt;
    private boolean robotFadeOrder;
    private ArrayList<Alpha> fadeableRobots;
    private int fadeCounter;
    private ArrayList<LaserEvent> laserEvents;
    private double gameSpeed;
    private int factor;
    private Stage stage;
    private float xShift;
    private float yShift;
    private List<List<Image>> explosions;

    public Events() {
        this.waitEvent = false;
        this.dt = 0f;
        this.robotFadeOrder = false;
        this.fadeableRobots = new ArrayList<>();
        this.fadeCounter = 0;
        this.laserEvents = new ArrayList<>();
        this.gameSpeed = 0.2;
        this.setLaserSpeed("normal");
        this.explosions = new ArrayList<>();
    }

    /**
     * Updates the time limit to play the next card in the current phase of the round.
     * (Calculated by time between frames).
     *
     * @param gameSpeed the limit.
     */
    public void setGameSpeed(String gameSpeed) {
        if ("fastest".equals(gameSpeed))
            this.gameSpeed = 0.01;
        else if ("fast".equals(gameSpeed))
            this.gameSpeed = 0.2;
        else if ("normal".equals(gameSpeed))
            this.gameSpeed = 0.5;
    }

    /** Sets the factor used in {@link LaserEvent} to increment the image of the laser relative to its direction */
    public void setLaserSpeed(String laserSpeed) {
        if ("slow".equals(laserSpeed))
            this.factor = 400;
        else if ("normal".equals(laserSpeed))
            this.factor = 800;
        else if ("fast".equals(laserSpeed))
            this.factor = 1200;
    }

    /**
     * Adds the time lapsed between frames to the variable, plays the next card when it reaches the gameSpeed.
     *
     * @param dt   Time lapsed between frames.
     * @param game The game.
     */
    public void waitMoveEvent(float dt, IGame game) {
        this.dt += dt;
        this.dt = game.continueGameLoop(this.dt, this.gameSpeed);
    }

    public boolean hasWaitEvent() {
        return waitEvent;
    }

    // Lets the UI know wether or not there are robots ready to move.
    public void setWaitMoveEvent(boolean value) {
        this.waitEvent = value;
    }

    /**
     * Replaces the robots texture with an image and fades it.
     *
     * @param pos     The robots position.
     * @param texture The robots texture.
     */
    public void fadeRobot(GridPoint2 pos, TextureRegion[][] texture, boolean falling, Color color) {
        Image image = new Image(texture[0][1]);
        image.setX(pos.x * SettingsUtil.TILE_SCALE + xShift);
        image.setY(pos.y * SettingsUtil.TILE_SCALE + yShift);
        image.setSize(SettingsUtil.TILE_SCALE, SettingsUtil.TILE_SCALE);
        this.fadeableRobots.add(new Alpha(1f, image, falling, color));
    }

    // Returns true if there are robots to be faded.
    public boolean getFadeRobot() {
        return this.robotFadeOrder;
    }

    public void setFadeRobot(boolean value) {
        this.robotFadeOrder = value;
    }

    // Fades the robots, clears the list of robots to be faded if all subjects have fully faded.
    public void fadeRobots(SpriteBatch batch) {

        for (Alpha alpha : this.fadeableRobots) {
            alpha.image.draw(batch, alpha.update(Gdx.graphics.getDeltaTime()));
            if (alpha.dt <= 0)
                fadeCounter++;
        }

        if (fadeCounter == this.fadeableRobots.size()) {
            for(Alpha alpha : fadeableRobots)
                if(!alpha.falling)
                    createNewExplosionEvent(alpha.image.getX(), alpha.image.getY(), alpha.color);
            this.fadeableRobots.clear();
            setFadeRobot(false);
        }
        fadeCounter = 0;
    }


    // Returns true if there are lasers on the screen.
    public boolean hasLaserEvent() {
        return !this.laserEvents.isEmpty();
    }

    /**
     * Removes lasers that have served their purpose. Returns and sets the list excluding these lasers.
     */
    public ArrayList<LaserEvent> getLaserEvents() {
        List<LaserEvent> temp = this.laserEvents.stream()
                .filter(LaserEvent::hasLaserEvent)
                .collect(Collectors.toList());
        this.laserEvents.removeAll(temp);
        for (LaserEvent laserEvent : this.laserEvents) {
            if (laserEvent.getRobot() != null) { // Fades and removes robots shot by lasers interactively.
                if (("Destroyed".equals(laserEvent.getRobot().getLogic().getStatus()))) {
                    GridPoint2 pos = laserEvent.getRobot().getPosition();
                    Color color = findColorByName(laserEvent.getRobot().getLogic().getName());
                    fadeRobot(pos, laserEvent.getRobot().getTexture(), laserEvent.getRobot().isFalling(), color);
                    laserEvent.getRobot().deleteRobot();
                    setFadeRobot(true);
                }
            }
        }
        this.laserEvents = (ArrayList<LaserEvent>) temp;
        return this.laserEvents;
    }

    public Color findColorByName(String name) {
        if(name.toLowerCase().contains("red"))
            return Color.RED;
        else if(name.toLowerCase().contains("blue"))
            return Color.BLUE;
        else if(name.toLowerCase().contains("green"))
            return Color.GREEN;
        else if(name.toLowerCase().contains("orange"))
            return Color.ORANGE;
        else if(name.toLowerCase().contains("purple"))
            return Color.PURPLE;
        else if(name.toLowerCase().contains("pink"))
            return Color.PINK;
        else if(name.toLowerCase().contains("yellow"))
            return Color.YELLOW;
        else if(name.toLowerCase().contains("angry"))
            return Color.FIREBRICK;
        return Color.RED;
    }

    /**
     * Creates a new Laser event for the laser that has been fired.
     *
     * @param origin The position the laser was fired from.
     * @param pos    The endpoint of the laser.
     */
    public void createNewLaserEvent(GridPoint2 origin, GridPoint2 pos) {
        this.laserEvents.add(new LaserEvent(factor, stage));
        this.laserEvents.get(this.laserEvents.size() - 1).laserEvent(origin, pos);
    }

    public void dispose() {
        this.laserEvents.clear();
        this.fadeableRobots.clear();
        this.explosions.clear();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.xShift = (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
        this.yShift = (stage.getHeight() - SettingsUtil.MAP_HEIGHT) / 2f;
    }

    public void createNewExplosionEvent(float x, float y, Color color) {
        ArrayList<Image> exploded = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            exploded.add(new Image(new Texture("explosion.png")));
            exploded.get(i).setX(x);
            exploded.get(i).setY(y);
            exploded.get(i).setColor(color);
        }
        explosions.add(exploded);
    }

    public boolean hasExplosionEvent() {
        List<List<Image>> temp = new ArrayList<>();
        for(List<Image> list : explosions) {
            int explodedPieceOffMap = 0;
            for(Image image : list) {
                if(image.getX() < 0 || image.getY() < 0 || image.getX() > 1920 || image.getY() > 1080)
                    explodedPieceOffMap++;
            }
            if(explodedPieceOffMap <= 2)
                temp.add(list);
        }
        this.explosions = temp;
        return !explosions.isEmpty();
    }

    public void explode(float dt, ArrayList<Image> explosions) {
        explosions.get(0).setY(explosions.get(0).getY() + dt * 500);
        explosions.get(1).setY(explosions.get(1).getY() - dt * 500);
        explosions.get(2).setX(explosions.get(2).getX() + dt * 500);
        explosions.get(3).setX(explosions.get(3).getX() - dt * 500);
    }

    public List<List<Image>> getExplosions() {
        return this.explosions;
    }

    /**
     * Class to take individual images of robots and fade them separately, (subtracts alpha until invisible).
     */
    private static class Alpha {
        private float dt;
        private final Image image;
        private final boolean falling;
        private Color color;

        private Alpha(float dt, Image image, boolean falling, Color color) {
            this.dt = dt;
            this.image = image;
            this.color = color;
            this.falling = falling;
        }

        private float update(float dt) {
            if(falling)
                falling();
            this.dt -= (0.333f * dt);
            return this.dt;
        }
        private void falling() {
            float oldX = image.getX();
            float oldY = image.getY();
            float width = image.getWidth();
            float height = image.getHeight();
            image.setSize(width / 1.015f, height / 1.015f);
            image.setX((width - image.getWidth()) / 1.75f + oldX);
            image.setY((height - image.getHeight()) / 3f + oldY);
            image.setOrigin(width - image.getWidth() / 2, height - image.getHeight() / 2);
            image.rotateBy( -10f);
        }
    }

    public void checkForDestroyedRobots(ArrayList<Robot> robots) {
        for (Robot robot : robots) {
            if (("Destroyed").equals(robot.getLogic().getStatus())) {
                System.out.println("\t- " + robot.getName() + " was destroyed");
                removeFromUI(robot);
            }
        }
    }

    public void removeFromUI(Robot robot) {
        Color color = findColorByName(robot.getName());
        fadeRobot(robot.getPosition(), robot.getTexture(), robot.isFalling(), color);
        robot.deleteRobot();
        System.out.println("\t- Removed " + robot.getName() + " from UI");
        setFadeRobot(true);
    }

    public boolean wonGame() {
        return wonGame;
    }

    public void setWonGame(boolean state) {
        this.wonGame = state;
    }
}
