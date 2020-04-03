package roborally.utilities.enums;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class TileNameTest {
    ArrayList<Integer> tileNameIDs;

    @Test
    public void verifyThatNoDuplicateTileNameIDExists() {
        tileNameIDs = new ArrayList<>();

        for (TileName tileName : TileName.values()) {
            int id = tileName.getTileID();
            assertFalse(tileName + " with ID: " + id + ". This ID is duplicated.", tileNameIDs.contains(id));
            tileNameIDs.add(id);
        }

    }
}