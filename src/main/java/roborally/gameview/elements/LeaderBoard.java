package roborally.gameview.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import roborally.game.robot.Robot;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LeaderBoard {
	public ArrayList<Group> playersUI;
	public HashMap<Group, Robot> robotList;
	private HashMap<Group, TextureRegion[][]> groupList;
	private Skin skin;

	public void addPlayers(ArrayList<Robot> robots) {
		playersUI = new ArrayList<>();
		robotList = new HashMap<>();
		groupList = new HashMap<>();
		skin = new Skin(Gdx.files.internal("data/skin.json"));
		for(Robot robot: robots) {
			addGroup(robot);
		}
	}
	public void addGroup(Robot robot) {
		Group group = new Group();
		TextureRegion[][] tr = TextureRegion.split(Objects.requireNonNull(AssetManagerUtil.getLeaderBoardTexture(robot.getName())), 96, 70);
		Image image = new Image(tr[0][0]);
		groupList.put(group, tr);
		group.addActor(image);
		robotList.put(group, robot);
		playersUI.add(group);
		Label robotName = new Label(robot.getName(), skin);
		robotName.setFontScale(1.25f);
		robotName.setY(image.getHeight());
		robotName.setX(image.getWidth() / 6 - 10);
		group.addActor(robotName);
	}
	public void arrangeGroups() {
		int y = 105;
		for (Group group : playersUI) {
			group.setY(y+=85);
		}
	}
	public ArrayList<Group> getGroup() {
		return playersUI;
	}

	public void updateLeaderBoard() {
		for(Group group : robotList.keySet()) {
			int nFlags = getNumberOfFlagsFromRobotInGroup(group);
			determineNextImage(group, nFlags);
		}
	}

	private int getNumberOfFlagsFromRobotInGroup(Group group) {
		return robotList.get(group).getLogic().getNumberOfVisitedFlags();
	}

	private void determineNextImage(Group group, int nFlags) {
		Image image = (Image) group.getChildren().get(0);
		Label label = (Label) group.getChildren().get(1);
		String name = robotList.get(group).getName();
		label.setText(name);
		if(image.getX() != (nFlags * 100)) {
			group.removeActor(image);
			group.removeActor(label);
			image = new Image(groupList.get(group)[0][nFlags]);
			image.setX(nFlags * 100);
			label.setX(label.getX() + 100);
			group.addActorAt(0, image);
			group.addActorAt(1, label);
		}
	}
}