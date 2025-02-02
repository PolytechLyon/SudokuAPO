package src.mypackage;

import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MenuTextuel<E> {
    private SolverStrategy<E> solver;
    private Grid<E> userGrid;

    public MenuTextuel() {
        this.solver = null;
        this.userGrid = null;
    }

    /**
     * 🏠 Afficher le menu textuel principal
     */
    public void AfficherMenuTextuel() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n🔷 MENU PRINCIPAL 🔷");
            System.out.println("1️⃣ - Charger une grille depuis l'input utilisateur");
            System.out.println("2️⃣ - Choisir un ou des solveurs");
            System.out.println("3️⃣ - Résoudre la grille");
            System.out.println("4️⃣ - Afficher la grille");
            System.out.println("5️⃣ - Quitter");
            System.out.print("👉 Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante

            switch (choix) {
                case 1:
                    LoadGridFromUserInputAndSolve();
                    break;
                case 2:
                    solver = chooseSolverFromUserInput();
                    break;
                case 3:
                    solveGrid();
                    break;
//                case 4:
//                    afficherGrille();
//                    break;
                case 5:
                    System.out.println("👋 Au revoir !");
                    continuer = false;
                    break;
                default:
                    System.out.println("❌ Choix invalide, veuillez réessayer.");
            }
        }
        scanner.close();
    }

    /**
     * 📌 Charger une grille depuis l'input utilisateur et l'initialiser
     */
    public void LoadGridFromUserInputAndSolve() {
        Scanner scanner = new Scanner(System.in);

        // 📌 1️⃣ Demander la taille de la grille
        System.out.print("Entrez la taille de la grille (ex: 9 pour un Sudoku 9x9) : ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        // 📌 2️⃣ Demander les valeurs possibles
        Set<String> possibleValues = new HashSet<>();
        System.out.print("Entrez les valeurs possibles séparées par des espaces (ex: 1 2 3 ... 9 ou A B C ... F) : ");
        String[] values = scanner.nextLine().split(" ");

        // Ajouter directement les valeurs
        for (String value : values) {
            possibleValues.add(value);
        }

        // 📌 3️⃣ Création de la grille modulable
        ClassicSudokuGrid<String> userGrid = new ClassicSudokuGrid<>(size, possibleValues);
        System.out.println("✅ Grille initialisée avec succès !");
        userGrid.displayGrid();

        // 📌 4️⃣ Permettre à l'utilisateur d'ajouter des valeurs
        while (true) {
            System.out.print("\nEntrez une case à remplir (format: ligne colonne valeur), ou 'fin' pour arrêter : ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("fin")) {
                break; // Arrêter la saisie
            }

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("⚠️ Format invalide ! Essayez : ligne colonne valeur (ex: 0 0 5)");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                String value = parts[2];

                if (!possibleValues.contains(value)) {
                    System.out.println("❌ Valeur invalide ! Elle doit être parmi : " + possibleValues);
                    continue;
                }

                userGrid.setValue(row, col, value);
                userGrid.displayGrid();
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Erreur : Ligne et colonne doivent être des nombres !");
            }
        }
//
//        // 📌 5️⃣ Résolution de la grille après la saisie
//        if (solver == null) {
//            System.out.println("⚠️ Aucun solveur sélectionné. Veuillez choisir un solveur.");
//            solver = chooseSolverFromUserInput(); // 📌 L'utilisateur choisit un solveur
//        }
//
//        System.out.println("🔄 Résolution de la grille...");
//        boolean solved = solver.solve(userGrid, true);
//
//        if (solved) {
//            System.out.println("✅ La grille a été résolue avec succès !");
//        } else {
//            System.out.println("❌ La grille ne peut pas être résolue !");
//        }
//
//        // 📌 Afficher la grille après la résolution
//        System.out.println("🧩 Grille après résolution :");
//        userGrid.displayGrid();

    }

    /**
     * 🧠 Permet de choisir un solveur à appliquer sur la grille
     */
    public SolverStrategy<E> chooseSolverFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🧠 Choisissez un solveur pour résoudre la grille :");
        System.out.println("1️⃣ - Solveur basé sur les règles");
        System.out.println("2️⃣ - Solveur par Backtracking");
        System.out.println("3️⃣ - Solveur Hybride (Règles + Backtracking)");
        System.out.print("👉 Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante

        switch (choix) {
            case 1:
                System.out.println("✅ Solveur basé sur les règles sélectionné.");
                return new RuleBasedSolver<>(new Logger());
            case 2:
                System.out.println("✅ Solveur Backtracking sélectionné.");
                return new BackTrackingSolver<>(new Logger());
            case 3:
                System.out.println("✅ Solveur Hybride sélectionné.");
//                return new HybridSolver<>(new Logger());
            default:
                System.out.println("❌ Choix invalide. Solveur Backtracking par défaut.");
                return new BackTrackingSolver<>(new Logger());
        }
    }

    /**
     * 🔥 Résoudre la grille avec le solveur choisi
     */
    public void solveGrid() {
        if (userGrid == null) {
            System.out.println("⚠️ Veuillez d'abord charger une grille !");
            return;
        }
        if (solver == null) {
            System.out.println("⚠️ Veuillez choisir un solveur avant de résoudre !");
            return;
        }

        System.out.println("🔄 Résolution en cours...");
        boolean solved = solver.solve(userGrid, true);

        if (solved) {
            System.out.println("✅ La grille a été résolue avec succès !");
        } else {
            System.out.println("❌ La grille ne peut pas être résolue !");
        }
    }


}
