package src;

import src.mypackage.*;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Integer> possibleValues = new HashSet<>();

        // 📌 Ajouter les nombres de 1 à 9
        for (int i = 1; i <= 9; i++) {
            possibleValues.add(i);
        }
        // Création d'une instance de ClassicSudokuGrid
        ClassicSudokuGrid sudokuGrid = new ClassicSudokuGrid<Integer>(9, possibleValues);

        sudokuGrid.setValue(0, 0, 5);
        sudokuGrid.setValue(0, 1, 3);
        sudokuGrid.setValue(0, 4, 7);

        sudokuGrid.setValue(1, 0, 6);
        sudokuGrid.setValue(1, 3, 1);
        sudokuGrid.setValue(1, 4, 9);
        sudokuGrid.setValue(1, 5, 5);

        sudokuGrid.setValue(2, 1, 9);
        sudokuGrid.setValue(2, 2, 8);
        sudokuGrid.setValue(2, 7, 6);

        sudokuGrid.setValue(3, 0, 8);
        sudokuGrid.setValue(3, 4, 6);
        sudokuGrid.setValue(3, 8, 3);

        sudokuGrid.setValue(4, 0, 4);
        sudokuGrid.setValue(4, 3, 8);
        sudokuGrid.setValue(4, 5, 3);
        sudokuGrid.setValue(4, 8, 1);

        sudokuGrid.setValue(5, 0, 7);
        sudokuGrid.setValue(5, 4, 2);
        sudokuGrid.setValue(5, 8, 6);

        sudokuGrid.setValue(6, 1, 6);
        sudokuGrid.setValue(6, 6, 2);
        sudokuGrid.setValue(6, 7, 8);

        sudokuGrid.setValue(7, 3, 4);
        sudokuGrid.setValue(7, 4, 1);
        sudokuGrid.setValue(7, 5, 9);
        sudokuGrid.setValue(7, 8, 5);

        sudokuGrid.setValue(8, 4, 8);
        sudokuGrid.setValue(8, 7, 7);
        sudokuGrid.setValue(8, 8, 9);

        sudokuGrid.displayGrid();
        ExclusionDirecte exclusionDirecte = new ExclusionDirecte<Integer>();
        exclusionDirecte.applyRule(sudokuGrid);
        sudokuGrid.displayPossibleValues();

        UniciteForcee uniciteForcee = new UniciteForcee<Integer>();

        uniciteForcee.applyRule(sudokuGrid);
        sudokuGrid.displayGrid();

        BackTrackingSolver backTrackingSolver = new BackTrackingSolver<Integer>(new Logger());
        backTrackingSolver.solve(sudokuGrid, false);
        sudokuGrid.displayGrid();


    }
}

