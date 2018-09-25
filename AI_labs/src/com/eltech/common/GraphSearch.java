package com.eltech.common;

import java.util.*;

public abstract class GraphSearch<S, A> {
    protected Set<S> closed = new HashSet<S>();
    protected Queue<Node<S, A>> fringe;
    int max = 0;

    public GraphSearch(Queue<Node<S, A>> fringe) {
        this.fringe = fringe;
    }

    public Solution<S, A> search(Problem<S, A> problem) {
        fringe.add(new Node<S, A>(problem.getInitialState()));
        while (true) {
            max = fringe.size() > max ? fringe.size() : max;
            if (fringe.isEmpty())
                return new Solution<S, A>(); // empty
            Node<S, A> node = fringe.poll();
            if (problem.Goal_Test(node.getState()))
                return new Solution<S, A>(node, max);
            if (!closed.contains(node.getState())) {
                closed.add(node.getState());
                fringe.addAll(Expand(node, problem));
            }
        }
    }

    protected Collection<Node<S, A>> Expand(Node<S, A> node, Problem<S, A> problem) {
        List<Node<S, A>> successors = new ArrayList<Node<S, A>>();

        for (Problem.Pair<A, S> pair : problem.Successor_Fn(node.getState())) {
            Node<S, A> s = new Node<S, A>(pair.getState(), pair.getAction(), node, node.getPath_Cost() + problem.Step_Cost(node, pair.getAction(), pair.getState()));
            successors.add(s);
        }

        return successors;
    }
}