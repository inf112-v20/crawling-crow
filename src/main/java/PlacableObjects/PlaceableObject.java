package PlacableObjects;

public class PlaceableObject {
    private int x,y;

    public PlaceableObject() {
        this.x = this.y = 0;
    }

    public int getPosX() { return this.x; }

    public int getPosY() { return this.y; }

    public void setPosX(int x) { this.x = x; }

    public void setPosY(int y) { this.y = y; }

}
