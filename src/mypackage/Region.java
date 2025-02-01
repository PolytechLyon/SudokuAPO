package src.mypackage;

import java.util.ArrayList;
import java.util.List;



public class Region<E> {
    private String id;
    private List<Cell<E>> cells;


    // Constructeur
    public Region(String id) {
        this.id = id;
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell<E> cell) {
        this.cells.add(cell);
    }

    public List<Cell<E>> getCells() {
        return cells;
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", cells=" + cells +
                '}';
    }
}




