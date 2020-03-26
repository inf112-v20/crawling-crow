package roborally.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.TileName;

import java.util.*;

public class Phase implements IPhase {

    private final boolean DEBUG = true;

    private ArrayList<Robot> robots;
    private Events events;
    private Robot winner;
    private ArrayList<IFlag> flags;
    private Queue<Robot> robotQueue;


    public Phase(ArrayList<Robot> robots, ArrayList<IFlag> flags, Events events){
        this.robots = robots;
        this.events = events;
        this.flags = flags;
        this.robotQueue = new LinkedList<>();
    }

    @Override
    public void revealProgramCards() {

    }

    @Override
    public void playNextRegisterCard() {
        if(robotQueue.isEmpty()) {
            this.robots.sort(Comparator.comparing(Robot::peekNextCard));
            robotQueue.addAll(robots);
        }
        Objects.requireNonNull(robotQueue.poll()).playNextCard();
        events.checkForDestroyedRobots(this.robots);
    }

    @Override
    public void moveAllConveyorBelts(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        moveExpressConveyorBelts(layers);
        moveExpressConveyorBelts(layers);
        moveNormalConveyorBelts(layers);
    }

    @Override
    public void moveCogs(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        for (Robot robot : robots) {
            GridPoint2 pos = robot.getPosition();
            if (layers.assertGearNotNull(pos)) {
                TileName tileName = layers.getGearTileName(pos);
                if (tileName == TileName.COG_CLOCKWISE)
                    robot.rotate(Direction.turnLeftFrom(robot.getLogic().getDirection()));
                else if (tileName == TileName.COG_COUNTER_CLOCKWISE)
                    robot.rotate(Direction.turnRightFrom(robot.getLogic().getDirection()));
            }
        }
    }

