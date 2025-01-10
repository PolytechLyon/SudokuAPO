import java.util.HashSet;
import java.util.Set;

public class SudokuSolver {
    protected final int taille; // Taille de la grille
    protected final Set<Integer>[][] grille; // Chaque case contient un ensemble de valeurs possibles

    @SuppressWarnings("unchecked")
    public SudokuSolver(int taille) {
        this.taille = taille;
        grille = new HashSet[taille][taille];

        // Initialiser chaque case avec tous les chiffres possibles (1 à taille)
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = new HashSet<>();
                for (int k = 1; k <= taille; k++) {
                    grille[i][j].add(k);
                }
            }
        }
    }

    // Affecte une valeur à une case
    public void affecterValeur(int ligne, int colonne, int valeur) {
        grille[ligne][colonne].clear();
        grille[ligne][colonne].add(valeur);
    }

    // Affiche la grille
    public void afficherGrille() {
        System.out.println("Grille actuelle :");
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j].size() == 1) {
                    System.out.print(grille[i][j].iterator().next() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
