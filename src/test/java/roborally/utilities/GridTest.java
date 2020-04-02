package roborally.utilities;

import org.junit.BeforeClass;
import org.junit.Test;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import static org.junit.Assert.assertTrue;

public class GridTest {
    private static Grid newMap;
    private static Grid riskyExchange;

    @BeforeClass
    public static void setUp() {
        newMap = new Grid("/maps/newmap.tmx");
        riskyExchange = new Grid("/maps/riskyExchangeBeginnerWithStartAreaVertical.tmx");
    }

    @Test
    public void verifyThatFlag1IsInGrid() {
        TileName tileName = TileName.FLAG_1;
        assertTrue(newMap.getGridLayer(LayerName.FLAG).containsValue(tileName));
    }

    @Test
    public void verifyThatFlag2IsInGrid() {
        TileName tileName = TileName.FLAG_2;
        assertTrue(newMap.getGridLayer(LayerName.FLAG).containsValue(tileName));
    }

    @Test
    public void verifyThatFlag3IsInGrid() {
        TileName tileName = TileName.FLAG_3;
        assertTrue(newMap.getGridLayer(LayerName.FLAG).containsValue(tileName));
    }
}