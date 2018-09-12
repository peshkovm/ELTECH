package com.eltech.lab1;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;

public abstract class GraphSearch<S,A> {
    private Set<S> closed;
    private Queue<Node<S,A>> fringe;

    GraphSearch(Queue<Node<S,A>> fringe) {
        this.fringe=fringe;
    }

    public Node<S,A> search(S state) {
        fringe.add(new Node<S,A>(state,null,null,0));
        while(true) {
            if(fringe.isEmpty())
                return null;
            Node<S,A> node = fringe.poll();
            if(Goal_Test(node.getState()))
                return Solution(node);
            if(!closed.contains(node.getState())){
                closed.add(node.getState());
                fringe.addAll(Expand(node));
            }
        }
    }

    abstract protected Node<S,A> Solution(Node<S,A> node);

    protected abstract boolean Goal_Test(S node);

    abstract protected Collection<Node<S,A>> Expand(Node<S,A> node);
}
