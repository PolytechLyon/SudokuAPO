package src.mypackage;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente la règle d'exclusion directe pour la résolution d'un Sudoku.
 * Cette règle consiste à exclure les valeurs possibles dans les cellules en fonction des valeurs déjà présentes
 * dans la même région, ligne ou colonne. Elle applique l'exclusion de manière itérative sur la grille.
 *
 * @param <E> Le type des valeurs dans la grille.
 */
public class ExclusionDirecte<E> extends Rule<E> {

    /**
     * Constructeur de la règle d'Exclusion Directe.
     *
     * @param logger Le logger utilisé pour enregistrer les étapes de l'application de la règle.
     */
    public ExclusionDirecte(Logger logger) {
        super(logger); // Hérite du logger
    }

    /**
     * Applique la règle d'Exclusion Directe sur la grille.
     * Cela consiste à supprimer les valeurs possibles des cellules qui sont déjà présentes dans la même région, ligne ou colonne.
     *
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return true si la grille a été modifiée, false sinon.
     */
    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;
        logger.log("Application de l'Exclusion Directe...");

        modified |= exclusionRegion(grid);
        modified |= exclusionLigne(grid);
        modified |= exclusionColonne(grid);

        logger.log("Fin de l'Exclusion Directe.");
        return modified;
    }

    /**
     * Applique l'exclusion dans chaque région de la grille.
     * Les valeurs déjà présentes dans une région sont supprimées des valeurs possibles des autres cellules vides de la même région.
     *
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return true si des modifications ont été effectuées, false sinon.
     */
    public boolean exclusionRegion(Grid<E> grid) {
        boolean modified = false;
        for (Region<E> region : grid.getRegions()) {
            Set<E> usedValues = new HashSet<>();
            // Collecte des valeurs utilisées dans la région
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null) {
                    usedValues.add(cell.getValue());
                }
            }
            // Suppression des valeurs utilisées des cellules vides dans la région
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    for (E value : usedValues) {
                        if (cell.getPossibleValues().contains(value)) {
                            cell.removePossibleValue(value);
                            modified = true;
                            logger.log("Suppression de " + value + " en (" + cell.getY() + ", " + cell.getX() + ") [Région]");
                        }
                    }
                }
            }
        }
        return modified;
    }

    /**
     * Applique l'exclusion dans chaque ligne de la grille.
     * Les valeurs déjà présentes dans une ligne sont supprimées des valeurs possibles des autres cellules vides de la même ligne.
     *
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return true si des modifications ont été effectuées, false sinon.
     */
    public boolean exclusionLigne(Grid<E> grid) {
        boolean modified = false;
        for (int y = 0; y < grid.getSize(); y++) {
            Set<E> usedValues = grid.getValuesForRow(y); // Récupère les valeurs utilisées dans la ligne
            // Suppression des valeurs utilisées des cellules vides dans la ligne
            for (Region<E> region : grid.getRegions()) {
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getY() == y && cell.getValue() == null) {
                        for (E value : usedValues) {
                            if (cell.getPossibleValues().contains(value)) {
                                cell.removePossibleValue(value);
                                modified = true;
                                logger.log("Suppression de " + value + " en (" + y + ", " + cell.getX() + ") [Ligne]");
                            }
                        }
                    }
                }
            }
        }
        return modified;
    }

    /**
     * Applique l'exclusion dans chaque colonne de la grille.
     * Les valeurs déjà présentes dans une colonne sont supprimées des valeurs possibles des autres cellules vides de la même colonne.
     *
     * @param grid La grille de Sudoku sur laquelle appliquer la règle.
     * @return true si des modifications ont été effectuées, false sinon.
     */
    public boolean exclusionColonne(Grid<E> grid) {
        boolean modified = false;
        for (int x = 0; x < grid.getSize(); x++) {
            Set<E> usedValues = grid.getValuesForColumn(x); // Récupère les valeurs utilisées dans la colonne
            // Suppression des valeurs utilisées des cellules vides dans la colonne
            for (Region<E> region : grid.getRegions()) {
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getX() == x && cell.getValue() == null) {
                        for (E value : usedValues) {
                            if (cell.getPossibleValues().contains(value)) {
                                cell.removePossibleValue(value);
                                modified = true;
                                logger.log("Suppression de " + value + " en (" + cell.getY() + ", " + x + ") [Colonne]");
                            }
                        }
                    }
                }
            }
        }
        return modified;
    }
}
