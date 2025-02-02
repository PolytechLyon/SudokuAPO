package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Cell<E> {
    private E value;
    private int y;
    private int x;

    public Cell(E value, int y, int x) {
        this.value = value;
        this.y = y;
        this.x = x;
    }

    // Getters et setters
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
