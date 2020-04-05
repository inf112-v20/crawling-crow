package roborally.utilities.enums;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LayerNameTest {
    ArrayList<String> layerNameStrings;

    @Test
    public void verifyThatNoDuplicateTileNameIDExists() {
        layerNameStrings = new ArrayList<>();

        for (LayerName layerName : LayerName.values()) {
            String layerNameString = layerName.getLayerString();
            assertFalse(layerName + " with String: " + layerNameString + ". This String is duplicated.",
                    layerNameStrings.contains(layerNameString));
            layerNameStrings.add(layerNameString);
        }

    }
}