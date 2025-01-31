import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Random;
import modele.GrilleSudoku;
import solver.SolveurSudoku;
import generator.GenerateurSudoku;

public class SudokuGUIModern extends JFrame {
    private static final int TAILLE = 9; // Grille classique 9x9
    private JTextField[][] cells;
    private GrilleSudoku grille;
    private GrilleSudoku solution; // Contiendra la solution générée
    private SolveurSudoku solver;
    private GenerateurSudoku generator;
    private boolean darkTheme = false; // Thème clair par défaut

    // Panels pour la mise en page
    private JPanel mainPanel;
    private JPanel gridPanel;
    private JPanel buttonPanel;
    private JPanel controlPanel; // Panneau dédié au slider

    // Slider et label pour la difficulté
    private JSlider sliderDifficulty;
    private JLabel sliderLabel;

    public SudokuGUIModern() {
        super("Sudoku - Interface Graphique Moderne");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if ("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception e) {
            // En cas d'erreur, le look & feel par défaut sera utilisé.
        }

        // Initialisation du modèle et des outils
        grille = new GrilleSudoku();
        solver = new SolveurSudoku(TAILLE);
        generator = new GenerateurSudoku(TAILLE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(true);

        // --- Panneau de la grille (9x9) ---
        gridPanel = new JPanel(new GridLayout(TAILLE, TAILLE));
        gridPanel.setOpaque(true);
        cells = new JTextField[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 24));
                // Bordures : plus épaisses en début de bloc et sur les bords extérieurs
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == TAILLE - 1) ? 3 : 1;
                int right = (j == TAILLE - 1) ? 3 : 1;
                cell.setBorder(new MatteBorder(top, left, bottom, right, Color.DARK_GRAY));
                // Stocker la position dans le composant
                cell.putClientProperty("row", i);
                cell.putClientProperty("col", j);
                // Écouteur pour valider la saisie à la perte du focus
                cell.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        JTextField tf = (JTextField) e.getSource();
                        if (!tf.isEditable()) return; // Ignorer les cases non éditables
                        String text = tf.getText().trim();
                        int row = (int) tf.getClientProperty("row");
                        int col = (int) tf.getClientProperty("col");
                        if (text.isEmpty()) {
                            grille.setCase(row, col, 0);
                            return;
                        }
                        try {
                            int newValue = Integer.parseInt(text);
                            if (newValue < 1 || newValue > TAILLE) {
                                JOptionPane.showMessageDialog(SudokuGUIModern.this,
                                        "Veuillez entrer une valeur entre 1 et " + TAILLE,
                                        "Erreur", JOptionPane.ERROR_MESSAGE);
                                tf.setText("");
                                grille.setCase(row, col, 0);
                                return;
                            }
                            // Vérification par rapport à la solution préalablement résolue
                            if (solution != null && solution.getCase(row, col) != newValue) {
                                JOptionPane.showMessageDialog(SudokuGUIModern.this,
                                        "Mauvaise valeur. Cette case attend " + solution.getCase(row, col),
                                        "Erreur", JOptionPane.ERROR_MESSAGE);
                                tf.setText("");
                                grille.setCase(row, col, 0);
                                return;
                            }
                            // Validation par rapport aux contraintes du sudoku
                            grille.setCase(row, col, 0);
                            if (!grille.estValide(row, col, newValue)) {
                                JOptionPane.showMessageDialog(SudokuGUIModern.this,
                                        "Conflit détecté pour la valeur " + newValue,
                                        "Erreur", JOptionPane.ERROR_MESSAGE);
                                tf.setText("");
                                grille.setCase(row, col, 0);
                            } else {
                                grille.setCase(row, col, newValue);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(SudokuGUIModern.this,
                                    "Entrée invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            tf.setText("");
                            grille.setCase(row, col, 0);
                        }
                    }
                });
                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }
        gridPanel.setPreferredSize(new Dimension(600, 600));
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // --- Panneau pour les boutons ---
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(true);
        JButton btnSolve = new JButton("Résoudre");
        JButton btnGenerate = new JButton("Générer");
        JButton btnReset = new JButton("Réinitialiser");
        JButton btnToggleTheme = new JButton("Thème sombre");

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);
        btnSolve.setFont(buttonFont);
        btnGenerate.setFont(buttonFont);
        btnReset.setFont(buttonFont);
        btnToggleTheme.setFont(buttonFont);

        btnSolve.setBackground(new Color(52, 152, 219));
        btnGenerate.setBackground(new Color(46, 204, 113));
        btnReset.setBackground(new Color(231, 76, 60));
        btnToggleTheme.setBackground(new Color(128, 128, 128));
        btnSolve.setForeground(Color.WHITE);
        btnGenerate.setForeground(Color.WHITE);
        btnReset.setForeground(Color.WHITE);
        btnToggleTheme.setForeground(Color.WHITE);
        btnSolve.setFocusPainted(false);
        btnGenerate.setFocusPainted(false);
        btnReset.setFocusPainted(false);
        btnToggleTheme.setFocusPainted(false);

        btnSolve.addActionListener(e -> {
            lireGrilleDepuisUI();
            if (solver.resoudre(grille)) {
                mettreAJourUI();
                JOptionPane.showMessageDialog(SudokuGUIModern.this, "Sudoku résolu !");
            } else {
                JOptionPane.showMessageDialog(SudokuGUIModern.this, "Aucune solution trouvée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGenerate.addActionListener(e -> {
            int nbCasesARetirer = sliderDifficulty.getValue();
            GrilleSudoku complete = generator.genererGrilleComplete();
            solution = complete.cloner();
            grille = complete.cloner();
            int total = TAILLE * TAILLE;
            int removed = 0;
            Random rand = new Random();
            while (removed < nbCasesARetirer) {
                int pos = rand.nextInt(total);
                int row = pos / TAILLE;
                int col = pos % TAILLE;
                if (grille.getCase(row, col) != 0) {
                    grille.setCase(row, col, 0);
                    removed++;
                }
            }
            mettreAJourUI();
            // Marquer les cases indices comme non éditables
            for (int i = 0; i < TAILLE; i++) {
                for (int j = 0; j < TAILLE; j++) {
                    if (grille.getCase(i, j) != 0) {
                        cells[i][j].setEditable(false);
                        cells[i][j].setForeground(new Color(0, 128, 0));
                    } else {
                        cells[i][j].setEditable(true);
                        cells[i][j].setForeground(darkTheme ? Color.WHITE : Color.BLACK);
                    }
                }
            }
        });

        btnReset.addActionListener(e -> {
            grille = new GrilleSudoku();
            solution = null;
            mettreAJourUI();
            for (int i = 0; i < TAILLE; i++) {
                for (int j = 0; j < TAILLE; j++) {
                    cells[i][j].setEditable(true);
                    cells[i][j].setForeground(darkTheme ? Color.WHITE : Color.BLACK);
                }
            }
        });

        btnToggleTheme.addActionListener(e -> {
            darkTheme = !darkTheme;
            btnToggleTheme.setText(darkTheme ? "Thème clair" : "Thème sombre");
            updateTheme();
        });

        buttonPanel.add(btnSolve);
        buttonPanel.add(btnGenerate);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnToggleTheme);

        // --- Panneau dédié au slider ---
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(true);
        sliderLabel = new JLabel("Difficulté (cases à retirer) :");
        sliderLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderDifficulty = new JSlider(JSlider.HORIZONTAL, 20, 60, 40);
        // Forcer des incréments de 5 et activer le snapping
        sliderDifficulty.setMajorTickSpacing(5);
        sliderDifficulty.setMinorTickSpacing(5);
        sliderDifficulty.setSnapToTicks(true);
        sliderDifficulty.setPaintTicks(true);
        sliderDifficulty.setPaintLabels(true);
        sliderDifficulty.setPreferredSize(new Dimension(400, 80));
        sliderDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderDifficulty.setBorder(null);
        // ChangeListener pour forcer le snapping
        sliderDifficulty.addChangeListener(e -> {
            int value = sliderDifficulty.getValue();
            int snapped = Math.round(value / 5.0f) * 5;
            if (value != snapped) {
                sliderDifficulty.setValue(snapped);
            }
            sliderDifficulty.repaint();
        });
        // Rendre le slider non opaque pour que son fond suive celui du panneau
        sliderDifficulty.setOpaque(false);
        controlPanel.add(sliderLabel);
        controlPanel.add(sliderDifficulty);

        // Regrouper buttonPanel et controlPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(controlPanel);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        updateTheme();
    }

    private void updateTheme() {
        Color background, foreground, cellBackground, borderColor, panelBg, trackColor;
        if (darkTheme) {
            background = new Color(45, 45, 45);
            foreground = Color.WHITE;
            cellBackground = new Color(60, 63, 65);
            borderColor = Color.LIGHT_GRAY;
            panelBg = new Color(45, 45, 45);
            trackColor = new Color(80, 80, 80);
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
            cellBackground = Color.WHITE;
            borderColor = Color.DARK_GRAY;
            panelBg = Color.WHITE;
            trackColor = new Color(220, 220, 220);
        }
        getContentPane().setBackground(background);
        mainPanel.setBackground(panelBg);
        gridPanel.setBackground(panelBg);
        buttonPanel.setBackground(panelBg);
        controlPanel.setBackground(panelBg);

        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cells[i][j].setBackground(cellBackground);
                if (cells[i][j].isEditable()) {
                    cells[i][j].setForeground(foreground);
                }
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == TAILLE - 1) ? 3 : 1;
                int right = (j == TAILLE - 1) ? 3 : 1;
                cells[i][j].setBorder(new MatteBorder(top, left, bottom, right, borderColor));
            }
        }
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton || comp instanceof JLabel) {
                comp.setForeground(foreground);
            }
        }
        sliderLabel.setForeground(foreground);
        sliderDifficulty.setForeground(foreground);
        sliderDifficulty.setBackground(panelBg);
        Dictionary<?, ?> labelTable = sliderDifficulty.getLabelTable();
        if (labelTable != null) {
            for (Enumeration<?> e = labelTable.elements(); e.hasMoreElements();) {
                Object label = e.nextElement();
                if (label instanceof JLabel) {
                    ((JLabel) label).setForeground(foreground);
                }
            }
        }
        // Stocker la couleur de track dans une propriété client et appliquer la nouvelle UI simple
        sliderDifficulty.putClientProperty("trackColor", trackColor);
        sliderDifficulty.setUI(new SimpleSliderUI(sliderDifficulty));
        repaint();
    }

    private void lireGrilleDepuisUI() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (!cells[i][j].isEditable()) continue;
                String text = cells[i][j].getText().trim();
                int value = 0;
                if (!text.isEmpty()) {
                    try {
                        value = Integer.parseInt(text);
                    } catch (NumberFormatException ex) {
                        value = 0;
                    }
                }
                grille.setCase(i, j, value);
            }
        }
    }

    private void mettreAJourUI() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                int val = grille.getCase(i, j);
                cells[i][j].setText(val == 0 ? "" : String.valueOf(val));
            }
        }
    }

    // --- UI personnalisée simple pour le slider ---
    private static class SimpleSliderUI extends BasicSliderUI {
        public SimpleSliderUI(JSlider slider) {
            super(slider);
        }
        @Override
        public void paintFocus(Graphics g) {
            // Supprimer le contour en pointillés
        }
        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int trackHeight = 8;
            int trackY = trackRect.y + (trackRect.height - trackHeight) / 2;
            Color trackColor = (Color) slider.getClientProperty("trackColor");
            if (trackColor == null) {
                trackColor = slider.getBackground();
            }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuGUIModern());
    }
}
