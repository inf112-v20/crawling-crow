package roborally.game.robot.ai;

import com.badlogic.gdx.math.GridPoint2;
import org.jetbrains.annotations.NotNull;
import roborally.game.Grid;
import roborally.utilities.enums.Direction;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import java.util.ArrayList;
import java.util.Iterator;

public class Pathway {
	private final GridPoint2 flagPos;
	private GridPoint2 position;
	private GridPoint2 tempPos;
	private Direction tempDir;
	private Direction direction;
	private double newDistToFlag;
	private Grid grid;

	public Pathway(GridPoint2 position, GridPoint2 flagPos, Direction direction, Grid grid) {
		this.position = position;
		this.flagPos = flagPos;
		this.direction = direction;
		this.grid = grid;
		this.newDistToFlag = 9999;
	}

	public double distanceToFlag() {
		return position.dst(flagPos);
	}

	public void storeCurrentPosition() {
		this.tempDir = direction;
		this.tempPos = position.cpy();
	}
	public void restoreCurrentPosition() {
		this.position = tempPos.cpy();
		this.direction = tempDir;
	}

	public void updatePos(int value) {
		stepInDirection(value);
	}

	public void turnLeft() {
		this.direction = Direction.turnLeftFrom(direction);
	}

	public void turnRight() {
		this.direction = Direction.turnRightFrom(direction);
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public double nextDstToFlag(int value) {
		ArrayList<GridPoint2> positions;
		GridPoint2 posCopy = this.position.cpy();
		positions = stepInDirection(value);
		double theDistance = distanceToFlag();
		if (isWall(positions)) {
			theDistance = 9999;
		}
		this.position = posCopy;
		this.newDistToFlag = theDistance;
		return theDistance;
	}

	private ArrayList<GridPoint2> stepInDirection(int value) {
		ArrayList<GridPoint2> positions = new ArrayList<>();
		positions.add(position.cpy());
		if (value == -1) {
			position.sub(direction.getStep());
			positions.add(position.cpy());
		}
		else if(value == 0) {
			position.add(direction.getStep());
			positions.add(position.cpy());
		}
		else {
			for (int i = 0; i < value; i++) {
				position.add(direction.getStep());
				positions.add(position.cpy());
			}
		}
		return positions;
	}

	public boolean closerToFlag() {
		return distanceToFlag() > newDistToFlag;
	}

	public boolean isWall(ArrayList<GridPoint2> positions) {
		Direction tempDir = Direction.turnLeftFrom(direction);
		tempDir = Direction.turnLeftFrom(tempDir);
		if (grid.getGridLayer(LayerName.WALL).containsKey(positions.get(0))) {
			String wallName = grid.getGridLayer(LayerName.WALL).get(positions.get(0)).toString();
			if (wallName.contains(direction.toString()))
				return true;
		}
		if (grid.getGridLayer(LayerName.WALL).containsKey(positions.get(positions.size()-1))) {
			String wallName = grid.getGridLayer(LayerName.WALL).get(positions.get(positions.size()-1)).toString();
			if (wallName.contains(tempDir.toString()))
				return true;
		}
		for (int i = 1; i < positions.size() - 1; i++) {
			boolean wallAtPos = grid.getGridLayer(LayerName.WALL).containsKey(positions.get(i));
			boolean isWall = false;
			if (wallAtPos) {
				String wallName = grid.getGridLayer(LayerName.WALL).get(positions.get(i)).toString();
				isWall = wallName.contains(direction.toString()) || wallName.contains(tempDir.toString());
			}
			if (isWall)
				return true;
		}
		return false;
	}

	public void tilesAtPos() {
		GridPoint2 stepsFromTiles = new GridPoint2();
		Iterator<TileName> tiles = grid.getTilesAtPosition(position);
		if (tiles==null)
			return;
		while (tiles.hasNext()) {
			TileName tileName = tiles.next();
			if (grid.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(tileName)) {
				stepsFromTiles.add(updatePos(tileName));
				rotatingTile(tileName.toString());
				stepsFromTiles.add(updatePos(tileName));
				rotatingTile(tileName.toString());
			}
			if (grid.getGridLayer(LayerName.CONVEYOR).containsValue(tileName)) {
				stepsFromTiles.add(updatePos(tileName));
				rotatingTile(tileName.toString());
			}
			if (grid.getGridLayer(LayerName.COG).containsValue(tileName))
				rotatingTile(tileName.toString());
		}
		position.add(stepsFromTiles);
	}

	public GridPoint2 updatePos(@NotNull TileName tileName) {
		String[] splitted;
		String tileString = tileName.toString();
		if (tileString.contains("JOIN_"))
			tileString = tileString.substring(tileString.indexOf("JOIN_"));
		else if (tileString.contains("TO_"))
			tileString = tileString.substring(tileString.indexOf("TO_"));
		splitted = tileString.split("_");
		Direction dir = Direction.valueOf(splitted[1]);
		return dir.getStep();
	}

	public void rotatingTile(String tileName) {
		if (tileName.contains("COUNTER"))
			turnLeft();
		else if (tileName.contains("CLOCKWISE"))
			turnRight();
	}

}
