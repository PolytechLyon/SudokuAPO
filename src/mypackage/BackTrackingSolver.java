package src.mypackage;

import src.mypackage.Logger;

/**
 * Stratégie de résolution de Sudoku utilisant l'algorithme de Backtracking.
 * Cette classe implémente la méthode de résolution de Sudoku avec le backtracking,
 * qui essaie de remplir les cellules vides avec des valeurs possibles tout en
 * vérifiant la validité des valeurs et en revenant en arrière lorsqu'une erreur est détectée.
 *
 * @param <E> Le type des valeurs contenues dans les cellules de la grille.
 */
public class BackTrackingSolver<E> extends SolverStrategy<E> {

    /**
     * Constructeur de la classe BackTrackingSolver.
     * Initialise la stratégie avec un logger pour enregistrer les étapes de la résolution.
     *
     * @param logger Le Logger à utiliser pour suivre les étapes de la résolution.
     */
    public BackTrackingSolver(Logger logger) {
        super(logger);
    }

    /**
     * Résout la grille de Sudoku en utilisant l'algorithme de Backtracking.
     * Affiche les logs de résolution si le paramètre afficherLogs est activé.
     *
     * @param grid La grille de Sudoku à résoudre.
     * @param afficherLogs Si true, les étapes du backtracking sont loguées.
     * @return true si le Sudoku a été résolu, false sinon.
     */
    @Override
    public boolean solve(Grid<E> grid, boolean afficherLogs) {
        if (afficherLogs) {
            logger.log("Début de la résolution avec Backtracking...");
        }

        if (!hasUniqueSolution(grid)) {
            if (afficherLogs) {
                logger.log("La grille n'a pas une solution unique !");
                logger.exportLogs();
            }
        }

        boolean solved = solveBacktrack(grid, afficherLogs);

        if (afficherLogs) {
            if (solved) {
                logger.log("✅ Sudoku résolu avec succès !");
            } else {
                logger.log("❌ Aucune solution possible !");
            }
            logger.exportLogs();
        }

        return solved;
    }

    /**
     * Algorithme principal de Backtracking pour résoudre la grille de Sudoku.
     * Il tente de remplir les cellules vides avec des valeurs possibles et
     * effectue un retour en arrière si une solution ne fonctionne pas.
     *
     * @param grid La grille de Sudoku à résoudre.
     * @param afficherLogs Si true, les étapes du backtracking sont loguées.
     * @return true si la grille a été résolue, false sinon.
     */
    public boolean solveBacktrack(Grid<E> grid, boolean afficherLogs) {
        Cell<E> emptyCell = findEmptyCell(grid); // Trouve une cellule vide
        if (emptyCell == null) return true; // Sudoku résolu si plus de cellules vides

        int y = emptyCell.getY();
        int x = emptyCell.getX();

        if (afficherLogs) {
            logger.log("Cellule vide trouvée en (" + y + ", " + x + ")");
            logger.log("Valeurs possibles : " + emptyCell.getPossibleValues());
        }

        // Essaye chaque valeur possible pour la cellule vide
        for (E value : emptyCell.getPossibleValues()) {
            if (grid.isValid(y, x, value)) {
                emptyCell.setValue(value); // Assigner une valeur temporaire

                if (afficherLogs) {
                    logger.log(" " + value + " placé en (" + y + ", " + x + ")");
                }

                if (solveBacktrack(grid, afficherLogs)) { // Continue récursivement
                    return true;
                }

                emptyCell.setValue(null); // Retour arrière
                if (afficherLogs) {
                    logger.log("Annulation : " + value + " retiré de (" + y + ", " + x + ")");
                }
            }
        }

        if (afficherLogs) {
            logger.exportLogs();
        }

        return false; // Aucun choix valide → retour arrière
    }

    /**
     * Trouve une cellule vide dans la grille.
     * Cette méthode parcourt toutes les cellules pour trouver la première cellule vide.
     *
     * @param grid La grille dans laquelle chercher une cellule vide.
     * @return La première cellule vide trouvée, ou null si la grille est entièrement remplie.
     */
    public Cell<E> findEmptyCell(Grid<E> grid) {
        for (Region<E> region : grid.getRegions()) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    return cell; // Retourne la première cellule vide trouvée
                }
            }
        }
        return null; // Plus de cellules vides → Sudoku résolu
    }

    /**
     * Vérifie si la grille a une solution unique.
     * Cette méthode utilise un backtracking modifié pour tester s'il existe exactement une solution pour la grille.
     *
     * @param grid La grille de Sudoku à tester.
     * @return true si la grille a une seule solution, false sinon.
     */
    public boolean hasUniqueSolution(Grid<E> grid) {
        return countSolutions(grid, 0, 2) == 1;  // Vérifie s'il y a exactement une solution
    }

    /**
     * Compte le nombre de solutions d'une grille de Sudoku avec un Backtracking modifié.
     * Cette méthode effectue un backtracking et compte les solutions possibles.
     *
     * @param grid La grille à tester.
     * @param solutionCount Nombre de solutions trouvées jusqu'à présent.
     * @param maxSolutions Nombre maximal de solutions à tester (optimisation).
     * @return Le nombre de solutions trouvées.
     */
    public int countSolutions(Grid<E> grid, int solutionCount, int maxSolutions) {
        Cell<E> emptyCell = findEmptyCell(grid);
        if (emptyCell == null) return solutionCount + 1; // Une solution trouvée

        int y = emptyCell.getY();
        int x = emptyCell.getX();

        for (E value : emptyCell.getPossibleValues()) {
            if (grid.isValid(y, x, value)) {
                emptyCell.setValue(value);
                solutionCount = countSolutions(grid, solutionCount, maxSolutions); // Continue récursivement
                emptyCell.setValue(null); // Retour arrière

                if (solutionCount >= maxSolutions) return solutionCount; // Stoppe si plusieurs solutions trouvées
            }
        }

        return solutionCount;
    }
}
