import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // Création du Multidoku avec **deux grilles fusionnées par un bloc**
        MultiDokuGrid<Integer> multidoku = new MultiDokuGrid<>(9);

        // Définition d’un bloc 3x3 fusionné
        multidoku.addSharedBlock(3, 3); // Bloc central des deux grilles

        // Remplissage partiel des grilles
        multidoku.grids.get(0).setValue(3, 3, 5);
        multidoku.grids.get(0).setValue(3, 4, 3);
        multidoku.grids.get(0).setValue(4, 3, 6);
        multidoku.grids.get(0).setValue(4, 4, 7);

        multidoku.grids.get(1).setValue(5, 3, 2);

        multidoku.grids.get(0).setValue(4, 2, 10);


        multidoku.grids.get(1).setValue(4, 8, 99);
        multidoku.grids.get(0).setValue(4, 8, 66);



        // Synchronisation des blocs fusionnés
        multidoku.synchronizeSharedBlocks();

        // Lancement de l'interface graphique
        new MultiDokuGUI<>(multidoku);
    }
}
