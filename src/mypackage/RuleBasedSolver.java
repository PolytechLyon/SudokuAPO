package src.mypackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un solveur basé sur des règles pour résoudre une grille de Sudoku.
 * Ce solveur applique un ensemble de règles de manière itérative jusqu'à ce que la grille soit stable.
 *
 * @param <E> Le type des éléments dans la grille.
 */
public class RuleBasedSolver<E> extends SolverStrategy<E> {
    private List<Rule<E>> rules; // Liste des règles appliquées par le solveur

    /**
     * Constructeur pour initialiser le solveur avec un ensemble de règles par défaut.
     *
     * @param logger Le logger utilisé pour enregistrer les étapes du processus de résolution.
     */
    public RuleBasedSolver(Logger logger) {
        super(logger);
        this.rules = new ArrayList<>();
        // Ajouter les règles par défaut
        this.rules.add(new ExclusionDirecte<>(logger));
        this.rules.add(new UniciteForcee<>(logger));
    }

    /**
     * Ajoute une règle supplémentaire au solveur.
     *
     * @param rule La règle à ajouter.
     */
    public void addRule(Rule<E> rule) {
        rules.add(rule);
    }

    /**
     * Résout la grille en appliquant toutes les règles jusqu'à ce que la grille soit stable.
     * La grille est modifiée si une règle est appliquée. Le processus se répète jusqu'à ce qu'aucune règle
     * ne modifie davantage la grille.
     *
     * @param grid La grille à résoudre.
     * @param afficherLogs Un indicateur pour savoir si les logs doivent être affichés.
     * @return true si la grille a été résolue avec succès.
     */
    @Override
    public boolean solve(Grid<E> grid, boolean afficherLogs) {
        boolean modified;
        int iteration = 0;

        do {
            modified = false;
            iteration++;

            // Parcourir toutes les règles et les appliquer
            for (Rule<E> rule : rules) {
                boolean ruleModified = rule.applyRule(grid);
                modified |= ruleModified; // Met à jour si une règle a modifié la grille

                // Loguer l'application de la règle si demandé
                if (afficherLogs && ruleModified) {
                    logger.log("Iteration " + iteration + " : " + rule.getClass().getSimpleName() + " applied.");
                }
            }

        } while (modified); // Continue tant qu'au moins une règle a modifié la grille

        // Afficher les logs finaux si demandé
        if (afficherLogs) {
            logger.exportLogs();
        }

        return true; // La grille est considérée comme résolue
    }
}
