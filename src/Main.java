package src;

import src.mypackage.*;

/**
 * Classe principale de l'application.
 * Lance l'application en créant un menu textuel interactif.
 */
public class Main {

    /**
     * Point d'entrée de l'application.
     * Crée une instance de MenuTextuel et lance l'affichage du menu interactif.
     *
     * @param args Arguments de ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        // Création du menu textuel
        MenuTextuel<String> menu = new MenuTextuel<>();

        // Lancer le menu textuel interactif
        menu.AfficherMenuTextuel();
    }
}
