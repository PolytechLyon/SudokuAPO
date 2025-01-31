package generator;

import modele.GrilleSudoku;
import solver.SolveurSudoku;
import java.util.Random;

/**
 * Classe permettant de générer des grilles de Sudoku.
 */
public class GenerateurSudoku {
    private int taille;

    public GenerateurSudoku(int taille) {
        this.taille = taille;
    }

    /**
     * Génère une grille complète de Sudoku.
     * @return une grille de Sudoku complétée.
     */
    public GrilleSudoku genererGrilleComplete() {
        // Pour une grille classique 9x9, on suppose des blocs de 3
        GrilleSudoku grille = new GrilleSudoku(taille, (int)Math.sqrt(taille));
        SolveurSudoku solveur = new SolveurSudoku(taille);
        if (solveur.resoudre(grille)) {
            return grille;
        }
        return null;
    }

    /**
     * Génère une grille de Sudoku avec des cases vides en fonction du nombre d'indices à retirer.
     * @param nbCasesARetirer nombre de cases à vider pour créer le puzzle.
     * @return une grille de Sudoku partiellement remplie.
     */
    public GrilleSudoku genererPuzzle(int nbCasesARetirer) {
        GrilleSudoku grilleComplete = genererGrilleComplete();
        if (grilleComplete == null) {
            return null;
        }
        GrilleSudoku puzzle = grilleComplete.cloner();
        Random rand = new Random();
        int tailleCarree = taille * taille;
        int casesRetirees = 0;
        while (casesRetirees < nbCasesARetirer) {
            int pos = rand.nextInt(tailleCarree);
            int ligne = pos / taille;
            int col = pos % taille;
            if (puzzle.getCase(ligne, col) != 0) {
                // On retire le chiffre (on ne teste pas ici l'unicité de la solution)
                puzzle.setCase(ligne, col, 0);
                casesRetirees++;
            }
        }
        return puzzle;
    }
}
