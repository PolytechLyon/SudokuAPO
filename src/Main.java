package src;

import src.mypackage.Cell;
import src.mypackage.ClassicSudokuGrid;
import src.mypackage.MultidokuGrid;
import src.mypackage.Region;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance de ClassicSudokuGrid
        ClassicSudokuGrid sudokuGrid = new ClassicSudokuGrid();

        // Affichage de la grille initiale
        sudokuGrid.displayGrid();

        // Définir quelques valeurs dans la grille
        sudokuGrid.setValue(0, 0, 5);  // Remplacer la cellule (0,0) par 5
        sudokuGrid.setValue(1, 1, 3);  // Remplacer la cellule (1,1) par 3
        sudokuGrid.setValue(2, 2, 7);  // Remplacer la cellule (2,2) par 7

        // Affichage de la grille après modifications
        System.out.println("\nAprès avoir défini quelques valeurs :");
        sudokuGrid.displayGrid();

        // Tester la validation (par exemple, une valeur invalide)
        System.out.println("\nEssai de définir une valeur invalide :");
        sudokuGrid.setValue(0, 0, 3);  // Tentative de placer 3 à (0,0), devrait échouer car la cellule contient déjà 5
        sudokuGrid.displayGrid();

        // Essayer de définir une valeur dans une autre cellule
        sudokuGrid.setValue(1, 2, 4);  // Valide cette fois-ci
        System.out.println("\nAprès avoir ajouté 4 en (1,2) :");

        sudokuGrid.displayGrid();
    }
}

