package src.mypackage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GenerateFromUser<E> extends Generator<E> {
    public GenerateFromUser(Difficulte difficulte) {
        super(difficulte);
    }

    /**
     * 🚀 Génère une grille Sudoku en demandant à l'utilisateur de la remplir.
     */
    public Grid<E> generateUserGrid() {
        Scanner scanner = new Scanner(System.in);

        // 📌 1️⃣ Demander la taille de la grille
        System.out.print("Entrez la taille de la grille (ex: 9 pour un Sudoku 3x3) : ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        // 📌 2️⃣ Demander les valeurs possibles
        Set<E> possibleValues = new HashSet<>();
        System.out.print("Entrez les valeurs possibles séparées par des espaces (ex: 1 2 3 ... 9 ou A B C ... F) : ");
        String[] values = scanner.nextLine().split(" ");

        // 📌 3️⃣ Ajouter les valeurs possibles
        for (String value : values) {
            possibleValues.add((E) value); // ⚠️ Supposition que l'entrée est correcte
        }

        // 📌 4️⃣ Création d'une grille vide
        ClassicSudokuGrid<E> userGrid = new ClassicSudokuGrid<>(size, possibleValues);
        System.out.println("✅ Grille vide initialisée !");

        // 📌 5️⃣ Demander à l'utilisateur d'entrer la grille ligne par ligne
        System.out.println("📝 Entrez votre grille ligne par ligne (utilisez '.' pour les cases vides) :");

        for (int y = 0; y < size; y++) {
            System.out.print("Ligne " + (y + 1) + " : ");
            String[] rowValues = scanner.nextLine().split(" ");

            if (rowValues.length != size) {
                System.out.println("⚠️ Erreur : Vous devez entrer exactement " + size + " valeurs !");
                y--; // Redemande la même ligne
                continue;
            }

            for (int x = 0; x < size; x++) {
                if (!rowValues[x].equals(".")) { // ⚠️ "." représente une case vide
                    E value = (E) rowValues[x];

                    if (!possibleValues.contains(value)) {
                        System.out.println("❌ Valeur invalide : " + value);
                        x--; // Redemande cette cellule
                        continue;
                    }

                    userGrid.setValue(y, x, value);
                }
            }
        }

        // 📌 6️⃣ Affichage de la grille avant résolution
        System.out.println("\n✅ Grille saisie par l'utilisateur :");
        userGrid.displayGrid();

        return userGrid;
    }
}
