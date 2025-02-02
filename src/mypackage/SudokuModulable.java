package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Représente une grille de Sudoku modulable, permettant de définir dynamiquement la taille de la grille
 * et les valeurs possibles. Cette classe gère l'initialisation des cellules et leur validation,
 * en prenant en compte les contraintes de ligne, colonne et région.
 *
 * @param <E> Le type des éléments contenus dans la grille (par exemple, Integer pour un Sudoku classique).
 */
public class SudokuModulable<E> extends Grid<E> {
    private Cell<E>[][] cells; // Grille des cellules

    /**
     * Constructeur de la classe SudokuModulable.
     * Initialise la grille de Sudoku avec la taille et les valeurs possibles spécifiées.
     *
     * @param size La taille de la grille de Sudoku (par exemple, 9 pour une grille 9x9).
     * @param possibleValues Les valeurs possibles à insérer dans la grille (par exemple, 1-9 pour un Sudoku classique).
     */
    public SudokuModulable(int size, Set<E> possibleValues) {
        super(size);
        this.cells = new Cell[size][size];

        // Initialisation des cellules avec les valeurs possibles
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new Cell<>(null, y, x, new HashSet<>(possibleValues)); // Copie des valeurs possibles
            }
        }

        // Initialisation des régions (Ex: blocs 3x3 pour Sudoku 9x9)
        initializeRegions();
    }

    /**
     * Initialise les régions (par exemple, les sous-grilles 3x3 pour un Sudoku 9x9).
     * Chaque cellule est ajoutée à la région correspondante en fonction de sa position.
     */
    private void initializeRegions() {
        int regionSize = (int) Math.sqrt(size); // Ex: 3 pour un Sudoku 9x9
        for (int i = 0; i < size; i++) {
            regions.add(new Region<>(i));
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int regionIndex = (y / regionSize) * regionSize + (x / regionSize); // Trouver la région correcte
                regions.get(regionIndex).addCell(cells[y][x]); // Ajouter la cellule à la région
            }
        }
    }

    /**
     * Vérifie si une valeur donnée est valide à une position spécifique (ligne, colonne, et région).
     * Une valeur est considérée valide si elle n'existe pas déjà dans la même ligne, la même colonne
     * ou la même région.
     *
     * @param y La ligne de la cellule.
     * @param x La colonne de la cellule.
     * @param value La valeur à insérer dans la cellule.
     * @return true si la valeur est valide, false sinon.
     */
    @Override
    public boolean isValid(int y, int x, E value) {
        if (value == null) return false;

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
     * Récupère la valeur stockée dans une cellule spécifique à la position (y, x).
     *
     * @param y La ligne de la cellule.
     * @param x La colonne de la cellule.
     * @return La valeur de la cellule à la position (y, x), ou null si la cellule est vide.
     */
    @Override
    public E getValue(int y, int x) {
        return cells[y][x].getValue();
    }

    /**
     * Définit une valeur dans une cellule à la position (y, x), si la valeur est valide.
     * Si la valeur n'est pas valide, l'insertion est ignorée et un message d'erreur est affiché.
     *
     * @param y La ligne de la cellule.
     * @param x La colonne de la cellule.
     * @param value La valeur à insérer dans la cellule.
     */
    @Override
    public void setValue(int y, int x, E value) {
        if (!isValid(y, x, value)) {
            System.out.println("Valeur invalide : " + value + " coordonées : " + x + ", " + y);
            return;
        }
        cells[y][x].setValue(value);
    }

    /**
     * Affiche la grille de Sudoku dans la console, en utilisant des "." pour les cellules vides.
     * Les séparateurs sont ajoutés pour les sous-grilles (par exemple, les blocs 3x3 dans un Sudoku 9x9).
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
}
