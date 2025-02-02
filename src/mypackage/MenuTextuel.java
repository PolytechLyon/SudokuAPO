package src.mypackage;

import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant un menu textuel pour interagir avec le jeu Sudoku.
 * Permet de charger une grille depuis l'entrée de l'utilisateur, choisir un solveur,
 * résoudre la grille et afficher les résultats.
 *
 * @param <E> Le type des éléments dans la grille (par exemple, Integer pour un Sudoku classique).
 */
public class MenuTextuel<E> {
    private SolverStrategy<E> solver;
    private ClassicSudokuGrid<E> userGrid;

    /**
     * Constructeur par défaut initialisant le solveur et la grille à null.
     */
    public MenuTextuel() {
        this.solver = null;
        this.userGrid = null;
    }

    /**
     * Affiche le menu principal permettant de choisir une action à réaliser.
     * L'utilisateur peut choisir de charger une grille, sélectionner un solveur,
     * résoudre la grille, afficher la grille ou quitter.
     */
    public void AfficherMenuTextuel() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n🔷 MENU PRINCIPAL 🔷");
            System.out.println("1 - Charger une grille depuis l'input utilisateur");
            System.out.println("2 - Choisir un ou des solveurs");
            System.out.println("3 - Résoudre la grille");
            System.out.println("4 - Afficher la grille");
            System.out.println("5 - Quitter");
            System.out.println("6 - Générer une grille automatiquement");
            System.out.print("Votre choix : ");

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
                case 4:
                    // afficherGrille(); // Si vous avez cette méthode, décommentez ici.
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    continuer = false;
                    break;
                case 6:
                    generateGridFromComputer();
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
        scanner.close();
    }

    /**
     * Charge une grille depuis l'entrée de l'utilisateur et résout cette grille
     * en utilisant le solveur choisi.
     */
    public void LoadGridFromUserInputAndSolve() {
        // Génération de la grille par l'utilisateur via `GenerateFromUser`
        GenerateFromUser<E> generator = new GenerateFromUser<>(Difficulte.MOYEN);
        ClassicSudokuGrid<E> userGrid = (ClassicSudokuGrid<E>) generator.generateUserGrid();

        // Demander quel solveur utiliser
        SolverStrategy<E> solver = chooseSolverFromUserInput();

        // Résolution de la grille
        System.out.println("Résolution en cours...");
        if (solver.solve(userGrid, true)) {
            System.out.println("Grille résolue avec succès !");
        } else {
            System.out.println("La grille ne peut pas être résolue !");
        }

        // Afficher la grille finale
        userGrid.displayGrid();
    }

    /**
     * Génère une grille de Sudoku automatiquement en fonction de la taille et de la difficulté
     * choisie par l'utilisateur.
     */
    public void generateGridFromComputer() {
        Scanner scanner = new Scanner(System.in);

        // Demander la taille de la grille
        System.out.print("Entrez la taille de la grille (ex: 9 pour un Sudoku 9x9) : ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        // Demander les valeurs possibles
        Set<E> possibleValues = new HashSet<>();
        System.out.print("Entrez les valeurs possibles séparées par des espaces (ex: 1 2 3 ... 9 ou A B C ... F) : ");
        String[] values = scanner.nextLine().split(" ");

        // Ajouter les valeurs possibles
        for (String value : values) {
            possibleValues.add((E) value); // Supposition que l'entrée est correcte
        }

        // Demander la difficulté
        System.out.println("Choisissez la difficulté :");
        System.out.println("1 - Facile");
        System.out.println("2 - Moyen");
        System.out.println("3 - Difficile");
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante

        Difficulte difficulte;
        switch (choix) {
            case 1 -> difficulte = Difficulte.FACILE;
            case 2 -> difficulte = Difficulte.MOYEN;
            case 3 -> difficulte = Difficulte.DIFFICILE;
            default -> {
                System.out.println("Choix invalide, sélection de la difficulté par défaut : Moyen");
                difficulte = Difficulte.MOYEN;
            }
        }

        // Génération de la grille
        System.out.println("\nGénération d'une grille " + size + "x" + size + " en cours...");
        GenerateFromComputer<E> generator = new GenerateFromComputer<>(difficulte);
        userGrid = new ClassicSudokuGrid<>(size, possibleValues);
        generator.GeneratePuzzle(userGrid, difficulte);

        System.out.println("Grille générée !");
        userGrid.displayGrid();
    }

    /**
     * Permet à l'utilisateur de choisir un solveur parmi les options disponibles.
     * Retourne le solveur choisi par l'utilisateur.
     *
     * @return Le solveur sélectionné.
     */
    public SolverStrategy<E> chooseSolverFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choisissez un solveur pour résoudre la grille :");
        System.out.println("1 - Solveur basé sur les règles");
        System.out.println("2 - Solveur par Backtracking");
        System.out.println("3 - Solveur Hybride (Règles + Backtracking)");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante

        switch (choix) {
            case 1:
                System.out.println("Solveur basé sur les règles sélectionné.");
                return new RuleBasedSolver<>(new Logger());
            case 2:
                System.out.println("Solveur Backtracking sélectionné.");
                return new BackTrackingSolver<>(new Logger());
            case 3:
                System.out.println("Solveur Hybride sélectionné.");
                // return new HybridSolver<>(new Logger());
            default:
                System.out.println("Choix invalide. Solveur Backtracking par défaut.");
                return new BackTrackingSolver<>(new Logger());
        }
    }

    /**
     * Résout la grille de Sudoku en utilisant le solveur choisi et affiche le résultat.
     */
    public void solveGrid() {
        if (userGrid == null) {
            System.out.println("Veuillez d'abord charger une grille !");
            return;
        }
        if (solver == null) {
            System.out.println("Veuillez choisir un solveur avant de résoudre !");
            return;
        }

        System.out.println("Résolution en cours...");
        boolean solved = solver.solve(userGrid, true);

        if (solved) {
            System.out.println("La grille a été résolue avec succès !");
        } else {
            System.out.println("La grille ne peut pas être résolue !");
        }
        userGrid.displayGrid();
    }
}
