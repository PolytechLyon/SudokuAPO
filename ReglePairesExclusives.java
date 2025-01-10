import java.util.HashSet;
import java.util.Set;

public class ReglePairesExclusives {
    private final SudokuSolver sudoku;

    public ReglePairesExclusives(SudokuSolver sudoku) {
        this.sudoku = sudoku;
    }

    public void appliquer() {
        // Appliquer la règle sur chaque ligne
        for (int ligne = 0; ligne < sudoku.taille; ligne++) {
            verifierPairesDansLigne(ligne);
        }

        // Appliquer la règle sur chaque colonne
        for (int colonne = 0; colonne < sudoku.taille; colonne++) {
            verifierPairesDansColonne(colonne);
        }

        // Appliquer la règle sur chaque bloc
        int tailleBloc = (int) Math.sqrt(sudoku.taille);
        for (int blocLigne = 0; blocLigne < tailleBloc; blocLigne++) {
            for (int blocColonne = 0; blocColonne < tailleBloc; blocColonne++) {
                verifierPairesDansBloc(blocLigne, blocColonne, tailleBloc);
            }
        }
    }

    private void verifierPairesDansLigne(int ligne) {
        for (int c1 = 0; c1 < sudoku.taille; c1++) {
            Set<Integer> possibles1 = sudoku.grille[ligne][c1];
            if (possibles1.size() == 2) { // Vérifier si la case contient exactement deux valeurs
                for (int c2 = c1 + 1; c2 < sudoku.taille; c2++) {
                    Set<Integer> possibles2 = sudoku.grille[ligne][c2];
                    if (possibles1.equals(possibles2)) { // Vérifier si les deux cases ont les mêmes deux valeurs
                        // Éliminer ces deux valeurs des autres cases de la ligne
                        for (int c3 = 0; c3 < sudoku.taille; c3++) {
                            if (c3 != c1 && c3 != c2) {
                                sudoku.grille[ligne][c3].removeAll(possibles1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void verifierPairesDansColonne(int colonne) {
        for (int l1 = 0; l1 < sudoku.taille; l1++) {
            Set<Integer> possibles1 = sudoku.grille[l1][colonne];
            if (possibles1.size() == 2) { // Vérifier si la case contient exactement deux valeurs
                for (int l2 = l1 + 1; l2 < sudoku.taille; l2++) {
                    Set<Integer> possibles2 = sudoku.grille[l2][colonne];
                    if (possibles1.equals(possibles2)) { // Vérifier si les deux cases ont les mêmes deux valeurs
                        // Éliminer ces deux valeurs des autres cases de la colonne
                        for (int l3 = 0; l3 < sudoku.taille; l3++) {
                            if (l3 != l1 && l3 != l2) {
                                sudoku.grille[l3][colonne].removeAll(possibles1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void verifierPairesDansBloc(int blocLigne, int blocColonne, int tailleBloc) {
        // Calculer les limites du bloc
        int debutLigne = blocLigne * tailleBloc;
        int debutColonne = blocColonne * tailleBloc;

        // Parcourir toutes les cases du bloc
        for (int l1 = debutLigne; l1 < debutLigne + tailleBloc; l1++) {
            for (int c1 = debutColonne; c1 < debutColonne + tailleBloc; c1++) {
                Set<Integer> possibles1 = sudoku.grille[l1][c1];
                if (possibles1.size() == 2) { // Vérifier si la case contient exactement deux valeurs
                    for (int l2 = l1; l2 < debutLigne + tailleBloc; l2++) {
                        for (int c2 = (l2 == l1 ? c1 + 1 : debutColonne); c2 < debutColonne + tailleBloc; c2++) {
                            Set<Integer> possibles2 = sudoku.grille[l2][c2];
                            if (possibles1.equals(possibles2)) { // Vérifier si les deux cases ont les mêmes deux valeurs
                                // Éliminer ces deux valeurs des autres cases du bloc
                                for (int l3 = debutLigne; l3 < debutLigne + tailleBloc; l3++) {
                                    for (int c3 = debutColonne; c3 < debutColonne + tailleBloc; c3++) {
                                        if ((l3 != l1 || c3 != c1) && (l3 != l2 || c3 != c2)) {
                                            sudoku.grille[l3][c3].removeAll(possibles1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
