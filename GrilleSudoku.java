package modele;

import java.util.Arrays;

/**
 * Classe représentant une grille de Sudoku.
 * La grille est représentée par un tableau 2D d'entiers.
 * Une valeur de 0 représente une case vide.
 */
public class GrilleSudoku {
    private int[][] grille;
    private int taille;
    private int tailleBloc;

    /**
     * Constructeur pour une grille de Sudoku classique 9x9.
     */
    public GrilleSudoku() {
        this.taille = 9;
        this.tailleBloc = 3;
        grille = new int[taille][taille];
    }

    /**
     * Constructeur pour une grille de Sudoku de taille personnalisée.
     * @param taille la taille de la grille (N)
     * @param tailleBloc la taille d'un bloc (par exemple 3 pour un sudoku 9x9)
     */
    public GrilleSudoku(int taille, int tailleBloc) {
        this.taille = taille;
        this.tailleBloc = tailleBloc;
        grille = new int[taille][taille];
    }

    /**
     * Renvoie la grille.
     * @return tableau 2D représentant la grille.
     */
    public int[][] getGrille() {
        return grille;
    }

    /**
     * Fixe une valeur dans une case.
     * @param ligne indice de la ligne (0-indexé)
     * @param col indice de la colonne (0-indexé)
     * @param valeur la valeur à fixer (0 pour vide)
     */
    public void setCase(int ligne, int col, int valeur) {
        grille[ligne][col] = valeur;
    }

    /**
     * Renvoie la valeur dans une case.
     * @param ligne indice de la ligne
     * @param col indice de la colonne
     * @return la valeur dans la case
     */
    public int getCase(int ligne, int col) {
        return grille[ligne][col];
    }

    /**
     * Vérifie si l'insertion de la valeur dans la case (ligne, col) est valide.
     * @param ligne indice de la ligne
     * @param col indice de la colonne
     * @param valeur la valeur à tester
     * @return vrai si l'insertion est valide selon les règles du Sudoku, sinon faux.
     */
    public boolean estValide(int ligne, int col, int valeur) {
        // Vérifier la ligne
        for (int j = 0; j < taille; j++) {
            if (grille[ligne][j] == valeur) {
                return false;
            }
        }
        // Vérifier la colonne
        for (int i = 0; i < taille; i++) {
            if (grille[i][col] == valeur) {
                return false;
            }
        }
        // Vérifier le bloc
        int debutLigne = (ligne / tailleBloc) * tailleBloc;
        int debutCol = (col / tailleBloc) * tailleBloc;
        for (int i = debutLigne; i < debutLigne + tailleBloc; i++) {
            for (int j = debutCol; j < debutCol + tailleBloc; j++) {
                if (grille[i][j] == valeur) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Affiche la grille dans la console.
     */
    public void afficher() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(grille[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Clone la grille.
     * @return une nouvelle instance de GrilleSudoku avec les mêmes valeurs.
     */
    public GrilleSudoku cloner() {
        GrilleSudoku clone = new GrilleSudoku(taille, tailleBloc);
        for (int i = 0; i < taille; i++) {
            clone.grille[i] = Arrays.copyOf(this.grille[i], taille);
        }
        return clone;
    }

    /**
     * Renvoie la taille de la grille.
     * @return taille de la grille
     */
    public int getTaille() {
        return taille;
    }
}
