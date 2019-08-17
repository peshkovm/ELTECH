package com.eltech.common;

public class DLSSearch<S, A> extends DFSSearch<S, A> {

    static class Solution_Cutoff<S, A> extends Solution<S, A> {
        @Override
        public String toString() {
            return "cutoff";
        }
    }

    public Solution<S, A> search(Problem<S, A> problem, int limit) {
        boolean cutoff_occured = false;
        fringe.add(new Node<S, A>(problem.getInitialState()));
        while (true) {
            max = fringe.size() > max ? fringe.size() : max;
            if (fringe.isEmpty())
                return cutoff_occured ? new Solution_Cutoff<S, A>() : new Solution<S, A>(); // empty
            Node<S, A> node = fringe.poll();
            timeComplexity++;
            if (problem.Goal_Test(node.getState()))
                return new Solution<S, A>(node, max, timeComplexity);
            if (node.getPath_Cost() == limit) {
                cutoff_occured = true;
            } else if (!closed.contains(node.getState())) {
                closed.add(node.getState());
                fringe.addAll(Expand(node, problem));
            }
        }
    }
}