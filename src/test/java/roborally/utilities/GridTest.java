package roborally.utilities;

import org.junit.Before;
import org.junit.Test;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;

import static org.junit.Assert.assertTrue;

public class GridTest {
    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid("/maps/newmap.tmx");
    }

    @Test
    public void flag1FoundInGrid() {
        TileName tileName = TileName.FLAG_1;
        assertTrue(grid.getGridLayer(LayerName.FLAG).containsValue(tileName));
    }

}