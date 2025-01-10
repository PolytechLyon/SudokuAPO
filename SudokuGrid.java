// Classe abstraite représentant une grille générale de Sudoku
public abstract class SudokuGrid {
    protected int size; // Taille de la grille (N x N)
    protected int[][] grid; // Tableau bidimensionnel représentant la grille

    // Constructeur pour initialiser la taille et la grille
    public SudokuGrid(int size) {
        this.size = size;
        this.grid = new int[size][size]; // Initialise une grille vide
    }

    // Méthode abstraite : vérifie si une valeur peut être placée à une position donnée
    public abstract boolean isValid(int row, int col, int value);

    // Méthode abstraite : résout la grille
    public abstract boolean solve();

    // Affiche la grille dans la console
    public void display() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(grid[row][col] + " "); // Affiche chaque case avec un espace
            }
            System.out.println(); // Retour à la ligne après chaque ligne
        }
    }

    // Définit une valeur dans une case donnée
    public void setValue(int row, int col, int value) {
        grid[row][col] = value;
    }

    // Retourne la valeur d'une case donnée
    public int getValue(int row, int col) {
        return grid[row][col];
    }
}
