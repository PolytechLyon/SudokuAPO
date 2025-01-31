import java.util.*;

class MultiDokuGrid<T> extends SudokuGrid<T> {
    public List<ClassicSudokuGrid<T>> grids; // Liste des grilles
    public List<int[][]> sharedBlocks; // Liste des blocs partagés (chaque bloc = 3x3)

    public MultiDokuGrid(int size) {
        super(size);
        grids = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            grids.add(new ClassicSudokuGrid<>());
        }
        sharedBlocks = new ArrayList<>();
    }

    // Définit un bloc partagé entre les deux grilles
    public void addSharedBlock(int rowStart, int colStart) {
        int[][] block = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                block[i][j] = (rowStart + i) * size + (colStart + j);
            }
        }
        sharedBlocks.add(block);
    }

    @Override
    public boolean isValid(int row, int col, T value) {
        for (ClassicSudokuGrid<T> grid : grids) {
            if (!grid.isValid(row, col, value)) {
                return false;
            }
        }

        // Vérification des blocs partagés
        for (int[][] block : sharedBlocks) {
            Set<T> valuesInBlock = new HashSet<>();
            for (int[] coord : block) {
                int r = coord[0] / size, c = coord[0] % size;
                for (ClassicSudokuGrid<T> grid : grids) {
                    T cellValue = grid.getValue(r, c);
                    if (cellValue != null) {
                        valuesInBlock.add(cellValue);
                    }
                }
            }
            if (valuesInBlock.size() > 1) {
                return false;
            }
        }
        return true;
    }

    public void synchronizeSharedBlocks() {
        for (int[][] block : sharedBlocks) {
            for (int[] coord : block) {
                int r = coord[0] / size, c = coord[0] % size;
                T value = grids.get(0).getValue(r, c);
                grids.get(1).setValue(r, c, value);
            }
        }
    }

    @Override
    public void display() {
        System.out.println("Multidoku (Fusion des Blocs) :");
        displayMergedGrid();
    }

    private void displayMergedGrid() {
        int sqrt = (int) Math.sqrt(size);
        String horizontalSeparator = "+".repeat(size * 4);

        for (int row = 0; row < size; row++) {
            if (row % sqrt == 0) System.out.println(horizontalSeparator);

            for (int col = 0; col < size; col++) {
                if (col % sqrt == 0) System.out.print("| ");
                T value1 = grids.get(0).getValue(row, col);
                System.out.print((value1 == null ? "." : value1) + " ");
            }

            System.out.print(" || "); // Séparateur des deux grilles fusionnées

            for (int col = 0; col < size; col++) {
                if (col % sqrt == 0) System.out.print("| ");
                T value2 = grids.get(1).getValue(row, col);
                System.out.print((value2 == null ? "." : value2) + " ");
            }
            System.out.println("|");
        }
        System.out.println(horizontalSeparator);
    }
}
