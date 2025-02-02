package src.mypackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Génère une grille de Sudoku à partir d'un générateur basé sur un ordinateur.
 * Le processus inclut la génération d'une grille remplie et valide,
 * puis la suppression de chiffres pour rendre la grille jouable en fonction de la difficulté choisie.
 *
 * @param <E> Le type des valeurs dans la grille.
 */
public class GenerateFromComputer<E> extends Generator<E> {
    Difficulte difficulte;
    public GenerateFromComputer(Difficulte difficulte) {
        super(difficulte);
    }

    /**
     * Génère un puzzle en remplissant une grille vide et en supprimant des valeurs
     * pour obtenir un puzzle jouable avec une solution unique.
     *
     * @param grid La grille de Sudoku.
     * @param difficulte Le niveau de difficulté.
     * @return La grille générée.
     */
    public Grid<E> GeneratePuzzle(Grid<E> grid, Difficulte difficulte) {
        // Vérifie si la grille est vide, et si oui, la remplit.
        if (isGridEmpty(grid)) {
            generateSolvedGrid(grid);
        }

        // Supprime des chiffres pour rendre la grille jouable.
        makeGridPlayable(grid, difficulte);

        return grid;
    }

    /**
     * Vérifie si la grille est vide (aucune valeur assignée).
     *
     * @param grid La grille de Sudoku.
     * @return true si la grille est vide, sinon false.
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
     * Génère une grille complètement remplie et valide en utilisant l'algorithme Backtracking.
     *
     * @param grid La grille de Sudoku.
     */
    public void generateSolvedGrid(Grid<E> grid) {
        BackTrackingSolver<E> backtrackingSolver = new BackTrackingSolver<>(new Logger());
        backtrackingSolver.solve(grid, false);
    }

    /**
     * Supprime des cellules pour rendre la grille jouable tout en garantissant qu'il existe
     * une solution unique.
     *
     * @param grid La grille de Sudoku.
     * @param difficulte Le niveau de difficulté.
     */
    public void makeGridPlayable(Grid<E> grid, Difficulte difficulte) {
        int numberOfCellsToRemove = calculateCellsToRemove(difficulte);
        List<Cell<E>> cellsToRemove = getShuffledCells(grid);

        int removedCells = 0;
        for (Cell<E> cell : cellsToRemove) {
            if (grid.getValue(cell.getY(), cell.getX()) != null) {
                E removedValue = grid.getValue(cell.getY(), cell.getX());
                grid.setValue(cell.getY(), cell.getX(), null);

                // Vérifie si la grille a une solution unique après avoir supprimé la valeur.
                if (!hasUniqueSolution(grid)) {
                    grid.setValue(cell.getY(), cell.getX(), removedValue);
                } else {
                    removedCells++;
                }

                // Arrête une fois que le nombre requis de cellules est supprimé.
                if (removedCells >= numberOfCellsToRemove) {
                    break;
                }
            }
        }
    }

    /**
     * Vérifie si la grille possède une solution unique après avoir supprimé certaines valeurs.
     *
     * @param grid La grille de Sudoku.
     * @return true si la grille a une solution unique, sinon false.
     */
    public boolean hasUniqueSolution(Grid<E> grid) {
        BackTrackingSolver<E> solver = new BackTrackingSolver<>(new Logger());
        return solver.countSolutions(grid, 0, 2) == 1;
    }

    /**
     * Récupère et mélange les cellules de la grille pour varier les suppressions.
     *
     * @param grid La grille de Sudoku.
     * @return La liste des cellules mélangées.
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
     * Calcule le nombre de cellules à supprimer en fonction de la difficulté.
     *
     * @param difficulte Le niveau de difficulté.
     * @return Le nombre de cellules à supprimer.
     */
    public int calculateCellsToRemove(Difficulte difficulte) {
        return switch (difficulte) {
            case FACILE -> 20;
            case MOYEN -> 40;
            case DIFFICILE -> 60;
        };
    }
}
