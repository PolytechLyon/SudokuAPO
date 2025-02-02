package src.mypackage;

public enum Difficulte {
    FACILE,    // Facile
    MOYEN,  // Moyen
    DIFFICILE;    // Difficile

    public String getDescription() {
        return switch (this) {
            case FACILE -> "Niveau facile";
            case MOYEN -> "Niveau moyen";
            case DIFFICILE -> "Niveau difficile";
            default -> "Inconnu";
        };
    }


}

