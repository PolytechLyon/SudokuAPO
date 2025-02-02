package src.mypackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniciteForcee<E> extends Rule<E> {

    public UniciteForcee() {
        super();
    }

    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;

        // 🔥 Appliquer l'unicité forcée sur chaque région, ligne et colonne
        modified |= uniciteForceeRegion(grid);

        return modified;
    }

    /**
     * 🔥 Règle d'unicité forcée dans les régions
     * Si une valeur n'est possible qu'à un seul endroit d'une région, elle est placée directement.
     */
    private boolean uniciteForceeRegion(Grid<E> grid) {
        boolean modified = false;

        for (Region<E> region : grid.getRegions()) {
            Map<E, Cell<E>> uniquePositions = new HashMap<>();
            Set<E> alreadyPlaced = new HashSet<>();

            // 1️⃣ Collecter les valeurs déjà placées
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null) {
                    alreadyPlaced.add(cell.getValue());
                }
            }

            // 2️⃣ Trouver les valeurs uniques qui n'ont qu'une seule possibilité
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    for (E value : cell.getPossibleValues()) {
                        if (!alreadyPlaced.contains(value)) {
                            if (!uniquePositions.containsKey(value)) {
                                uniquePositions.put(value, cell);
                            } else {
                                uniquePositions.put(value, null); // ⚠️ Plus d'une possibilité -> Pas unique
                            }
                        }
                    }
                }
            }

            // 3️⃣ Affecter les valeurs uniques à leur cellule correspondante
            for (Map.Entry<E, Cell<E>> entry : uniquePositions.entrySet()) {
                E value = entry.getKey();
                Cell<E> cell = entry.getValue();
                if (cell != null) { // ✅ Valeur possible UNIQUEMENT dans cette cellule
                    cell.setValue(value);
                    cell.getPossibleValues().clear();
                    modified = true;
                }
            }
        }

        return modified;
    }

}