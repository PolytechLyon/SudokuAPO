public class Main {
    public static void main(String[] args) {
        SudokuSolver sudoku = new SudokuSolver(9);
        RegleExclusionDirecte regle1 = new RegleExclusionDirecte(sudoku);
        RegleUniciteForcee regle2 = new RegleUniciteForcee(sudoku);

        // Exemple d'affectation
        sudoku.affecterValeur(0, 0, 5);
        sudoku.affecterValeur(0, 1, 3);
        sudoku.affecterValeur(0, 2, 4);
        sudoku.affecterValeur(0, 3, 6);
        sudoku.affecterValeur(0, 4, 7);
        sudoku.affecterValeur(0, 5, 8);
        sudoku.affecterValeur(0, 6, 9);
        sudoku.affecterValeur(0, 7, 2);
        regle1.appliquer(0, 0, 5); // Appliquer la règle d'exclusion directe

        sudoku.afficherGrille();

        // Appliquer la règle d'unicité forcée
        regle2.appliquer();
        sudoku.afficherGrille();
    }
}
