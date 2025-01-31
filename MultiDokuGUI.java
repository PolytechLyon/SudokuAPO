import javax.swing.*;
import java.awt.*;

class MultiDokuGUI<T> extends JFrame {
    public MultiDokuGrid<T> multidoku;

    public MultiDokuGUI(MultiDokuGrid<T> multidoku) {
        this.multidoku = multidoku;
        setTitle("Multidoku - Fusion des Blocs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new MergedSudokuPanel<>(multidoku));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class MergedSudokuPanel<T> extends JPanel {
    public MultiDokuGrid<T> multidoku;
    private static final int CELL_SIZE = 50;

    public MergedSudokuPanel(MultiDokuGrid<T> multidoku) {
        this.multidoku = multidoku;
        setPreferredSize(new Dimension(2 * 9 * CELL_SIZE, 9 * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int gridSize = multidoku.size;
        int sqrt = (int) Math.sqrt(gridSize);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int x1 = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                int x2 = (col + 10) * CELL_SIZE; // Décalage pour la 2ème grille

                // Vérifier si cette case appartient à un bloc fusionné
                boolean isShared = false;
                for (int[][] block : multidoku.sharedBlocks) {
                    for (int[] coord : block) {
                        if (coord[0] / gridSize == row && coord[0] % gridSize == col) {
                            isShared = true;
                            break;
                        }
                    }
                }

                // Définir la couleur de fond pour les blocs partagés
                if (isShared) {
                    g.setColor(new Color(255, 204, 102)); // Jaune pour blocs partagés
                    g.fillRect(x1, y, CELL_SIZE, CELL_SIZE);
                    g.fillRect(x2, y, CELL_SIZE, CELL_SIZE);
                }

                g.setColor(Color.BLACK);
                g.drawRect(x1, y, CELL_SIZE, CELL_SIZE);
                g.drawRect(x2, y, CELL_SIZE, CELL_SIZE);

                T value1 = multidoku.grids.get(0).getValue(row, col);
                T value2 = multidoku.grids.get(1).getValue(row, col);

                if (value1 != null) {
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.drawString(value1.toString(), x1 + CELL_SIZE / 3, y + 2 * CELL_SIZE / 3);
                }
                if (value2 != null) {
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.drawString(value2.toString(), x2 + CELL_SIZE / 3, y + 2 * CELL_SIZE / 3);
                }
            }
        }
    }
}
