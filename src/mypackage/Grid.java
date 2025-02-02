package src.mypackage;

import src.mypackage.Region;

import java.util.ArrayList;
import java.util.List;


public abstract class Grid<E> {
    protected int size; // Taille de la grille
    protected List<Region<E>> regions; // Liste des régions

    // Constructeur
    public Grid(int size) {
        this.size = size;
        this.regions = new ArrayList<>();
    }

    // Méthodes abstraites pour gérer les valeurs des cellules
    public abstract boolean isValid(int y, int x, E value);
    public abstract E getValue(int y, int x);
    public abstract void setValue(int y, int x, E value);

    public List<Region<E>> getRegions() {
        return regions;
    }

    public void addRegion(Region<E> region) {
        this.regions.add(region);
    }
}
