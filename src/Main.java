package src;

import src.mypackage.Cell;
import src.mypackage.Region;

public class Main {
    public static void main(String[] args) {
        // Créer des cellules avec différentes valeurs
        Cell<Integer> cell1 = new Cell<>(5, 0, 0);
        Cell<Integer> cell2 = new Cell<>(3, 0, 1);
        Cell<Integer> cell3 = new Cell<>(8, 1, 0);

        // Créer une région
        Region region = new Region("Région 1");

        // Ajouter des cellules à la région
        region.addCell(cell1);
        region.addCell(cell2);
        region.addCell(cell3);

        // Afficher les informations de la région
        System.out.println(region);

        // Vérifier si une cellule existe dans la région
        System.out.println("Contient cellule (0, 0): " + region.containsCell(0, 0)); // True
        System.out.println("Contient cellule (1, 1): " + region.containsCell(1, 1)); // False

        // Tester les cellules individuellement
        System.out.println("Cellule 1: " + cell1);
        System.out.println("Cellule 2: " + cell2);
        System.out.println("Cellule 3: " + cell3);
    }
}


