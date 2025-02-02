package src.mypackage;

public class BackTrackingSolver<E> extends SolverStrategy<E> {

    public BackTrackingSolver(Logger logger) {
        super(logger);
    }

    @Override
    public boolean solve(Grid<E> grid, boolean afficherLogs) {
        return solveBacktrack(grid);
    }

    /**
     * 🔥 Algorithme principal de Backtracking
     * Essaie de remplir les cellules vides avec des valeurs possibles
     */
    private boolean solveBacktrack(Grid<E> grid) {
        Cell<E> emptyCell = findEmptyCell(grid); // 🔍 Trouver une cellule vide
        if (emptyCell == null) return true; // ✅ Grille résolue si plus de cellules vides

        int y = emptyCell.getY();
        int x = emptyCell.getX();

        for (E value : emptyCell.getPossibleValues()) {  // 🔥 Essayer chaque valeur possible
            if (grid.isValid(y, x, value)) {
                emptyCell.setValue(value); // ✅ Assigner une valeur temporaire

                if (solveBacktrack(grid)) { // 🔁 Continuer récursivement
                    return true;
                }

                emptyCell.setValue(null); // ❌ Retour arrière
            }
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
}
