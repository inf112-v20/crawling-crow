package Position;

import java.util.Objects;

public class Position implements IPosition {
    private int x, y;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    // TODO: Refactor to be more generalized
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }

        if(o == null){
            return false;
        }

        if(getClass() != o.getClass()){
            return false;
        }

        IPosition pos = (Position) o;

        return Objects.equals(getX(), pos.getX()) && Objects.equals(getY(), pos.getY());


    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }
}
