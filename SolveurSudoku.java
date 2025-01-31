package solver;

import modele.GrilleSudoku;
import java.util.Random;

/**
 * Classe implémentant la résolution d'un Sudoku par retour sur trace (backtracking).
 */
public class SolveurSudoku {
    private int taille;

    public SolveurSudoku(int taille) {
        this.taille = taille;
    }

    /**
     * Résout le Sudoku en modifiant la grille en place.
     * @param grille une instance de GrilleSudoku
     * @return vrai si le sudoku a été résolu, sinon faux.
     */
    public boolean resoudre(GrilleSudoku grille) {
        int[] pos = trouverCaseVide(grille);
        if (pos == null) {
            // Aucun vide, sudoku résolu
            return true;
        }
        int ligne = pos[0];
        int col = pos[1];

        // Essayer les valeurs 1 à taille dans un ordre aléatoire
        int[] valeurs = melangerValeurs(taille);
        for (int val : valeurs) {
            if (grille.estValide(ligne, col, val)) {
                grille.setCase(ligne, col, val);
                if (resoudre(grille)) {
                    return true;
                }
                // Backtrack
                grille.setCase(ligne, col, 0);
            }
        }
        return false;
    }

    /**
     * Trouve la première case vide dans la grille.
     * @param grille une instance de GrilleSudoku
     * @return un tableau de deux entiers {ligne, col} ou null si aucune case vide.
     */
    private int[] trouverCaseVide(GrilleSudoku grille) {
        int taille = grille.getTaille();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille.getCase(i, j) == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Mélange les valeurs de 1 à n dans un ordre aléatoire.
     * @param n la taille maximale
     * @return un tableau d'entiers mélangés.
     */
    private int[] melangerValeurs(int n) {
        int[] valeurs = new int[n];
        for (int i = 0; i < n; i++) {
            valeurs[i] = i + 1;
        }
        Random rand = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = valeurs[i];
            valeurs[i] = valeurs[j];
            valeurs[j] = temp;
        }
        return valeurs;
    }
}
