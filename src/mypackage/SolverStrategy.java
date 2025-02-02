package src.mypackage;
import java.util.ArrayList;
import java.util.List;

public abstract class SolverStrategy<E> {
    protected Logger logger;  // Le logger utilisé pour enregistrer les étapes

    // Constructeur qui reçoit un Logger
    public SolverStrategy(Logger logger) {
        this.logger = logger;
    }

    // Méthode de résolution à implémenter dans les sous-classes
    public abstract boolean solve(Grid<E> grid, boolean afficherLogs);

    // Retourne la liste des étapes enregistrées par le Logger
    public List<String> getSteps() {
        return logger.getLogs();
    }
}
