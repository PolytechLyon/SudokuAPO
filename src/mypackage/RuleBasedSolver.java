package src.mypackage;

import java.util.ArrayList;
import java.util.List;

public class RuleBasedSolver<E> extends SolverStrategy<E> {
    private List<Rule<E>> rules; // ✅ Liste de règles génériques

    public RuleBasedSolver(Logger logger) {
        super(logger);
        this.rules = new ArrayList<>();
    }

    // ✅ Ajouter une règle
    public void addRule(Rule<E> rule) {
        rules.add(rule);
    }

    // ✅ Appliquer toutes les règles jusqu'à stabilisation
    @Override
    public boolean solve(Grid<E> grid, boolean afficherLogs) {
        boolean modified;
        int iteration = 0;

        do {
            modified = false;
            iteration++;

            // 📌 Parcourir toutes les règles et les appliquer
            for (Rule<E> rule : rules) {
                boolean ruleModified = rule.applyRule(grid);
                modified |= ruleModified; // Met à jour si une règle a modifié la grille

                // ✅ Loguer l'application de la règle si demandé
                if (afficherLogs && ruleModified) {
                    logger.log("🟢 Iteration " + iteration + " : " + rule.getClass().getSimpleName() + " applied.");
                }
            }

        } while (modified); // 🔄 Continue tant qu'au moins une règle a modifié la grille

        // ✅ Si demandé, afficher les logs finaux
        if (afficherLogs) {
            logger.exportLogs();
        }

        return true; // Vérifie si la grille est résolue
    }
}
