package src.mypackage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Génère une grille de Sudoku en demandant à l'utilisateur de la remplir.
 * L'utilisateur doit entrer les valeurs ligne par ligne, et la grille
 * est ensuite générée en fonction des entrées fournies.
 *
 * @param <E> Le type des valeurs dans la grille.
 */
public class GenerateFromUser<E> extends Generator<E> {

    public GenerateFromUser(Difficulte difficulte) {
        super(difficulte);
    }

    /**
     * Génère une grille Sudoku en demandant à l'utilisateur de la remplir.
     *
     * @return La grille remplie par l'utilisateur.
     */
    public Grid<E> generateUserGrid() {
        Scanner scanner = new Scanner(System.in);

        // Demander la taille de la grille
        System.out.print("Entrez la taille de la grille (ex: 9 pour un Sudoku 9x9) : ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        // Demander les valeurs possibles
        Set<E> possibleValues = new HashSet<>();
        System.out.print("Entrez les valeurs possibles séparées par des espaces (ex: 1 2 3 ... 9 ou A B C ... F) : ");
        String[] values = scanner.nextLine().split(" ");

        // Ajouter les valeurs possibles à un ensemble
        for (String value : values) {
            possibleValues.add((E) value); // Supposition que l'entrée est correcte
        }

        // Création d'une grille vide
        ClassicSudokuGrid<E> userGrid = new ClassicSudokuGrid<>(size, possibleValues);
        System.out.println("Grille vide initialisée !");

        // Demander à l'utilisateur d'entrer la grille ligne par ligne
        System.out.println("Entrez votre grille ligne par ligne (utilisez '.' pour les cases vides) :");

        for (int y = 0; y < size; y++) {
            System.out.print("Ligne " + (y + 1) + " : ");
            String[] rowValues = scanner.nextLine().split(" ");

            // Vérification si le nombre de valeurs est correct
            if (rowValues.length != size) {
                System.out.println("Erreur : Vous devez entrer exactement " + size + " valeurs !");
                y--; // Redemande la même ligne
                continue;
            }

            for (int x = 0; x < size; x++) {
                if (!rowValues[x].equals(".")) { // "." représente une case vide
                    E value = (E) rowValues[x];

                    // Vérification si la valeur entrée est valide
                    if (!possibleValues.contains(value)) {
                        System.out.println("Valeur invalide : " + value);
                        x--; // Redemande cette cellule
                        continue;
                    }

                    userGrid.setValue(y, x, value);
                }
            }
        }

        // Affichage de la grille avant résolution
        System.out.println("\nGrille saisie par l'utilisateur :");
        userGrid.displayGrid();

        return userGrid;
    }
}
