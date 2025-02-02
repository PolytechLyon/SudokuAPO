package src.mypackage;

public class BackTrackingSolver<E> extends SolverStrategy<E> {

    public BackTrackingSolver(Logger logger) {
        super(logger);
    }

    @Override
    public boolean solve(Grid<E> grid, boolean afficherLogs) {
        if (afficherLogs) {
            logger.log("🚀 Début de la résolution avec Backtracking...");
        }

        if (!hasUniqueSolution(grid)) {
            if (afficherLogs) {
                logger.log("❌ La grille n'a pas une solution unique !");
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
     * 🔥 Algorithme principal de Backtracking
     * Essaie de remplir les cellules vides avec des valeurs possibles
     */
    private boolean solveBacktrack(Grid<E> grid, boolean afficherLogs) {
        Cell<E> emptyCell = findEmptyCell(grid); // 🔍 Trouver une cellule vide
        if (emptyCell == null) return true; // ✅ Sudoku résolu si plus de cellules vides

        int y = emptyCell.getY();
        int x = emptyCell.getX();

        if (afficherLogs) {
            logger.log("🔍 Cellule vide trouvée en (" + y + ", " + x + ")");
            logger.log("🎲 Valeurs possibles : " + emptyCell.getPossibleValues());
        }

        for (E value : emptyCell.getPossibleValues()) {  // 🔥 Essayer chaque valeur possible
            if (grid.isValid(y, x, value)) {
                emptyCell.setValue(value); // ✅ Assigner une valeur temporaire

                if (afficherLogs) {
                    logger.log("✅ " + value + " placé en (" + y + ", " + x + ")");
                }

                if (solveBacktrack(grid, afficherLogs)) { // 🔁 Continuer récursivement
                    return true;
                }

                emptyCell.setValue(null); // ❌ Retour arrière
                if (afficherLogs) {
                    logger.log("↩️ Annulation : " + value + " retiré de (" + y + ", " + x + ")");
                }
            }
        }

        if (afficherLogs) {
            logger.exportLogs();
        }


        return false; // ❌ Aucun choix valide → retour arrière
    }

    /**
     * 📌 Trouve une cellule vide dans la grille
     */
    private Cell<E> findEmptyCell(Grid<E> grid) {
        for (Region<E> region : grid.getRegions()) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    return cell; // 🔍 Retourne la première cellule vide trouvée
                }
            }
        }
        return null; // ✅ Plus de cellules vides → Sudoku résolu
    }

    /**
     * 🔍 Vérifie si la grille a une solution unique.
     * Retourne :
     *   - true si la grille a **une seule solution**
     *   - false si la grille **a plusieurs solutions**
     */
    public boolean hasUniqueSolution(Grid<E> grid) {
        return countSolutions(grid, 0, 2) == 1;  // ✅ Vérifie s'il y a **exactement une solution**
    }

    /**
     * 🔢 Compte le nombre de solutions d'une grille avec un Backtracking modifié.
     * @param grid La grille à tester
     * @param solutionCount Nombre de solutions trouvées
     * @param maxSolutions Nombre maximal de solutions à tester (optimisation)
     * @return Nombre de solutions trouvées
     */
    public int countSolutions(Grid<E> grid, int solutionCount, int maxSolutions) {
        Cell<E> emptyCell = findEmptyCell(grid);
        if (emptyCell == null) return solutionCount + 1; // ✅ Une solution trouvée

        int y = emptyCell.getY();
        int x = emptyCell.getX();

        for (E value : emptyCell.getPossibleValues()) {
            if (grid.isValid(y, x, value)) {
                emptyCell.setValue(value);
                solutionCount = countSolutions(grid, solutionCount, maxSolutions); // 🔁 Continue récursivement
                emptyCell.setValue(null); // ❌ Retour arrière

                if (solutionCount >= maxSolutions) return solutionCount; // 🚨 Stopper si plusieurs solutions trouvées
            }
        }

        return solutionCount;
    }

}