    @Override
    public void fireLasers() {
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.08 * AssetManagerUtil.volume);
        for (Robot robot : robots) {
            robot.fireLaser();
            ArrayList<GridPoint2> coords = robot.getLaser().getCoords();
            if (!coords.isEmpty())
                events.createNewLaserEvent(robot.getPosition(), coords.get(coords.size() - 1));
        }
        // TODO: Implement the corresponding phase.
    }

    @Override
    public void updateCheckPoints() {

    }

    @Override
    public void registerFlagPositions() {
        System.out.println("\nChecking if any robots have currently arrived at their next flag position...");
        for (IFlag flag : flags) {
            int flagX = flag.getPosition().x;
            int flagY = flag.getPosition().y;
            for (Robot robot : robots) {
                int robotX = robot.getPosition().x;
                int robotY = robot.getPosition().y;
                if (flagX == robotX && flagY == robotY) {
                    int nextFlag = robot.getNextFlag();
                    if (flag.getID() == nextFlag) {
                        robot.visitNextFlag();
                        robot.getLogic().setCheckPoint(flagX, flagY);
                        System.out.println("A flag has been visited");
                    }
                }
            }
        }
    }

    @Override
    public boolean checkForWinner() {
        //assert (gameRunning);
        //assert (roundStep == RoundStep.PHASES);
        //assert (phaseStep == PhaseStep.CHECK_FOR_WINNER);
        if (DEBUG) System.out.println("\nChecking if someone won...");

        boolean someoneWon = checkAllRobotsForWinner();
        //if (someoneWon) {
        //    endGame();
        //}

        if (DEBUG) System.out.println("Found winner: " + someoneWon);
        return someoneWon;
    }

    @Override
    public void run(ILayers layers) {
        revealProgramCards();
        //playNextRegisterForAllRobots();
        moveAllConveyorBelts(layers);
        moveCogs(layers);
        fireLasers();
        updateCheckPoints();
        registerFlagPositions();
        checkForWinner();
    }

    private void moveNormalConveyorBelts(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        ArrayList<Robot> rotateRobots = new ArrayList<>();
        for (Robot robot : robots) {
            GridPoint2 pos = robot.getPosition();
            if (layers.assertConveyorSlowNotNull(pos.x, pos.y)) {
                TileName tileName = layers.getConveyorSlowTileName(pos);
                // Move in a special way so that no collision happens.
                System.out.println(robot.getName() + " is on " + tileName.toString());
                // TODO: HashMap
                if (tileName == TileName.CONVEYOR_RIGHT || tileName.toString().contains("TO_EAST") || tileName.toString().contains("JOIN_EAST"))
                    robot.tryToMove(Direction.East.getStep());
                else if (tileName == TileName.CONVEYOR_NORTH || tileName.toString().contains("TO_NORTH") || tileName.toString().contains("JOIN_NORTH"))
                    robot.tryToMove(Direction.North.getStep());
                else if (tileName == TileName.CONVEYOR_LEFT || tileName.toString().contains("TO_WEST") || tileName.toString().contains("JOIN_WEST"))
                    robot.tryToMove(Direction.West.getStep());
                else if (tileName == TileName.CONVEYOR_SOUTH || tileName.toString().contains("TO_SOUTH") || tileName.toString().contains("JOIN_SOUTH"))
                    robot.tryToMove(Direction.South.getStep());
                rotateRobots.add(robot);
            }
        }
        rotateConveyorBelts(rotateRobots, layers);
    }

    private void moveExpressConveyorBelts(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        ArrayList<Robot> rotateRobots = new ArrayList<>();
        for (Robot robot : robots) {
            GridPoint2 pos = robot.getPosition();
            if (layers.assertConveyorFastNotNull(pos.x, pos.y)) {
                TileName tileName = layers.getConveyorFastTileName(pos);
                // Move in a special way so that no collision happens.
                // TODO: HashMap
                if (tileName == TileName.CONVEYOR_EXPRESS_EAST || tileName.toString().contains("TO_EAST") || tileName.toString().contains("JOIN_EAST"))
                    robot.tryToMove(Direction.East.getStep());
                else if (tileName == TileName.CONVEYOR_EXPRESS_NORTH || tileName.toString().contains("TO_NORTH") || tileName.toString().contains("JOIN_NORTH"))
                    robot.tryToMove(Direction.North.getStep());
                else if (tileName == TileName.CONVEYOR_EXPRESS_WEST || tileName.toString().contains("TO_WEST") || tileName.toString().contains("JOIN_WEST"))
                    robot.tryToMove(Direction.West.getStep());
                else if (tileName == TileName.CONVEYOR_EXPRESS_SOUTH || tileName.toString().contains("TO_SOUTH") || tileName.toString().contains("JOIN_SOUTH"))
                    robot.tryToMove(Direction.South.getStep());
                rotateRobots.add(robot);
            }
        }
        rotateConveyorBelts(rotateRobots, layers);
    }

    private void rotateConveyorBelts(ArrayList<Robot> rotateRobots, ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        TileName tileName;
        if (rotateRobots.isEmpty())
            return;
        for (Robot robot : rotateRobots) {
            if (layers.assertConveyorSlowNotNull(robot.getPosition().x, robot.getPosition().y))
                tileName = layers.getConveyorSlowTileName(robot.getPosition());
            else if (layers.assertConveyorFastNotNull(robot.getPosition().x, robot.getPosition().y))
                tileName = layers.getConveyorFastTileName(robot.getPosition());
            else
                return;
            if (tileName.toString().contains("COUNTER_CLOCKWISE"))
                robot.rotate(Direction.turnLeftFrom(robot.getLogic().getDirection()));
            else if (tileName.toString().contains("CLOCKWISE"))
                robot.rotate(Direction.turnRightFrom(robot.getLogic().getDirection()));
        }
    }

    private boolean checkAllRobotsForWinner() {
        //assert (gameRunning);
        //assert (roundStep == RoundStep.PHASES);
        //assert (phaseStep == PhaseStep.CHECK_FOR_WINNER);
        checkAllRobotsAreCreated();

        for (Robot robot : robots) {
            if (robot.hasVisitedAllFlags()) {
                winner = robot;
            }
        }

        return (winner != null);
    }

    private boolean checkAllRobotsAreCreated() {
        boolean robotsAreCreated = true;
        if (robots == null) {
            robotsAreCreated = false;
        } else {
            for (Robot robot : robots) {
                if (robot == null) {
                    robotsAreCreated = false;
                    break;
                }
            }
        }
        if (!robotsAreCreated) {
            throw new IllegalStateException("Robots are not created");
        }
        return true;
    }

    @Override
    public Robot getWinner(){
        return this.winner;
    }

}
