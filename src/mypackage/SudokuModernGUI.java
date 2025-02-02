package src.mypackage;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Classe principale représentant l'interface graphique moderne de Sudoku avec Java Swing.
 * Cette classe permet de résoudre des grilles de Sudoku, de les générer, de réinitialiser et de basculer entre les thèmes clair et foncé.
 */
public class SudokuModernGUI extends JFrame {

    private static final int GRID_SIZE = 9; // Sudoku classique 9x9
    private ClassicSudokuGrid<Character> sudokuGrid;
    private JTextField[][] cellFields;
    private JPanel mainPanel;    // Panneau principal
    private JPanel gridPanel;
    private JPanel buttonPanel;
    private JPanel controlPanel; // Pour le slider et la sélection du solveur
    private JPanel bottomPanel;  // Contient buttonPanel et controlPanel
    private JSlider sliderDifficulty;
    private JLabel sliderLabel;
    private JButton btnSolve, btnGenerate, btnReset, btnToggleTheme;
    private JComboBox<String> solverCombo;
    private boolean darkTheme = false;
    private Logger logger;

    // Ensemble des valeurs possibles (lettres de A à I)
    private Set<Character> possibleValues;

    /**
     * Constructeur de la classe SudokuModernGUI.
     * Initialise les composants et configure le Sudoku avec des valeurs possibles allant de 'A' à 'I'.
     */
    public SudokuModernGUI() {
        super("Sudoku Modern GUI");
        initializePossibleValues();
        // Création d'une grille classique avec les valeurs possibles
        sudokuGrid = new ClassicSudokuGrid<>(GRID_SIZE, possibleValues);
        logger = new Logger();
        initComponents();
        updateUIFromGrid();
    }

    /**
     * Initialise les valeurs possibles du Sudoku (lettres de 'A' à 'I').
     */
    private void initializePossibleValues() {
        possibleValues = new HashSet<>();
        for (char c = 'A'; c <= 'I'; c++) {
            possibleValues.add(c);
        }
    }

