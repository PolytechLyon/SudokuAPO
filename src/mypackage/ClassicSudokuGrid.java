package src.mypackage;

public class ClassicSudokuGrid extends SudokuModulable {

    // Constructeur
    public ClassicSudokuGrid() {
        super(9); // Un Sudoku classique a une grille de 9x9
    }

    // Surcharger displayGrid() pour un Sudoku 9x9
    public void displayGrid() {
        System.out.println("Affichage de la grille classique 9x9 :");

        // Affichage de la grille ligne par ligne
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Object value = getValue(y, x); // Récupère la valeur à la position (y, x)

                // Affiche un point (.) si la valeur est nulle (case vide)
                if (value == null) {
                    value = "."; // Valeur vide à afficher comme un point
                }

                // Ajouter un séparateur vertical après chaque 3ème colonne
                if (x % 3 == 0 && x != 0) {
                    System.out.print("| ");
                }

                // Affiche la valeur
                System.out.print(value + " ");
            }
            System.out.println();

            // Ajouter une ligne de séparation après chaque bloc de 3x3
            if ((y + 1) % 3 == 0 && y != 8) {
                System.out.println("---------------------");
            }
        }
    }

    // Autres méthodes ou ajustements peuvent être ajoutés ici si nécessaire
}
