package PlacableObjects;

public class PlaceableObject {
    private boolean fellOff;
    private int x,y;

    public PlaceableObject() {
        x = y = 0;
        fellOff = false;
    }

    public int getPosX() { return this.x; }

    public int getPosY() { return this.y; }

    public void setPosX() { this.x = x; }

    public void setPosY() { this.y = y; }

}
