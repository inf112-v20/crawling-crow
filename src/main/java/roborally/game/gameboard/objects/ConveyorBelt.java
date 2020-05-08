package roborally.game.gameboard.objects;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.robot.Robot;
import roborally.gameview.layout.ILayers;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.*;
import java.util.stream.Collectors;

public class ConveyorBelt {
	private ArrayList<Robot> robots;

	public void activateConveyorBelt(ILayers layers, LayerName layerName, ArrayList<Robot> robots) {
		this.robots = robots;
		initializeConveyorBelt(layers, layerName);
	}

	private void initializeConveyorBelt(ILayers layers, LayerName layerName) {
		//TODO: Rather send in a list of relevant coordinates to separate UI from backend
		ArrayList<Robot> rotateRobots = new ArrayList<>();
		List<List<Robot>> robotsOnBelts = Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		for (Robot robot : robots) {
			GridPoint2 pos = robot.getPosition();
			if (layers.layerNotNull(layerName, pos)) {
				TileName tileName = layers.getTileName(layerName, pos);
				if (SettingsUtil.DEBUG_MODE) System.out.println(robot.getName() + " is on " + tileName.toString());
				if (tileName.toString().contains("TO_EAST") || tileName.toString().contains("JOIN_EAST"))
					robotsOnBelts.get(0).add(robot);
				else if (tileName.toString().contains("TO_NORTH") || tileName.toString().contains("JOIN_NORTH"))
					robotsOnBelts.get(1).add(robot);
				else if (tileName.toString().contains("TO_WEST") || tileName.toString().contains("JOIN_WEST"))
					robotsOnBelts.get(2).add(robot);
				else if (tileName.toString().contains("TO_SOUTH") || tileName.toString().contains("JOIN_SOUTH"))
					robotsOnBelts.get(3).add(robot);
				rotateRobots.add(robot);
			}
		}
		moveConveyorBelt(robotsOnBelts, layers);
		rotateConveyorBelts(rotateRobots, layers);
	}

	private void moveConveyorBelt(List<List<Robot>> listOfRobotsOnBelts, ILayers layers) {
		listOfRobotsOnBelts.get(0).sort(Comparator.comparing(Robot::getPositionX, Comparator.reverseOrder()));
		listOfRobotsOnBelts.get(1).sort(Comparator.comparing(Robot::getPositionY, Comparator.reverseOrder()));
		listOfRobotsOnBelts.get(2).sort(Comparator.comparing(Robot::getPositionX));
		listOfRobotsOnBelts.get(3).sort(Comparator.comparing(Robot::getPositionY));
		Queue<GridPoint2> validPositions = new LinkedList<>();
		List<Direction> dir = Arrays.asList(Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH);
		int index = 0;
		for (List<Robot> listOfRobotsOnOneBelt : listOfRobotsOnBelts) {
			for (Robot currentRobot : listOfRobotsOnOneBelt) {
				validPositions.add(currentRobot.getPosition().cpy().add(dir.get(index).getStep()));
			}
			index++;
		}
		Map<Robot, GridPoint2> remainingRobots = new HashMap<>();
		GridPoint2 validPos;
		GridPoint2 step;
		for (int beltIndex = 0; beltIndex < listOfRobotsOnBelts.size(); beltIndex++) {
			step = dir.get(beltIndex).getStep();
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
}
