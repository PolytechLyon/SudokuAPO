package src.mypackage;

public abstract class Generator<E> {
    protected Difficulte difficulte;  // Difficulté du puzzle à générer

    // Constructeur prenant la difficulté comme paramètre
    public Generator(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

}

