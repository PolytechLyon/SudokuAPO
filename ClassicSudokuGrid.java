// Classe pour une grille classique de Sudoku (9x9)
class ClassicSudokuGrid extends SudokuGrid {

    // Constructeur pour une grille classique (par défaut 9x9)
    public ClassicSudokuGrid() {
        super(9); // Appelle le constructeur de la classe parente avec une taille de 9
    }

    // Vérifie si une valeur est valide dans une case donnée
    @Override
    public boolean isValid(int row, int col, int value) {
        // Vérification de la ligne : la valeur ne doit pas déjà y être
        for (int i = 0; i < size; i++) {
            if (grid[row][i] == value) {
                return false;
            }
        }

        // Vérification de la colonne : la valeur ne doit pas déjà y être
        for (int i = 0; i < size; i++) {
            if (grid[i][col] == value) {
                return false;
            }
        }

        // Vérification du bloc : la valeur ne doit pas être dans le même bloc
        int sqrt = (int) Math.sqrt(size); // Taille du bloc (par exemple, 3x3 pour une grille 9x9)
        int boxRowStart = row - row % sqrt; // Début de la ligne du bloc
        int boxColStart = col - col % sqrt; // Début de la colonne du bloc

        for (int i = boxRowStart; i < boxRowStart + sqrt; i++) {
            for (int j = boxColStart; j < boxColStart + sqrt; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }
        }
        return true; // Si toutes les conditions sont respectées, la valeur est valide
    }

    // Résout la grille en utilisant une méthode de retour sur trace
    @Override
    public boolean solve() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == 0) { // Si la case est vide
                    for (int value = 1; value <= size; value++) { // Tester toutes les valeurs possibles
                        if (isValid(row, col, value)) { // Si la valeur est valide
                            grid[row][col] = value; // Affecte la valeur à la case

                            if (solve()) { // Appel récursif pour résoudre le reste de la grille
                                return true; // Si la grille est résolue, retour à l'appel précédent
                            }
                            grid[row][col] = 0; // Annule le choix si la solution est incorrecte
                        }
                    }
                    return false; // Aucun chiffre valide, la grille est insolvable
                }
            }
        }
        return true; // La grille est résolue
    }
}
