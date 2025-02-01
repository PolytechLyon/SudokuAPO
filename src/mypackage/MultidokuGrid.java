package src.mypackage;

import src.mypackage.Cell;
import src.mypackage.Grid;

import java.util.ArrayList;
import java.util.List;

public class MultidokuGrid extends Grid {
    private List<Cell<?>> sharedCells; // Liste des cellules partagées entre plusieurs grilles

    // Constructeur
    public MultidokuGrid(int size) {
        super(size);
        this.sharedCells = new ArrayList<>();
    }

    // Ajouter une cellule partagée
    public void addSharedCell(Cell<?> cell) {
        this.sharedCells.add(cell);
    }

    // Vérifier si une cellule est partagée
    private boolean isSharedCell(int row, int col) {
        for (Cell<?> cell : sharedCells) {
            if (cell.getX() == col && cell.getY() == row) {
                return true;
            }
        }
        return false;
    }

    // Vérifier si une valeur peut être placée à une position donnée
    @Override
    public <E> boolean isValid(int row, int col, E value) {
        // Logique de validation pour vérifier que la valeur peut être placée à cette position
        return true; // Placeholder
    }

    // Obtenir la valeur d'une cellule
    @Override
    public <E> E getValue(int row, int col) {
        // Logique pour retourner la valeur de la cellule
        for (Region region : regions) {
            for (Cell<?> cell : region.getCells()) {
                if (cell.getX() == col && cell.getY() == row) {
                    return (E) cell.getValue();
                }
            }
        }
        return null; // Retourne null si aucune cellule trouvée
    }

    // Définir une valeur dans la grille
    @Override
    public <E> void setValue(int row, int col, E value) {
        for (Region region : regions) {
            for (Cell<?> cell : region.getCells()) {
                if (cell.getX() == col && cell.getY() == row) {
                    // On cast ici à Cell<E> pour éviter le problème de type générique
                    @SuppressWarnings("unchecked")
                    Cell<E> typedCell = (Cell<E>) cell;
                    typedCell.setValue(value);
                    return;
                }
            }
        }
    }

    // Affichage de la grille avec marquage des cellules partagées

    public void displayGrid() {
        System.out.println("Multidoku - Taille " + size + "x" + size + ":");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Object value = getValue(row, col); // Nous traitons ici le type comme un objet

                if (isSharedCell(row, col)) {
                    System.out.print("[" + (value != null ? value : " ") + "] "); // Affiche les cellules partagées entre []
                } else {
                    System.out.print(" " + (value != null ? value : " ") + "  "); // Affichage normal
                }

                if ((col + 1) % Math.sqrt(size) == 0 && col + 1 < size) {
                    System.out.print("| "); // Séparation des régions verticales
                }
            }
            System.out.println();

            if ((row + 1) % Math.sqrt(size) == 0 && row + 1 < size) {
                System.out.println("-".repeat(size * 3));
            }
        }
    }
}
