package src.mypackage;

import java.util.List;
import java.util.ArrayList;



public class MultidokuGrid extends Grid {
    public MultidokuGrid(int size) {
        super(size);
    }

    @Override
    public boolean isValid(int row, int col, int value) {
        return false;
    }

    @Override
    public boolean solve() {
        return false;
    }

    @Override
    public int getValue(int row, int col) {
        return 0;
    }
//    private List<Cell<?>> sharedCells; // Liste des cellules partagées entre les grilles
//
//    // Constructeur
//    public MultidokuGrid() {
//        super();
//        this.sharedCells = new ArrayList<>();
//    }
//
//    // Ajouter une cellule partagée
//    public void addSharedCell(Cell<?> cell) {
//        this.sharedCells.add(cell);
//    }
//
//
//
//
//    // elles font quoi ces 2 là ?
//    public boolean isValid(int row, int col, int value) {
//        return true;
//    }
//    public boolean solve() {
//        return true;
//    }
//
//
//
//
//    // Getter pour les cellules partagées
//    public List<Cell<?>> getSharedCells() {
//        return sharedCells;
//    }
//
//    @Override
//    public String toString() {
//        return "MultidokuGrid{" +
//                "sharedCells=" + sharedCells +
//                '}';
//    }
//
//
//    public MultidokuGrid(int size) {
//        super(size);
//    }
}
