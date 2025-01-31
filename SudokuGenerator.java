import java.util.*;

class SudokuGenerator<T> {
    private Random random = new Random();

    public void generate(SudokuGrid<T> grid, List<T> possibleValues, int filledCells) {
        int count = 0;
        while (count < filledCells) {
            int row = random.nextInt(grid.size);
            int col = random.nextInt(grid.size);
            if (grid.getValue(row, col) == null) {
                T value = possibleValues.get(random.nextInt(possibleValues.size()));
                if (grid.isValid(row, col, value)) {
                    grid.setValue(row, col, value);
                    count++;
                }
            }
        }
    }
}
