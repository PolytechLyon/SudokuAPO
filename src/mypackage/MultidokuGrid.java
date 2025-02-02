package src.mypackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une grille de Sudoku multigrilles (Multidoku).
 * Cette grille peut avoir des cellules partagées entre plusieurs grilles de Sudoku.
 *
 * @param <E> Le type des éléments dans la grille (par exemple, Integer pour un Sudoku classique).
 */
public class MultidokuGrid<E> extends Grid<E> {
    private List<Cell<E>> sharedCells; // Liste des cellules partagées

    /**
     * Constructeur pour initialiser une grille de taille donnée.
     *
     * @param size La taille de la grille (par exemple, 9 pour un Sudoku 9x9).
     */
    public MultidokuGrid(int size) {
        super(size);
        this.sharedCells = new ArrayList<>();
    }

    /**
     * Ajoute une cellule partagée entre plusieurs grilles.
     *
     * @param cell La cellule à ajouter à la liste des cellules partagées.
     */
    public void addSharedCell(Cell<E> cell) {
        sharedCells.add(cell);
    }

    /**
     * Vérifie si une cellule donnée est partagée entre plusieurs grilles.
     *
     * @param row La ligne de la cellule.
     * @param col La colonne de la cellule.
     * @return true si la cellule est partagée, false sinon.
     */
    private boolean isSharedCell(int row, int col) {
        for (Cell<E> cell : sharedCells) {
            if (cell.getX() == col && cell.getY() == row) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si la valeur donnée peut être placée à la position spécifiée dans la grille.
     *
     * @param row La ligne de la cellule.
     * @param col La colonne de la cellule.
     * @param value La valeur à vérifier.
     * @return true si la valeur est valide, false sinon.
     */
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

    /**
     * Obtient la valeur de la cellule spécifiée dans la grille.
     *
     * @param row La ligne de la cellule.
     * @param col La colonne de la cellule.
     * @return La valeur de la cellule ou null si la cellule est vide.
     */
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

    /**
     * Définit la valeur de la cellule spécifiée dans la grille.
     *
     * @param row La ligne de la cellule.
     * @param col La colonne de la cellule.
     * @param value La valeur à placer dans la cellule.
     */
    @Override
    public void setValue(int row, int col, E value) {
        if (!isValid(row, col, value)) {
            System.out.println("Valeur invalide");
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
