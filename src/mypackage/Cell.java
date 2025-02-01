package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Cell<E> {
    private E value; // Valeur de la cellule (peut être un entier, un caractère, etc.)
    private int y; // Position en y (ligne)
    private int x; // Position en x (colonne)
    private List<Grid> linkedGrids; // Liste des grilles auxquelles cette cellule est liée
    private Set<E> possibleValues; // Ensemble des valeurs possibles pour cette cellule


    // Constructeur
    public Cell(E value, int y, int x) {
        this.value = value;
        this.y = y;
        this.x = x;
        this.linkedGrids = new ArrayList<>();
    }

    // Getters et setters
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public int getY() {
        return y; // Ligne
    }

    public int getX() {
        return x; // Colonne
    }

    public List<Grid> getLinkedGrids() {
        return linkedGrids;
    }

    public void addLinkedGrid(Grid grid) {
        this.linkedGrids.add(grid);
    }

    public Set<E> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(Set<E> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public void removePossibleValue(E value) {
        this.possibleValues.remove(value);
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