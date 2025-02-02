package src.mypackage;

import java.util.ArrayList;
import java.util.List;


public class Region<E> {
    private int id;
    private List<Cell<E>> cells; // Liste de cellules génériques

    // Constructeur
    public Region(int id) {
        this.id = id;
        this.cells = new ArrayList<>();
    }

    // Ajouter une cellule à la région
    public void addCell(Cell<E> cell) {
        this.cells.add(cell);
    }

    // Obtenir toutes les cellules de la région
    public List<Cell<E>> getCells() {
        return cells;
    }

    // Vérifier si une cellule appartient à la région
    public boolean containsCell(int y, int x) {
        for (Cell<E> cell : cells) {
            if (cell.getY() == y && cell.getX() == x) {
                return true;
            }
        }
        return false;
    }
}
