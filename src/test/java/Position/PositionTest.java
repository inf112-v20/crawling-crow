package Position;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {
    int x = 0;
    int y = 0;

    IPosition thisPos, thisPosClone;

    @Before
    public void setUp() throws Exception{
        thisPos = new Position(x,y);
        thisPosClone = new Position(x, y);
    }

    @Test
    public void getPosX() {
        assertEquals(x, thisPos.getX());
    }

    @Test
    public void getPosY() {
        assertEquals(y, thisPos.getY());
    }

    @Test
    public void positionsWithSameXandYAreEqual() {
        System.out.println(thisPos.equals(thisPosClone));
        assertEquals(thisPos, thisPosClone);

        //assertEquals(thisPos, thisPosClone);
    }


}