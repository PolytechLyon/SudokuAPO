package src;

import src.mypackage.*;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Character> possibleValues = new HashSet<>();

        // 📌 Ajouter les lettres de 'A' à 'I' pour représenter les chiffres 1 à 9
        for (char c = 'A'; c <= 'I'; c++) {
            possibleValues.add(c);
        }

        // Création d'une instance de ClassicSudokuGrid avec des caractères
        ClassicSudokuGrid<Character> sudokuGrid = new ClassicSudokuGrid<>(9, possibleValues);

        // 📌 Remplir la grille avec la correspondance `1-9` -> `A-I`
        sudokuGrid.setValue(0, 0, 'E');  // 5 -> E
        sudokuGrid.setValue(0, 1, 'C');  // 3 -> C
        sudokuGrid.setValue(0, 4, 'G');  // 7 -> G

        sudokuGrid.setValue(1, 0, 'F');  // 6 -> F
        sudokuGrid.setValue(1, 3, 'A');  // 1 -> A
        sudokuGrid.setValue(1, 4, 'I');  // 9 -> I
        sudokuGrid.setValue(1, 5, 'E');  // 5 -> E

        sudokuGrid.setValue(2, 1, 'I');  // 9 -> I
        sudokuGrid.setValue(2, 2, 'H');  // 8 -> H
        sudokuGrid.setValue(2, 7, 'F');  // 6 -> F

        sudokuGrid.setValue(3, 0, 'H');  // 8 -> H
        sudokuGrid.setValue(3, 4, 'F');  // 6 -> F
        sudokuGrid.setValue(3, 8, 'C');  // 3 -> C

        sudokuGrid.setValue(4, 0, 'D');  // 4 -> D
        sudokuGrid.setValue(4, 3, 'H');  // 8 -> H
        sudokuGrid.setValue(4, 5, 'C');  // 3 -> C
        sudokuGrid.setValue(4, 8, 'A');  // 1 -> A

        sudokuGrid.setValue(5, 0, 'G');  // 7 -> G
        sudokuGrid.setValue(5, 4, 'B');  // 2 -> B
        sudokuGrid.setValue(5, 8, 'F');  // 6 -> F

        sudokuGrid.setValue(6, 1, 'F');  // 6 -> F
        sudokuGrid.setValue(6, 6, 'B');  // 2 -> B
        sudokuGrid.setValue(6, 7, 'H');  // 8 -> H

        sudokuGrid.setValue(7, 3, 'D');  // 4 -> D
        sudokuGrid.setValue(7, 4, 'A');  // 1 -> A
        sudokuGrid.setValue(7, 5, 'I');  // 9 -> I
        sudokuGrid.setValue(7, 8, 'E');  // 5 -> E

        sudokuGrid.setValue(8, 4, 'H');  // 8 -> H
        sudokuGrid.setValue(8, 7, 'G');  // 7 -> G
        sudokuGrid.setValue(8, 8, 'I');  // 9 -> I

        // 📌 Affichage initial
        System.out.println("\n🔵 Grille initiale :");
        sudokuGrid.displayGrid();

        // ✅ Appliquer la règle d'exclusion directe
        ExclusionDirecte<Character> exclusionDirecte = new ExclusionDirecte<>(new Logger());
        exclusionDirecte.applyRule(sudokuGrid);
        System.out.println("\n🟡 Après Exclusion Directe :");
        sudokuGrid.displayPossibleValues();

        // ✅ Appliquer la règle d'unicité forcée
        UniciteForcee<Character> uniciteForcee = new UniciteForcee<>(new Logger());
        uniciteForcee.applyRule(sudokuGrid);
        System.out.println("\n🟢 Après Unicité Forcée :");
        sudokuGrid.displayGrid();

        // ✅ Appliquer le Backtracking Solver
        BackTrackingSolver<Character> backTrackingSolver = new BackTrackingSolver<>(new Logger());
        backTrackingSolver.solve(sudokuGrid, false);
        System.out.println("\n🚀 Après Backtracking Solver :");
        sudokuGrid.displayGrid();
    }
}
