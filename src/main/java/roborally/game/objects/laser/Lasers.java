package roborally.game.objects.laser;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;

public class Lasers {
    private String name;
    private Laser laser;
    private HashMap<String, Laser> lasers;
    private GridPoint2 pos;

    public Lasers() {
        this.name = "";
        lasers = new HashMap<>();
    }
    /** Makes a new laser, horizontal or vertical. Directly checks if the robot is currently in a laser instance.
     * @param id the id of the laser, be it (47) vertical or (39) horizontal.
     * @param name The name of the robot
     * @param pos The position of the robot
     * If it is not in a laser instance then it will create a new laser out of the position, and add this robot to a list.
     */
    public void createLaser(int id, GridPoint2 pos, String name) {
        this.pos = pos;
        if(!checkIfRobotMovesInLaser(name)) {
            this.laser = new Laser(id);
            laser.findLaser(this.pos);
            laser.removeLaser();
            this.name = name;
            lasers.put(name, laser);
        }
    }

    /** Checks if the robot was in a laser and has moved out of the laser, but first checks if it is still in the instance.
     * @param name The name of the robot
     * @param pos The position of the robot.
     * Restores the entire laser if it's no longer in the instance, else it moves in the laser.
     */
    public void checkIfRobotWasInLaser(String name, GridPoint2 pos) {
        if (this.name.equals(name)) {
            if(laser.checkIfPosIsInLaserPath(pos))
                laser.moveInLaser(pos);
            else {
                laser.restoreLaser();
                this.name = "";
                this.laser = null;
                lasers.remove(name);
            }
        }
    }

    /** Checks if the robot is currently in a laser instance, if it is then it moves inside the laser instance.
     * @param name The name of the robot
     * Returns true if the laser is in an instance, else false.
     */
    public boolean checkIfRobotMovesInLaser(String name) {
        if (lasers.get(name) != null) {
            lasers.get(name).moveInLaser(this.pos);
            return true;
        }
        return false;
    }
}
