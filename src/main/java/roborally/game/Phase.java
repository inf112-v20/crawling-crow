package roborally.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.BoardObject;
import roborally.game.gameboard.objects.IFlag;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.*;
import java.util.stream.Collectors;

public class Phase implements IPhase {

    private final boolean DEBUG = true;

    private ArrayList<Robot> robots;
    private Events events;
    private Robot winner;
    private ArrayList<IFlag> flags;
    private ArrayList<BoardObject> repairSites;
    private Queue<Robot> robotQueue;


    public Phase(ArrayList<Robot> robots, ArrayList<IFlag> flags, ArrayList<BoardObject> repairSites, Events events) {
        this.robots = robots;
        this.events = events;
        this.flags = flags;
        this.repairSites = repairSites;
        this.robotQueue = new LinkedList<>();
    }

    @Override
    public void run(ILayers layers) {
        //revealProgramCards();
        //playNextRegisterCard();
        moveAllConveyorBelts(layers);
        moveCogs(layers);
        fireLasers();
        checkForLasers();
        registerRepairSitePositionsAndUpdateArchiveMarker();
        registerFlagPositionsAndUpdateArchiveMarker();
        checkForWinner();
        System.out.println("\t- COMPLETED ONE PHASE");
    }

    @Override
    public void revealProgramCards() {
        //123
    }

    @Override
    public void playNextRegisterCard() {
        if (robotQueue.isEmpty()) {
            this.robots.sort(Comparator.comparing(Robot::peekNextCardInHand, Comparator.reverseOrder()));
            robotQueue.addAll(robots);
        }
        Objects.requireNonNull(robotQueue.poll()).playNextCard();
        events.checkForDestroyedRobots(this.robots);
    }

    @Override
    public void moveAllConveyorBelts(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        initializeExpressConveyorBelts(layers);
        initializeExpressConveyorBelts(layers);
        initializeNormalConveyorBelts(layers);
    }

