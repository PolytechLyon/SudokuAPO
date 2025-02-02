package src.mypackage;

/**
 * Enumération représentant les différents niveaux de difficulté pour un jeu de Sudoku.
 *
 * Chaque niveau de difficulté est associé à une description spécifique.
 */
public enum Difficulte {

    FACILE,
    MOYEN,
    DIFFICILE;

    /**
     * Retourne la description textuelle du niveau de difficulté.
     *
     * @return La description du niveau de difficulté.
     */
    public String getDescription() {
        return switch (this) {
            case FACILE -> "Niveau facile";
            case MOYEN -> "Niveau moyen";
            case DIFFICILE -> "Niveau difficile";
            default -> "Inconnu";
        };
    }
}
