public class Main {
    public static void main(String[] args) {
        SudokuSolver sudoku = new SudokuSolver(9);
        RegleExclusionDirecte regle1 = new RegleExclusionDirecte(sudoku);
        RegleUniciteForcee regle2 = new RegleUniciteForcee(sudoku);

        // Exemple d'affectation
        sudoku.affecterValeur(0, 1, 3);

        sudoku.affecterValeur(0, 2, 4);
        sudoku.affecterValeur(0, 3, 6);
        sudoku.affecterValeur(0, 4, 7);
        sudoku.affecterValeur(0, 6, 9);
        sudoku.affecterValeur(0, 7, 2);

        sudoku.affecterValeur(1, 5, 7);
        sudoku.affecterValeur(2, 5, 9);
        sudoku.affecterValeur(3, 5, 2);
        sudoku.affecterValeur(4, 5, 8);
        sudoku.affecterValeur(5, 5, 9);
        sudoku.affecterValeur(6, 5, 5);
        sudoku.affecterValeur(7, 5, 6);
        sudoku.affecterValeur(7, 5, 3);
        sudoku.affecterValeur(8, 5, 4);


        sudoku.affecterValeur(1, 8, 7);
        sudoku.affecterValeur(2, 8, 9);
        sudoku.affecterValeur(3, 8, 2);
        sudoku.affecterValeur(4, 8, 8);
        sudoku.affecterValeur(5, 8, 9);
        sudoku.affecterValeur(6, 8, 5);
        sudoku.affecterValeur(7, 8, 6);
        sudoku.affecterValeur(7, 8, 3);
        sudoku.affecterValeur(8, 8, 1);

        sudoku.afficherGrille();

        // Appliquer la règle d'unicité forcée
        regle2.appliquer();
        sudoku.afficherGrille();
    }
}
