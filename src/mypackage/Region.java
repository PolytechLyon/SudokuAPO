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

    public boolean containsCell(int row, int col) {
        // Vérifie chaque cellule de la région et compare les coordonnées (row, col)
        for (Cell<?> cell : cells) {
            if (cell.getX() == col && cell.getY() == row) {
                return true; // La cellule fait partie de la région
            }
        }
        return false; // La cellule n'est pas dans la région
    }
}




