package src.mypackage;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente une cellule dans une grille de Sudoku.
 * Chaque cellule a une valeur actuelle, des coordonnées (x, y), et un ensemble de valeurs possibles qui peuvent
 * être attribuées à cette cellule.
 *
 * @param <E> Le type des valeurs contenues dans la cellule.
 */
public class Cell<E> {
    private E value; // Valeur actuelle de la cellule
    private int y; // Coordonnée y de la cellule
    private int x; // Coordonnée x de la cellule
    private Set<E> possibleValues;  // Ensemble des valeurs possibles

    /**
     * Constructeur de la cellule avec une valeur initiale, ses coordonnées et un ensemble de valeurs possibles.
     *
     * @param value La valeur initiale de la cellule.
     * @param y La coordonnée y de la cellule.
     * @param x La coordonnée x de la cellule.
     * @param possibleValues L'ensemble des valeurs possibles pour cette cellule.
     */
    public Cell(E value, int y, int x, Set<E> possibleValues) {
        this.value = value;
        this.y = y;
        this.x = x;
        this.possibleValues = new HashSet<>(possibleValues); // Cloner pour éviter les références partagées
    }

    /**
     * Retourne l'ensemble des valeurs possibles pour cette cellule.
     *
     * @return Un ensemble de valeurs possibles.
     */
    public Set<E> getPossibleValues() {
        return possibleValues;
    }

    /**
     * Supprime une valeur des valeurs possibles pour cette cellule.
     *
     * @param value La valeur à retirer de l'ensemble des valeurs possibles.
     */
    public void removePossibleValue(E value) {
        possibleValues.remove(value);
    }

    /**
     * Retourne la valeur actuelle de la cellule.
     *
     * @return La valeur actuelle de la cellule.
     */
    public E getValue() {
        return value;
    }

    /**
     * Définit une nouvelle valeur pour la cellule.
     *
     * @param value La nouvelle valeur à attribuer à la cellule.
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Retourne la coordonnée y de la cellule.
     *
     * @return La coordonnée y de la cellule.
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne la coordonnée x de la cellule.
     *
     * @return La coordonnée x de la cellule.
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la cellule.
     *
     * @return Une chaîne de caractères représentant la cellule.
     */
    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
