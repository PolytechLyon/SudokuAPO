package src.mypackage;

public enum Difficulte {
    EASY,    // Facile
    MEDIUM,  // Moyen
    HARD;    // Difficile

    public String getDescription() {
        switch (this) {
            case EASY:
                return "Niveau facile";
            case MEDIUM:
                return "Niveau moyen";
            case HARD:
                return "Niveau difficile";
            default:
                return "Inconnu";
        }
    }
}

