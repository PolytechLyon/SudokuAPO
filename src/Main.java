package src;

import src.mypackage.Cell;
import src.mypackage.Region;

public class Main {
    public static void main(String[] args) {
        // Création d'une région
        Region<Integer> region = new Region<>("R1");

        // Création de quelques cellules
        Cell<Integer> cell1 = new Cell<>(5, 0, 0);
        Cell<Integer> cell2 = new Cell<>(8, 1, 1);
        Cell<Integer> cell3 = new Cell<>(3, 2, 2);

        // Ajout des cellules à la région
        region.addCell(cell1);
        region.addCell(cell2);
        region.addCell(cell3);

        // Affichage des résultats
        System.out.println("Contenu de la région :");
        System.out.println(region);
    }
}

