package src.mypackage;

import java.util.ArrayList;
import java.util.List;


public class Cell<E> {
    private E value; // Valeur de la cellule (peut être un entier, un caractère, etc.)
    private int x; // Position en x (colonne)
    private int y; // Position en y (ligne)
    private List<Grid> linkedGrids; // Liste des grilles auxquelles cette cellule est liée

    // Constructeur
    public Cell(E value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.linkedGrids = new ArrayList<>();
    }

    // Getters et setters
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public int getX() {
        return x; // Colonne
    }

    public int getY() {
        return y; // Ligne
    }

    public List<Grid> getLinkedGrids() {
        return linkedGrids;
    }

    public void addLinkedGrid(Grid grid) {
        this.linkedGrids.add(grid);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", x=" + x + // Colonne
                ", y=" + y + // Ligne
                '}';
    }
}