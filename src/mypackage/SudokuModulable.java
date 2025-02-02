package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SudokuModulable<E> extends Grid<E> {
    private Cell<E>[][] cells; // Grille des cellules

    public SudokuModulable(int size, Set<E> possibleValues) {
        super(size);
        this.cells = new Cell[size][size];

        // 🔥 Initialisation des cellules avec la liste des valeurs possibles
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new Cell<>(null, y, x, new HashSet<>(possibleValues)); // 🟢 Copie des valeurs possibles
            }
        }

        // 📌 Initialisation des régions (Ex: blocs 3x3 pour Sudoku 9x9)
        initializeRegions();
    }

    private void initializeRegions() {
        int regionSize = (int) Math.sqrt(size); // Ex: 3 pour un Sudoku 9x9
        for (int i = 0; i < size; i++) {
            regions.add(new Region<>(i));
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int regionIndex = (y / regionSize) * regionSize + (x / regionSize); // Trouver la région correcte
                regions.get(regionIndex).addCell(cells[y][x]); // Ajouter la cellule à la région
            }
        }
    }

    @Override
    public boolean isValid(int y, int x, E value) {
        if (value == null) return false;

        // ✅ Vérifier si la valeur est déjà présente dans la même ligne
        if (getValuesForRow(y).contains(value)) {
            return false;
        }

        // ✅ Vérifier si la valeur est déjà présente dans la même colonne
        if (getValuesForColumn(x).contains(value)) {
            return false;
        }

        // ✅ Vérifier si la valeur existe déjà dans la même région
        for (Region<E> region : regions) {
            if (region.containsCell(y, x)) { // Trouver la région contenant la cellule
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getValue() != null && cell.getValue().equals(value)) {
                        return false;
                    }
                }
            }
        }

        return true; // ✅ Valeur valide
    }


    @Override
    public E getValue(int y, int x) {
        return cells[y][x].getValue();
    }

    @Override
    public void setValue(int y, int x, E value) {
        if (!isValid(y, x, value)) {
            System.out.println("❌ Valeur invalide : " + value + " coordonées : " + x + ", " + y);
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
