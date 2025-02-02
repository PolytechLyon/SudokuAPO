package src.mypackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GenerateFromComputer extends Generator {

    public GenerateFromComputer(Difficulte difficulte) {
        super(difficulte);
    }

    @Override
    public Grid GeneratePuzzle(Grid grid, Difficulte difficulte) {
        // Vérifier si la grille est vide avant de la remplir
        if (isGridEmpty(grid)) {
            generateSolvedGrid(grid);
        }

        // Supprimer des chiffres pour rendre la grille jouable
        makeGridPlayable(grid, difficulte);

        return grid;
    }

    private boolean isGridEmpty(Grid grid) {
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                if (grid.getValue(y, x) != null) {
                    return false; // La grille contient déjà des valeurs
                }
            }
        }
        return true; // La grille est complètement vide
    }

    private void generateSolvedGrid(Grid grid) {
        BackTrackingSolver backtrackingSolver = new BackTrackingSolver(new Logger());
        backtrackingSolver.solve(grid, false);
    }

    private void makeGridPlayable(Grid grid, Difficulte difficulte) {
        int numberOfCellsToRemove = calculateCellsToRemove(difficulte);
        List<Cell> cellsToRemove = getShuffledCells(grid);

        int removedCells = 0;
        for (Cell cell : cellsToRemove) {
            if (grid.getValue(cell.getY(), cell.getX()) != null) {
                Object removedValue = grid.getValue(cell.getY(), cell.getX());
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

    private boolean hasUniqueSolution(Grid grid) {
        BackTrackingSolver solver = new BackTrackingSolver(new Logger());
        return solver.solve(grid, true);
    }

    private List<Cell> getShuffledCells(Grid grid) {
        List<Cell> cells = new ArrayList<>();
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                cells.add(grid.getCell(y, x));
            }
        }
        Collections.shuffle(cells);
        return cells;
    }

    private int calculateCellsToRemove(Difficulte difficulte) {
        return switch (difficulte) {
            case FACILE -> 20;
            case MOYEN -> 40;
            case DIFFICILE -> 60;
        };
    }
}
