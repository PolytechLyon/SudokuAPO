class ClassicSudokuGrid<T> extends SudokuGrid<T> {
    public ClassicSudokuGrid() {
        super(9); // Sudoku 9x9
    }

    @Override
    public boolean isValid(int row, int col, T value) {
        // Vérification de la ligne
        for (int i = 0; i < size; i++) {
            if (grid[row][i] != null && grid[row][i].equals(value)) {
                return false;
            }
        }

        // Vérification de la colonne
        for (int i = 0; i < size; i++) {
            if (grid[i][col] != null && grid[i][col].equals(value)) {
                return false;
            }
        }

        // Vérification du bloc
        int sqrt = (int) Math.sqrt(size);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int i = boxRowStart; i < boxRowStart + sqrt; i++) {
            for (int j = boxColStart; j < boxColStart + sqrt; j++) {
                if (grid[i][j] != null && grid[i][j].equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }
}
