package src.mypackage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MenuTextuel {
    private SolverStrategy solver;

    public MenuTextuel() {
        this.solver = null;
    }

    public void LoadGridFromUserInputAndSolve() {
        Scanner scanner = new Scanner(System.in);

        // 📌 1️⃣ Demander la taille de la grille
        System.out.print("Entrez la taille de la grille (ex: 9 pour un Sudoku 9x9) : ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        Set<String> possibleValues = new HashSet<>();
        System.out.print("Entrez les valeurs possibles séparées par des espaces (ex: 1 2 3 ... 9 ou A B C ... F) : ");
        String[] values = scanner.nextLine().split(" ");
        possibleValues.addAll(Arrays.asList(values));
        Grid userGrid = new SudokuModulable(size, possibleValues);
    }

    public SolverStrategy chooseSolverFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🧠 Choisissez un solveur pour résoudre la grille :");
        System.out.println("1️⃣ - Solveur basé sur les règles");
        System.out.println("2️⃣ - Solveur par Backtracking");
        System.out.println("3️⃣ - Solveur Hybride (Règles + Backtracking)");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        switch (choix) {
            case 1:
                System.out.println("✅ Solveur basé sur les règles sélectionné.");
                return new RuleBasedSolver(new Logger());
            case 2:
                System.out.println("✅ Solveur Backtracking sélectionné.");
                return new BackTrackingSolver(new Logger());
            default:
                System.out.println("❌ Choix invalide. Utilisation du solveur par défaut : Backtracking.");
                return new BackTrackingSolver(new Logger());
        }
    }


    public void setSolver(SolverStrategy solver) {
        this.solver = solver;
    }

}
