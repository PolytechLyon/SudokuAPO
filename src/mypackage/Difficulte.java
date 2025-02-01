package src.mypackage;

public enum Difficulte {
    EASY,    // Facile
    MEDIUM,  // Moyen
    HARD;    // Difficile

    public String getDescription() {
        return switch (this) {
            case EASY -> "Niveau facile";
            case MEDIUM -> "Niveau moyen";
            case HARD -> "Niveau difficile";
            default -> "Inconnu";
        };
    }


}

