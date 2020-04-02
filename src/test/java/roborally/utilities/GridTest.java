package roborally.utilities;

import org.junit.Before;
import org.junit.Test;
import roborally.utilities.enums.TileName;

import static org.junit.Assert.*;

public class GridTest {
    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid("/maps/newmap.tmx");
    }

    @Test
    public void printTest() {
        grid.printGrid();
    }

    @Test
    public void flag1FoundInGrid() {
        TileName tileName = TileName.FLAG_1;
        assertTrue(grid.getGridLayer("Flag").containsValue(tileName));
    }

}