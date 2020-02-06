package inf112.skeleton.app;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    RoboRally game = new RoboRally();
    @Test
    public void shouldAnswerWithTrue() {
        String name = game.getClass().getSimpleName();
        assertNull(game.getClass().getEnclosingClass());
        assertEquals(name,"RoboRally");
    }
}
