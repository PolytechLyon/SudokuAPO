package src;

import src.mypackage.*;

public class Main {
    public static void main(String[] args) {
        // 📌 Création du menu textuel
        MenuTextuel<String> menu = new MenuTextuel<>();

        // 📌 Lancer le menu textuel interactif
        menu.AfficherMenuTextuel();
    }
}
