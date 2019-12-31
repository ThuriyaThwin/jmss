package counterbased;

import abstractImplementation.ClauseAbs;
import interfaces.CounterBasedClause;
import org.jetbrains.annotations.NotNull;
import formula.Clause;
import specificationCore.Solver;

import java.util.List;

public class ClauseCbs extends ClauseAbs implements CounterBasedClause {
    private int satisfiedLit = 0;
    private int unsatisfedLit = 0;

    public ClauseCbs(@NotNull Solver solver) {
        super(solver);
    }

    @Override
    public boolean isConflicting() {
        return (unsatisfedLit == size());

    }

    public boolean isUnit() {
        return (unsatisfedLit == size() - 1) && satisfiedLit == 0;
    }


    @Override
    public Integer getUnitLiteral() {
        if (isUnit()) {
            List<Integer> lits = state.F().getLiterals(id);
            for (int i = 0; i < lits.size(); i++) {
                if (state.F().getVariable(lits.get(i)).getAssignment() == -1) {
                    return lits.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void addLiteral(Integer lit) {

    }

    @Override
    public void incrementSatisfied() {
        satisfiedLit += 1;
    }

    @Override
    public void incrementUnsatisfied() {
        unsatisfedLit += 1;
        if (isUnit()) state.F().foundUnitClause(this);
        if (isConflicting()) state.F().foundConflictClause(this);
    }

    @Override
    public void decrementSatisfied() {
        satisfiedLit -= 1;
        if (isUnit()) state.F().foundUnitClause(this);
    }

    @Override
    public void decrementUnsatisfied() {
        unsatisfedLit -= 1;
        if (isUnit()) state.F().foundUnitClause(this);
    }

    @Override
    public void assertConflictingState() {
        unsatisfedLit = size();
        satisfiedLit = 0;
    }





    @Override
    public String toString() {
        String s = "";
        for (Integer literal : getLiterals()) {
            s += literal.toString() + " ";
        }

        return s;
    }
}
