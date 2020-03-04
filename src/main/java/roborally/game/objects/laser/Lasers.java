package roborally.game.objects.laser;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.tools.AssetManagerUtil;

import java.util.*;

public class Lasers {
    private HashMap<String, HashSet<Laser>> activeLasers;
    private ArrayList<Integer> laserId;

    public Lasers() {
        activeLasers = new HashMap<>();
        laserId = new ArrayList<>();
    }

    /**
     * Makes a new laser, horizontal or vertical. Directly checks if the robot is currently in a laser instance.
     *
     * @param id   the id of the laser, be it (47) vertical or (39) horizontal.
     * @param name The name of the robot
     * @param pos  The position of the robot
     *             If it is not in a laser instance then it will create a new laser out of the position, and add this robot to a list.
     */
    public void createLaser(int id, GridPoint2 pos, String name) {
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEPIN_LASER);
        sound.play((float) 0.1);
        Laser laser = new Laser(id); // sjekke id først, er det fler enn 1 laser så legger man til flere lasere.
        int cannonId = laser.findLaser(pos);
        laser.update(); // update
        if (!checkForLaser(name, pos, cannonId)) {
            activeLasers.get(name).add(laser);
            laserId.add(cannonId);
        }
    }

    public void getName(String name, GridPoint2 pos) {
        if (activeLasers.get(name) != null) {
            for (Laser laser : activeLasers.get(name)) {
                laser.update();
                if (!laser.gotPos(pos))
                    activeLasers.get(name).remove(laser);
            }
        }
    }

    /**
     * Checks if the robot was in a laser and has moved out of the laser, but first checks if it is still in the instance.
     *
     * @param name The name of the robot
     * @param pos  The position of the robot.
     *             Restores the entire laser if it's no longer in the instance, else it moves in the laser.
     */
    public boolean checkForLaser(String name, GridPoint2 pos, int id) {
        boolean hasId = false;
        if (activeLasers.containsKey(name)) {
            HashSet<Laser> lasers = activeLasers.get(name);
            for (Laser laser : lasers) {
                laser.update();
                if (laserId.contains(id))
                    hasId = true;
                if (!laser.gotPos(pos))
                    activeLasers.get(name).remove(laser);
            }
        } else
            activeLasers.put(name, new HashSet<>());
        return hasId;
    }
}
