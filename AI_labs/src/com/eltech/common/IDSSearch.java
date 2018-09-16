package com.eltech.common;

public class IDSSearch<S, A> extends DFSSearch<S, A> {

    private DLSSearch<S, A> obj = new DLSSearch<>();

    @Override
    public Solution<S, A> search(Problem<S, A> problem) {
        Solution<S, A> solution = null;
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            solution = obj.search(problem, depth);
            obj.fringe.clear();
            obj.closed.clear();
            if (!(solution instanceof DLSSearch.Solution_Cutoff))
                break;
        }
        return solution;
    }
}