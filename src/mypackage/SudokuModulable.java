package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SudokuModulable<E> extends Grid {
    private List<Region> regions; // Liste des régions
    private Cell<E>[][] cells;    // Tableau des cellules génériques

    // Constructeur
    public SudokuModulable(int size) {
        super(size);
        this.regions = new ArrayList<>();
        this.cells = new Cell[size][size];

        // Initialisation des cellules avec la valeur null pour chaque cellule
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new Cell<>(null, y, x); // Valeur initiale null
            }
        }
    }

    @Override
    public <E> void setValue(int y, int x, E value) {
        if (y < 0 || y >= size || x < 0 || x >= size) {
            System.out.println("❌ Erreur : Coordonnées (" + y + ", " + x + ") hors limites !");
            return;
        }

        if (!isValid(y, x, value)) {
            System.out.println("❌ Impossible d'ajouter " + value + " en (" + y + ", " + x + "), conflit avec les règles !");
            return;
        }

        // Affectation de la valeur à la cellule
        cells[y][x].setValue(value);  // Pas besoin de cast ici car `cells[y][x]` est déjà un `Cell<E>`
    }

    @Override
    public E getValue(int y, int x) {
        return cells[y][x].getValue();  // Retourne la valeur sous forme générique
    }

    // Vérification de validité pour un type générique
    @Override
    public <E> boolean isValid(int y, int x, E value) {
        // Implémentation spécifique à la logique de validation
        // Exemple simple : ici, aucune cellule ne peut avoir la même valeur dans la même région.
        for (Region region : regions) {
            for (Cell<?> cell : region.getCells()) {
                if (cell.getValue() != null && cell.getValue().equals(value)) {
                    return false;  // Si la valeur est déjà présente dans la région, c'est invalide
                }
            }
        }
        return true;
    }
}
