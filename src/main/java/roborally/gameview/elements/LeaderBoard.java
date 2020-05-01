package roborally.gameview.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import roborally.game.robot.Robot;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderBoard {
	public ArrayList<Group> playersUI;
	public HashMap<Group, Robot> robotList;
	public void addPlayers(ArrayList<Robot> robots) {
		playersUI = new ArrayList<>();
		robotList = new HashMap<>();
		for(Robot robot: robots) {
			addGroup(robot);
		}
	}
	public void addGroup(Robot robot) {
		Group group = new Group();
		String[] splitted = robot.getName().split(" ");
		Texture texture = new Texture(Gdx.files.internal("robots/leaderboard/" + splitted[0].toLowerCase()+"-leadingboard.png"));
		Image image = new Image(texture);
		image.setWidth(image.getWidth() - 100);
		group.addActor(image);
		robotList.put(group, robot);
		playersUI.add(group);
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

	public void getImageFromRobotsFlags(Group group) {
		//robotList.get(group).getNumberOfFlags();
	}
}
