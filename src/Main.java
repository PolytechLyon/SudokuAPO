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

        // Affichage de la grille initiale
        sudokuGrid.displayGrid();

        // Définir quelques valeurs dans la grille
        sudokuGrid.setValue(0, 0, 5);  // Remplacer la cellule (0,0) par 5
        sudokuGrid.setValue(1, 1, 3);  // Remplacer la cellule (1,1) par 3
        sudokuGrid.setValue(2, 2, 7);  // Remplacer la cellule (2,2) par 7
        sudokuGrid.setValue(5, 5, 7);  // Valide cette fois-ci


        // Affichage de la grille après modifications
        System.out.println("\nAprès avoir défini quelques valeurs :");
        sudokuGrid.displayGrid();

        // Tester la validation (par exemple, une valeur invalide)
        System.out.println("\nEssai de définir une valeur invalide :");
        sudokuGrid.setValue(0, 0, 3);  // Tentative de placer 3 à (0,0), devrait échouer car la cellule contient déjà 5
        sudokuGrid.displayGrid();

        // Essayer de définir une valeur dans une autre cellule
        sudokuGrid.setValue(1, 2, 3);  // Valide cette fois-ci
        System.out.println("\nAprès avoir ajouté 3 en (1,2) :");

        sudokuGrid.setValue(1, 3, 3);  // Valide cette fois-ci
        sudokuGrid.setValue(3, 1, 3);  // Valide cette fois-ci
        sudokuGrid.setValue(0, 2, 3);  // Valide cette fois-ci
        sudokuGrid.setValue(5, 5, 7);  // Valide cette fois-ci


        sudokuGrid.displayGrid();

        sudokuGrid.displayGrid();
    }
}

