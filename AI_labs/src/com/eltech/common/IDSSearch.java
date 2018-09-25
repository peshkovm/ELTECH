package com.eltech.common;

import java.util.ArrayList;
import java.util.List;

public class IDSSearch<S, A> {

    private DLSSearch<S, A> obj = new DLSSearch<>();

    private List<Node<S, A>> list1 = new ArrayList<>();
    private List<S> list2 = new ArrayList<>();

    private int depth = 0;

    public Solution<S, A> search(Problem<S, A> problem) {

        Solution<S, A> solution = null;
        for (; depth < Integer.MAX_VALUE; depth++) {
            solution = obj.search(problem, depth);

            if (!(solution instanceof DLSSearch.Solution_Cutoff)) {
                list1.clear();
                list2.clear();
                list1.addAll(obj.fringe);
                list2.addAll(obj.closed);
                break;
            }
            obj.fringe.clear();
            obj.closed.clear();
            obj.fringe.addAll(list1);
            obj.closed.addAll(list2);
        }
        return solution;
    }
}