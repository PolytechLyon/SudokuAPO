package src.mypackage;

/**
 * Classe abstraite représentant une règle appliquée sur une grille de Sudoku.
 * Chaque règle dérivée de cette classe doit définir son propre comportement
 * d'application sur une grille de Sudoku. Elle peut être utilisée pour la déduction
 * de certaines valeurs dans la grille selon les règles du Sudoku.
 *
 * @param <E> Le type des valeurs contenues dans les cellules de la grille.
 */
public abstract class Rule<E> {
    /**
     * Logger utilisé pour suivre l'application des règles.
     * Il permet de journaliser les actions effectuées par la règle.
     */
    protected Logger logger;

    /**
     * Constructeur de la classe Rule avec un logger.
     *
     * @param logger Logger à utiliser pour suivre l'application des règles.
     */
    public Rule(Logger logger) {
        this.logger = logger;
    }

    /**
     * Applique la règle de déduction sur la grille donnée.
     * Chaque sous-classe devra implémenter cette méthode pour définir le comportement
     * spécifique de la règle.
     *
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return {@code true} si la règle a modifié la grille, {@code false} sinon.
     */
    public abstract boolean applyRule(Grid<E> grid);
}

