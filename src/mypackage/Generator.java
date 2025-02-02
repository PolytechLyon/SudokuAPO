package src.mypackage;

/**
 * Classe abstraite représentant un générateur de puzzle Sudoku.
 * Cette classe sert de base pour la création de puzzles avec une difficulté spécifique.
 * Elle est générique pour permettre l'utilisation de différents types de valeurs (par exemple, entiers ou caractères).
 *
 * @param <E> Le type des valeurs utilisées dans le puzzle (par exemple, Integer ou Character).
 */
public abstract class Generator<E> {

    /**
     * Difficulté du puzzle à générer.
     * La difficulté détermine le nombre d'indices et la complexité de la grille générée.
     */
    protected Difficulte difficulte;

    /**
     * Constructeur de la classe Generator.
     * Initialise la difficulté du puzzle.
     *
     * @param difficulte La difficulté du puzzle (FACILE, MOYEN, DIFFICILE).
     */
    public Generator(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

}
