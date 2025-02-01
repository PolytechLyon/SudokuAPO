package src;

import src.mypackage.Cell;
import src.mypackage.ClassicSudokuGrid;
import src.mypackage.MultidokuGrid;
import src.mypackage.Region;

public class Main {
    public static void main(String[] args) {
        ClassicSudokuGrid sudoku = new ClassicSudokuGrid();

        sudoku.setValue(0, 0, 5);
        sudoku.setValue(1, 3, 8);
        sudoku.setValue(4, 4, 7);
        sudoku.setValue(8, 8, 9);

        sudoku.setValue(0, 3, 5); // Doit afficher une erreur
        sudoku.setValue(3, 3, 8); // Doit afficher une erreur
        sudoku.setValue(2, 2, 5); // Doit afficher une erreur

        sudoku.displayGrid();
    }
}



