package src.mypackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Représente une règle de résolution de Sudoku appelée "Unicité Forcée".
 * Cette règle applique la logique de l'unicité forcée dans chaque région du Sudoku.
 * Si une valeur peut être insérée dans une cellule en raison de son unicité dans une région,
 * elle est placée automatiquement.
 *
 * @param <E> Le type des éléments contenus dans la grille (par exemple, Integer pour un Sudoku classique).
 */
public class UniciteForcee<E> extends Rule<E> {

    /**
     * Constructeur de la règle Unicité Forcée.
     *
     * @param logger Le logger à utiliser pour enregistrer les actions effectuées par cette règle.
     */
    public UniciteForcee(Logger logger) {
        super(logger);
    }

    /**
     * Applique la règle d'unicité forcée sur la grille.
     * La règle consiste à analyser chaque région du Sudoku et à déterminer si une valeur
     * peut être insérée de manière forcée dans une cellule en raison de son unicité dans cette région.
     *
     * @param grid La grille de Sudoku à résoudre.
     * @return true si la règle a modifié la grille, false sinon.
     */
    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;
        logger.log("Application de l'Unicité Forcée...");

        modified |= uniciteForceeRegion(grid);

        logger.log("Fin de l'Unicité Forcée.");
        return modified;
    }

    /**
     * Applique la règle d'unicité forcée à chaque région de la grille.
     * Pour chaque cellule vide, la méthode vérifie si une seule valeur possible
     * reste pour cette cellule, ce qui entraînerait un placement forcé de cette valeur.
     *
     * @param grid La grille de Sudoku à résoudre.
     * @return true si la règle a modifié la grille, false sinon.
     */
    private boolean uniciteForceeRegion(Grid<E> grid) {
        boolean modified = false;

        // Parcours de toutes les régions de la grille
        for (Region<E> region : grid.getRegions()) {
            Map<E, Cell<E>> uniquePositions = new HashMap<>();
            Set<E> alreadyPlaced = new HashSet<>();

            // Ajout des valeurs déjà placées dans la région
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null) {
                    alreadyPlaced.add(cell.getValue());
                }
            }

            // Recherche de valeurs uniques possibles à insérer dans les cellules vides
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    for (E value : cell.getPossibleValues()) {
                        if (!alreadyPlaced.contains(value)) {
                            if (!uniquePositions.containsKey(value)) {
                                uniquePositions.put(value, cell);
                            } else {
                                uniquePositions.put(value, null); // Invalidation de la valeur si elle est déjà présente
                            }
                        }
                    }
                }
            }

            // Placement forcé des valeurs uniques dans les cellules
            for (Map.Entry<E, Cell<E>> entry : uniquePositions.entrySet()) {
                E value = entry.getKey();
                Cell<E> cell = entry.getValue();
                if (cell != null) {
                    cell.setValue(value);
                    cell.getPossibleValues().clear(); // Efface les valeurs possibles après avoir placé la valeur
                    modified = true;
                    logger.log("Placement forcé de " + value + " en (" + cell.getY() + ", " + cell.getX() + ") [Unicité]");
                }
            }
        }

        return modified;
    }
}
