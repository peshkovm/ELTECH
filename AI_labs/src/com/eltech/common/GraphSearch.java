package com.eltech.common;

import java.util.*;

public abstract class GraphSearch<S, A> {
    private Set<S> closed = new HashSet<S>();
    private Queue<Node<S, A>> fringe;

    public Solution<S, A> search(Problem<S, A> problem) {
        fringe.add(new Node<S, A>(problem.getInitialState()));
        while (true) {
            if (fringe.isEmpty())
                return new Solution<S, A>(); // empty
            Node<S, A> node = fringe.poll();
            if (problem.Goal_Test(node.getState()))
                return new Solution<S, A>(node);
            if (!closed.contains(node.getState())) {
                closed.add(node.getState());
                fringe.addAll(Expand(node, problem));
            }
        }
    }

    private Collection<Node<S, A>> Expand(Node<S, A> node, Problem<S, A> problem) {
        List<Node<S, A>> successors = new ArrayList<Node<S, A>>();

        for (Problem.Pair<A, S> pair : problem.Successor_Fn(node.getState())) {
            Node<S, A> s = new Node<S, A>(pair.getState(), pair.getAction(), node, node.getPath_Cost() + problem.Step_Cost(node.getState(), pair.getAction(), pair.getState()));
            successors.add(s);
        }
        return successors;
    }
}
