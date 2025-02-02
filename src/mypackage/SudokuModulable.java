package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SudokuModulable<E> extends Grid<E> {
    private Cell<E>[][] cells; // Grille des cellules

    // Constructeur
    public SudokuModulable(int size) {
        super(size);
        this.cells = new Cell[size][size];

        // Initialisation des cellules
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new Cell<>(null, y, x);
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
}