    /**
     * Initialise tous les composants de l'interface graphique.
     * Configure les boutons, le panneau de la grille, le slider, et les actions associées.
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panneau principal
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(darkTheme ? new Color(45, 45, 45) : Color.WHITE);

        // Panneau de la grille
        gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanel.setBackground(mainPanel.getBackground());
        cellFields = new JTextField[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(new Font("SansSerif", Font.BOLD, 24));
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == GRID_SIZE - 1) ? 3 : 1;
                int right = (j == GRID_SIZE - 1) ? 3 : 1;
                tf.setBorder(new MatteBorder(top, left, bottom, right, Color.DARK_GRAY));
                cellFields[i][j] = tf;
                gridPanel.add(tf);
            }
        }
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Panneau pour les boutons et la sélection du solveur
        buttonPanel = new JPanel();
        buttonPanel.setBackground(mainPanel.getBackground());
        btnSolve = new JButton("Solve");
        btnGenerate = new JButton("Generate");
        btnReset = new JButton("Reset");
        btnToggleTheme = new JButton("Dark Theme");

        // Combobox pour choisir la méthode de résolution
        String[] solverOptions = { "BackTrackingSolver", "RuleBasedSolver" };
        solverCombo = new JComboBox<>(solverOptions);
        solverCombo.setFont(new Font("SansSerif", Font.BOLD, 16));
        // Application d'un renderer moderne pour le JComboBox
        solverCombo.setRenderer(new ModernComboBoxRenderer());
        solverCombo.setOpaque(false);
        solverCombo.setBackground(mainPanel.getBackground());

        styleButton(btnSolve, new Color(52, 152, 219));
        styleButton(btnGenerate, new Color(46, 204, 113));
        styleButton(btnReset, new Color(231, 76, 60));
        styleButton(btnToggleTheme, new Color(128, 128, 128));

        buttonPanel.add(btnSolve);
        buttonPanel.add(btnGenerate);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnToggleTheme);
        buttonPanel.add(new JLabel("Solver:"));
        buttonPanel.add(solverCombo);

        // Actions des boutons
        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) solverCombo.getSelectedItem();
                SolverStrategy<Character> solver;
                if ("RuleBasedSolver".equals(selected)) {
                    solver = new RuleBasedSolver<>(logger);
                } else { // Par défaut, BackTrackingSolver
                    solver = new BackTrackingSolver<>(logger);
                }
                boolean solved = solver.solve(sudokuGrid, true);
                if (solved) {
                    updateUIFromGrid();
                    JOptionPane.showMessageDialog(SudokuModernGUI.this, "Sudoku solved!");
                } else {
                    JOptionPane.showMessageDialog(SudokuModernGUI.this, "No solution found!");
                }
            }
        });

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = sliderDifficulty.getValue(); // 1: EASY, 2: MEDIUM, 3: HARD
                // Pour générer, on résout d'abord la grille complète via BackTrackingSolver
                BackTrackingSolver<Character> btSolver = new BackTrackingSolver<>(logger);
                btSolver.solve(sudokuGrid, false);
                // Déterminer le nombre de cellules à retirer selon le niveau de difficulté
                int cellsToRemove = (level == 1) ? 30 : (level == 2) ? 40 : 50;
                removeCells(cellsToRemove);
                updateUIFromGrid();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuGrid = new ClassicSudokuGrid<>(GRID_SIZE, possibleValues);
                updateUIFromGrid();
            }
        });

        btnToggleTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darkTheme = !darkTheme;
                btnToggleTheme.setText(darkTheme ? "Light Theme" : "Dark Theme");
                updateTheme();
            }
        });

        // Panneau de contrôle pour le slider de difficulté
        controlPanel = new JPanel();
        controlPanel.setBackground(mainPanel.getBackground());
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        sliderLabel = new JLabel("Difficulty (1: EASY, 2: MEDIUM, 3: HARD):");
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        // Limiter le slider aux valeurs 1, 2 et 3
        sliderDifficulty = new JSlider(JSlider.HORIZONTAL, 1, 3, 2);
        sliderDifficulty.setMajorTickSpacing(1);
        sliderDifficulty.setMinorTickSpacing(1);
        sliderDifficulty.setSnapToTicks(true);
        sliderDifficulty.setPaintTicks(true);
        sliderDifficulty.setPaintLabels(true);
        sliderDifficulty.setPreferredSize(new Dimension(400, 80));
        sliderDifficulty.setBorder(null);
        sliderDifficulty.setOpaque(false);
        sliderDifficulty.setUI(new SimpleSliderUI(sliderDifficulty));
        controlPanel.add(sliderLabel);
        controlPanel.add(sliderDifficulty);

        // Regrouper boutons et contrôle dans un panneau bas
        bottomPanel = new JPanel();
        bottomPanel.setBackground(mainPanel.getBackground());
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(controlPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        updateTheme();
        setVisible(true);
    }

    /**
     * Applique un style commun aux boutons.
     * @param btn Le bouton à styliser.
     * @param bgColor La couleur de fond du bouton.
     */
    // Renderer personnalisé pour le JComboBox
    private class ModernComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
        public ModernComboBoxRenderer() {
            setOpaque(true);
            setFont(new Font("SansSerif", Font.BOLD, 16));
            setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(value);
            if (isSelected) {
                setBackground(new Color(52, 152, 219));
                setForeground(Color.WHITE);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }

    // Applique un style commun aux boutons
    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    /**
     * Met à jour l'affichage de la grille dans l'interface utilisateur.
     * Les valeurs des cellules sont extraites du modèle de données (sudokuGrid).
     */
    private void updateUIFromGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Character value = sudokuGrid.getValue(i, j);
                cellFields[i][j].setText(value == null ? "" : value.toString());
            }
        }
    }

    /**
     * Met à jour le thème de l'interface (clair/foncé).
     */
    private void updateTheme() {
        Color bg = darkTheme ? new Color(45, 45, 45) : Color.WHITE;
        Color fg = darkTheme ? Color.WHITE : Color.BLACK;
        getContentPane().setBackground(bg);
        mainPanel.setBackground(bg);
        gridPanel.setBackground(bg);
        buttonPanel.setBackground(bg);
        controlPanel.setBackground(bg);
        bottomPanel.setBackground(bg);

        // Mettre à jour les cellules et leurs bordures pour le thème foncé
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cellFields[i][j].setBackground(darkTheme ? new Color(60, 63, 65) : Color.WHITE);
                cellFields[i][j].setForeground(fg);
                // Redéfinir la bordure : si thème foncé, utiliser du blanc pour les séparateurs
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == GRID_SIZE - 1) ? 3 : 1;
                int right = (j == GRID_SIZE - 1) ? 3 : 1;
                Color borderColor = darkTheme ? Color.WHITE : Color.DARK_GRAY;
                cellFields[i][j].setBorder(new MatteBorder(top, left, bottom, right, borderColor));
            }
        }

        sliderLabel.setForeground(fg);
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton || comp instanceof JLabel) {
                comp.setForeground(fg);
            }
        }
        sliderDifficulty.setBackground(bg);
        sliderDifficulty.setForeground(fg);
        sliderDifficulty.setUI(new SimpleSliderUI(sliderDifficulty));
        repaint();
    }

    /**
     * Retire un nombre défini de cellules de la grille de Sudoku (en mettant leur valeur à null).
     * @param count Le nombre de cellules à retirer.
     */
    private void removeCells(int count) {
        int total = GRID_SIZE * GRID_SIZE;
        int removed = 0;
        Random rand = new Random();
        while (removed < count) {
            int pos = rand.nextInt(total);
            int i = pos / GRID_SIZE;
            int j = pos % GRID_SIZE;
            if (sudokuGrid.getValue(i, j) != null) {
                sudokuGrid.setValue(i, j, null);
                removed++;
            }
        }
    }

    /**
     * Classe personnalisée pour l'UI du JSlider afin de changer son apparence.
     */
    private static class SimpleSliderUI extends BasicSliderUI {
        public SimpleSliderUI(JSlider slider) {
            super(slider);
        }
        @Override
        public void paintFocus(Graphics g) {
            // Ne rien dessiner pour supprimer le focus
        }
        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int trackHeight = 8;
            int trackY = trackRect.y + (trackRect.height - trackHeight) / 2;
            Color trackColor = slider.getBackground().darker();
            g2.setColor(trackColor);
            g2.fillRoundRect(trackRect.x, trackY, trackRect.width, trackHeight, 8, 8);
            g2.dispose();
        }
        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int thumbSize = 16;
            int x = thumbRect.x + (thumbRect.width - thumbSize) / 2;
            int y = thumbRect.y + (thumbRect.height - thumbSize) / 2;
            g2.setColor(slider.getForeground());
            g2.fillOval(x, y, thumbSize, thumbSize);
            g2.dispose();
        }
        @Override
        public void paintTicks(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(slider.getForeground());
            if (slider.getOrientation() == JSlider.HORIZONTAL) {
                for (int i = slider.getMinimum(); i <= slider.getMaximum(); i += slider.getMinorTickSpacing()) {
                    int xPos = xPositionForValue(i);
                    int yPos = trackRect.y + trackRect.height;
                    g2.drawLine(xPos, yPos, xPos, yPos + 4);
                }
            }
            g2.dispose();
        }
    }

    /**
     * Point d'entrée de l'application, lance l'interface graphique.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuModernGUI());
    }
}
