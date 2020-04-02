package roborally.utilities;

import org.junit.Test;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import static org.junit.Assert.assertTrue;

public class GridTest {
    private Grid grid = new Grid("/maps/newMap.tmx");

    /*@Test
    public void printTest() {
        grid.printGrid();
    }*/

    @Test
    public void flag1FoundInGrid() {
        TileName tileName = TileName.FLAG_1;
        assertTrue(grid.getGridLayer(LayerName.FLAG).containsValue(tileName));
    }

}