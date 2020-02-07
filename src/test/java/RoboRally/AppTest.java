package RoboRally;

import RoboRally.GameBoard.GameBoard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    GameBoard game = new GameBoard();
    @Test
    public void shouldAnswerWithTrue() {
        String name = game.getClass().getSimpleName();
        assertNull(game.getClass().getEnclosingClass());
        assertEquals(name,"RoboRally");
    }
}
