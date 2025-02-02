package src.mypackage;

public abstract class Rule<E> {
    protected Logger logger; // ✅ Logger accessible par toutes les règles

    /**
     * Constructeur de la classe Rule avec un logger.
     * @param logger Logger à utiliser pour suivre l'application des règles.
     */
    public Rule(Logger logger) {
        this.logger = logger;
    }

    /**
     * Applique la règle de déduction sur la grille donnée.
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return true si la règle a modifié la grille, false sinon.
     */
    public abstract boolean applyRule(Grid<E> grid);
}
