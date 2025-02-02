package src.mypackage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Cell<E> {
    private E value;
    private int y;
    private int x;
    private Set<E> possibleValues;  // ✅ Ensemble des valeurs possibles


    public Cell(E value, int y, int x, Set<E> possibleValues) {
        this.value = value;
        this.y = y;
        this.x = x;
        this.possibleValues = new HashSet<>(possibleValues); // 🔥 Cloner pour éviter les références partagées
    }

    // ✅ Récupérer la liste des valeurs possibles
    public Set<E> getPossibleValues() {
        return possibleValues;
    }

    // ✅ Réduire les valeurs possibles
    public void removePossibleValue(E value) {
        possibleValues.remove(value);
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
