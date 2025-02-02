package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SudokuModulable<E> extends Grid<E> {
    private Cell<E>[][] cells; // Grille des cellules

    // 🔥 ✅ Correction : Ajout de `possibleValues`
    public SudokuModulable(int size, Set<E> possibleValues) {
        super(size);
        this.cells = new Cell[size][size];

        // Initialisation des cellules avec les valeurs possibles
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new Cell<>(null, y, x, possibleValues);  // ✅ Passer les valeurs possibles
            }
        }
    }

    @Override
    public boolean isValid(int y, int x, E value) {
        if (value == null) return false;

        // Vérifier si la valeur existe déjà dans la même région
        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null && cell.getValue().equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public E getValue(int y, int x) {
        return cells[y][x].getValue();
    }

    @Override
    public void setValue(int y, int x, E value) {
        if (!isValid(y, x, value)) {
            System.out.println("❌ Valeur invalide : " + value);
            return;
        }
        cells[y][x].setValue(value);
    }

    public void displayGrid() {
        System.out.println("Sudoku - Taille " + size + "x" + size + ":");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Object value = getValue(row, col); // Récupère la valeur de la cellule
                System.out.print((value != null ? value : ".") + " "); // Si la valeur est null, affiche "."
                // Affichage de séparateurs si nécessaire (comme pour un Sudoku 3x3)
                if ((col + 1) % Math.sqrt(size) == 0 && col + 1 < size) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((row + 1) % Math.sqrt(size) == 0 && row + 1 < size) {
                System.out.println("-".repeat(size * 2));
            }
        }
    }
}
