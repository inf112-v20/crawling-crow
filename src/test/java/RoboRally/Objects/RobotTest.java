package RoboRally.Objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    IRobot robot;

    @Before
    public void setUp(){
        //TODO: Ask how to initialize a map from within a test
        TiledMap tiledMap = new TmxMapLoader().load("testMap001.tmx");
        TiledMapTileLayer robotLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Player");

        robot = new Robot(robotLayer);
    }
    @Test
    public void robotHasTexture(){
        assertNotNull(robot.getTexture());
    }

    @Test
    public void robotHasACell(){
        assertNotNull(robot.getCell());
    }

    @Test
    public void robotHasAWinCell(){
        assertNotNull(robot.getWonCell());
    }

    @Test
    public void robotHasALoseCell(){
        assertNotNull(robot.getLostCell());
    }

    @Test
    public void robotHasAPosition(){
        assertNotNull(robot.getPosition());
    }



}