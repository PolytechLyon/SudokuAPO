package src.mypackage;

import src.mypackage.Region;

import java.util.ArrayList;
import java.util.List;

public abstract class Grid {
    protected int size; // Taille de la grille
    protected List<Region> regions; // Liste des régions de la grille

    // Constructeur
    public Grid(int size) {
        this.size = size;
        this.regions = new ArrayList<>();
    }

    // Méthode abstraite pour valider une cellule (implémentée dans les sous-classes)
    public abstract <E> boolean isValid(int y, int x, E value);

    // Obtenir la valeur d'une cellule dans la grille
    public abstract <E> E getValue(int y, int x);

    // Méthode pour afficher la grille
    public void display() {
        // A voir si on souhaite afficher quelque chose dans la classe mère ou si c'est spécifique aux sous-classes
    }

    // Définir la valeur dans la grille
    public abstract <E> void setValue(int y, int x, E value);

    // Récupérer les régions associées à une cellule
    public List<Region> getRegionsForCell(int y, int x) {
        List<Region> result = new ArrayList<>();
        for (Region region : regions) {
            if (region.containsCell(y, x)) {
                result.add(region);
            }
        }
        return result;
    }

    // Getters et setters
    public int getSize() {
        return size;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void addRegion(Region region) {
        this.regions.add(region);
    }

    @Override
    public String toString() {
        return "Grid{" +
                "size=" + size +
                ", regions=" + regions +
                '}';
    }

    public void displayGrid() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Object value = getValue(y, x);
                System.out.print((value != null ? value : ".") + " ");

                if ((x + 1) % Math.sqrt(size) == 0 && x + 1 < size) {
                    System.out.print("| "); // Séparation des régions verticales
                }
            }
            System.out.println();

            if ((y + 1) % Math.sqrt(size) == 0 && y + 1 < size) {
                System.out.println("-".repeat(size * 2)); // Séparation des régions horizontales
            }
        }
    }

}
