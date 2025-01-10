// Classe principale pour tester la grille de Sudoku
public class Main {
    public static void main(String[] args) {
        SudokuGrid sudoku = new ClassicSudokuGrid(); // Crée une grille classique 9x9

        // Exemple de remplissage partiel de la grille
        sudoku.setValue(0, 0, 5);
        sudoku.setValue(0, 1, 3);
        sudoku.setValue(1, 0, 6);
        sudoku.setValue(4, 4, 7);
        sudoku.setValue(8, 8, 9);

        // Affiche la grille avant résolution
        System.out.println("Grille avant résolution :");
        sudoku.display();

        // Résout la grille et affiche le résultat
        if (sudoku.solve()) {
            System.out.println("Grille après résolution :");
            sudoku.display();
        } else {
            System.out.println("Impossible de résoudre la grille !");
        }
    }
}