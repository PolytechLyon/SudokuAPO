package src.mypackage;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente une grille classique de Sudoku.
 * Cette classe gère la création, la validation et l'affichage d'une grille de Sudoku.
 * Elle hérite de la classe {@link Grid} et permet d'appliquer les règles du Sudoku classique
 * (vérification de la validité des valeurs dans les lignes, colonnes et régions).
 *
 * @param <E> Le type des valeurs dans la grille.
 */
public class ClassicSudokuGrid<E> extends Grid<E> {

    /**
     * Constructeur pour initialiser une grille de Sudoku de taille donnée et avec un ensemble de valeurs possibles.
     *
     * @param size La taille de la grille (par exemple 9 pour un Sudoku classique 9x9).
     * @param possibleValues L'ensemble des valeurs possibles pour les cellules de la grille.
     */
    public ClassicSudokuGrid(int size, Set<E> possibleValues) {
        super(size);
        this.cells = new Cell[size][size];

        // Initialisation des cellules avec la liste des valeurs possibles
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new Cell<>(null, y, x, new HashSet<>(possibleValues)); // Copie des valeurs possibles
            }
        }

        // Initialisation des régions (Ex: blocs 3x3 pour Sudoku 9x9)
        initializeRegions();
    }

    /**
     * Initialise les régions de la grille.
     * Pour un Sudoku classique 9x9, cela initialise les régions 3x3.
     */
    private void initializeRegions() {
        int regionSize = (int) Math.sqrt(size); // Ex: 3 pour un Sudoku 9x9
        for (int i = 0; i < size; i++) {
            regions.add(new Region<>(i));
        }

        // Affectation des cellules aux régions correspondantes
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int regionIndex = (y / regionSize) * regionSize + (x / regionSize); // Trouver la région correcte
                regions.get(regionIndex).addCell(cells[y][x]); // Ajouter la cellule à la région
            }
        }
    }

    /**
     * Vérifie si une valeur donnée est valide dans une cellule spécifiée par ses coordonnées.
     *
     * @param y La coordonnée y de la cellule.
     * @param x La coordonnée x de la cellule.
     * @param value La valeur à vérifier.
     * @return true si la valeur est valide, false sinon.
     */
    @Override
    public boolean isValid(int y, int x, E value) {

        // Vérifier si la valeur est déjà présente dans la même ligne
        if (getValuesForRow(y).contains(value)) {
            return false;
        }

        // Vérifier si la valeur est déjà présente dans la même colonne
        if (getValuesForColumn(x).contains(value)) {
            return false;
        }

        // Vérifier si la valeur existe déjà dans la même région
        for (Region<E> region : regions) {
            if (region.containsCell(y, x)) { // Trouver la région contenant la cellule
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getValue() != null && cell.getValue().equals(value)) {
                        return false;
                    }
                }
            }
        }

        return true; // Valeur valide
    }

    /**
     * Retourne la valeur actuelle d'une cellule à une position spécifique.
     *
     * @param y La coordonnée y de la cellule.
     * @param x La coordonnée x de la cellule.
     * @return La valeur de la cellule.
     */
    @Override
    public E getValue(int y, int x) {
        return cells[y][x].getValue();
    }

    /**
     * Définit une valeur dans une cellule donnée par ses coordonnées.
     * Si la valeur n'est pas valide, elle ne sera pas assignée.
     *
     * @param y La coordonnée y de la cellule.
     * @param x La coordonnée x de la cellule.
     * @param value La valeur à définir.
     */
    @Override
    public void setValue(int y, int x, E value) {
        if (!isValid(y, x, value)) {
            System.out.println("Valeur invalide : " + value + " coordonnées : " + y + ", " + x);
            return;
        }
        cells[y][x].setValue(value);
    }

    /**
     * Affiche la grille de Sudoku dans la console avec un format visuel lisible.
     * Utilise des séparateurs pour afficher les régions (par exemple, pour un Sudoku 9x9, les blocs 3x3).
     */
    public void displayGrid() {
        System.out.println("Sudoku - Taille " + size + "x" + size + ":");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Object value = getValue(row, col); // Récupère la valeur de la cellule
                System.out.print((value != null ? value : ".") + " "); // Si la valeur est null, affiche "."
                // Affichage de séparateurs si nécessaire (comme pour un Sudoku 3x3)
                if ((col + 1) % Math.sqrt(size) == 0 && col + 1 < size) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((row + 1) % Math.sqrt(size) == 0 && row + 1 < size) {
                System.out.println("-".repeat(size * 2));
            }
        }
    }

    /**
     * Affiche les valeurs possibles de chaque cellule de la grille.
     * Pour chaque cellule, les coordonnées et l'ensemble des valeurs possibles sont affichés.
     */
    public void displayPossibleValues() {
        System.out.println("\nValeurs possibles après application des règles :\n");

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Cell<E> cell = getCell(y, x); // Récupère la cellule
                if (cell.getValue() == null) {
                    System.out.printf("(%d,%d) %s\t", y, x, cell.getPossibleValues());
                }
            }
            System.out.println();
        }
    }

}
