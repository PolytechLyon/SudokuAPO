package src.mypackage;

import src.mypackage.Region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe abstraite représentant une grille de Sudoku.
 * Cette classe gère les cellules et les régions de la grille, ainsi que les règles de validation des valeurs.
 * Elle est générique afin de pouvoir fonctionner avec différents types de valeurs pour les cellules.
 *
 * @param <E> Le type des valeurs contenues dans les cellules de la grille.
 */
public abstract class Grid<E> {

    /**
     * Taille de la grille (par exemple 9 pour une grille Sudoku 9x9).
     */
    protected int size;

    /**
     * Liste des régions (par exemple des blocs 3x3 dans un Sudoku classique).
     */
    protected List<Region<E>> regions;

    /**
     * Grille des cellules.
     * Chaque cellule contient une valeur et peut être manipulée pour résoudre le puzzle.
     */
    protected Cell<E>[][] cells;

    /**
     * Constructeur de la classe Grid.
     * Initialise la taille de la grille et la liste des régions.
     *
     * @param size Taille de la grille (par exemple 9 pour un Sudoku 9x9).
     */
    public Grid(int size) {
        this.size = size;
        this.regions = new ArrayList<>();
    }

    /**
     * Méthode abstraite permettant de valider une valeur dans une cellule donnée.
     * Chaque sous-classe devra implémenter cette méthode pour définir les règles de validation (par exemple, vérifier les lignes, colonnes et régions).
     *
     * @param y     La ligne de la cellule.
     * @param x     La colonne de la cellule.
     * @param value La valeur à vérifier.
     * @return {@code true} si la valeur est valide, {@code false} sinon.
     */
    public abstract boolean isValid(int y, int x, E value);

    /**
     * Méthode abstraite permettant de récupérer la valeur d'une cellule à une position donnée.
     *
     * @param y La ligne de la cellule.
     * @param x La colonne de la cellule.
     * @return La valeur de la cellule.
     */
    public abstract E getValue(int y, int x);

    /**
     * Méthode abstraite permettant d'assigner une valeur à une cellule à une position donnée.
     *
     * @param y     La ligne de la cellule.
     * @param x     La colonne de la cellule.
     * @param value La valeur à assigner.
     */
    public abstract void setValue(int y, int x, E value);

    /**
     * Retourne la liste des régions de la grille.
     *
     * @return Liste des régions.
     */
    public List<Region<E>> getRegions() {
        return regions;
    }

    /**
     * Ajoute une région à la grille.
     *
     * @param region La région à ajouter.
     */
    public void addRegion(Region<E> region) {
        this.regions.add(region);
    }

    /**
     * Retourne les valeurs déjà présentes dans une ligne spécifique.
     *
     * @param y La ligne pour laquelle récupérer les valeurs.
     * @return Un ensemble de valeurs présentes dans la ligne.
     */
    public Set<E> getValuesForRow(int y) {
        Set<E> rowValues = new HashSet<>();

        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getY() == y && cell.getValue() != null) {
                    rowValues.add(cell.getValue());
                }
            }
        }

        return rowValues;
    }

    /**
     * Retourne la taille de la grille.
     *
     * @return La taille de la grille.
     */
    public int getSize() {
        return size;
    }

    /**
     * Retourne les valeurs déjà présentes dans une colonne spécifique.
     *
     * @param x La colonne pour laquelle récupérer les valeurs.
     * @return Un ensemble de valeurs présentes dans la colonne.
     */
    public Set<E> getValuesForColumn(int x) {
        Set<E> colValues = new HashSet<>();

        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getX() == x && cell.getValue() != null) {
                    colValues.add(cell.getValue());
                }
            }
        }

        return colValues;
    }

    /**
     * Récupère la cellule à une position spécifique.
     *
     * @param y La ligne de la cellule.
     * @param x La colonne de la cellule.
     * @return La cellule à la position donnée.
     */
    public Cell<E> getCell(int y, int x) {
        return cells[y][x];
    }
}
