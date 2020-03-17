package roborally.game.objects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import roborally.game.objects.gameboard.Flag;
import roborally.game.objects.gameboard.IFlag;

import static org.junit.Assert.assertEquals;

public class FlagTest {
    IFlag flag;

    @Test
    public void assertThatGetPosEqualsFlagPosition() {
        GridPoint2 flagPos = new GridPoint2(2, 2);
        this.flag = new Flag(1, flagPos);
        assertEquals(flag.getPosition(), flagPos);

    }

    @Test
    public void getThatGetIdEqualsFlagId() {
        int id = 1;
        this.flag = new Flag(id, new GridPoint2(2, 2));
        assertEquals(flag.getID(), id);
    }
}