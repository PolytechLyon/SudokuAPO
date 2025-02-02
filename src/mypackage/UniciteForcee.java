package src.mypackage;

public class UniciteForcee<E> extends Rule<E> {

    public UniciteForcee() {
        super();
    }

    @Override
    public boolean applyRule(Grid<E> grid) {
        boolean modified = false;

        // 🔥 Appliquer l'unicité forcée sur chaque cellule (régions, lignes et colonnes indirectement traitées)
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                Cell<E> cell = grid.getCell(y, x);

                // ✅ Vérifier si la cellule est vide et si elle n'a qu'une seule valeur possible
                if (cell.getValue() == null && cell.getPossibleValues().size() == 1) {
                    E uniqueValue = cell.getPossibleValues().iterator().next(); // Récupère l'unique valeur possible
                    cell.setValue(uniqueValue);
                    cell.getPossibleValues().clear(); // 🔥 Supprime toutes les autres valeurs possibles
                    modified = true;
                }
            }
        }
        return modified;
    }
}
