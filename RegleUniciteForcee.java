import java.util.HashSet;
import java.util.Set;

public class RegleUniciteForcee {
    private final SudokuSolver sudoku;

    public RegleUniciteForcee(SudokuSolver sudoku) {
        this.sudoku = sudoku;
    }

    public void appliquer() {
        // Vérifier chaque ligne
        for (int ligne = 0; ligne < sudoku.taille; ligne++) {
            for (int valeur = 1; valeur <= sudoku.taille; valeur++) {
                Set<Integer> colonnesPossibles = new HashSet<>();
                for (int colonne = 0; colonne < sudoku.taille; colonne++) {
                    if (sudoku.grille[ligne][colonne].contains(valeur)) {
                        colonnesPossibles.add(colonne);
                    }
                }
                if (colonnesPossibles.size() == 1) {
                    int colonneUnique = colonnesPossibles.iterator().next();
                    sudoku.affecterValeur(ligne, colonneUnique, valeur);
                }
            }
        }

        // Vérifier chaque colonne
        for (int colonne = 0; colonne < sudoku.taille; colonne++) {
            for (int valeur = 1; valeur <= sudoku.taille; valeur++) {
                Set<Integer> lignesPossibles = new HashSet<>();
                for (int ligne = 0; ligne < sudoku.taille; ligne++) {
                    if (sudoku.grille[ligne][colonne].contains(valeur)) {
                        lignesPossibles.add(ligne);
                    }
                }
                if (lignesPossibles.size() == 1) {
                    int ligneUnique = lignesPossibles.iterator().next();
                    sudoku.affecterValeur(ligneUnique, colonne, valeur);
                }
            }
        }

        // Vérifier chaque bloc
        int tailleBloc = (int) Math.sqrt(sudoku.taille);
        for (int blocLigne = 0; blocLigne < tailleBloc; blocLigne++) {
            for (int blocColonne = 0; blocColonne < tailleBloc; blocColonne++) {
                for (int valeur = 1; valeur <= sudoku.taille; valeur++) {
                    Set<int[]> casesPossibles = new HashSet<>();
                    int debutLigne = blocLigne * tailleBloc;
                    int debutColonne = blocColonne * tailleBloc;
                    for (int ligne = debutLigne; ligne < debutLigne + tailleBloc; ligne++) {
                        for (int colonne = debutColonne; colonne < debutColonne + tailleBloc; colonne++) {
                            if (sudoku.grille[ligne][colonne].contains(valeur)) {
                                casesPossibles.add(new int[] { ligne, colonne });
                            }
                        }
                    }
                    if (casesPossibles.size() == 1) {
                        int[] caseUnique = casesPossibles.iterator().next();
                        sudoku.affecterValeur(caseUnique[0], caseUnique[1], valeur);
                    }
                }
            }
        }
    }
}
