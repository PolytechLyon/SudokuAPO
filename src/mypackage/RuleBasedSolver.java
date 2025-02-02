package src.mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class RuleBasedSolver<E> extends SolverStrategy<E>{
    private List<Rule> rules;

    public RuleBasedSolver(Logger logger)
    {
        super(logger);
        this.rules = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public boolean solve(Grid<E> grid, boolean AfficherLogs) {
        return AfficherLogs;
    }
}

