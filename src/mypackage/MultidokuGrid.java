package src.mypackage;

import java.util.ArrayList;
import java.util.List;

public class MultidokuGrid<E> extends Grid<E> {
    private List<Cell<E>> sharedCells; // Liste des cellules partagées

    public MultidokuGrid(int size) {
        super(size);
        this.sharedCells = new ArrayList<>();
    }

    // Ajouter une cellule partagée entre plusieurs grilles
    public void addSharedCell(Cell<E> cell) {
        sharedCells.add(cell);
    }

    private boolean isSharedCell(int row, int col) {
        for (Cell<E> cell : sharedCells) {
            if (cell.getX() == col && cell.getY() == row) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValid(int row, int col, E value) {
        if (value == null) return false;

        // Vérifier si la valeur existe déjà dans la même région
        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null && cell.getValue().equals(value)) {
                    return false;
                }
            }
        }

        // Vérifier les cellules partagées
        for (Cell<E> cell : sharedCells) {
            if (cell.getX() == col && cell.getY() == row && cell.getValue() != null && cell.getValue().equals(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E getValue(int row, int col) {
        for (Cell<E> cell : sharedCells) {
            if (cell.getX() == col && cell.getY() == row) {
                return cell.getValue();
            }
        }

        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getX() == col && cell.getY() == row) {
                    return cell.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void setValue(int row, int col, E value) {
        if (!isValid(row, col, value)) {
            System.out.println("❌ Valeur invalide");
            return;
        }

        for (Cell<E> cell : sharedCells) {
            if (cell.getX() == col && cell.getY() == row) {
                cell.setValue(value);
                return;
            }
        }

        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getX() == col && cell.getY() == row) {
                    cell.setValue(value);
                    return;
                }
            }
        }
    }
}
