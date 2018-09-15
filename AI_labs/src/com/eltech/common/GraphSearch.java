package com.eltech.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public abstract class GraphSearch<S, A> {
    private Set<S> closed=new HashSet<S>();
    private Queue<Node<S, A>> fringe;

    public GraphSearch(Queue<Node<S, A>> fringe) {
        this.fringe = fringe;
    }

    public Node<S, A> search(S state) {
        fringe.add(new Node<S, A>(state, null, null, 0));
        while (true) {
            if (fringe.isEmpty())
                return null;
            Node<S, A> node = fringe.poll();
            if (Goal_Test(node.getState()))
                return Solution(node);
            if (!closed.contains(node.getState())) {
                closed.add(node.getState());
                fringe.addAll(Expand(node));
            }
        }
    }

    abstract protected Node<S, A> Solution(Node<S, A> node);

    protected abstract boolean Goal_Test(S state);

    abstract protected Collection<Node<S, A>> Expand(Node<S, A> node);
}
