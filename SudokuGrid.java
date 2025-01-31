import java.util.*;

abstract class SudokuGrid<T> {
    protected int size; // Taille de la grille (ex : 9 pour 9x9)
    protected T[][] grid; // Grille de Sudoku générique

    @SuppressWarnings("unchecked")
    public SudokuGrid(int size) {
        this.size = size;
        this.grid = (T[][]) new Object[size][size]; // Création générique
    }

    // Vérifie si une valeur peut être placée dans une case
    public abstract boolean isValid(int row, int col, T value);

    // Affiche la grille
    public void display() {
        for (int row = 0; row < size; row++) {
            if (row % Math.sqrt(size) == 0) System.out.println("-".repeat(size * 2));
            for (int col = 0; col < size; col++) {
                if (col % Math.sqrt(size) == 0) System.out.print("| ");
                System.out.print((grid[row][col] != null ? grid[row][col] : ".") + " ");
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(size * 2));
    }

    // Définit une valeur dans la grille
    public void setValue(int row, int col, T value) {
        grid[row][col] = value;
    }

    // Obtient la valeur d'une case
    public T getValue(int row, int col) {
        return grid[row][col];
    }
}
