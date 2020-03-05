package roborally.game.objects.laser;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.tools.AssetManagerUtil;

import java.util.*;

public class Lasers {
    private HashMap<String, HashSet<Laser>> activeLasers;

    public Lasers() {
        activeLasers = new HashMap<>();
    }

    /**
     * Makes lasers and adds them into a register of lasers the robot is active in.
     * @param id   the id of the laser, be it (47) vertical or (39) horizontal.
     * @param name The name of the robot
     * @param pos  The position of the robot
     */
    public void createLaser(int id, GridPoint2 pos, String name) {
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.STEPIN_LASER);
        sound.play((float) 0.1);
        Laser laser = new Laser(id);
        if(id!=40) {
            laser.findLaser(pos);
            checkForLaser(name, laser);
        }
        else
            makeTwoLasers(name, pos);
    }

    /**
     * See if the robot still is in one of the laser it is registered in, removes the ones it is no longer in.
     * @param name The name of the robot
     * @param pos the position of the robot.
     */
    public void updateLaser(String name, GridPoint2 pos) {
        HashSet<Laser> temp = new HashSet<>();
        if (activeLasers.get(name) != null) {
            activeLasers.get(name).forEach(laser -> {
                laser.update();
                if (laser.gotPos(pos)) {
                    System.out.println("Cannon at " + pos);
                }
                else
                    temp.add(laser);
            });
            temp.forEach(laser -> activeLasers.get(name).remove(laser));
            System.out.println(activeLasers.get(name));
        }
    }

    /**
     * Checks through the set of lasers the robot is in, if the new laser is from the same cannon.
     * Adds a new laser if its not the same cannon. Updates the laser.
     * @param name The name of the robot
     * @param newLaser  The new laser being added
     */
    public void checkForLaser(String name, Laser newLaser) {
        boolean hasLaser = false;
        if (activeLasers.containsKey(name)) {
            HashSet<Laser> lasers = activeLasers.get(name);
            for (Laser laser : lasers) {
                System.out.println(laser.getId() + " " + laser.getPos() + " " + newLaser.getPos());
                laser.update();
                if (laser.getPos().equals(newLaser.getPos()))
                    hasLaser = true;
            }
        }
        else
            activeLasers.put(name, new HashSet<>());
        if(!hasLaser) {
            activeLasers.get(name).add(newLaser);
            newLaser.update(); // update
        }
    }

    // Finds both the vertical and horizontal cannon. Adds them.
    public void makeTwoLasers(String name, GridPoint2 pos) {
        Laser laser = new Laser(39);
        laser.findLaser(pos);
        checkForLaser(name, laser);
        Laser laser2 = new Laser(47);
        laser2.findLaser(pos);
        checkForLaser(name, laser2);
    }
}
