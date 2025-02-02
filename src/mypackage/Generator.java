package src.mypackage;

public abstract class Generator {
    protected Difficulte difficulte;  // Difficulté du puzzle à générer

    // Constructeur prenant la difficulté comme paramètre
    public Generator(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

    // Méthode abstraite qui génère le puzzle en fonction de la grille et de la difficulté
    public abstract Grid GeneratePuzzle(Grid grid, Difficulte difficulte);
}

