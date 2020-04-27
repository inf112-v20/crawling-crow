package roborally.utilities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.BeforeClass;
import org.junit.Test;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import static org.junit.Assert.*;

public class RiskyExchangeMapTest {
    private static Grid riskyExchange;

    @BeforeClass
    public static void setUp() {
        riskyExchange = new Grid("/maps/riskyExchangeBeginnerWithStartAreaVertical.tmx");
    }

    @Test
    public void testThatFlagIsPutInCorrectPosition() {
        TileName flag = TileName.FLAG_1;
        LayerName flagLayer = LayerName.FLAG;
        riskyExchange.putTileInLayer(flagLayer, flag, new GridPoint2(0,0));
        assertEquals(riskyExchange.getTileNameFromLayerPos(flagLayer, new GridPoint2(0, 0)), flag);
    }

    @Test
    public void verifyThatHolesIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.HOLE).containsValue(TileName.HOLE));
    }

    //region Flags
    @Test
    public void verifyThatFlag1IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.FLAG).containsValue(TileName.FLAG_1));
    }

    @Test
    public void verifyThatFlag2IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.FLAG).containsValue(TileName.FLAG_2));
    }

    @Test
    public void verifyThatFlag3IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.FLAG).containsValue(TileName.FLAG_3));
    }
    //endregion

    //region Conveyor
    @Test
    public void verifyThatConveyorToNorthIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_TO_NORTH));
    }

    @Test
    public void verifyThatConveyorToSouthIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_TO_SOUTH));
    }

    @Test
    public void verifyThatConveyorToWestIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_TO_WEST));
    }

    @Test
    public void verifyThatConveyorToEastIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_TO_EAST));
    }

    @Test
    public void verifyThatConveyorRotateCounterClockwiseWestToSouthIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_COUNTER_CLOCKWISE_WEST_TO_SOUTH));
    }

    @Test
    public void verifyThatConveyorRotateCounterClockwiseNorthToWestIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_COUNTER_CLOCKWISE_NORTH_TO_WEST));
    }

    @Test
    public void verifyThatConveyorRotateClockwiseNorthToEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_CLOCKWISE_NORTH_TO_EAST));
    }

    @Test
    public void verifyThatConveyorRotateClockwiseEastToSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_CLOCKWISE_EAST_TO_SOUTH));
    }

    @Test
    public void verifyThatConveyorRotateCounterClockwiseSouthToEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_COUNTER_CLOCKWISE_SOUTH_TO_EAST));
    }

    @Test
    public void verifyThatConveyorRotateCounterClockwiseEastToNorthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_COUNTER_CLOCKWISE_EAST_TO_NORTH));
    }

    @Test
    public void verifyThatConveyorRotateClockwiseWestToNorthIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_CLOCKWISE_WEST_TO_NORTH));
    }

    @Test
    public void verifyThatConveyorRotateClockwiseSouthToWestIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_ROTATE_CLOCKWISE_SOUTH_TO_WEST));
    }

    @Test
    public void verifyThatConveyorJoinNorthFromSouthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_NORTH_FROM_SOUTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorJoinEastFromNorthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_EAST_FROM_NORTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorJoinSouthFromNorthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_SOUTH_FROM_NORTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorJoinWestFromSouthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_WEST_FROM_SOUTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorJoinEastFromNorthAndSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_EAST_FROM_NORTH_AND_SOUTH));
    }

    @Test
    public void verifyThatConveyorJoinSouthFromEastAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_SOUTH_FROM_EAST_AND_WEST));
    }

    @Test
    public void verifyThatConveyorJoinNorthFromSouthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_NORTH_FROM_SOUTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorJoinEastFromSouthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_EAST_FROM_SOUTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorJoinSouthFromNorthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_SOUTH_FROM_NORTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorJoinWestFromNorthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_WEST_FROM_NORTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorJoinNorthFromEastAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_NORTH_FROM_EAST_AND_WEST));
    }

    @Test
    public void verifyThatConveyorJoinWestFromNorthAndSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR).containsValue(TileName.CONVEYOR_JOIN_WEST_FROM_NORTH_AND_SOUTH));
    }
    //endregion

    //region Conveyor express
    @Test
    public void verifyThatConveyorExpressToSouthIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_TO_SOUTH));
    }

    @Test
    public void verifyThatConveyorExpressToWestIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_TO_WEST));
    }

    @Test
    public void verifyThatConveyorExpressToEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_TO_EAST));
    }

    @Test
    public void verifyThatConveyorExpressToNorthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_TO_NORTH));
    }

    @Test
    public void verifyThatConveyorExpressRotateCounterClockwiseWestToSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_WEST_TO_SOUTH));
    }

    @Test
    public void verifyThatConveyorExpressRotateCounterClockwiseNorthToWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_NORTH_TO_WEST));
    }

    @Test
    public void verifyThatConveyorExpressRotateClockwiseNorthToEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_NORTH_TO_EAST));
    }

    @Test
    public void verifyThatConveyorExpressRotateClockwiseEastToSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_EAST_TO_SOUTH));
    }

    @Test
    public void verifyThatConveyorExpressRotateCounterClockwiseSouthToEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_SOUTH_TO_EAST));
    }

    @Test
    public void verifyThatConveyorExpressRotateCounterClockwiseEastToNorthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_EAST_TO_NORTH));
    }

    @Test
    public void verifyThatConveyorExpressRotateClockwiseWestToNorthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_WEST_TO_NORTH));
    }

    @Test
    public void verifyThatConveyorExpressRotateClockwiseSouthToWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_SOUTH_TO_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinNorthFromSouthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_NORTH_FROM_SOUTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinEastFromNorthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_EAST_FROM_NORTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinSouthFromNorthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_NORTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorExpressJoinWestFromSouthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_WEST_FROM_SOUTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorExpressJoinEastFromNorthAndSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_EAST_FROM_NORTH_AND_SOUTH));
    }

    @Test
    public void verifyThatConveyorExpressJoinSouthFromEastAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_EAST_AND_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinNorthFromSouthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_NORTH_FROM_SOUTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorExpressJoinEastFromSouthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_EAST_FROM_SOUTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinSouthFromNorthAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_NORTH_AND_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinWestFromNorthAndEastIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_WEST_FROM_NORTH_AND_EAST));
    }

    @Test
    public void verifyThatConveyorExpressJoinNorthFromEastAndWestIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_NORTH_FROM_EAST_AND_WEST));
    }

    @Test
    public void verifyThatConveyorExpressJoinWestFromNorthAndSouthIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CONVEYOR_EXPRESS).containsValue(TileName.CONVEYOR_EXPRESS_JOIN_WEST_FROM_NORTH_AND_SOUTH));
    }
    //endregion

    //region Wall
    @Test
    public void verifyThatWallTopIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_NORTH));
    }

    @Test
    public void verifyThatWallRightIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_EAST));
    }

    @Test
    public void verifyThatWallLeftIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_WEST));
    }

    @Test
    public void verifyThatWallBottomIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_SOUTH));
    }

    @Test
    public void verifyThatWallCornerTopRightIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_CORNER_NORTH_EAST));
    }

    @Test
    public void verifyThatWallCornerTopLeftIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_CORNER_NORTH_WEST));
    }

    @Test
    public void verifyThatWallCornerBottomRightIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_CORNER_SOUTH_EAST));
    }

    @Test
    public void verifyThatWallCornerBottomLeftIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WALL).containsValue(TileName.WALL_CORNER_SOUTH_WEST));
    }
    //endregion

    //region Start positions
    @Test
    public void verifyThatStartPosition1IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_1));
    }

    @Test
    public void verifyThatStartPosition2IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_2));
    }

    @Test
    public void verifyThatStartPosition3IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_3));
    }

    @Test
    public void verifyThatStartPosition4IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_4));
    }

    @Test
    public void verifyThatStartPosition5IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_5));
    }

    @Test
    public void verifyThatStartPosition6IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_6));
    }

    @Test
    public void verifyThatStartPosition7IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_7));
    }

    @Test
    public void verifyThatStartPosition8IsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.START_POSITIONS).containsValue(TileName.START_POSITION_8));
    }
    //endregion

    //region Repair sites
    @Test
    public void verifyThatWrenchIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WRENCH).containsValue(TileName.WRENCH));
    }

    @Test
    public void verifyThatWrenchHammerIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.WRENCH_HAMMER).containsValue(TileName.WRENCH_HAMMER));
    }
    //endregion

    //region Cogs
    @Test
    public void verifyThatCogClockwiseIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.COG).containsValue(TileName.COG_CLOCKWISE));
    }

    @Test
    public void verifyThatCogCounterClockwiseIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.COG).containsValue(TileName.COG_COUNTER_CLOCKWISE));
    }
    //endregion

    //region Lasers
    @Test
    public void verifyThatLaserVerticalIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.LASER).containsValue(TileName.LASER_VERTICAL));
    }

    @Test
    public void verifyThatLaserHorizontalIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.LASER).containsValue(TileName.LASER_HORIZONTAL));
    }

    @Test
    public void verifyThatLaserCrossIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.LASER).containsValue(TileName.LASER_CROSS));
    }

    @Test
    public void verifyThatLaserDoubleCrossIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.LASER).containsValue(TileName.LASER_DOUBLE_CROSS));
    }

    @Test
    public void verifyThatLaserDoubleVerticalIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.LASER).containsValue(TileName.LASER_DOUBLE_VERTICAL));
    }

    @Test
    public void verifyThatLaserDoubleHorizontalIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.LASER).containsValue(TileName.LASER_DOUBLE_HORIZONTAL));
    }
    //endregion

    //region Wall Cannons
    @Test
    public void verifyThatWallCannonBottomIsOnMap() {
        assertTrue(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_BOTTOM));
    }

    @Test
    public void verifyThatWallCannonTopIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_TOP));
    }

    @Test
    public void verifyThatWallCannonRightIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_RIGHT));
    }

    @Test
    public void verifyThatWallCannonLeftIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_LEFT));
    }

    @Test
    public void verifyThatWallCannonDoubleTopIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_DOUBLE_TOP));
    }

    @Test
    public void verifyThatWallCannonDoubleRightIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_DOUBLE_RIGHT));
    }

    @Test
    public void verifyThatWallCannonDoubleLeftIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_DOUBLE_LEFT));
    }

    @Test
    public void verifyThatWallCannonDoubleBottomIsNotOnMap() {
        assertFalse(riskyExchange.getGridLayer(LayerName.CANNON).containsValue(TileName.WALL_CANNON_DOUBLE_BOTTOM));
    }
    //endregion
}