package src.mypackage;

public abstract class Rule<E> {

    /**
     * Applique la règle de déduction sur la grille donnée.
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return true si la règle a modifié la grille, false sinon.
     */
    public abstract boolean applyRule(Grid<E> grid);
}
