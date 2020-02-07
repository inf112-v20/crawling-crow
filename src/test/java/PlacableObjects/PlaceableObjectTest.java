package PlacableObjects;

import Board.GameBoard;
import Board.IGameBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlaceableObjectTest {

    private PlaceableObject placeableObject;
    private GameBoard board;


    @Test
    public void getPosXTest() {
        placeableObject = new PlaceableObject();
        int startX = 0;
        assertEquals(placeableObject.getPosX(),startX);
    }

    @Test
    public void getPosYTest() {
        placeableObject = new PlaceableObject();
        int startY = 0;
        assertEquals(placeableObject.getPosY(), startY);
    }

    @Test
    public void moveTest() {
        placeableObject = new PlaceableObject();
        placeableObject.setPosX(3);
        placeableObject.setPosY(3);
        assertEquals(placeableObject.getPosX(),3);
        assertEquals(placeableObject.getPosY(),3);
    }
}