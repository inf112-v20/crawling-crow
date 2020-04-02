package roborally.utilities;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Base64Coder;
import roborally.utilities.enums.TileName;
import roborally.utilities.tiledtranslator.TiledTranslator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Grid {
    private Map<String, Map<GridPoint2, TileName>> gridLayers;
    private TiledTranslator tT;
    private int width;
    private int height;

    public Grid(String string) {
        gridLayers = new HashMap<>();
        tT = new TiledTranslator();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(string)));
            int i = 0;
            while (!(line = br.readLine().strip()).equals("</map>"))
                if (line.contains("layer id")) {
                    if (height == 0 || width == 0)
                        setWidthHeight(line);

                    int nameStartIndex = ("name".length() + 2) + line.indexOf("name");
                    int nameEndIndex = line.indexOf("width") - 2;
                    String layerName = (line.substring(nameStartIndex, nameEndIndex));
                    br.readLine();
                    String gridLayerEncoded = br.readLine().strip();
                    makeNewGridLayer(layerName, gridLayerEncoded);
                }
            br.close();
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
    }

    private static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public Map<GridPoint2, TileName> getGridLayer(String gridLayer) {
        return gridLayers.get(gridLayer);
    }

    public void printGrid() {
        for (String key : gridLayers.keySet()) {
            System.out.println("Grid Layer: " + key);
            for (GridPoint2 tilePos : gridLayers.get(key).keySet())
                System.out.print(gridLayers.get(key).get(tilePos) + " -> " + tilePos + " ");
            System.out.println();
            System.out.println();
        }
    }

    private void makeNewGridLayer(String name, String gridLayer) throws IOException, DataFormatException {
        gridLayers.put(name, new HashMap<>());
        byte[] bytes = Base64Coder.decode(gridLayer);
        bytes = decompress(bytes);
        int x = 0;
        int y = 0;
        for (int j = 0; j < bytes.length; j += 4) {
            if (bytes[j] != 0) {
                gridLayers.get(name).put(new GridPoint2(x, height - y - 1), tT.getTileName(bytes[j] & 0xFF));
            }
            x++;
            x = x % width;
            if (x == 0) {
                y++;
            }
        }
    }

    private void setWidthHeight(String string) {
        int widthEndIdx = string.indexOf("width") + "width".length() + 1;
        String width = string.substring(widthEndIdx + 1, widthEndIdx + 3);
        this.width = Integer.parseInt(width);
        int heightEndIdx = string.indexOf("height") + "height".length() + 1;
        String height = string.substring(heightEndIdx + 1, heightEndIdx + 3);
        this.height = Integer.parseInt(height);
    }
}
