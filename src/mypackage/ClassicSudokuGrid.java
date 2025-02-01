package src.mypackage;

import java.util.HashSet;
import java.util.Set;

public class ClassicSudokuGrid extends Grid {

    // Constructeur
    public ClassicSudokuGrid() {
        super(9); // Un Sudoku classique a une grille de 9x9
        initializeRegions();
    }

    // Initialisation des régions (chaque bloc 3x3 est une région)
    private void initializeRegions() {
        for (int i = 0; i < 9; i++) {
            regions.add(new Region("Bloc " + i));
        }

        // Ajouter les cellules aux régions correspondantes
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int regionIndex = (y / 3) * 3 + (x / 3);
                Cell<Integer> cell = new Cell<>(null, x, y); // x = colonne, y = ligne
                regions.get(regionIndex).addCell(cell);
            }
        }
    }

    // Vérifier si une valeur est valide selon les règles du Sudoku classique
    @Override
    public <E> boolean isValid(int y, int x, E value) {
        if (!(value instanceof Integer)) {
            return false; // Sudoku classique utilise des nombres entiers
        }
        int num = (Integer) value;

        // Vérifier la ligne (y fixe, on parcourt x)
        for (int i = 0; i < 9; i++) {
            if (getValue(y, i) != null && getValue(y, i).equals(num)) return false;
        }

        // Vérifier la colonne (x fixe, on parcourt y)
        for (int i = 0; i < 9; i++) {
            if (getValue(i, x) != null && getValue(i, x).equals(num)) return false;
        }

        // Vérifier le bloc 3x3
        int startY = (y / 3) * 3;
        int startX = (x / 3) * 3;
        for (int i = startY; i < startY + 3; i++) {
            for (int j = startX; j < startX + 3; j++) {
                if (getValue(i, j) != null && getValue(i, j).equals(num)) return false;
            }
        }

        return true;
    }

    // Obtenir la valeur d'une cellule
    @Override
    public <E> E getValue(int y, int x) {
        for (Region region : regions) {
            for (Cell<?> cell : region.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return (E) cell.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public <E> void setValue(int row, int col, E value) {
        if (!(value instanceof Integer)) {
            System.out.println("❌ Erreur : Valeur non entière !");
            return;
        }

        if (isValid(row, col, value)) {
            for (Region region : regions) {
                for (Cell<?> cell : region.getCells()) {
                    if (cell.getX() == col && cell.getY() == row) {
                        @SuppressWarnings("unchecked")
                        Cell<Integer> typedCell = (Cell<Integer>) cell;
                        typedCell.setValue((Integer) value);
                        return;
                    }
                }
            }
        } else {
            System.out.println("❌ Impossible d'ajouter " + value + " en (" + row + ", " + col + ")");
        }
    }

}
