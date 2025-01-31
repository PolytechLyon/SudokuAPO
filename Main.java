import modele.GrilleSudoku;
import solver.SolveurSudoku;
import generator.GenerateurSudoku;
import java.util.Scanner;

/**
 * Classe principale pour exécuter le programme Sudoku.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;
        do {
            System.out.println("Menu:");
            System.out.println("1. Résoudre une grille de Sudoku");
            System.out.println("2. Générer un puzzle de Sudoku");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    // Résolution d'une grille de Sudoku saisie par l'utilisateur
                    GrilleSudoku grille = new GrilleSudoku();
                    System.out.println("Entrez les 9 lignes de la grille (0 pour vide) :");
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            grille.setCase(i, j, scanner.nextInt());
                        }
                    }
                    System.out.println("Grille initiale :");
                    grille.afficher();

                    SolveurSudoku solveur = new SolveurSudoku(9);
                    if (solveur.resoudre(grille)) {
                        System.out.println("Sudoku résolu :");
                        grille.afficher();
                    } else {
                        System.out.println("Le Sudoku n'a pas de solution.");
                    }
                    break;
                case 2:
                    // Génération d'un puzzle de Sudoku
                    GenerateurSudoku generateur = new GenerateurSudoku(9);
                    GrilleSudoku grilleComplete = generateur.genererGrilleComplete();
                    System.out.println("Grille complète générée :");
                    grilleComplete.afficher();
                    System.out.print("Nombre de cases à retirer pour créer le puzzle : ");
                    int nbRetirer = scanner.nextInt();
                    GrilleSudoku puzzle = generateur.genererPuzzle(nbRetirer);
                    System.out.println("Puzzle généré :");
                    puzzle.afficher();
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 3);

        scanner.close();
    }
}
