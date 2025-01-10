public class RegleExclusionDirecte {
    // Référence à l'objet SudokuSolver contenant la grille et les paramètres du Sudoku
    private final SudokuSolver sudoku;

    // Constructeur : prend en paramètre un objet SudokuSolver pour accéder à la grille
    public RegleExclusionDirecte(SudokuSolver sudoku) {
        this.sudoku = sudoku; // On associe l'objet SudokuSolver à cette règle
    }

    /**
     * Applique la règle d'exclusion directe.
     * Cette règle consiste à éliminer une valeur donnée des possibilités
     * dans les cases de la même ligne, colonne et bloc que la case ciblée.
     *
     * @param ligne   La ligne de la case où la valeur est fixée
     * @param colonne La colonne de la case où la valeur est fixée
     * @param valeur  La valeur à exclure des autres cases
     */
    public void appliquer(int ligne, int colonne, int valeur) {
        // 1. Supprimer la valeur des possibilités dans les cases de la même ligne
        for (int c = 0; c < sudoku.taille; c++) {
            if (c != colonne) { // Ne pas modifier la case elle-même
                sudoku.grille[ligne][c].remove(valeur); // Supprimer la valeur des possibilités
            }
        }

        // 2. Supprimer la valeur des possibilités dans les cases de la même colonne
        for (int l = 0; l < sudoku.taille; l++) {
            if (l != ligne) { // Ne pas modifier la case elle-même
                sudoku.grille[l][colonne].remove(valeur); // Supprimer la valeur des possibilités
            }
        }

        // 3. Calculer les limites du bloc contenant la case
        int tailleBloc = (int) Math.sqrt(sudoku.taille); // Taille d'un bloc (ex : 3 pour un Sudoku 9x9)
        int debutBlocLigne = (ligne / tailleBloc) * tailleBloc; // Ligne de départ du bloc
        int debutBlocColonne = (colonne / tailleBloc) * tailleBloc; // Colonne de départ du bloc

        // 4. Supprimer la valeur des possibilités dans les cases du même bloc
        for (int l = debutBlocLigne; l < debutBlocLigne + tailleBloc; l++) {
            for (int c = debutBlocColonne; c < debutBlocColonne + tailleBloc; c++) {
                if (l != ligne || c != colonne) { // Ne pas modifier la case elle-même
                    sudoku.grille[l][c].remove(valeur); // Supprimer la valeur des possibilités
                }
            }
        }
    }
}
