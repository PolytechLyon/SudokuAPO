import java.util.*;

class SudokuSolver<T> {
    public boolean solve(SudokuGrid<T> grid, List<T> possibleValues) {
        for (int row = 0; row < grid.size; row++) {
            for (int col = 0; col < grid.size; col++) {
                if (grid.getValue(row, col) == null) { // Case vide
                    for (T value : possibleValues) {
                        if (grid.isValid(row, col, value)) {
                            grid.setValue(row, col, value);
                            if (solve(grid, possibleValues)) {
                                return true;
                            }
                            grid.setValue(row, col, null); // Annule le choix
                        }
                    }
                    return false; // Aucun chiffre valide, retour arrière
                }
            }
        }
        return true; // Sudoku résolu
    }
}
