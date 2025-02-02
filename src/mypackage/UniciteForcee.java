package src.mypackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniciteForcee<E> extends Rule<E> {
    public UniciteForcee(Logger logger) {
        super(logger);
    }

    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;
        logger.log("🚀 Application de l'Unicité Forcée...");

        modified |= uniciteForceeRegion(grid);

        logger.log("✅ Fin de l'Unicité Forcée.");
        return modified;
    }

    private boolean uniciteForceeRegion(Grid<E> grid) {
        boolean modified = false;
        for (Region<E> region : grid.getRegions()) {
            Map<E, Cell<E>> uniquePositions = new HashMap<>();
            Set<E> alreadyPlaced = new HashSet<>();

            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null) {
                    alreadyPlaced.add(cell.getValue());
                }
            }

            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    for (E value : cell.getPossibleValues()) {
                        if (!alreadyPlaced.contains(value)) {
                            if (!uniquePositions.containsKey(value)) {
                                uniquePositions.put(value, cell);
                            } else {
                                uniquePositions.put(value, null);
                            }
                        }
                    }
                }
            }

            for (Map.Entry<E, Cell<E>> entry : uniquePositions.entrySet()) {
                E value = entry.getKey();
                Cell<E> cell = entry.getValue();
                if (cell != null) {
                    cell.setValue(value);
                    cell.getPossibleValues().clear();
                    modified = true;
                    logger.log("✅ Placement forcé de " + value + " en (" + cell.getY() + ", " + cell.getX() + ") [Unicité]");
                }
            }
        }
        return modified;
    }
}
