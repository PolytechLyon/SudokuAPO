package src.mypackage;

import src.mypackage.Region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    public Set<E> getValuesForRow(int y) {
        Set<E> rowValues = new HashSet<>();

        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getY() == y && cell.getValue() != null) {
                    rowValues.add(cell.getValue());
                }
            }
        }

        return rowValues;
    }

    public int getSize() {
        return size;
    }

    /**
     * 🔥 Retourne les valeurs déjà présentes dans une colonne spécifique.
     */
    public Set<E> getValuesForColumn(int x) {
        Set<E> colValues = new HashSet<>();

        for (Region<E> region : regions) {
            for (Cell<E> cell : region.getCells()) {
                if (cell.getX() == x && cell.getValue() != null) {
                    colValues.add(cell.getValue());
                }
            }
        }

        return colValues;
    }
}
