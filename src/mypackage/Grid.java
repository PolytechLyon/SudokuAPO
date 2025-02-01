package src.mypackage;

import java.util.List;
import java.util.ArrayList;

public abstract class Grid {
    protected int size; // Taille de la grille
    protected List<Region> regions; // Liste des régions de la grille

    // Constructeur
    public Grid(int size) {
        this.size = size;
        this.regions = new ArrayList<>();
    }

    // Méthode abstraite pour valider une cellule (implémentée dans les sous-classes)
    public abstract boolean isValid(int row, int col, int value);

    // Méthode abstraite pour résoudre la grille (implémentée dans les sous-classes)
    public abstract boolean solve();

    // Méthode pour afficher la grille
    public void display() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(getValue(row, col) + " ");
            }
            System.out.println();
        }
    }

    // Définir la valeur dans la grille
    public void setValue(int row, int col, int value) {
        // Implémentation spécifique pour affecter la valeur à la position (row, col)
    }

    // Obtenir la valeur d'une cellule dans la grille
    public abstract int getValue(int row, int col);

    // Récupérer les régions associées à une cellule
    public List<Region> getRegionsForCell(int row, int col) {
        List<Region> result = new ArrayList<>();
        for (Region region : regions) {
            if (region.containsCell(row, col)) {
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
}
