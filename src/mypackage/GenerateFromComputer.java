package src.mypackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GenerateFromComputer<E> extends Generator<E> {

    public GenerateFromComputer(Difficulte difficulte) {
        super(difficulte);
    }

    public Grid<E> GeneratePuzzle(Grid<E> grid, Difficulte difficulte) {
        // Vérifier si la grille est vide avant de la remplir
        if (isGridEmpty(grid)) {
            generateSolvedGrid(grid);
        }

        // Supprimer des chiffres pour rendre la grille jouable
        makeGridPlayable(grid, difficulte);

        return grid;
    }

    /**
     * Vérifie si la grille est vide (aucune valeur assignée).
     */
    public boolean isGridEmpty(Grid<E> grid) {
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                if (grid.getValue(y, x) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Génère une grille complètement remplie et valide en utilisant le backtracking.
     */
    public void generateSolvedGrid(Grid<E> grid) {
        BackTrackingSolver<E> backtrackingSolver = new BackTrackingSolver<>(new Logger());
        backtrackingSolver.solve(grid, false);
    }

    /**
     * Supprime des cellules tout en garantissant une solution unique.
     */
    public void makeGridPlayable(Grid<E> grid, Difficulte difficulte) {
        int numberOfCellsToRemove = calculateCellsToRemove(difficulte);
        List<Cell<E>> cellsToRemove = getShuffledCells(grid);

        int removedCells = 0;
        for (Cell<E> cell : cellsToRemove) {
            if (grid.getValue(cell.getY(), cell.getX()) != null) {
                E removedValue = grid.getValue(cell.getY(), cell.getX());
                grid.setValue(cell.getY(), cell.getX(), null);

                if (!hasUniqueSolution(grid)) {
                    grid.setValue(cell.getY(), cell.getX(), removedValue);
                } else {
                    removedCells++;
                }

                if (removedCells >= numberOfCellsToRemove) {
                    break;
                }
            }
        }
    }

    /**
     * Vérifie si la grille possède une solution unique après suppression des valeurs.
     */
    public boolean hasUniqueSolution(Grid<E> grid) {
        BackTrackingSolver<E> solver = new BackTrackingSolver<>(new Logger());
        return solver.countSolutions(grid, 0, 2) == 1;
    }

    /**
     * Récupère et mélange les cellules de la grille pour varier les suppressions.
     */
    public List<Cell<E>> getShuffledCells(Grid<E> grid) {
        List<Cell<E>> cells = new ArrayList<>();
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                cells.add(grid.getCell(y, x));
            }
        }
        Collections.shuffle(cells);
        return cells;
    }

    /**
     * Définit le nombre de cellules à supprimer en fonction de la difficulté.
     */
    public int calculateCellsToRemove(Difficulte difficulte) {
        return switch (difficulte) {
            case FACILE -> 20;
            case MOYEN -> 40;
            case DIFFICILE -> 60;
        };
    }
}
