package src.mypackage;

import java.util.HashSet;
import java.util.Set;

public class ExclusionDirecte<E> extends Rule<E> {
    public ExclusionDirecte(Logger logger) {
        super(logger); // ✅ Hérite du logger
    }

    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;
        logger.log("🚀 Application de l'Exclusion Directe...");

        modified |= exclusionRegion(grid);
        modified |= exclusionLigne(grid);
        modified |= exclusionColonne(grid);

        logger.log("✅ Fin de l'Exclusion Directe.");
        return modified;
    }

    private boolean exclusionRegion(Grid<E> grid) {
        boolean modified = false;
        for (Region<E> region : grid.getRegions()) {
            Set<E> usedValues = new HashSet<>();
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null) {
                    usedValues.add(cell.getValue());
                }
            }
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    for (E value : usedValues) {
                        if (cell.getPossibleValues().contains(value)) {
                            cell.removePossibleValue(value);
                            modified = true;
                            logger.log("🗑️ Suppression de " + value + " en (" + cell.getY() + ", " + cell.getX() + ") [Région]");
                        }
                    }
                }
            }
        }
        return modified;
    }

    private boolean exclusionLigne(Grid<E> grid) {
        boolean modified = false;
        for (int y = 0; y < grid.getSize(); y++) {
            Set<E> usedValues = grid.getValuesForRow(y);
            for (Region<E> region : grid.getRegions()) {
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getY() == y && cell.getValue() == null) {
                        for (E value : usedValues) {
                            if (cell.getPossibleValues().contains(value)) {
                                cell.removePossibleValue(value);
                                modified = true;
                                logger.log("🗑️ Suppression de " + value + " en (" + y + ", " + cell.getX() + ") [Ligne]");
                            }
                        }
                    }
                }
            }
        }
        return modified;
    }

    private boolean exclusionColonne(Grid<E> grid) {
        boolean modified = false;
        for (int x = 0; x < grid.getSize(); x++) {
            Set<E> usedValues = grid.getValuesForColumn(x);
            for (Region<E> region : grid.getRegions()) {
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getX() == x && cell.getValue() == null) {
                        for (E value : usedValues) {
                            if (cell.getPossibleValues().contains(value)) {
                                cell.removePossibleValue(value);
                                modified = true;
                                logger.log("🗑️ Suppression de " + value + " en (" + cell.getY() + ", " + x + ") [Colonne]");
                            }
                        }
                    }
                }
            }
        }
        return modified;
    }
}
