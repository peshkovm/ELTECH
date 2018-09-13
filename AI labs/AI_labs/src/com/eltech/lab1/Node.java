package com.eltech.lab1;

public class Node<S, A> {
    public Node(S state, A action, Node<S, A> parent, int path_cost) {
        State = state;
        Action = action;
        Parent = parent;
        Path_Cost = path_cost;
    }

    private S State;
    private A Action;
    private Node<S, A> Parent;
    private int Path_Cost;

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
}
