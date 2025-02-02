package src.mypackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une région d'une grille de Sudoku.
 * Une région est constituée de cellules et elle peut être utilisée pour
 * gérer des sections spécifiques de la grille (par exemple, des lignes,
 * des colonnes ou des sous-grilles).
 *
 * @param <E> Le type des éléments contenus dans la grille (par exemple, Integer pour un Sudoku classique).
 */
public class Region<E> {
    private int id;
    private List<Cell<E>> cells; // Liste de cellules génériques

    /**
     * Constructeur pour initialiser une région avec un identifiant.
     *
     * @param id L'identifiant de la région.
     */
    public Region(int id) {
        this.id = id;
        this.cells = new ArrayList<>();
    }

    /**
     * Ajoute une cellule à la région.
     *
     * @param cell La cellule à ajouter à la région.
     */
    public void addCell(Cell<E> cell) {
        this.cells.add(cell);
    }

    /**
     * Obtient toutes les cellules de la région.
     *
     * @return La liste des cellules de la région.
     */
    public List<Cell<E>> getCells() {
        return cells;
    }

    /**
     * Vérifie si une cellule appartient à cette région, en fonction de ses coordonnées.
     *
     * @param y La ligne de la cellule.
     * @param x La colonne de la cellule.
     * @return true si la cellule appartient à la région, false sinon.
     */
    public boolean containsCell(int y, int x) {
        for (Cell<E> cell : cells) {
            if (cell.getY() == y && cell.getX() == x) {
                return true;
            }
        }
        return false;
    }
}