    @Override
    public void moveCogs(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        for (Robot robot : robots) {
            GridPoint2 pos = robot.getPosition();
            if (layers.layerNotNull(LayerName.COG, pos)) {
                TileName tileName = layers.getTileName(LayerName.COG, pos);
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
    public void registerRepairSitePositionsAndUpdateArchiveMarker() {
        System.out.println("\nChecking if any robots have arrived at a repair site...");
        for (BoardObject repairSite : repairSites) {
            for (Robot robot : robots) {
                if (robot.getPosition().equals(repairSite.getPosition())) {
                    robot.getLogic().setArchiveMarker(repairSite.getPosition());
                    System.out.println("- Type of repair site: " + repairSite.getType().name());

                    robot.getLogic().addHealth(1); // FIXME: should only happen in Round not phase

                    // TODO: Add this
                    /*if (repairSite.getType() == TileName.WRENCH_HAMMER) {
                        // Add 1 health and option card
                    } else {
                        // Add 1 health
                    }*/
                }
            }
        }
    }

    @Override
    public void registerFlagPositionsAndUpdateArchiveMarker() {
        System.out.println("\nChecking if any robots have arrived at their next flag position...");
        for (IFlag flag : flags) {
            for (Robot robot : robots) {
                if (robot.getLogic().getPosition().equals(flag.getPosition())) {
                    int nextFlag = robot.getNextFlag();
                    if (flag.getID() == nextFlag) {
                        robot.visitNextFlag();
                        robot.getLogic().setArchiveMarker(flag.getPosition());
                        System.out.println("- " + robot.getName() + " has visited flag no. " + flag.getID());
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
        if (DEBUG) System.out.println("- Did someone win? " + someoneWon);

        if (someoneWon) {
        //    endGame();
            if (DEBUG) System.out.println("- Found winner: " + winner.getName());
        }

        return someoneWon;
    }

    private void checkForLasers() {
        for (Robot robot : robots)
            if (robot.checkForStationaryLaser()) {
                robot.takeDamage(1);
                System.out.println("- Hit by stationary laser");
            }
    }

    private void initializeNormalConveyorBelts(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        ArrayList<Robot> rotateRobots = new ArrayList<>();
        List<List<Robot>> robotsOnBelts = Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        for (Robot robot : robots) {
            GridPoint2 pos = robot.getPosition();
            if (layers.layerNotNull(LayerName.CONVEYOR, pos)) {
                TileName tileName = layers.getTileName(LayerName.CONVEYOR, pos);
                // Move in a special way so that no collision happens.
                System.out.println(robot.getName() + " is on " + tileName.toString());
                // TODO: HashMap??
                if (tileName == TileName.CONVEYOR_RIGHT || tileName.toString().contains("TO_EAST") || tileName.toString().contains("JOIN_EAST"))
                    robotsOnBelts.get(0).add(robot);
                else if (tileName == TileName.CONVEYOR_NORTH || tileName.toString().contains("TO_NORTH") || tileName.toString().contains("JOIN_NORTH"))
                    robotsOnBelts.get(1).add(robot);
                else if (tileName == TileName.CONVEYOR_LEFT || tileName.toString().contains("TO_WEST") || tileName.toString().contains("JOIN_WEST"))
                    robotsOnBelts.get(2).add(robot);
                else if (tileName == TileName.CONVEYOR_SOUTH || tileName.toString().contains("TO_SOUTH") || tileName.toString().contains("JOIN_SOUTH"))
                    robotsOnBelts.get(3).add(robot);
                rotateRobots.add(robot);
            }
        }
        moveConveyorBelt(robotsOnBelts, layers);
        rotateConveyorBelts(rotateRobots, layers);

        /* TODO: Implement abstract classes for normal and express conveyor belts
        for (Robot currentRobot : robots) {
            robotPositionsList.add(currentRobot.getPosition());
        }

        AbstractConveyorNormal.moveBelts(robotsOnBelts, layers);
        AbstractConveyorNormal.rotateBelts(robotsOnBelts, layers);*/
    }

    private void moveConveyorBelt(List<List<Robot>> listOfRobotsOnBelts, ILayers layers) {
        listOfRobotsOnBelts.get(0).sort(Comparator.comparing(Robot::getPositionX, Comparator.reverseOrder()));
        listOfRobotsOnBelts.get(1).sort(Comparator.comparing(Robot::getPositionY, Comparator.reverseOrder()));
        listOfRobotsOnBelts.get(2).sort(Comparator.comparing(Robot::getPositionX));
        listOfRobotsOnBelts.get(3).sort(Comparator.comparing(Robot::getPositionY));

        Queue<GridPoint2> validPositions = new LinkedList<>();

        List<Direction> enums = Arrays.asList(Direction.East, Direction.North, Direction.West, Direction.South);

        int index = 0;
        for (List<Robot> listOfRobotsOnOneBelt : listOfRobotsOnBelts) {
            for (Robot currentRobot : listOfRobotsOnOneBelt) {
                validPositions.add(currentRobot.getPosition().cpy().add(enums.get(index).getStep()));
            }
            index++;
        }
        Map<Robot, GridPoint2> remainingRobots = new HashMap<>();
        GridPoint2 validPos;
        GridPoint2 step;
        for (int beltIndex = 0; beltIndex < listOfRobotsOnBelts.size(); beltIndex++) {
            step = enums.get(beltIndex).getStep();
            for (Robot robot : listOfRobotsOnBelts.get(beltIndex)) {
                if (validPositions.isEmpty())
                    break;
                if (!validPositions.contains((validPos = validPositions.poll()))
                        && !layers.layerNotNull(LayerName.ROBOT, robot.getPosition().cpy().add(step)))
                    robot.tryToMove(step);
                else if (layers.layerNotNull(LayerName.ROBOT, robot.getPosition().cpy().add(step)))
                    remainingRobots.put(robot, step);
                else {
                    GridPoint2 finalPos = validPos;
                    List<GridPoint2> list = validPositions.stream()
                            .filter(o -> o.equals(finalPos))
                            .collect(Collectors.toList());
                    validPositions.removeAll(list);
                }
            }
        }
        for (Robot robot : remainingRobots.keySet())
            if (!layers.layerNotNull(LayerName.ROBOT, robot.getPosition().cpy().add(remainingRobots.get(robot))))
                robot.tryToMove(remainingRobots.get(robot));
    }

    private void initializeExpressConveyorBelts(ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        List<List<Robot>> belts = Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ArrayList<Robot> rotateRobots = new ArrayList<>();
        for (Robot robot : robots) {
            GridPoint2 pos = robot.getPosition();
            if (layers.layerNotNull(LayerName.CONVEYOR_EXPRESS, pos)) {
                TileName tileName = layers.getTileName(LayerName.CONVEYOR_EXPRESS, pos);
                // Move in a special way so that no collision happens.
                // TODO: HashMap
                if (tileName == TileName.CONVEYOR_EXPRESS_EAST || tileName.toString().contains("TO_EAST") || tileName.toString().contains("JOIN_EAST"))
                    belts.get(0).add(robot);
                else if (tileName == TileName.CONVEYOR_EXPRESS_NORTH || tileName.toString().contains("TO_NORTH") || tileName.toString().contains("JOIN_NORTH"))
                    belts.get(1).add(robot);
                else if (tileName == TileName.CONVEYOR_EXPRESS_WEST || tileName.toString().contains("TO_WEST") || tileName.toString().contains("JOIN_WEST"))
                    belts.get(2).add(robot);
                else if (tileName == TileName.CONVEYOR_EXPRESS_SOUTH || tileName.toString().contains("TO_SOUTH") || tileName.toString().contains("JOIN_SOUTH"))
                    belts.get(3).add(robot);
                rotateRobots.add(robot);
            }
        }
        moveConveyorBelt(belts, layers);
        rotateConveyorBelts(rotateRobots, layers);
    }

    private void rotateConveyorBelts(ArrayList<Robot> rotateRobots, ILayers layers) {
        //TODO: Rather send in a list of relevant coordinates to separate UI from backend
        TileName tileName;
        if (rotateRobots.isEmpty())
            return;
        for (Robot robot : rotateRobots) {
            if (layers.layerNotNull(LayerName.CONVEYOR, robot.getPosition()))
                tileName = layers.getTileName(LayerName.CONVEYOR, robot.getPosition());
            else if (layers.layerNotNull(LayerName.CONVEYOR_EXPRESS, robot.getPosition()))
                tileName = layers.getTileName(LayerName.CONVEYOR_EXPRESS, robot.getPosition());
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
    public Robot getWinner() {
        return this.winner;
    }

}
