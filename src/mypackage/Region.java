package src.mypackage;

import java.util.ArrayList;
import java.util.List;

public class Region {
    private String id;
    private List<Cell<?>> cells; // Liste de cellules, peut être de n'importe quel type

    // Constructeur
    public Region(String id) {
        this.id = id;
        this.cells = new ArrayList<>();
    }

    // Ajouter une cellule à la région
    public void addCell(Cell<?> cell) {
        this.cells.add(cell);
    }

    // Obtenir toutes les cellules de la région
    public List<Cell<?>> getCells() {
        return cells;
    }

    // Getter pour l'identifiant de la région
    public String getId() {
        return id;
    }

    // Vérifier si une cellule avec des coordonnées spécifiques est dans la région
    public boolean containsCell(int y, int x) {
        // Vérifie chaque cellule de la région et compare les coordonnées (y, x)
        for (Cell<?> cell : cells) {
            if (cell.getY() == y && cell.getX() == x) {
                return true; // La cellule fait partie de la région
            }
        }
        return false; // La cellule n'est pas dans la région
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", cells=" + cells +
                '}';
    }
}
