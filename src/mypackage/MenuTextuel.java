package src.mypackage;

import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MenuTextuel<E> {
    private SolverStrategy<E> solver;
    private ClassicSudokuGrid<E> userGrid;

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
            System.out.println("6️⃣ - Générer une grille automatiquement");
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
                case 6:
                    generateGridFromComputer();
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
        // 📌 Génération de la grille par l'utilisateur via `GenerateFromUser`
        GenerateFromUser<E> generator = new GenerateFromUser<>(Difficulte.MOYEN);
        ClassicSudokuGrid<E> userGrid = (ClassicSudokuGrid<E>) generator.generateUserGrid();

        // 📌 6️⃣ Demander quel solveur utiliser
        SolverStrategy<E> solver = chooseSolverFromUserInput();

        // 📌 7️⃣ Résolution de la grille
        System.out.println("🧩 Résolution en cours...");
        if (solver.solve(userGrid, true)) {
            System.out.println("✅ Grille résolue avec succès !");
        } else {
            System.out.println("❌ La grille ne peut pas être résolue !");
        }

        // 📌 8️⃣ Afficher la grille finale
        userGrid.displayGrid();
    }

    public void generateGridFromComputer() {
        Scanner scanner = new Scanner(System.in);

        // 📌 Demander la taille de la grille
        System.out.print("Entrez la taille de la grille (ex: 9 pour un Sudoku 9x9) : ");
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

        // 📌 Demander la difficulté
        System.out.println("Choisissez la difficulté :");
        System.out.println("1️⃣ - Facile");
        System.out.println("2️⃣ - Moyen");
        System.out.println("3️⃣ - Difficile");
        System.out.print("👉 Votre choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante

        Difficulte difficulte;
        switch (choix) {
            case 1 -> difficulte = Difficulte.FACILE;
            case 2 -> difficulte = Difficulte.MOYEN;
            case 3 -> difficulte = Difficulte.DIFFICILE;
            default -> {
                System.out.println("❌ Choix invalide, sélection de la difficulté par défaut : Moyen");
                difficulte = Difficulte.MOYEN;
            }
        }

        // 📌 Génération de la grille
        System.out.println("\n🔹 Génération d'une grille " + size + "x" + size + " en cours...");
        GenerateFromComputer<E> generator = new GenerateFromComputer<>(difficulte);
        userGrid = new ClassicSudokuGrid<>(size, possibleValues);
        generator.GeneratePuzzle(userGrid, difficulte);

        System.out.println("✅ Grille générée !");
        userGrid.displayGrid();
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
        userGrid.displayGrid();

    }


}
