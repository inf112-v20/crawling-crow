package roborally.gameview.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import roborally.game.robot.IRobot;
import roborally.game.robot.Robot;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Leaderboard {
	public ArrayList<Group> leaderboardGroup;
	public HashMap<Group, IRobot> robotList;
	private HashMap<Group, TextureRegion[][]> groupList;
	private Skin skin;

	public void addPlayers(ArrayList<Robot> robots) {
		this.leaderboardGroup = new ArrayList<>();
		this.robotList = new HashMap<>();
		this.groupList = new HashMap<>();
		this.skin = new Skin(Gdx.files.internal("data/skin.json"));
		for (IRobot robot: robots) {
			addGroup(robot);
		}
	}

	public void addGroup(IRobot robot) {
		Group group = new Group();
		TextureRegion[][] tr = TextureRegion.split(Objects.requireNonNull(
				AssetManagerUtil.getLeaderBoardTexture(robot.getName())), 96, 70);
		Image image = new Image(tr[0][0]);
		groupList.put(group, tr);
		group.addActor(image);
		robotList.put(group, robot);
		leaderboardGroup.add(group);
		Label robotName = new Label(robot.getName(), skin);
		robotName.setFontScale(1.25f);
		robotName.setY(image.getHeight());
		robotName.setX(image.getWidth() / 6 - 10);
		group.addActor(robotName);
	}

	public void arrange() {
		float leaderboardHeight = SettingsUtil.WINDOW_HEIGHT / 11f;
		for (Group group : leaderboardGroup) {
			group.setY(leaderboardHeight += (group.getChildren().get(0).getHeight() + 20));
		}
	}

	public ArrayList<Group> get() {
		return leaderboardGroup;
	}

	public void update() {
		for (Group group : robotList.keySet()) {
			int nFlags = getNumberOfFlagsFromRobotInGroup(group);
			determineNextImage(group, nFlags);
		}
	}

	private int getNumberOfFlagsFromRobotInGroup(Group group) {
		return robotList.get(group).getLogic().getNumberOfVisitedFlags();
	}

	private void determineNextImage(Group group, int nFlags) {
		Image image = (Image) group.getChildren().get(0);
		Label nameLabel = (Label) group.getChildren().get(1);
		String name = robotList.get(group).getName();
		nameLabel.setText(name);
		if (image.getX() != (nFlags * 100)) {
			group.removeActor(image);
			group.removeActor(nameLabel);
			image = new Image(groupList.get(group)[0][nFlags]);
			image.setX(nFlags * image.getWidth());
			nameLabel.setX(image.getX());
			group.addActorAt(0, image);
			group.addActorAt(1, nameLabel);
		}
	}
}