package com.eltech.common;

public class Node<S, A> {

    private S State;
    private A Action;
    private Node<S, A> Parent;
    private int Path_Cost;

    public Node(S state) {
        this(state,null,null,0);
    }

    public Node(S state, A action, Node<S, A> parent, int path_cost) {
        State = state;
        Action = action;
        Parent = parent;
        Path_Cost = path_cost;
    }

    public S getState() {
        return State;
    }

    public A getAction() {
        return Action;
    }

    public Node<S, A> getParent() {
        return Parent;
    }

    public int getPath_Cost() {
        return Path_Cost;
    }

    @Override
    public String toString() {
        return "State " + State;
    }
}
