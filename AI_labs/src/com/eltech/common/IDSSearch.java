package com.eltech.common;

public class IDSSearch<S, A> extends DLSSearch<S, A> {
    @Override
    public Solution<S, A> search(Problem<S, A> problem) {
        Solution<S, A> solution = null;
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            solution = search(problem, depth);
            if (!(solution instanceof Solution_Cutoff))
                break;
        }
        return solution;
    }
}