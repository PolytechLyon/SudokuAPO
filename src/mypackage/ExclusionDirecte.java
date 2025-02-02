package src.mypackage;

import java.util.HashSet;
import java.util.Set;

public class ExclusionDirecte<E> extends Rule<E> {
    public ExclusionDirecte() {
        super();
    }

    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;

        // 1️⃣ Appliquer l'exclusion par région ✅
        modified |= exclusionRegion(grid);

        // ✅ Appliquer l'exclusion par ligne
        modified |= exclusionLigne(grid);

        // ✅ Appliquer l'exclusion par colonne
        modified |= exclusionColonne(grid);

        return modified;
    }

    /**
     * 🔥 Exclusion directe dans chaque région
     * Si une valeur est déjà attribuée dans une région, elle est supprimée des valeurs possibles des autres cellules de cette région.
     */
    private boolean exclusionRegion(Grid<E> grid) {
        boolean modified = false;

        for (Region<E> region : grid.getRegions()) {
            Set<E> usedValues = new HashSet<>();

            // 1️⃣ Collecter les valeurs déjà placées dans la région
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() != null) {
                    usedValues.add(cell.getValue());
                }
            }

            // 2️⃣ Supprimer ces valeurs des cellules vides dans la région
            for (Cell<E> cell : region.getCells()) {
                if (cell.getValue() == null) {
                    for (E value : usedValues) {
                        if (cell.getPossibleValues().contains(value)) {
                            cell.removePossibleValue(value);
                            modified = true;
                        }
                    }
                }
            }
        }

        return modified;
    }

    /**
     * 🚧 Exclusion directe par ligne (NON implémentée)
     */
    private boolean exclusionLigne(Grid<E> grid) {
        boolean modified = false;

        for (int y = 0; y < grid.getSize(); y++) {  // Parcours chaque ligne
            Set<E> usedValues = grid.getValuesForRow(y); // 🔥 Récupérer les valeurs déjà placées dans la ligne

            for (Region<E> region : grid.getRegions()) { // 🔥 Parcours des régions pour accéder aux cellules
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getY() == y && cell.getValue() == null) {  // 🔥 Cellule vide dans la ligne
                        for (E value : usedValues) {
                            if (cell.getPossibleValues().contains(value)) {
                                cell.removePossibleValue(value);
                                modified = true;
                            }
                        }
                    }
                }
            }
        }

        return modified;
    }

    /**
     * 🚧 Exclusion directe par colonne (NON implémentée)
     */
    private boolean exclusionColonne(Grid<E> grid) {
        boolean modified = false;

        for (int x = 0; x < grid.getSize(); x++) {  // 🔥 Parcours chaque colonne
            Set<E> usedValues = grid.getValuesForColumn(x); // 🔥 Récupérer les valeurs déjà placées dans la colonne

            for (Region<E> region : grid.getRegions()) { // 🔥 Parcours des régions pour accéder aux cellules
                for (Cell<E> cell : region.getCells()) {
                    if (cell.getX() == x && cell.getValue() == null) {  // 🔥 Cellule vide dans la colonne
                        for (E value : usedValues) {
                            if (cell.getPossibleValues().contains(value)) {
                                cell.removePossibleValue(value);
                                modified = true;
                            }
                        }
                    }
                }
            }
        }

        return modified;
    }
}
