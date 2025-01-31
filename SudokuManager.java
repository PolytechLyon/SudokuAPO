import java.util.*;

class SudokuManager<T> {
    private SudokuGrid<T> grid;
    private SudokuSolver<T> solver;
    private SudokuGenerator<T> generator;
    private List<T> possibleValues;

    public SudokuManager(SudokuGrid<T> grid, List<T> possibleValues) {
        this.grid = grid;
        this.solver = new SudokuSolver<>();
        this.generator = new SudokuGenerator<>();
        this.possibleValues = possibleValues;
    }

    public void run() {
        System.out.println("Grille générée :");
        generator.generate(grid, possibleValues, 20); // Génère 20 cellules aléatoires
        grid.display();

        System.out.println("Résolution du Sudoku...");
        if (solver.solve(grid, possibleValues)) {
            System.out.println("Solution trouvée :");
            grid.display();
        } else {
            System.out.println("Impossible de résoudre !");
        }
    }
}
