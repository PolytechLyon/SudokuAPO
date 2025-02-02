package src.mypackage;

import java.util.List;

/**
 * Classe abstraite représentant une stratégie de résolution pour un Sudoku.
 * Cette classe définit la structure d'une stratégie de résolution de Sudoku.
 * Chaque sous-classe devra implémenter la méthode `solve` pour fournir une
 * méthode spécifique de résolution.
 *
 * @param <E> Le type des valeurs contenues dans les cellules de la grille.
 */
public abstract class SolverStrategy<E> {
    /**
     * Le logger utilisé pour enregistrer les étapes de la résolution.
     * Il permet de journaliser chaque action effectuée pendant la résolution.
     */
    protected Logger logger;

    /**
     * Constructeur de la classe SolverStrategy.
     *
     * @param logger Le Logger à utiliser pour suivre les étapes de la résolution.
     */
    public SolverStrategy(Logger logger) {
        this.logger = logger;
    }

    /**
     * Résout la grille de Sudoku en utilisant la stratégie définie dans la sous-classe.
     * Cette méthode doit être implémentée dans les sous-classes pour définir
     * l'algorithme spécifique de résolution.
     *
     * @param grid La grille de Sudoku à résoudre.
     * @param afficherLogs Détermine si les étapes de résolution doivent être affichées.
     * @return {@code true} si la résolution a été réussie, {@code false} sinon.
     */
    public abstract boolean solve(Grid<E> grid, boolean afficherLogs);

    /**
     * Retourne la liste des étapes enregistrées par le Logger.
     *
     * @return Une liste des logs (étapes) enregistrés durant la résolution.
     */
    public List<String> getSteps() {
        return logger.getLogs();
    }
}
