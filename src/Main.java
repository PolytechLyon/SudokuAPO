package src;

import src.mypackage.Cell;
import src.mypackage.MultidokuGrid;
import src.mypackage.Region;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création d'une grille Multidoku avec taille 9x9
        MultidokuGrid grid = new MultidokuGrid(9);

        // Création de quelques régions (par exemple des régions de 3x3)
        Region region1 = new Region("Region 1");
        Region region2 = new Region("Region 2");

        // Création de cellules et ajout à des régions
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                // Ajout des cellules à la région
                Cell<Integer> cell = new Cell<>(null, y, x);
                if ((x < 3 && y < 3) || (x >= 3 && x < 6 && y >= 3 && y < 6)) {
                    region1.addCell(cell); // Par exemple, ajouter des cellules dans les premières régions
                } else {
                    region2.addCell(cell); // Par exemple, ajouter des cellules dans une autre région
                }
            }
        }

        // Ajouter les régions à la grille
        grid.addRegion(region1);
        grid.addRegion(region2);

        // Remplir quelques cellules avec des valeurs
        grid.setValue(0, 0, 5);  // Ajoute la valeur 5 à la cellule (0, 0)
        grid.setValue(1, 1, 3);  // Ajoute la valeur 3 à la cellule (1, 1)
        grid.setValue(2, 2, 8);  // Ajoute la valeur 8 à la cellule (2, 2)
        grid.setValue(3, 3, 4);  // Ajoute la valeur 4 à la cellule (3, 3)
        grid.setValue(4, 4, 7);  // Ajoute la valeur 7 à la cellule (4, 4)
        grid.setValue(5, 5, 6);  // Ajoute la valeur 6 à la cellule (5, 5)
        grid.setValue(6, 6, 9);  // Ajoute la valeur 9 à la cellule (6, 6)
        grid.setValue(7, 7, 2);  // Ajoute la valeur 2 à la cellule (7, 7)
        grid.setValue(8, 8, 1);  // Ajoute la valeur 1 à la cellule (8, 8)


        Integer value = grid.getValue(6,6 ); // Devrait renvoyer 5
        System.out.println("Valeur de la cellule (3, 3) : " + value);
        // Test d'affichage de la grille
        grid.displayGrid();
    }
}
